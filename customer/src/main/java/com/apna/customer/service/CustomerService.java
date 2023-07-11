package com.apna.customer.service;

import com.apna.clients.fraud.FraudCheckResponse;
import com.apna.clients.fraud.FraudClient;
import com.apna.clients.notification.NotificationClient;
import com.apna.clients.notification.NotificationRequest;
import com.apna.customer.dto.Customer;
import com.apna.customer.exchanges.CustomerDepositResponse;
import com.apna.customer.exchanges.CustomerRequest;
import com.apna.customer.exchanges.TransactionDetailsResponse;
import com.apna.customer.exchanges.TransactionRequest;
import com.apna.customer.repository.CustomerRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final NotificationClient notificationClient;
  private final FraudClient fraudClient;
  private final RabbitMqSender rabbitMqSender;

  public void registerCustomer(CustomerRequest request) {
    Customer customer = Customer.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .balance(0).build();
    customerRepository.saveAndFlush(customer);
    FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId(), request.getEmail());

    if (fraudCheckResponse.isFraudulentCustomer()) {
      throw new IllegalStateException("fraudster");
    }

  }

  public Integer getBalance(Integer customerId) {
    Customer customer = customerRepository.getById(customerId);
    return customer.getBalance();
  }

  public CustomerDepositResponse updateBalance(Integer customerId, Integer amount) {
    Customer customer = customerRepository.getById(customerId);
    customer.setBalance(customer.getBalance() + amount);
    customerRepository.save(customer);
    return new CustomerDepositResponse(customerId, customer.getBalance(), LocalDateTime.now());
  }

  public TransactionDetailsResponse makeTransaction(TransactionRequest transactionRequest) {

    Customer sender = customerRepository.getById(transactionRequest.getSenderId());
    Customer receiver = customerRepository.getById(transactionRequest.getReceiverId());
    //TransactionDetailsResponse transactionDetailsResponse = null;
    if (validateBalance(sender, transactionRequest.getTransactionAmount()) == true) {
      sender.setBalance(sender.getBalance() - transactionRequest.getTransactionAmount());
      receiver.setBalance(receiver.getBalance() + transactionRequest.getTransactionAmount());
    }

    customerRepository.save(sender);
    customerRepository.save(receiver);

    rabbitMqSender.send(new NotificationRequest(
        receiver.getId(),
        receiver.getFirstName() + " " + receiver.getLastName(),
        sender.getFirstName() + " " + sender.getLastName(),
        String.format("Amount %d has been sent from %s to %s",
            transactionRequest.getTransactionAmount(), sender.getFirstName(),
            receiver.getFirstName()),
        transactionRequest.getTransactionAmount()
    ));

    return new TransactionDetailsResponse(sender.getId(), receiver.getId(),
        transactionRequest.getTransactionAmount(),
        sender.getBalance(), LocalDateTime.now());
  }

  private boolean validateBalance(Customer sender, Integer amount) {
    //Check sender balance and amount
    return sender.getBalance() >= amount;
  }
}
