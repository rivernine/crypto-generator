package com.rivernine.cryptoGenerator.schedule.tradeMarket.dto;

import com.rivernine.cryptoGenerator.domain.expected.Expected;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class TradeMarketResponseDto {
  private String market;
  // private String tradeDate;
  private Double price;  
  // private Double tradeVolume;
  // private Double accTradeVolume;
  // private Double accTradeVolume24h;
  
  @Builder
  // public TradeMarketDto(String market, String tradeDate, Double price, Double tradeVolume, Double accTradeVolume, Double accTradeVolume24h) {
  public TradeMarketResponseDto(String market, Double price) {
    this.market = market;
    this.price = price;
  }
}
