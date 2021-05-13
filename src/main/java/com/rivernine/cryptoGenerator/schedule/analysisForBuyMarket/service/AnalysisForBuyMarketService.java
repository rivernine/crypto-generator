package com.rivernine.cryptoGenerator.schedule.analysisForBuyMarket.service;

import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.analysisForBuyMarket.algorithm.SimpleAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForBuyMarketService {

  private final CryptoRepository cryptoRepository;
  private final UpbitApi upbitApi;

  @Value("${testParameter.analysisForBuyReturn}")
  private Boolean analysisForBuyReturn;

  private SimpleAlgorithm simpleAlgorithm;

  public Boolean analysis() {
    return simpleAlgorithm.simpleAlgorithm();
  }

  public void buy() {
    upbitApi.buyMarket();
  }

}
