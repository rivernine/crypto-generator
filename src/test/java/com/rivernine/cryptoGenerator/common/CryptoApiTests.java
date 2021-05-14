package com.rivernine.cryptoGenerator.common;

import com.google.gson.JsonObject;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CryptoApiTests {  

  @Autowired
  CryptoApi cryptoApi;

  @Test
  public void postBidOrders_test() {
    String market = "krw-doge";
    String price = "5000";
    
    try {
      JsonObject result = cryptoApi.postBidOrders(market, price);
      assertThat(result.get("market")).isEqualTo("krw-doge");
    } catch ( Exception e ){
      System.out.println(e.getMessage());
    }
  }
}
