package com.rivernine.cryptoGenerator.schedule.ordersChance;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.service.OrdersChanceService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrdersChanceJobConfiguration {

  private final OrdersChanceService ordersChanceService;
  private final StatusProperties statusProperties;

  public void getOrdersChanceForAskJob(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    OrdersChanceDto ordersChanceDto = ordersChanceService.getOrdersChanceForAsk(market);
    statusProperties.setCurrentStatus(11);
    statusProperties.setOrdersChanceDtoForAsk(ordersChanceDto);
  } 
}