package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
public class JobScheduler {

  private final JobLauncher jobLauncher;
  private final CollectMarketJobConfiguration collectMarketJobConfiguration;
  private final OrdersChanceJobConfiguration ordersChanceJobConfiguration;

  @Qualifier("analysisForBidMarket")
  @Autowired
  private final Job analysisForBidMarketJob;
  @Qualifier("analysisForAskMarket")
  @Autowired
  private final Job analysisForAskMarketJob;

  private final StatusProperties statusProperties;

  @Value("${upbit.market}")	
  private String market;  

  // Collect markets
  @Scheduled(fixedRateString = "${schedule.collectDelay}")
  public void runCollectMarketJob() {
    // collectMarketJobConfiguration.collectMarketJob(market);
    log.info("runCollectMarketJob()");
  }

  // Analysis market
  @Scheduled(fixedRateString = "${schedule.analysisDelay}")
  public void runAnalysisMarketJob() {
    try {
      log.info(Integer.toString(statusProperties.getCurrentStatus()));
      switch(statusProperties.getCurrentStatus()) {
        case 0:        
          // 프로그램 시작상태
          // db에서 현재 저장된 상태를 읽어옴
          log.info("[currentStatus: 0] Application Start! ");
          statusProperties.setCurrentStatus(1);
          break;
        case 1:
          // 매수 전 상태
          // 분석 후 매수주문
          log.info("[currentStatus: 1] Crypto Start! ");
          log.info("[currentStatus: 1] Run analysis for bid.");
          jobLauncher.run(analysisForBidMarketJob, new JobParametersBuilder()
                                                        .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                                                        .toJobParameters());
          break;
        case 10:
          // 매수주문완료 상태
          // 체결 여부를 파악
          log.info("[currentStatus: 10] Complete request! ");
          log.info("[currentStatus: 10] Checking response of bidding.");
          ordersChanceJobConfiguration.getOrdersChanceForAskJob(market);
          break;
        case 11:
          // 매수체결 상태
          // 분석 후 매도주문
          log.info("[currentStatus: 11] Complete bidding!");
          log.info("[currentStatus: 11] Run analysis for ask.");
          // jobLauncher.run(analysisForAskMarketJob, new JobParametersBuilder()
          //                                               .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
          //                                               .toJobParameters());
          statusProperties.setCurrentStatus(20);
          break;
        case 20:
          // 매도주문완료 상태
          // 체결 여부를 파악
          // 체결 시 1로 돌아감
          log.info("[currentStatus: 20] Done!");
          statusProperties.setCurrentStatus(0);
          break;                
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}
