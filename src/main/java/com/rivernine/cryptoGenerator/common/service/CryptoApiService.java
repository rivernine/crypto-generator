package com.rivernine.cryptoGenerator.common.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.exchange.dto.ExchangeResponseDto;

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
  
  // 시장가 매수
  public JsonObject postBidOrders(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "bid", "-1", price, "price");
  }

  // 지정가 매수
  public JsonObject postBidOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "bid", volume, price, "limit");
  }

  // 시장가 매도
  public JsonObject postAskOrders(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "ask", volume, "-1", "market");
  }

  // 지정가 매도
  public JsonObject postAskOrdersSetPrice(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.postOrders(market, "ask", volume, price, "limit");
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

  public JsonObject deleteOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return upbitApi.deleteOrder(uuid);
  }

  public ExchangeResponseDto bid(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    ExchangeResponseDto exchangeResponseDto = ExchangeResponseDto.builder()
                                                .success(false)
                                                .build();
    if( !statusProperties.getBidRunning() || statusProperties.getBidPending() ) {
      statusProperties.setBidRunning(true);
      statusProperties.setBidPending(true);

      List<String> pricePerBidLevel = scaleTradeStatusProperties.getBalancePerLevel();
      int level = scaleTradeStatusProperties.getLevel();
      JsonObject response = postBidOrders(market, pricePerBidLevel.get(level));
      if(!response.has("error")) {
        exchangeResponseDto.setUuid(response.get("uuid").getAsString());
        exchangeResponseDto.setMarket(response.get("market").getAsString());
        exchangeResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        exchangeResponseDto.setSuccess(true);
        scaleTradeStatusProperties.addBidInfoPerLevel(exchangeResponseDto);
        statusProperties.setBidPending(false);
      }
      statusProperties.setBidRunning(false);
    }

    return exchangeResponseDto;
  }

  public ExchangeResponseDto ask(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    ExchangeResponseDto exchangeResponseDto = ExchangeResponseDto.builder()
                                                .success(false)
                                                .build();
    if( !statusProperties.getAskRunning() || statusProperties.getAskPending() ) {
      statusProperties.setAskRunning(true);
      statusProperties.setAskPending(true);

      JsonObject response = postAskOrdersSetPrice(market, volume, price);
      // JsonObject response = postBidOrders(market, pricePerBidLevel.get(level));
      if(!response.has("error")) {
        exchangeResponseDto.setUuid(response.get("uuid").getAsString());
        exchangeResponseDto.setMarket(response.get("market").getAsString());
        exchangeResponseDto.setPaidFee(response.get("reserved_fee").getAsString());
        exchangeResponseDto.setSuccess(true);
        scaleTradeStatusProperties.addAskInfoPerLevel(exchangeResponseDto);
        statusProperties.setAskPending(false);
      }
      statusProperties.setAskRunning(false);
    }

    return exchangeResponseDto;
  }
}
