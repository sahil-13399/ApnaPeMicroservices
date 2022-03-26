package com.apna.customer.exchanges;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsResponse {

  private Integer senderId;
  private Integer receiverId;
  private Integer transactionAmount;
  private Integer senderBalance;
  private LocalDateTime timestamp;

}
