package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScaleTradeJobScheduler {

  @Value("${upbit.market}")	
  private String market;  

  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final CollectMarketJobConfiguration collectMarketJobConfiguration;

  // @Scheduled(fixedRateString = "${schedule.analysisDelay}")
  @Scheduled(fixedDelay = 1000)
  public void runScaleTradingJob() {
    try {
      switch(scaleTradeStatusProperties.getCurrentStatus()) {
        case 0:        
          log.info("[currentStatus: 0] [getCandlesJob market/minutes/count] ");
          collectMarketJobConfiguration.getCandlesJob(market, "5", "1");
          break;
        case 1:
          log.info("[currentStatus: 1] [analysisForBidMarketJob] ");
          break;
        case 10:
          log.info("[currentStatus: 10] [getOrdersChanceForAskJob] ");
          break;
        case 11:
          log.info("[currentStatus: 11] [analysisForAskMarketJob]");
          break;
        case 20:
          log.info("[currentStatus: 20] Done!");
          break;                
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }