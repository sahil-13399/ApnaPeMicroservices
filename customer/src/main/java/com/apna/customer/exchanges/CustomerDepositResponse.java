package com.apna.customer.exchanges;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDepositResponse {

  private Integer customerId;
  private Integer newBalance;
  private LocalDateTime timestamp;

}
