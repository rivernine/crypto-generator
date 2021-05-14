package com.rivernine.cryptoGenerator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Component
@NoArgsConstructor
@Getter
@Setter
class Status {
  private int currentStatus;

  @Builder
  public Status(int currentStatus) {
    this.currentStatus = currentStatus;
  }
}
