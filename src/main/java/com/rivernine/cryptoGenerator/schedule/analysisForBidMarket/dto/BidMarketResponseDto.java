package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BidMarketResponseDto {
  private String uuid;
  private String market;
  
  @Builder
  public BidMarketResponseDto(String uuid, String market) {
    this.uuid = uuid;
    this.market = market;
  }
}