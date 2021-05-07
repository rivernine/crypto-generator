package com.rivernine.cryptoGenerator.domain.crypto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CryptoId {
  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private String trade_date_kst;

  @Column(nullable = false)
  private String trade_time_kst;  
}

