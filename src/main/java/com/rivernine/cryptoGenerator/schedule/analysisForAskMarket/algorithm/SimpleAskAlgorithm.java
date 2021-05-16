package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleAskAlgorithm {
  
  @Value("${testParameter.analysisForAskReturn}")
  private Boolean analysisForAskReturn;

  public Boolean algorithm() {
    return analysisForAskReturn;
  }
}
