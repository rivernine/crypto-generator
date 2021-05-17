package com.rivernine.cryptoGenerator.schedule.ordersChance.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrdersChanceDtoForAsk {
  private String market;
  private String uuid;
  private String paidFee;     // 지불 수수료
  private String tradePrice;  // 체결가격
  private String tradeVolume; // 체결 양
  private String tradeFunds;  // 체결된 총 가격

  @Builder
  public OrdersChanceDtoForAsk(String market, String uuid, String paidFee, String tradePrice, String tradeVolume, String tradeFunds) {
    this.market = market;
    this.uuid = uuid;
    this.paidFee = paidFee;
    this.tradePrice = tradePrice;
    this.tradeVolume = tradeVolume;
    this.tradeFunds = tradeFunds;
  }
}