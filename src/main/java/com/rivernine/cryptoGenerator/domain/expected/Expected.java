package com.rivernine.cryptoGenerator.domain.expected;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Expected {
  @Id
  private String market;
  @Column(nullable = false)
  private Double price;

  @Builder
  public Expected(String market, Double price){
    this.market = market;
    this.price = price;
  }
}
