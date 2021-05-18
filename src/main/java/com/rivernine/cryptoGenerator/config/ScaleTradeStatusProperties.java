package com.rivernine.cryptoGenerator.config;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Component
@NoArgsConstructor
@Getter
@Setter
public class ScaleTradeStatusProperties {
  // 0: getOrderChance
  // 10: analysisForBid
  // 11: bid
  // 12: listen
  // 13: 
  public int currentStatus;
}
