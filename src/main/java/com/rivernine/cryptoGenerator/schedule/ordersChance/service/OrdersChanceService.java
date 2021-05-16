package com.rivernine.cryptoGenerator.schedule.ordersChance.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersChanceService {

  private final CryptoApi cryptoApi;
  
  public OrdersChanceDto getOrdersChanceForAsk(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    JsonObject jsonObject = cryptoApi.getOrdersChanceForAsk(market);
    return  OrdersChanceDto.builder()
              .currency(jsonObject.get("currency").getAsString())
              .balance(jsonObject.get("balance").getAsString())
              .locked(jsonObject.get("locked").getAsString())
              .avg_buy_price(jsonObject.get("avg_buy_price").getAsString())
              .build();
  }

}
