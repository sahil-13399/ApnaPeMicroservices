package com.apna.customer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

  private Integer senderId;
  private Integer receiverId;
  private Integer transactionAmount;
}
