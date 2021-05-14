package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleAlgorithm {
  
  @Value("${testParameter.analysisForBidReturn}")
  private Boolean analysisForBidReturn;

  public Boolean simpleAlgorithm() {
    return analysisForBidReturn;
  }
}
