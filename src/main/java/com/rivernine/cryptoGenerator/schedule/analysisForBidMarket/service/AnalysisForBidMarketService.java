package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.service;

import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.algorithm.SimpleAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForBidMarketService {

  private final CryptoRepository cryptoRepository;
  private final UpbitApi upbitApi;

  @Value("${testParameter.analysisForBidReturn}")
  private Boolean analysisForBidReturn;

  private SimpleAlgorithm simpleAlgorithm;

  public Boolean analysis() {
    return simpleAlgorithm.simpleAlgorithm();
  }

  public void bid() {
    try {
    upbitApi.bidMarket();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

}
