package com.rivernine.cryptoGenerator.schedule.tradeMarket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.schedule.tradeMarket.dto.ExpectedResponseDto;
import com.rivernine.cryptoGenerator.schedule.tradeMarket.service.TradeMarketService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TradeMarketJobConfiguration {

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

	@Value("${upbit.markets}")
	private String markets;  

  private final TradeMarketService tradeMarketService;

  public Double getMarketJob() {
    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);

    return jsonObjectArray[0].get("trade_price").getAsDouble();
    // Multiple market
    // for( int i = 0; i < jsonObjectArray.length; i++ ){
    //   JsonObject jsonObject = jsonObjectArray[i];
    //   TradeMarketResponseDto tradeMarketResponseDto = TradeMarketResponseDto.builder()
    //                                   .market(jsonObject.get("market").getAsString())
    //                                   .price(jsonObject.get("trade_price").getAsDouble())
    //                                   .build();
    //   return tradeMarketResponseDto.getPrice();      
    //   // return jsonObject.get("trade_price").getAsDouble();
    // }        
  } 

  // public boolean comparePrice(Double currentPrice) {
  //   ExpectedResponseDto expectedResponseDto = tradeMarketService.expectedPrice(markets);
  //   if (currentPrice expectedResponseDto.getPrice() ){
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }
}