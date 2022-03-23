package com.apna.customer;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final RestTemplate restTemplate;

  public void registerCustomer(CustomerRequest request) {
    Customer customer = Customer.builder()
        .firstName(request.getFirstName()).build();
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
  }
