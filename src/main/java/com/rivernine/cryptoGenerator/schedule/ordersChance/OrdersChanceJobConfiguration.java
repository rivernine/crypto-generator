package com.rivernine.cryptoGenerator.schedule.ordersChance;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.service.OrdersChanceService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrdersChanceJobConfiguration {

  private final OrdersChanceService ordersChanceService;
  private final StatusProperties statusProperties;

  public void getOrdersChanceForBidJob(String market) {
    OrdersChanceDto bidOrdersChanceDto = ordersChanceService.getOrdersChanceForBid(market);
    if(Double.parseDouble(bidOrdersChanceDto.getBalance()) >= 5000){
      log.info("Set status (0 -> 1)");
      statusProperties.setCurrentStatus(1);
      statusProperties.setOrdersChanceDtoForBid(bidOrdersChanceDto);
    }
  } 

  public void getOrdersChanceForAskJob(String market) {
    OrdersChanceDto askOrdersChanceDto = ordersChanceService.getOrdersChanceForAsk(market);
    if(askOrdersChanceDto.getCurrency() != null){
      log.info("Set status (10 -> 11)");
      statusProperties.setCurrentStatus(11);
      statusProperties.setOrdersChanceDtoForAsk(askOrdersChanceDto);
    }
  } 
}