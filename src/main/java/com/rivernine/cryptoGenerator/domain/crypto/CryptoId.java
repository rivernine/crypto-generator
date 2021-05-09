package com.rivernine.cryptoGenerator.domain.crypto;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CryptoId implements Serializable {
  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private String trade_date_kst;

  @Column(nullable = false)
  private String trade_time_kst;  

  public CryptoId(String market, String trade_date_kst, String trade_time_kst) {
    this.market = market;
    this.trade_date_kst = trade_date_kst;
    this.trade_time_kst = trade_time_kst;
  }
}
