package com.rivernine.cryptoGenerator.schedule.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TradeDto {
  private String uuid;
  private String market;
  private String price;
  private String volume;
  private String funds;
  private Boolean isDone = false;

  @Builder
  public TradeDto(String uuid, String market, String price, String volume, String funds) {
    this.uuid = uuid;
    this.market = market;
    this.price = price;
    this.volume = volume;
    this.funds = funds;
  }
}