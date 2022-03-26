package com.apna.customer.exchanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class FraudCheckResponse {

  @JsonProperty("isFraudulentCustomer")
  private boolean isFraudulentCustomer;

  public FraudCheckResponse(boolean isFraudulentCustomer) {
    this.isFraudulentCustomer = isFraudulentCustomer;
  }

  public boolean isFraudulentCustomer() {
    return isFraudulentCustomer;
  }
}