package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto;

import java.time.LocalDateTime;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AnalysisForBidMarketResponseDto {
  private String market;
  private LocalDateTime tradeDate;  
  private Double price;  
  private Double tradeVolume;
  private Double accTradeVolume;
  private Double accTradeVolume24h;
  
  public AnalysisForBidMarketResponseDto(Crypto entity) {
    this.market = entity.getMarket();
    this.tradeDate = entity.getTradeDate();
    this.price = entity.getPrice();
    this.tradeVolume = entity.getTradeVolume();
    this.accTradeVolume = entity.getAccTradeVolume();
    this.accTradeVolume24h = entity.getAccTradeVolume24h();
  }

}