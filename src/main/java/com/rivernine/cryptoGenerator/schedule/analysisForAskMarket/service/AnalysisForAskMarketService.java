package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.algorithm.SimpleAskAlgorithm;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.dto.AskMarketResponseDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisForAskMarketService {

  private final CryptoApi cryptoApi;
  private final SimpleAskAlgorithm simpleAskAlgorithm;

  public Boolean analysis() {
    return simpleAskAlgorithm.algorithm();
  }

  public AskMarketResponseDto ask(String market, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    JsonObject response = cryptoApi.postAskOrders(market, volume);    
    try {
      return AskMarketResponseDto.builder()
      .uuid(response.get("uuid").getAsString())
      .market(response.get("market").getAsString())
      .success(true)
      .build();
    } catch (Exception e) {
      return AskMarketResponseDto.builder()
      .success(false)
      .build();
    }
  }

}
