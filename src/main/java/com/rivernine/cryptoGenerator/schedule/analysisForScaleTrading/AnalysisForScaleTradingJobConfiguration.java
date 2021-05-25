package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading;

import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service.AnalysisForScaleTradingService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AnalysisForScaleTradingJobConfiguration {
  
  private final AnalysisForScaleTradingService analysisForScaleTradingService;

  public void getRecentCandlesJob(String minutes, int count){
    analysisForScaleTradingService.getRecentCandles(minutes, count);
  }

}
