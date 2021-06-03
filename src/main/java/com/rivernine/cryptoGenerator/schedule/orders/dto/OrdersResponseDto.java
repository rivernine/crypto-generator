package com.rivernine.cryptoGenerator.schedule.orders.dto;

import java.util.Map;

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
  private String state;
  private Map<String, TradeDto> trades;
  private Double tradePrice;
  private Boolean success;

  @Builder
  public OrdersResponseDto(String uuid, String market, String state, Map<String, TradeDto> trades, Double tradePrice, Boolean success) {
    this.uuid = uuid;
    this.market = market;
    this.state = state;
    this.trades = trades;
    this.tradePrice = tradePrice;
    this.success = success;
  }
}