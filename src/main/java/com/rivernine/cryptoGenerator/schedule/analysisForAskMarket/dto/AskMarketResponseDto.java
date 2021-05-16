package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.dto;

import java.time.LocalDateTime;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
public class AskMarketResponseDto {
  private String uuid;
  private String market;
  private Boolean success;

  @Builder
  public AskMarketResponseDto(String uuid, String market, Boolean success) {
    this.uuid = uuid;
    this.market = market;
    this.success = success;
  }
}