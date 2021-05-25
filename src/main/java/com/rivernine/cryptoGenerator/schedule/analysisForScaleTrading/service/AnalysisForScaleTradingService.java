package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
  
  public List<CandleDto> getRecentCandles(String minutes, int count) {
    List<CandleDto> result = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    Map<LocalDateTime, CandleDto> candleDtoMap = scaleTradeStatusProperties.getCandleDtoMap();
    for( int i = 0; i < count; i++ ) {
      if(candleDtoMap.containsKey(now)) {
        log.info("getRecentCandles " + candleDtoMap.get(now).toString());
        result.add(candleDtoMap.get(now));
      }
      now = now.minusMinutes(Long.parseLong(minutes));
    }

    return result;
  }

  public Boolean analysisCandles(List<CandleDto> candles) {
    Boolean result = true;
    if(candles.size() < 2) {
      log.info("candles size is " + Integer.toString(candles.size()));
      result = false;
    } else {
      for(CandleDto candle: candles) {
        if(candle.getFlag() != -1)
          result = false;
      }
    }
    
    return result;
  }
}
