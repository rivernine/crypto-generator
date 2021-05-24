package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalysisForScaleTradingService {
  
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final CryptoApi cryptoApi;
  
}
