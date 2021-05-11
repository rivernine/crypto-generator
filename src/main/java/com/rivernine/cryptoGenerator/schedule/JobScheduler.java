package com.rivernine.cryptoGenerator.schedule;

import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JobScheduler {

  private final CollectMarketJobConfiguration collectMarketJobConfiguration;
  // private final AnalysisMarketJobConfiguration analysisMarketJobConfiguration;

  @Scheduled(fixedRateString = "${schedule.collectDelay}")
  public void runCollectMarketJob() {
    collectMarketJobConfiguration.collectMarketJob();
  }

  // @Scheduled(fixedDelay = 10000)
  // public void runCheckCollectMarketJob() {
  //   collectMarketJobConfiguration.checkCollectMarketJob();
  // }
}
