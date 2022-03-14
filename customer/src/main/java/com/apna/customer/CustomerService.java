package com.apna.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {


  public void registerCustomer(CustomerRequest customerRequest) {
    Customer customer = Customer.builder()
        .firstName(customerRequest.getFirstName())
        .lastName(customerRequest.getLastName())
        .email(customerRequest.getEmail())
        .build();

    //todo : check if email is valid
    //todo : check if email is not taken
    //todo : store customer in db
  }
}
