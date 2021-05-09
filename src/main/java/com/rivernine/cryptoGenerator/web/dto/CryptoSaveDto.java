package com.rivernine.cryptoGenerator.web.dto;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoId;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CryptoSaveDto {
  private String market;
  private String trade_date_kst;
  private String trade_time_kst;  
  private Double price;  
  private Double trade_volume;
  private Double acc_trade_volume;
  private Double acc_trade_volume_24h;
  
  @Builder
  public CryptoSaveDto(String market, String trade_date_kst, String trade_time_kst, Double price, Double trade_volume, Double acc_trade_volume, Double acc_trade_volume_24h) {
    this.market = market;
    this.trade_date_kst = trade_date_kst;
    this.trade_time_kst = trade_time_kst;
    this.price = price;
    this.trade_volume = trade_volume;
    this.acc_trade_volume = acc_trade_volume;
    this.acc_trade_volume_24h = acc_trade_volume_24h;
  }

  public Crypto toEntity() {
    return Crypto.builder()
            .market(market)
            .trade_date_kst(trade_date_kst)
            .trade_time_kst(trade_time_kst)
            .price(price)
            .trade_volume(trade_volume)
            .acc_trade_volume(acc_trade_volume)
            .acc_trade_volume_24h(acc_trade_volume_24h)
            .build();
  }
}
