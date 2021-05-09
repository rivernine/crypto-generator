package com.rivernine.cryptoGenerator.domain.crypto;

import java.io.Serializable;

import javax.persistence.Column;

public class CryptoId implements Serializable {
  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private String trade_date_kst;

  @Column(nullable = false)
  private String trade_time_kst;  

}
