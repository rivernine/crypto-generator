package com.rivernine.cryptoGenerator.web.dto;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CryptoSaveDto {
  private String market;
  private Double price;  

  @Builder
  public CryptoSaveDto(String market, Double price) {
    this.market = market;
    this.price = price;
  }

  public Crypto toEntity() {
    return Crypto.builder()
            .market(market)
            .price(price)
            .build();
  }
}
