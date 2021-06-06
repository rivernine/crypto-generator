package com.rivernine.cryptoGenerator.common.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.TradeDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CryptoApiService {

  private final UpbitApi upbitApi;
  private final StatusProperties statusProperties;
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;

  public JsonObject getMarket(String market) {
    return upbitApi.getMarket(market);
  }

  public JsonObject[] getCandles(String market, String minutes, String count){
    return upbitApi.getCandles(market, minutes, count);
  }

  public Double getPrice(String market) {
    return upbitApi.getMarket(market).get("trade_price").getAsDouble();
  }


  public JsonObject getOrdersChanceForBid(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return upbitApi.getOrdersChance(market).get("bid_account").getAsJsonObject();
  }

  public JsonObject getOrdersChanceForAsk(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return upbitApi.getOrdersChance(market).get("ask_account").getAsJsonObject();
  }

  public OrdersResponseDto getOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();
    JsonObject response = upbitApi.getOrder(uuid);
    if(!response.has("error")) {
      // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
      ordersResponseDto.setUuid(response.get("uuid").getAsString());
      ordersResponseDto.setMarket(response.get("market").getAsString());
      ordersResponseDto.setState(response.get("state").getAsString());
      Map<String, TradeDto> trades = new HashMap<>();
      JsonArray jsonArray = response.get("trades").getAsJsonArray();
      for(JsonElement element: jsonArray) {
        JsonObject obj = (JsonObject) element;
        TradeDto trade = TradeDto.builder()
                          .uuid(obj.get("uuid").getAsString())
                          .market(obj.get("market").getAsString())
                          .price(obj.get("price").getAsString())
                          .volume(obj.get("volume").getAsString())
                          .funds(obj.get("funds").getAsString())
                          .build();
        trades.put(obj.get("uuid").getAsString(), trade);
      }
      ordersResponseDto.setTrades(trades);
      ordersResponseDto.setSuccess(true);
    }

    return ordersResponseDto;
  }

  public OrdersResponseDto deleteOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();

    JsonObject response = upbitApi.deleteOrder(uuid);
    if(!response.has("error")) {
      ordersResponseDto.setUuid(response.get("uuid").getAsString());
      ordersResponseDto.setMarket(response.get("market").getAsString());
      // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
      ordersResponseDto.setState(response.get("state").getAsString());
      ordersResponseDto.setSuccess(true);
    }

    return ordersResponseDto;
  }

  // 지정가 매수
  public OrdersResponseDto bid(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();
    if( !statusProperties.getBidRunning() || statusProperties.getBidPending() ) {
      statusProperties.setBidRunning(true);
      statusProperties.setBidPending(true);

      JsonObject response = upbitApi.postOrders(market, "bid", volume, price, "limit");
      if(!response.has("error")) {
        ordersResponseDto.setUuid(response.get("uuid").getAsString());
        ordersResponseDto.setMarket(response.get("market").getAsString());
        // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        ordersResponseDto.setState(response.get("state").getAsString());
        ordersResponseDto.setSuccess(true);
        // scaleTradeStatusProperties.addBidInfoPerLevel(ordersResponseDto);
        statusProperties.setBidPending(false);
      }
      statusProperties.setBidRunning(false);
    }

    return ordersResponseDto;
  }

  // 지정가 매도
  public OrdersResponseDto ask(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();
    if( !statusProperties.getAskRunning() || statusProperties.getAskPending() ) {
      statusProperties.setAskRunning(true);
      statusProperties.setAskPending(true);

      JsonObject response = upbitApi.postOrders(market, "ask", volume, price, "limit");
      if(!response.has("error")) {
        ordersResponseDto.setUuid(response.get("uuid").getAsString());
        ordersResponseDto.setMarket(response.get("market").getAsString());
        // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        ordersResponseDto.setState(response.get("state").getAsString());
        ordersResponseDto.setSuccess(true);
        // scaleTradeStatusProperties.addAskInfoPerLevel(ordersResponseDto);
        statusProperties.setAskPending(false);
      }
      statusProperties.setAskRunning(false);
    }

    return ordersResponseDto;
  }

  // 시장가 매수
  public OrdersResponseDto bid(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();
    if( !statusProperties.getBidRunning() || statusProperties.getBidPending() ) {
      statusProperties.setBidRunning(true);
      statusProperties.setBidPending(true);

      JsonObject response = upbitApi.postOrders(market, "bid", "-1", price, "price");
      if(!response.has("error")) {
        ordersResponseDto.setUuid(response.get("uuid").getAsString());
        ordersResponseDto.setMarket(response.get("market").getAsString());
        // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        ordersResponseDto.setState(response.get("state").getAsString());
        ordersResponseDto.setSuccess(true);
        scaleTradeStatusProperties.addBidInfoPerLevel(ordersResponseDto);
        statusProperties.setBidPending(false);
      }
      statusProperties.setBidRunning(false);
    }

    return ordersResponseDto;
  }

  // 시장가 매도
  public OrdersResponseDto ask(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                                            .success(false)
                                            .build();
    if( !statusProperties.getAskRunning() || statusProperties.getAskPending() ) {
      statusProperties.setAskRunning(true);
      statusProperties.setAskPending(true);

      JsonObject response = upbitApi.postOrders(market, "ask", volume, "-1", "market");
      if(!response.has("error")) {
        ordersResponseDto.setUuid(response.get("uuid").getAsString());
        ordersResponseDto.setMarket(response.get("market").getAsString());
        // ordersResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        ordersResponseDto.setState(response.get("state").getAsString());
        ordersResponseDto.setSuccess(true);
        statusProperties.setAskPending(false);
      }
      statusProperties.setAskRunning(false);
    }

    return ordersResponseDto;
  }
}
