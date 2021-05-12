package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.tradeMarket.TradeMarketJobConfiguration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobScheduler {

  private final JobLauncher jobLauncher;
  private final Job job;

  private final CollectMarketJobConfiguration collectMarketJobConfiguration;
  private final TradeMarketJobConfiguration tradeMarketJobConfiguration;

  // Collect markets
  @Scheduled(fixedRateString = "${schedule.collectDelay}")
  public void runCollectMarketJob() {
    collectMarketJobConfiguration.collectMarketJob();
  }

  // Analysis market
  @Scheduled(fixedDelay = 5000)
  public void runAnalysisMarketJob() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
                                          .addString("job.name", "analysisMarketJob")
                                          .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                                          .toJobParameters();
      jobLauncher.run(job, jobParameters);
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }

  Double tmpPrice = 5000.0;

  // Trade market
  // 실시간 가격 비교 후 거래
  @Scheduled(fixedDelay = 500)
  public void runTradeMarketJob() {
    Double currentPrice = tradeMarketJobConfiguration.getMarketJob();
    if(tradeMarketJobConfiguration.comparePrice(currentPrice)){
      // buy
    } else {
      // 
    }
  }

  // @Scheduled(fixedDelay = 10000)
  // public void runCheckCollectMarketJob() {
  //   collectMarketJobConfiguration.checkCollectMarketJob();
  // }
}
