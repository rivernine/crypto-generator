package com.rivernine.cryptoGenerator.domain.crypto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
@IdClass(CryptoId.class)
public class Crypto {
 
  @Id
  private String market;
  @Id
  private String trade_date_kst;
  @Id
  private String trade_time_kst;  

  @Column(nullable = false)
  private Double price;
  private Double trade_volume;
  private Double acc_trade_volume;
  private Double acc_trade_volume_24h;

  @Builder
  public Crypto(String market, String trade_date_kst, String trade_time_kst, Double price, Double trade_volume, Double acc_trade_volume, Double acc_trade_volume_24h){
    this.market = market;
    this.trade_date_kst = trade_date_kst;
    this.trade_time_kst = trade_time_kst;
    this.price = price;
    this.trade_volume = trade_volume;
    this.acc_trade_volume = acc_trade_volume;
    this.acc_trade_volume_24h = acc_trade_volume_24h;
  }
}
