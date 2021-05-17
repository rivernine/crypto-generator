package com.rivernine.cryptoGenerator.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UpbitApi {

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

  @Value("${upbit.accessKey}")
	private String accessKey;  
  @Value("${upbit.secretKey}")
	private String secretKey;  
  private String serverUrl = "https://api.upbit.com";

  public JsonObject[] getMarkets(String markets){
    String jsonString = restTemplate.getForObject(serverUrl + "/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray;
  }

  public JsonObject getMarket(String market){
    String jsonString = restTemplate.getForObject(serverUrl + "/v1/ticker?markets=" + market, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);
    return jsonObjectArray[0];
  }  

  public JsonObject postOrders(String market, String side, String volume, String price, String ordType) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    JsonObject result = null;
    HashMap<String, String> params = new HashMap<>();
    params.put("market", market);
    params.put("side", side);
    if(!volume.equals("-1"))
      params.put("volume", volume);
    if(!price.equals("-1"))
      params.put("price", price);
    params.put("ord_type", ordType);

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
        HttpPost request = new HttpPost(serverUrl + "/v1/orders");
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", authenticationToken);
        request.setEntity(new StringEntity(new Gson().toJson(params)));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String jsonString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(jsonString);
        result = gson.fromJson(jsonString, JsonObject.class);
    } catch (IOException e) {
        e.printStackTrace();
    }

    return result;
  }

  public JsonObject getOrdersChance(String market) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    JsonObject result = null;

    HashMap<String, String> params = new HashMap<>();
    params.put("market", market);

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
        HttpGet request = new HttpGet(serverUrl + "/v1/orders/chance?" + queryString);
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", authenticationToken);

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String jsonString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(jsonString);
        result = gson.fromJson(jsonString, JsonObject.class);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return result;
  }

  public JsonObject getOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {    
    JsonObject result = null;

    HashMap<String, String> params = new HashMap<>();
    params.put("uuid", uuid);

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
        HttpGet request = new HttpGet(serverUrl + "/v1/order?" + queryString);
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", authenticationToken);

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String jsonString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(jsonString);
        result = gson.fromJson(jsonString, JsonObject.class);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return result;
  }

}


