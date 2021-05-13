package com.rivernine.cryptoGenerator.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UpbitApi {

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

	@Value("${upbit.markets}")
	private String markets;  

  public JsonObject getMarket(){
    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray[0];
  }
  
  public JsonObject[] getMarkets(){
    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray;
  }

}
