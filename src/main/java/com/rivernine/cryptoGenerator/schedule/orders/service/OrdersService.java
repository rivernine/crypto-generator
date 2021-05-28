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

  public OrdersResponseDto bid(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.bid(market, price);
  }

  public OrdersResponseDto ask(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.ask(market, volume, price);
  }

  public OrdersResponseDto deleteOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.deleteOrder(uuid);
  }

  // public OrdersChanceDtoForAsk getOrdersChanceForAsk(String market, String uuid) {
  //   log.info("Start getOrdersChanceForAsk");
  //   try {
  //     JsonObject response = cryptoApi.getOrder(uuid);
  //     JsonArray trades = response.get("trades").getAsJsonArray();
  //     if(trades.size() > 0) {
  //       Double count = 0.0;
  //       Double price = 0.0;
  //       Double volume = 0.0;
  //       Double funds = 0.0;

  //       for(int i = 0; i < trades.size(); i++){
  //         JsonObject obj = trades.get(i).getAsJsonObject();
  //         log.info(gson.toJson(obj));
  //         log.info(obj.get("market").getAsString());
  //         log.info(obj.get("side").getAsString());
  //         if(obj.get("market").getAsString().equals(market) && obj.get("side").getAsString().equals("bid")){
  //           count += 1.0;
  //           price += obj.get("price").getAsDouble();
  //           volume += obj.get("volume").getAsDouble();
  //           funds += obj.get("funds").getAsDouble();
  //         }
  //       }

  //       return OrdersChanceDtoForAsk.builder()
  //               .market(market)
  //               .uuid(response.get("uuid").getAsString())
  //               .paidFee(response.get("paid_fee").getAsString())
  //               .tradePrice(Double.toString(price/count))
  //               .tradeVolume(Double.toString(volume))
  //               .tradeFunds(Double.toString(funds))
  //               .build();
  //     } 
  //   } catch (Exception e) {
  //     log.info("Exception 발생: " + e.getMessage());
  //   }
  //   return OrdersChanceDtoForAsk.builder()
  //           .build();
  // }
}
