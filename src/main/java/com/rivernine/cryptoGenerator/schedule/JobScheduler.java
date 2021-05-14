package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

  @Qualifier("analysisForBidMarket")
  @Autowired
  private final Job analysisForBidMarketJob;

  @Qualifier("analysisForAskMarket")
  @Autowired
  private final Job analysisForAskMarketJob;

  @Value("${testParameter.currentStatus}")
  private int currentStatus;

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
                                          .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                                          .toJobParameters();
      log.info("currentStatus : " + Integer.toString(currentStatus));
      if (currentStatus == 0){
        log.info("This is Bid Job !!!!");
        jobLauncher.run(analysisForBidMarketJob, jobParameters);
      } else if (currentStatus == 1){
        log.info("This is Ask Job !!!!");
        jobLauncher.run(analysisForAskMarketJob, jobParameters);
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}
