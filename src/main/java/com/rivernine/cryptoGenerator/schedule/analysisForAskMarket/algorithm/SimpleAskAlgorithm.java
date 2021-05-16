package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.algorithm;

import com.rivernine.cryptoGenerator.config.StatusProperties;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleAskAlgorithm {
  
  public Boolean algorithm(Double currentPrice, StatusProperties statusProperties) {
    // Market balance
    Double balance = Double.parseDouble(statusProperties.getOrdersChanceDtoForAsk().getBalance());
    Double avgBuyPrice = Double.parseDouble(statusProperties.getOrdersChanceDtoForAsk().getAvg_buy_price());
    // KRW
    Double usedBalance = statusProperties.getUsedBalance();
    Double askBalance = balance * currentPrice;
    Double askFee = askBalance * 0.0005;
    Double bidFee = usedBalance * 0.0005;

    Double targetBalance = usedBalance + askFee + bidFee;
    Double targetPrice = targetBalance / balance;
    log.info("My average buy price: " + Double.toString(avgBuyPrice));
    log.info("Target price: " + Double.toString(targetPrice));
    
    if( currentPrice >= targetPrice ){
      return true;
    } else {
      return false;
    }
  }
}
