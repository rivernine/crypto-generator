package com.rivernine.cryptoGenerator.schedule.ordersChance;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.service.OrdersChanceService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrdersChanceJobConfiguration {

  private final OrdersChanceService ordersChanceService;

  public OrdersChanceDto getOrdersChanceForBidJob(String market) {
    return ordersChanceService.getOrdersChanceForBid(market);
  } 

  public OrdersChanceDto getOrdersChanceForAskJob(String market) {
    return ordersChanceService.getOrdersChanceForAsk(market);
  } 
}