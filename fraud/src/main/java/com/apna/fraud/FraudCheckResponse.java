package com.apna.fraud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class FraudCheckResponse {

  private boolean isFraudulentCustomer;

  public FraudCheckResponse(boolean isFraudulentCustomer) {
    this.isFraudulentCustomer = isFraudulentCustomer;
  }

  public boolean isFraudulentCustomer() {
    return isFraudulentCustomer;
  }
}
