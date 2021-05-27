package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AnalysisForBidMarketDto {
  private String uuid;
  private String market;
  private Boolean success;

  @Builder
  public AnalysisForBidMarketDto(String uuid, String market, Boolean success) {
    this.uuid = uuid;
    this.market = market;
    this.success = success;
  }
}