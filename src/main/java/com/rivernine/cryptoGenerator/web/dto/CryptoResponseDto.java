package com.rivernine.cryptoGenerator.web.dto;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoId;

import lombok.Getter;

@Getter
public class CryptoResponseDto {
  private CryptoId cryptoId;
  private Double price;
  private Double trade_volume;
  private Double acc_trade_volume;
  private Double acc_trade_volume_24h;

  public CryptoResponseDto(Crypto entity) {
    this.cryptoId = entity.getCryptoId();
    this.price = entity.getPrice();
    this.trade_volume = entity.getTrade_volume();
    this.acc_trade_volume = entity.getAcc_trade_volume();
    this.acc_trade_volume_24h = entity.getAcc_trade_volume_24h();
  }

}
// public class CryptoResponseDto {
//   private String market;
//   private String trade_date;
//   private String trade_time;
//   private String trade_date_kst;
//   private String trade_time_kst;
//   private Double trade_timestamp;
//   private Double opening_price;
//   private Double high_price;
//   private Double low_price;
//   private Double trade_price;
//   private Double prev_closing_price;
//   private String change;
//   private Double change_price;
//   private Double change_rate;
//   private Double signed_change_price;
//   private Double signed_change_rate;
//   private Double trade_volume;
//   private Double acc_trade_price;
//   private Double acc_trade_price_24h;
//   private Double acc_trade_volume;
//   private Double acc_trade_volume_24h;
//   private Double highest_52_week_price;
//   private String highest_52_week_date;
//   private Double lowest_52_week_price;
//   private String lowest_52_week_date;
//   private Long timestamp;
// }
