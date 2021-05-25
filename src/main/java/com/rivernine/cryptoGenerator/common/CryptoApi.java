package com.rivernine.cryptoGenerator.common;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.dto.BidMarketResponseDto;
import com.rivernine.cryptoGenerator.common.service.CryptoApiService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CryptoApi {

  private final CryptoApiService cryptoApiService;

  public JsonObject getMarket(String market) {    
    return cryptoApiService.getMarket(market);
  }

  public JsonObject[] getCandles(String market, String minutes, String count){
    return cryptoApiService.getCandles(market, minutes, count);
  }

  public Double getPrice(String market) {
    return cryptoApiService.getPrice(market);
  }
  
  // 시장가 매수
  public JsonObject postBidOrders(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApiService.postBidOrders(market, price);
  }

  // 지정가 매수
  public JsonObject postBidOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApiService.postBidOrdersSetPrice(market, volume, price);
  }

  // 시장가 매도
  public JsonObject postAskOrders(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApiService.postAskOrders(market, volume);
  }

  // 지정가 매도
  public void postAskOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    cryptoApiService.postAskOrdersSetPrice(market, volume, price);
  }

  public JsonObject getOrdersChanceForBid(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return cryptoApiService.getOrdersChanceForBid(market);
  }

  public JsonObject getOrdersChanceForAsk(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return cryptoApiService.getOrdersChanceForAsk(market);
  }

  public JsonObject getOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApiService.getOrder(uuid);
  }

  public BidMarketResponseDto bid(String market, String price, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    return cryptoApiService.bid(market, price, volume);
  }
}
