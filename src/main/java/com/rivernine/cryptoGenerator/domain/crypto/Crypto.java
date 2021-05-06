package com.rivernine.cryptoGenerator.domain.crypto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Crypto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String market;

  @Column(nullable = false)
  private Double price;

  @Builder
  public Crypto(String market, Double price){
    this.market = market;
    this.price = price;
  }
}
