package com.rivernine.cryptoGenerator.schedule.ordersChance.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersChanceService {

  private final CryptoApi cryptoApi;
  
  public OrdersChanceDto getOrdersChanceForAsk(String market) {
    try {
      JsonObject response = cryptoApi.getOrdersChanceForAsk(market);
          
      if(response.has("currency")) {
        return OrdersChanceDto.builder()
            .currency(response.get("currency").getAsString())
            .balance(response.get("balance").getAsString())
            .locked(response.get("locked").getAsString())
            .avg_buy_price(response.get("avg_buy_price").getAsString())
            .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());     
    }
    return OrdersChanceDto.builder()
            .currency(null)
            .build();
  }

  public OrdersChanceDto getOrdersChanceForBid(String market) {
    try {
      JsonObject response = cryptoApi.getOrdersChanceForBid(market);
          
      if(response.has("currency")) {
        return OrdersChanceDto.builder()
            .currency(response.get("currency").getAsString())
            .balance(response.get("balance").getAsString())
            .locked(response.get("locked").getAsString())
            .avg_buy_price(response.get("avg_buy_price").getAsString())
            .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());     
    }
    return OrdersChanceDto.builder()
            .currency(null)
            .build();
  }
}
