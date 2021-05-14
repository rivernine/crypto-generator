package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BidMarketResponseDto {
  private String uuid;
  private String market;
  private String price;
  private String avg_price;
  
  @Builder
  public BidMarketResponseDto(String uuid, String market, String price, String avg_price) {
    this.uuid = uuid;
    this.market = market;
    this.price = price;
    this.avg_price = avg_price;
  }
}