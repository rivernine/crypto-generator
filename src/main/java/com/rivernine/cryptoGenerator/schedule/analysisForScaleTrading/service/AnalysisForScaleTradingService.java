package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalysisForScaleTradingService {
  
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  
  public List<CandleDto> getRecentCandles(int count) {
    List<CandleDto> result = new ArrayList<>();

    Map<LocalDateTime, CandleDto> candleDtoMap = scaleTradeStatusProperties.getCandleDtoMap();
    List<LocalDateTime> keys = new ArrayList<>(candleDtoMap.keySet());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    keys.sort((s1, s2) -> s2.format(formatter).compareTo(s1.format(formatter)));
  
    if(keys.size() >= count){
      int addCount = 0;
      for(LocalDateTime key: keys) {
        if(addCount == 2)
          break;
        log.info("getRecentCandles " + candleDtoMap.get(key).toString());
        result.add(candleDtoMap.get(key));
        addCount++;
      }
    } else {
      log.info("candles size is " + Integer.toString(candleDtoMap.size()));
    }
    
    return result;
  }

  public Boolean analysisCandles(List<CandleDto> candles) {
    Boolean result = true;
    for(CandleDto candle: candles) {
      if(candle.getFlag() != -1)
        result = false;
    }
    
    return result;
  }

  public String getAskPrice(OrdersChanceDto ordersChanceDtoForAsk) {
    String usedBalance = scaleTradeStatusProperties.getUsedBalance();
    String usedFee = scaleTradeStatusProperties.getUsedFee();
    String totalUsedBalance = Double.toString(Double.parseDouble(usedBalance) + Double.parseDouble(usedFee));
    String coinBalance = ordersChanceDtoForAsk.getBalance();

    Double targetMargin = 0.002;
    Double targetBalance = Double.parseDouble(totalUsedBalance) * (1 + targetMargin);
    String targetPrice = Double.toString(targetBalance / Double.parseDouble(coinBalance));
    String targetPriceAbleOrder = targetPrice = changeAbleOrderPrice(targetPrice);

    log.info("totalUsedBalance : coinBalance : targetPrice : targetPriceAbleOrder");
    log.info(totalUsedBalance + " : " + coinBalance + " : " + targetPrice + " : " + targetPriceAbleOrder);

    return targetPriceAbleOrder;
  }

  public String changeAbleOrderPrice(String price) {
    Double result;
    Double priceD = Double.parseDouble(price);
    Double orderUnit; 
    if(priceD.compareTo(0.0) != -1 && priceD.compareTo(10.0) == -1) {
      orderUnit = 0.01;
    } else if(priceD.compareTo(10.0) != -1 && priceD.compareTo(100.0) == -1) {
      orderUnit = 0.1;
    } else if(priceD.compareTo(100.0) != -1 && priceD.compareTo(1000.0) == -1) {
      orderUnit = 1.0;
    } else if(priceD.compareTo(1000.0) != -1 && priceD.compareTo(10000.0) == -1) {
      orderUnit = 5.0;
    } else if(priceD.compareTo(10000.0) != -1 && priceD.compareTo(100000.0) == -1) {
      orderUnit = 10.0;
    } else if(priceD.compareTo(100000.0) != -1 && priceD.compareTo(500000.0) == -1) {
      orderUnit = 50.0;
    } else if(priceD.compareTo(500000.0) != -1 && priceD.compareTo(1000000.0) == -1) {
      orderUnit = 100.0;
    } else if(priceD.compareTo(1000000.0) != -1 && priceD.compareTo(2000000.0) == -1) {
      orderUnit = 500.0;
    } else {
      orderUnit = 1000.0;
    }

    Double mod = priceD % orderUnit;
    if(mod.compareTo(0.0) == 0) {
      result = priceD;
    } else {
      Double tmp = priceD / orderUnit;
      result = tmp.intValue() * orderUnit + orderUnit;
    }

    log.info("priceD : orderUnit : result");
    log.info(priceD.toString() + " : " + orderUnit.toString() + " : " + result.toString());

    return Double.toString(result);
  }
}
