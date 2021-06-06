package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalysisForScaleTradingService {
  
  @Value("${upbit.targetMargin}")	
  private Double targetMargin;  
  @Value("${upbit.scaleTradeRate}")	
  private Double scaleTradeRate;
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;

  public List<CandleDto> getRecentCandles(String market, int count) {
    List<CandleDto> result = new ArrayList<>();
    Map<LocalDateTime, CandleDto> candleDtoMap = scaleTradeStatusProperties.getCandleDtoMaps().get(market);
    List<LocalDateTime> keys = new ArrayList<>(candleDtoMap.keySet());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    keys.sort((s1, s2) -> s2.format(formatter).compareTo(s1.format(formatter)));
  
    if(keys.size() >= count){
      int addCount = 0;
      for(LocalDateTime key: keys) {
        if(addCount == count)
          break;
        result.add(candleDtoMap.get(key));
        addCount++;
      }
    } else {
      log.info("getRecentCandles: candles size is " + Integer.toString(candleDtoMap.size()));
    }
    
    return result;
  }

  public Boolean analysisCandles(List<CandleDto> candles, int count) {
    Boolean result = false;
    if(candles.size() < count) {
      log.info("analysisCandles: candles size is " + Integer.toString(candles.size()));
      result = false;
    } else {
      if(candles.get(0).getFlag() != -1)
        return false;
      int longBlueCandleCount = 0;
      // for(CandleDto candle: candles) {
      //   log.info(candle.toString());
      // }
      Double minPrice = 100000000.00000000;
      Double maxPrice = 0.0;

      for(CandleDto candle: candles) {
        Double thresholdPrice = candle.getOpeningPrice() * (1 - scaleTradeRate);
        log.info(candle.toString() + " | threshold(scaleTradeRate) : " + thresholdPrice);
        
        if(candle.getFlag() == 1) {
          log.info("getFlag == 1. Return false");
          return false;
        }
        maxPrice = Double.max(maxPrice, candle.getOpeningPrice());
        minPrice = Double.min(minPrice, candle.getTradePrice());
        
        Double thresholdPrice2 = maxPrice * (1 - (targetMargin * 2));
        log.info("threshold(targetMargin * 2) : " + thresholdPrice2);
        if(minPrice.compareTo(thresholdPrice2) != 1) {
          return true;
        } 

        if(candle.getTradePrice().compareTo(thresholdPrice) != 1) {
          longBlueCandleCount += 1;
        }
        if(longBlueCandleCount >= 2) {
          return true;
        }
      }
      
    }

    return result;
  }

  public String getAskPrice(OrdersChanceDto ordersChanceDtoForAsk) {
    Double feeRate = 0.0005;
    String usedBalance = scaleTradeStatusProperties.getUsedBalance();
    String usedFee = scaleTradeStatusProperties.getUsedFee();
    String totalUsedBalance = Double.toString(Double.parseDouble(usedBalance) + Double.parseDouble(usedFee));
    String coinBalance = ordersChanceDtoForAsk.getBalance();

    Double targetBalance = Double.parseDouble(totalUsedBalance) * (1 + targetMargin + feeRate);
    String targetPrice = Double.toString(targetBalance / Double.parseDouble(coinBalance));
    String targetPriceAbleOrder = changeAbleOrderPrice(targetPrice);

    log.info("usedBalance : usedFee : totalUsedBalance");
    log.info(usedBalance + " : " + usedFee + " : " + totalUsedBalance);
    log.info("coinBalance : targetPrice : targetPriceAbleOrder");
    log.info(coinBalance + " : " + targetPrice + " : " + targetPriceAbleOrder);

    return targetPriceAbleOrder;
  }

  public Boolean compareCurPriceLastBidTradePrice(Double curPrice, Double lastBidTradePrice) {
    Boolean result;
    Double thresholdPrice = lastBidTradePrice * (1 - scaleTradeRate);
    log.info("curPrice : lastBidTradePrice : thresholdPrice");
    log.info(curPrice.toString() + " : " + lastBidTradePrice.toString() + " : " + thresholdPrice.toString());
    if(curPrice.compareTo(thresholdPrice) == -1) {
      result = true;
    } else {
      result = false;
    }

    return result;
  }

  public Double getLossCutPrice(String avgBuyPrice) {
    return Double.parseDouble(avgBuyPrice) * (1 - (targetMargin * 5.0));
  }

  public String changeAbleOrderPrice(String price) {
    Double result;
    Double priceD = Double.parseDouble(price);
    Double orderUnit = getOrderUnit(priceD);
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

  public Double getOrderUnit(Double price) {
    Double orderUnit; 
    if(price.compareTo(0.0) != -1 && price.compareTo(10.0) == -1) {
      orderUnit = 0.01;
    } else if(price.compareTo(10.0) != -1 && price.compareTo(100.0) == -1) {
      orderUnit = 0.1;
    } else if(price.compareTo(100.0) != -1 && price.compareTo(1000.0) == -1) {
      orderUnit = 1.0;
    } else if(price.compareTo(1000.0) != -1 && price.compareTo(10000.0) == -1) {
      orderUnit = 5.0;
    } else if(price.compareTo(10000.0) != -1 && price.compareTo(100000.0) == -1) {
      orderUnit = 10.0;
    } else if(price.compareTo(100000.0) != -1 && price.compareTo(500000.0) == -1) {
      orderUnit = 50.0;
    } else if(price.compareTo(500000.0) != -1 && price.compareTo(1000000.0) == -1) {
      orderUnit = 100.0;
    } else if(price.compareTo(1000000.0) != -1 && price.compareTo(2000000.0) == -1) {
      orderUnit = 500.0;
    } else {
      orderUnit = 1000.0;
    }

    return orderUnit;
  }
}
