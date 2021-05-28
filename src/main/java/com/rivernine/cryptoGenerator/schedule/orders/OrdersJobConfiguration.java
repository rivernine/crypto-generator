package com.rivernine.cryptoGenerator.schedule.orders;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;
import com.rivernine.cryptoGenerator.schedule.orders.service.OrdersService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrdersJobConfiguration {

  private final OrdersService ordersService;

  public OrdersChanceDto getOrdersChanceForBidJob(String market) {
    return ordersService.getOrdersChanceForBid(market);
  } 

  public OrdersChanceDto getOrdersChanceForAskJob(String market) {
    return ordersService.getOrdersChanceForAsk(market);
  } 

  public OrdersResponseDto getOrderJob(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return ordersService.getOrder(uuid);
  }

  public OrdersResponseDto bidJob(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return ordersService.bid(market, price);
  }

  public OrdersResponseDto askJob(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return ordersService.ask(market, volume, price);
  }

  public OrdersResponseDto deleteOrderJob(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return ordersService.deleteOrder(uuid);
  }
}