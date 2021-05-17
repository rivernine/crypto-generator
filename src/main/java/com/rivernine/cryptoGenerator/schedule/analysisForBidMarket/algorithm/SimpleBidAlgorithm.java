package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleBidAlgorithm {
  
  @Value("${testParameter.skipBidAlgorithm}")
  private Boolean skipBidAlgorithm;

  public Boolean algorithm() {
    if(skipBidAlgorithm)
      return true;
    return false;
  }
}
