// package com.rivernine.cryptoGenerator.common;

// import java.io.IOException;
// import java.math.BigInteger;
// import java.security.MessageDigest;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.google.gson.Gson;
// import com.google.gson.JsonObject;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
// import org.apache.http.HttpEntity;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.entity.StringEntity;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.util.EntityUtils;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// public class UpbitApiTests {  

//   // @Test
//   // public void postOrder_test() {
//   //   try {
//   //     String market = "KRW-DOGE";
//   //     String side = "bid";
//   //     String volume = "null";
//   //     String price = "5000";
//   //     String ordType = "price";

      


//   //     Gson gson = new Gson();
//   //     JsonObject result = null;
//   //     HashMap<String, String> params = new HashMap<>();
//   //     params.put("market", market);
//   //     params.put("side", side);
//   //     params.put("volume", volume);
//   //     params.put("price", price);
//   //     params.put("ord_type", ordType);

//   //     ArrayList<String> queryElements = new ArrayList<>();
//   //     for(Map.Entry<String, String> entity : params.entrySet()) {
//   //         queryElements.add(entity.getKey() + "=" + entity.getValue());
//   //     }

//   //     String queryString = String.join("&", queryElements.toArray(new String[0]));

//   //     System.out.println("queryString : " + queryString);

//   //     MessageDigest md = MessageDigest.getInstance("SHA-512"); 
//   //     md.update(queryString.getBytes("UTF-8"));

//   //     String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

//   //     System.out.println("queryHash: " + queryHash);
//   //     System.out.println("uuid: " + UUID.randomUUID().toString());
//   //     Algorithm algorithm = Algorithm.HMAC256(secretKey);
//   //     String jwtToken = JWT.create()
//   //             .withClaim("access_key", accessKey)
//   //             .withClaim("nonce", UUID.randomUUID().toString())
//   //             .withClaim("query_hash", queryHash)
//   //             .withClaim("query_hash_alg", "SHA512")
//   //             .sign(algorithm);

//   //     String authenticationToken = "Bearer " + jwtToken;

//   //     System.out.println("jwtToken: " + jwtToken);
//   //     System.out.println("authenticationToken: " +  authenticationToken);

//   //     try {
//   //         HttpClient client = HttpClientBuilder.create().build();
//   //         HttpPost request = new HttpPost(serverUrl + "/v1/orders");
//   //         request.setHeader("Content-Type", "application/json");
//   //         request.addHeader("Authorization", authenticationToken);
//   //         request.setEntity(new StringEntity(new Gson().toJson(params)));

//   //         HttpResponse response = client.execute(request);
//   //         HttpEntity entity = response.getEntity();

//   //         String jsonString = EntityUtils.toString(entity, "UTF-8");
//   //         System.out.println(jsonString);
//   //         result = gson.fromJson(jsonString, JsonObject.class);
//   //     } catch (IOException e) {
//   //         e.printStackTrace();
//   //     }
//   //   } catch (Exception e ) {
//   //     e.printStackTrace();
//   //   }
//   // }
// }
