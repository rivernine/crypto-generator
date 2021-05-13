package com.rivernine.cryptoGenerator.schedule.analysisForBuyMarket.service;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForBuyMarketService {
  private final CryptoRepository cryptoRepository;

  @Value("${testParameter.analysisForBuyReturn}")
  private Boolean analysisForBuyReturn;

  public Boolean analysis() {
    return analysisForBuyReturn;
  }

}
