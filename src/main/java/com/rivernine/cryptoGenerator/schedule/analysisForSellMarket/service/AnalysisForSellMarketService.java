package com.rivernine.cryptoGenerator.schedule.analysisForSellMarket.service;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForSellMarketService {
  private final CryptoRepository cryptoRepository;

  @Value("${testParameter.analysisForSellReturn}")
  private Boolean analysisForSellReturn;

  public Boolean analysis() {
    return analysisForSellReturn;
  }

}
