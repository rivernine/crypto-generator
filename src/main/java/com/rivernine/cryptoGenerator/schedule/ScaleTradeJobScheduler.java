package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.ordersChance.OrdersChanceJobConfiguration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
          log.info("[currentStatus: 0] [getOrdersChanceForBidJob] ");
          collectMarketJobConfiguration.getOrdersChanceForBidJob(market);
          break;
        case 1:
          // 매수 전 상태
          // 분석 후 매수주문
          log.info("[currentStatus: 1] [analysisForBidMarketJob] ");
          jobLauncher.run(analysisForBidMarketJob, new JobParametersBuilder()
                                                        .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                                                        .toJobParameters());
          break;
        case 10:
          // 매수주문완료 상태
          // 체결 여부를 파악
          log.info("[currentStatus: 10] [getOrdersChanceForAskJob] ");
          ordersChanceJobConfiguration.getOrdersChanceForAskJob(market, statusProperties.getUuid());
          break;
        case 11:
          // 매수체결 상태
          // 분석 후 매도주문
          log.info("[currentStatus: 11] [analysisForAskMarketJob]");
          jobLauncher.run(analysisForAskMarketJob, new JobParametersBuilder()
                                                        .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                                                        .toJobParameters());
          break;
        case 20:
          // 매도주문완료 상태
          // 체결 여부를 파악
          // 체결 시 1로 돌아감
          log.info("[currentStatus: 20] Done!");
          statusProperties.init();
          break;                
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }