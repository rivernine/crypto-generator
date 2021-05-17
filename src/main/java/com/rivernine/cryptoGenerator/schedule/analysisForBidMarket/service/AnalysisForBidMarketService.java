package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.service;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.algorithm.SimpleBidAlgorithm;
import com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto.BidMarketResponseDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@RequiredArgsConstructor
@Service
public class AnalysisForBidMarketService {

  private final CryptoApi cryptoApi;
  private final SimpleBidAlgorithm simpleBidAlgorithm;

  public Boolean analysis() {
    return simpleBidAlgorithm.algorithm();
  }

  public BidMarketResponseDto bid(String market, String price) {
    try {
      JsonObject response = cryptoApi.postBidOrders(market, price);    
      if(response.has("uuid")) {
        return BidMarketResponseDto.builder()
                .uuid(response.get("uuid").getAsString())
                .market(response.get("market").getAsString())
                .success(true)
                .build();
      }
    } catch (Exception e) {
      log.info(e.getMessage());      
    }
    return BidMarketResponseDto.builder()
            .success(false)
            .build();
  }
}
