package com.rivernine.cryptoGenerator.domain.crypto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CryptoId implements Serializable {
  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private LocalDateTime tradeDate;

  public CryptoId(String market, LocalDateTime tradeDate) {
    this.market = market;
    this.tradeDate = tradeDate;
  }
}
