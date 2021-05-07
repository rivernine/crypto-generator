package com.rivernine.cryptoGenerator.web.dto;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoId;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CryptoSaveDto {
  private CryptoId cryptoId;
  private Double price;  
  private Double trade_volume;
  private Double acc_trade_volume;
  private Double acc_trade_volume_24h;
  
  @Builder
  public CryptoSaveDto(CryptoId crpytoId, Double price, Double trade_volume, Double acc_trade_volume, Double acc_trade_volume_24h) {
    this.cryptoId = crpytoId;
    this.price = price;
    this.trade_volume = trade_volume;
    this.acc_trade_volume = acc_trade_volume;
    this.acc_trade_volume_24h = acc_trade_volume_24h;
  }

  public Crypto toEntity() {
    return Crypto.builder()
            .cryptoId(cryptoId)
            .price(price)
            .trade_volume(trade_volume)
            .acc_trade_volume(acc_trade_volume)
            .acc_trade_volume_24h(acc_trade_volume_24h)
            .build();
  }
}
