package com.rivernine.cryptoGenerator.schedule.collectMarket.dto;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CollectMarketSaveDto {
  private String market;
  private String tradeDate;  
  private Double price;  
  private Double tradeVolume;
  private Double accTradeVolume;
  private Double accTradeVolume24h;
  
  @Builder
  public CollectMarketSaveDto(String market, String tradeDate, Double price, Double tradeVolume, Double accTradeVolume, Double accTradeVolume24h) {
    this.market = market;
    this.tradeDate = tradeDate;
    this.price = price;
    this.tradeVolume = tradeVolume;
    this.accTradeVolume = accTradeVolume;
    this.accTradeVolume24h = accTradeVolume24h;
  }

  public Crypto toEntity() {
    return Crypto.builder()
            .market(market)
            .tradeDate(tradeDate)
            .price(price)
            .tradeVolume(tradeVolume)
            .accTradeVolume(accTradeVolume)
            .accTradeVolume24h(accTradeVolume24h)
            .build();
  }
}
