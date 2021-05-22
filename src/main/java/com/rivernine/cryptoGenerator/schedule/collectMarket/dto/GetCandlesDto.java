package com.rivernine.cryptoGenerator.schedule.collectMarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class GetCandlesDto {
  private String market;
  private String candleDateTime;
  private Double openingPrice;  // 시가
  private Double highPrice;     // 고가
  private Double lowPrice;      // 저가
  private Double tradePrice;    // 종가
  private Boolean flag;         // 0: 음봉, 1: 양봉

  @Builder
  public GetCandlesDto(String market, String candleDateTime, Double openingPrice, Double highPrice, Double lowPrice, Double tradePrice, Boolean flag){
    this.market = market;
    this.candleDateTime = candleDateTime;
    this.openingPrice = openingPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.tradePrice = tradePrice;
    this.flag = flag;
  }
}
