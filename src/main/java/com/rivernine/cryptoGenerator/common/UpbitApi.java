package com.rivernine.cryptoGenerator.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class UpbitApi {

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

	@Value("${upbit.markets}")
	private String markets;  
  @Value("${upbit.market}")
	private String market;  
  @Value("${upbit.accessKey}")
	private String accessKey;  
  @Value("${upbit.secretKey}")
	private String secretKey;  

  public JsonObject[] getMarkets(){
    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray;
  }

  public JsonObject getMarket(){
    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + market, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray[0];
  }

  public Double getMarketPrice(){
    return getMarket().get("trade_price").getAsDouble();
  }

  public void bidMarket() throws NoSuchAlgorithmException, UnsupportedEncodingException {
    System.out.println("<<< buyMarket >>>");
    
    HashMap<String, String> params = new HashMap<>();
    params.put("market", market);
    params.put("side", "bid");
    // params.put("volume", "0.01");  // 시장가 매수 시 null
    params.put("price", "100");
    params.put("ord_type", "price");

    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }

    String queryString = String.join("&", queryElements.toArray(new String[0]));

    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(queryString.getBytes("UTF-8"));

    String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String jwtToken = JWT.create()
            .withClaim("access_key", accessKey)
            .withClaim("nonce", UUID.randomUUID().toString())
            .withClaim("query_hash", queryHash)
            .withClaim("query_hash_alg", "SHA512")
            .sign(algorithm);

    String authenticationToken = "Bearer " + jwtToken;

    try {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.upbit.com/v1/orders");
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", authenticationToken);
        request.setEntity(new StringEntity(new Gson().toJson(params)));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        System.out.println(EntityUtils.toString(entity, "UTF-8"));
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void sellMarket(){
    System.out.println("<<< sellMarket >>>");
  }

}


