package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.algorithm;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDtoForAsk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleAskAlgorithm {
  
  @Value("${testParameter.skipAskAlgorithm}")
  private Boolean skipAskAlgorithm;

  public Boolean algorithm(Double currentPrice, StatusProperties statusProperties) {    
    OrdersChanceDtoForAsk ordersChanceDtoForAsk = statusProperties.getOrdersChanceDtoForAsk();

    Double tradePaidFee = Double.parseDouble(ordersChanceDtoForAsk.getPaidFee());
    Double tradePrice = Double.parseDouble(ordersChanceDtoForAsk.getTradePrice());
    Double tradeVolume = Double.parseDouble(ordersChanceDtoForAsk.getTradeVolume());
    Double tradeFunds =  Double.parseDouble(ordersChanceDtoForAsk.getTradeFunds());

    Double expectedReturn = currentPrice * tradeVolume;
    Double threshold = tradeFunds + tradePaidFee;
    Double targetPrice = threshold / tradeVolume;

    log.info("My average buy price: " + Double.toString(tradePrice));
    log.info("Current price: " + Double.toString(currentPrice));
    log.info("Target price: " + Double.toString(targetPrice));
    
    if(skipAskAlgorithm)
      return true;
      
    if( currentPrice >= targetPrice ){
      return true;
    } else {
      return false;
    }
  }
}
