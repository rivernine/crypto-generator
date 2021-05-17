package com.rivernine.cryptoGenerator.schedule.ordersChance.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class OrdersChanceDtoForBid {
  private String currency;
  private String balance;  
  private String locked;  
  
  @Builder
  public OrdersChanceDtoForBid(String currency, String balance, String locked) {
    this.currency = currency;
    this.balance = balance;
    this.locked = locked;
  }
}
