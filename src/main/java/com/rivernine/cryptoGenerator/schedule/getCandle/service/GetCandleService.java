package com.rivernine.cryptoGenerator.schedule.getCandle.service;

import java.time.LocalDateTime;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetCandleService {

  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final CryptoApi cryptoApi;

  public void getCandles(String market, String minutes, String count) {
    JsonObject[] candles = cryptoApi.getCandles(market, minutes, count);
    for(JsonObject candle: candles){
      String candleDateTimeKst = candle.get("candle_date_time_kst").getAsString();
      Double openingPrice = candle.get("opening_price").getAsDouble();
      Double tradePrice = candle.get("trade_price").getAsDouble();
      int flag;
      if(openingPrice > tradePrice)
        flag = 1;
      else if(openingPrice == tradePrice)
        flag = 0;
      else
        flag = -1;

      CandleDto candleDto = CandleDto.builder()
                              .market(candle.get("market").getAsString())
                              .candleDateTime(candleDateTimeKst)
                              .openingPrice(openingPrice)
                              .highPrice(candle.get("high_price").getAsDouble())
                              .lowPrice(candle.get("low_price").getAsDouble())
                              .tradePrice(tradePrice)
                              .flag(flag)
                              .build();

      scaleTradeStatusProperties.addCandlesDtoMap(LocalDateTime.parse(candleDateTimeKst), candleDto);
    }
  }

  public void printCandles() {
    scaleTradeStatusProperties.printCandlesDtoMap();
  }
}
