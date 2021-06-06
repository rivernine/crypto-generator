package com.rivernine.cryptoGenerator.schedule.orders.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersService {

  private final CryptoApi cryptoApi;
  Gson gson = new Gson();

  public OrdersChanceDto getOrdersChanceForBid(String market) {
    try {
      JsonObject response = cryptoApi.getOrdersChanceForBid(market);
          
      if(response.has("currency")) {
        return OrdersChanceDto.builder()
                .currency(response.get("currency").getAsString())
                .balance(response.get("balance").getAsString())
                .locked(response.get("locked").getAsString())
                .avgBuyPrice(response.get("avg_buy_price").getAsString())
                .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());     
    }
    return OrdersChanceDto.builder()
            .currency(null)
            .build();
  }

  public OrdersChanceDto getOrdersChanceForAsk(String market) {
    try {
      JsonObject response = cryptoApi.getOrdersChanceForAsk(market);
          
      if(response.has("currency")) {
        return OrdersChanceDto.builder()
                .currency(response.get("currency").getAsString())
                .balance(response.get("balance").getAsString())
                .locked(response.get("locked").getAsString())
                .avgBuyPrice(response.get("avg_buy_price").getAsString())
                .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());     
    }
    return OrdersChanceDto.builder()
            .currency(null)
            .build();
  }

  public OrdersResponseDto getOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    OrdersResponseDto orderResponse = cryptoApi.getOrder(uuid);
    while(true) {
      if(orderResponse.getUuid() != null)
        break;
      orderResponse = cryptoApi.getOrder(uuid);
    }
    return cryptoApi.getOrder(uuid);
  }

  public OrdersResponseDto bid(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.bid(market, volume, price);
  }

  public OrdersResponseDto ask(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.ask(market, volume, price);
  }

  public OrdersResponseDto bid(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.bid(market, price);
  }

  public OrdersResponseDto ask(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.ask(market, volume);
  }

  public OrdersResponseDto deleteOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.deleteOrder(uuid);
  }

}
