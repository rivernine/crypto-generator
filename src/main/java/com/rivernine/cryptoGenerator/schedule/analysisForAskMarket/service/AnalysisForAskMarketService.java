package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.service;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForAskMarketService {
  private final CryptoRepository cryptoRepository;

  @Value("${testParameter.analysisForAskReturn}")
  private Boolean analysisForAskReturn;

  public Boolean analysis() {
    return analysisForAskReturn;
  }

}
