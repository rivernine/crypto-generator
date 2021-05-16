package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.service;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.algorithm.SimpleAskAlgorithm;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.dto.AskMarketResponseDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class AnalysisForAskMarketService {

  private final CryptoApi cryptoApi;
  private final SimpleAskAlgorithm simpleAskAlgorithm;

  public Boolean analysis(String market, StatusProperties statusProperties) {
    return simpleAskAlgorithm.algorithm(cryptoApi.getPrice(market), statusProperties);
  }

  public AskMarketResponseDto ask(String market, String volume) {
    try {
      JsonObject response = cryptoApi.postAskOrders(market, volume);    
      if(response.has("uuid")) {
        return AskMarketResponseDto.builder()
                .uuid(response.get("uuid").getAsString())
                .market(response.get("market").getAsString())
                .success(true)
                .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());     
    }
    return AskMarketResponseDto.builder()
              .success(false)
              .build();
  }

}
