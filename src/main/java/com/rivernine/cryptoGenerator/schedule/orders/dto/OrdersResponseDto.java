package com.rivernine.cryptoGenerator.schedule.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrdersResponseDto {
  private String uuid;
  private String market;
  private String paidFee;
  private Boolean success;

  @Builder
  public OrdersResponseDto(String uuid, String market, String paidFee, Boolean success) {
    this.uuid = uuid;
    this.market = market;
    this.paidFee = paidFee;
    this.success = success;
  }
}