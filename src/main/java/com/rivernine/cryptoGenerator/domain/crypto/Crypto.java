package com.rivernine.cryptoGenerator.domain.crypto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
public class Crypto {
  @EmbeddedId
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private CryptoId cryptoId;

  @Column(nullable = false)
  private Double price;
  private Double trade_volume;
  private Double acc_trade_volume;
  private Double acc_trade_volume_24h;

  @Builder
  public Crypto(CryptoId cryptoId, Double price, Double trade_volume, Double acc_trade_volume, Double acc_trade_volume_24h){
    this.cryptoId = cryptoId;
    this.price = price;
    this.trade_volume = trade_volume;
    this.acc_trade_volume = acc_trade_volume;
    this.acc_trade_volume_24h = acc_trade_volume_24h;
  }
}
