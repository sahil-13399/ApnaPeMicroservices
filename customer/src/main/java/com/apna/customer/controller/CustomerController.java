package com.apna.customer.controller;


import com.apna.customer.exchanges.CustomerDepositResponse;
import com.apna.customer.exchanges.CustomerRequest;
import com.apna.customer.service.CustomerService;
import com.apna.customer.exchanges.TransactionDetailsResponse;
import com.apna.customer.exchanges.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/{customerId}")
  public ResponseEntity<Integer> getBalanceCustomer(
      @PathVariable("customerId") Integer customerId) {
    log.info("Checking customer balance");
    Integer totalBalance = customerService.getBalance(customerId);

    return new ResponseEntity<>(totalBalance, HttpStatus.OK);
  }

  @PutMapping("/deposit/{customerId}")
  public ResponseEntity<CustomerDepositResponse> depositAmount(@RequestParam Integer amount,
                                                               @PathVariable(name = "customerId")
                                                                   Integer customerId) {
    log.info("Updating customer balance");
    CustomerDepositResponse customerDepositResponse =
        customerService.updateBalance(customerId, amount);
    return new ResponseEntity<>(customerDepositResponse, HttpStatus.OK);
  }

  @PostMapping("/transaction")
  public ResponseEntity<TransactionDetailsResponse> makeTransaction(
      @RequestBody TransactionRequest transactionRequest) {
    TransactionDetailsResponse transactionDetailsResponse =
        customerService.makeTransaction(transactionRequest);
    return new ResponseEntity<>(transactionDetailsResponse, HttpStatus.ACCEPTED);
  }

}
