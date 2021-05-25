package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalysisForScaleTradingService {
  
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final CryptoApi cryptoApi;
  
  public void getRecentCandles(String minutes, int count) {
    List<CandleDto> result;
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    Map<LocalDateTime, CandleDto> candleDtoMap = scaleTradeStatusProperties.getCandleDtoMap();
    for( int i = 0; i < count; i++ ) {
      if(candleDtoMap.containsKey(now)) {
        log.info("getRecentCandles " + candleDtoMap.get(now).toString());
      }
      now = now.minusMinutes(Long.parseLong(minutes));
    }
  }
}
