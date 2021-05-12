package com.rivernine.cryptoGenerator.schedule.analysisMarket.dto;

import java.time.LocalDateTime;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class AnalysisMarketResponseDto {
  private String market;
  private LocalDateTime tradeDate;  
  private Double price;  
  private Double tradeVolume;
  private Double accTradeVolume;
  private Double accTradeVolume24h;
  
  public AnalysisMarketResponseDto(Crypto entity) {
    this.market = entity.getMarket();
    this.tradeDate = entity.getTradeDate();
    this.price = entity.getPrice();
    this.tradeVolume = entity.getTradeVolume();
    this.accTradeVolume = entity.getAccTradeVolume();
    this.accTradeVolume24h = entity.getAccTradeVolume24h();
  }

}