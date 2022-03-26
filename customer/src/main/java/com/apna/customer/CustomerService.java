package com.apna.customer;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final RestTemplate restTemplate;

  public void registerCustomer(CustomerRequest request) {
    Customer customer = Customer.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .balance(0).build();
    // todo: check if email valid
    // todo: check if email not taken
    customerRepository.saveAndFlush(customer);
    // todo: check if fraudster
    FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
        "http://localhost:8081/api/v1/fraud-check/{customerId}",
        FraudCheckResponse.class,
        customer.getId()
    );

    if (fraudCheckResponse.isFraudulentCustomer()) {
      throw new IllegalStateException("fraudster");
    }

    // todo: send notification

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
    TransactionDetailsResponse transactionDetailsResponse = null;
    if (validateBalance(sender, transactionRequest.getTransactionAmount()) == true) {
      sender.setBalance(sender.getBalance() - transactionRequest.getTransactionAmount());
      receiver.setBalance(receiver.getBalance() + transactionRequest.getTransactionAmount());
    }

    customerRepository.save(sender);
    customerRepository.save(receiver);

    return new TransactionDetailsResponse(sender.getId(), receiver.getId(),
        transactionRequest.getTransactionAmount(),
        sender.getBalance(), LocalDateTime.now());
  }

  private boolean validateBalance(Customer sender, Integer amount) {
    //Check sender balance and amount
    return sender.getBalance() >= amount;
  }
}
