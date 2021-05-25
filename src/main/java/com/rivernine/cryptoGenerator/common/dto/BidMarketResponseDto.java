package com.rivernine.cryptoGenerator.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BidMarketResponseDto {
  private String uuid;
  private String market;
  private Boolean success;

  @Builder
  public BidMarketResponseDto(String uuid, String market, Boolean success) {
    this.uuid = uuid;
    this.market = market;
    this.success = success;
  }
}