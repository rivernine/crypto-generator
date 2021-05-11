package com.rivernine.cryptoGenerator.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rivernine.cryptoGenerator.schedule.collectMarket.CollectMarketJobConfiguration;

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
  // private final AnalysisMarketJobConfiguration analysisMarketJobConfiguration;

  @Scheduled(fixedRateString = "${schedule.collectDelay}")
  public void runCollectMarketJob() {
    collectMarketJobConfiguration.collectMarketJob();
  }

  @Scheduled(fixedDelay = 5000)
  public void runAnalysisMarketJob() {
    System.out.println("runAnalysisMarketJob");
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

  // @Scheduled(fixedDelay = 10000)
  // public void runCheckCollectMarketJob() {
  //   collectMarketJobConfiguration.checkCollectMarketJob();
  // }
}
