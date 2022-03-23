package fraud;

public class FraudCheckResponse {

  private boolean isFraudulentCustomer;

  public FraudCheckResponse(boolean isFraudulentCustomer) {
    this.isFraudulentCustomer = isFraudulentCustomer;
  }
}
