package com.rivernine.cryptoGenerator.schedule.orders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class OrdersChanceDto {
  private String currency;
  private String balance;  
  private String locked;  
  private String avgBuyPrice;
  
  @Builder
  public OrdersChanceDto(String currency, String balance, String locked, String avgBuyPrice) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
    this.avgBuyPrice = avgBuyPrice;
  }
}
