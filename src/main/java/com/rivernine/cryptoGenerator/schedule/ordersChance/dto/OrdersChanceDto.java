package com.rivernine.cryptoGenerator.schedule.ordersChance.dto;

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
  private String avg_buy_price;
  
  @Builder
  public OrdersChanceDto(String currency, String balance, String locked, String avg_buy_price) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
    this.avg_buy_price = avg_buy_price;
  }
}
