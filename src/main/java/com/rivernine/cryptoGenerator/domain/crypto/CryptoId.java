package com.rivernine.cryptoGenerator.domain.crypto;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CryptoId implements Serializable {
  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private String tradeDate;

  public CryptoId(String market, String tradeDate) {
    this.market = market;
    this.tradeDate = tradeDate;
  }
}
