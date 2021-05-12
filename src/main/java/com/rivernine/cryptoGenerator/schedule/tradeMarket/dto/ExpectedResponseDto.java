package com.rivernine.cryptoGenerator.schedule.tradeMarket.dto;

import com.rivernine.cryptoGenerator.domain.expected.Expected;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class ExpectedResponseDto {
  private String market;
  private Double price;  
  
  public ExpectedResponseDto(Expected entity) {
    this.market = entity.getMarket();
    this.price = entity.getPrice();
  }
}
