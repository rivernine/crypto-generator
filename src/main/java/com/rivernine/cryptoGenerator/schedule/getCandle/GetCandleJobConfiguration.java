package com.rivernine.cryptoGenerator.schedule.getCandle;

import com.rivernine.cryptoGenerator.schedule.getCandle.service.GetCandleService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetCandleJobConfiguration {

  private final GetCandleService getCandleService;

  public void getCandlesJob(String market, String minutes, String count) {
    getCandleService.getCandles(market, minutes, count);
  }

}
