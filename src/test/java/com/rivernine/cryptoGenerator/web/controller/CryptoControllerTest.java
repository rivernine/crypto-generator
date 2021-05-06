package com.rivernine.cryptoGenerator.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.web.dto.CryptoSaveDto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class CryptoControllerTest {
  
  @Autowired
  private MockMvc mvc;

  @Autowired
  private CryptoRepository cryptoRepository;

  @Test
  public void return_hello() throws Exception {
    String hello = "hello";

    mvc.perform(get("/hello"))
              .andExpect(status().isOk())
              .andExpect(content().string(hello));
  }

  @Test
  public void return_upbit() throws Exception {
    String markets = "KRW-BTC";

    mvc.perform(get("/price/KRW-BTC"))
          .andExpect(status().isOk());
  }

  @AfterAll
  public void tearDown() throws Exception {
    cryptoRepository.deleteAll();
  }

  // @Test
  // public void Crypto_save() throws Exception{
  //   String market = "KRW-BTC";
  //   Double price = 60000000.000000;
  //   CryptoSaveDto saveDto = CryptoSaveDto.builder()
  //                             .market(market)
  //                             .price(price)
  //                             .build();

  //   String url = "http://localhost:8080/api/v1/crypto";

  //   ResponseEntity<Long> responseEntity = restTemplate.cryptoFor


  // }
}
