package com.rivernine.cryptoGenerator.schedule.ordersChance;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDtoForAsk;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDtoForBid;
import com.rivernine.cryptoGenerator.schedule.ordersChance.service.OrdersChanceService;

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
    OrdersChanceDtoForBid ordersChanceDtoForBid = ordersChanceService.getOrdersChanceForBid(market);
    if(Double.parseDouble(ordersChanceDtoForBid.getBalance()) >= 5000){
      log.info("Set status (0 -> 1)");
      statusProperties.setOrdersChanceDtoForBid(ordersChanceDtoForBid);
      statusProperties.setCurrentStatus(1);
    }
  } 

  public void getOrdersChanceForAskJob(String market, String uuid) {
    OrdersChanceDtoForAsk ordersChanceDtoForAsk = ordersChanceService.getOrdersChanceForAsk(market, uuid);
    if(ordersChanceDtoForAsk.getUuid() != null){
      log.info("Set status (10 -> 11)");
      statusProperties.setOrdersChanceDtoForAsk(ordersChanceDtoForAsk);
      statusProperties.setCurrentStatus(11);
    }
  } 
}