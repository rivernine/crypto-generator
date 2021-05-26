package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading;

import java.util.List;

import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service.AnalysisForScaleTradingService;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AnalysisForScaleTradingJobConfiguration {
  
  private final AnalysisForScaleTradingService analysisForScaleTradingService;

  public List<CandleDto> getRecentCandlesJob(String minutes, int count){
    return analysisForScaleTradingService.getRecentCandles(minutes, count);
  }

  public Boolean analysisCandlesJob(List<CandleDto> candles) {
    return analysisForScaleTradingService.analysisCandles(candles);
  }

  public String getAskPriceJob(OrdersChanceDto ordersChanceDtoForAsk) {
    return analysisForScaleTradingService.getAskPrice(ordersChanceDtoForAsk);
  }
}
