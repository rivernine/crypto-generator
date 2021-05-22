package com.rivernine.cryptoGenerator.common;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CryptoApi {

  // @Value("${upbit.markets}")
	// private String markets;  
  @Value("${upbit.market}")
	private String market;  
  @Value("${upbit.accessKey}")
	private String accessKey;  
  @Value("${upbit.secretKey}")
	private String secretKey;  

  private final UpbitApi upbitApi;

  public JsonObject getMarket(String market) {
    return upbitApi.getMarket(market);
  }

  public JsonObject[] getCandles(String market, String minutes, String count){
    return upbitApi.getCandles(market, minutes, count);
  }

  public Double getPrice(String market) {
    return upbitApi.getMarket(market).get("trade_price").getAsDouble();
  }
  
  // 시장가 매수
  public JsonObject postBidOrders(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "bid", "-1", price, "price");
  }

  // 지정가 매수
  public void postBidOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    upbitApi.postOrders(market, "bid", volume, price, "limit");
  }

  // 시장가 매도
  public JsonObject postAskOrders(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "ask", volume, "-1", "market");
  }

  // 지정가 매도
  public void postAskOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    upbitApi.postOrders(market, "bid", volume, price, "limit");
  }

  public JsonObject getOrdersChanceForBid(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return upbitApi.getOrdersChance(market).get("bid_account").getAsJsonObject();
  }

  public JsonObject getOrdersChanceForAsk(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return upbitApi.getOrdersChance(market).get("ask_account").getAsJsonObject();
  }

  public JsonObject getOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.getOrder(uuid);
  }
}
