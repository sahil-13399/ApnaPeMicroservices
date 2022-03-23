package com.apna.customer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

  final
  CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public void registerCustomer(@RequestBody CustomerRequest customerRequest) {
    log.info("New Customer");
    customerService.registerCustomer(customerRequest);
  }

}
