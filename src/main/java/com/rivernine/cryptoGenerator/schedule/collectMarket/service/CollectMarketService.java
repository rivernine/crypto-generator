package com.rivernine.cryptoGenerator.schedule.collectMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CollectMarketSaveDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CollectMarketService {

  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final CryptoRepository cryptoRepository;
  private final CryptoApi cryptoApi;
  
  public CollectMarketSaveDto getMarket(String market) {
    JsonObject jsonObject = cryptoApi.getMarket(market);
    return CollectMarketSaveDto.builder()
              .market(jsonObject.get("market").getAsString())
              .tradeDate(jsonObject.get("trade_date_kst").getAsString()+jsonObject.get("trade_time_kst").getAsString())
              .price(jsonObject.get("trade_price").getAsDouble())
              .tradeVolume(jsonObject.get("trade_volume").getAsDouble())
              .accTradeVolume(jsonObject.get("acc_trade_volume").getAsDouble())
              .accTradeVolume24h(jsonObject.get("acc_trade_volume_24h").getAsDouble())
              .build();
  }

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

      scaleTradeStatusProperties.addCandlesDtoMap(candleDateTimeKst, candleDto);
      // log.info(candleDto.toString());
    }
  }

  public void printCandles() {
    scaleTradeStatusProperties.printCandlesDtoMap();
  }

  @Transactional
  public void save(CollectMarketSaveDto collectMarketSaveDto){
    cryptoRepository.save(collectMarketSaveDto.toEntity());
  }

  @Transactional
  public List<Crypto> findAll() {
    return cryptoRepository.findAll();
  }
}
