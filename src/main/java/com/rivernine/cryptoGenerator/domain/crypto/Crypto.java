package com.rivernine.cryptoGenerator.domain.crypto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Getter
@NoArgsConstructor
@Entity
@IdClass(CryptoId.class)
public class Crypto {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
  @Id
  private String market;
  @Id
  private LocalDateTime tradeDate;

  @Column(nullable = false)
  private Double price;
  private Double tradeVolume;
  private Double accTradeVolume;
  private Double accTradeVolume24h;

  @Builder
  public Crypto(String market, String tradeDate, Double price, Double tradeVolume, Double accTradeVolume, Double accTradeVolume24h){
    this.market = market;
    this.tradeDate = LocalDateTime.parse(tradeDate, FORMATTER);
    this.price = price;
    this.tradeVolume = tradeVolume;
    this.accTradeVolume = accTradeVolume;
    this.accTradeVolume24h = accTradeVolume24h;
  }
}
