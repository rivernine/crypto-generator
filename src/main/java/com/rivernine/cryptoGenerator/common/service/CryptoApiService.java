package com.rivernine.cryptoGenerator.common.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.common.dto.BidMarketResponseDto;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;

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

  public BidMarketResponseDto bid(String market, String price, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    BidMarketResponseDto bidMarketResponseDto = BidMarketResponseDto.builder()
                                    .success(false)
                                    .build();
    if( !statusProperties.getBidRunning() || statusProperties.getBidPending() ) {
      statusProperties.setBidRunning(true);
      statusProperties.setBidPending(true);

      List<String> pricePerBidLevel = scaleTradeStatusProperties.getPricePerLevel();
      int level = scaleTradeStatusProperties.getLevel();
      JsonObject response = postBidOrdersSetPrice(market, price, pricePerBidLevel.get(level));
      if(!response.has("error")) {
        bidMarketResponseDto.setUuid(response.get("uuid").getAsString());
        bidMarketResponseDto.setMarket(response.get("market").getAsString());
        bidMarketResponseDto.setSuccess(true);
        scaleTradeStatusProperties.addBidInfoPerLevel(bidMarketResponseDto);
        statusProperties.setBidPending(false);
      }
      statusProperties.setBidRunning(false);
    }

    return bidMarketResponseDto;
  }
}
