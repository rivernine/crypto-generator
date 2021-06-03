package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading;

import java.util.List;

import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service.AnalysisForScaleTradingService;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AnalysisForScaleTradingJobConfiguration {
  
  private final AnalysisForScaleTradingService analysisForScaleTradingService;

  public List<CandleDto> getRecentCandlesJob(int count) {
    return analysisForScaleTradingService.getRecentCandles(count);
  }

  public CandleDto getLastCandleJob() {
    return analysisForScaleTradingService.getRecentCandles(1).get(0);
  }

  public Boolean analysisCandlesJob(List<CandleDto> candles, int count) {
    return analysisForScaleTradingService.analysisCandles(candles, count);
  }

  public Boolean compareCurPriceLastBidTradePrice(Double curPrice, Double lastBidTradePrice) {
    return analysisForScaleTradingService.compareCurPriceLastBidTradePrice(curPrice, lastBidTradePrice);
  }

  public String getAskPriceJob(OrdersChanceDto ordersChanceDtoForAsk) {
    return analysisForScaleTradingService.getAskPrice(ordersChanceDtoForAsk);
  }
}
