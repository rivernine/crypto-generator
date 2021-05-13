package com.rivernine.cryptoGenerator.schedule.analysisForSellMarket;

import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.schedule.analysisForSellMarket.service.AnalysisForSellMarketService;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AnalysisForSellMarketJobConfiguration {
  public static final String JOB_NAME = "analysisForSellMarket";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final AnalysisForSellMarketService analysisForSellMarketService;
  private final UpbitApi upbitApi;
  
  @Value("${schedule.chunkSize}")
  private int chunkSize;

  @Bean(name = JOB_NAME)
  public Job analysisForSellMarketJob() {
    return jobBuilderFactory.get(JOB_NAME)
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(sellStep())
            .end()
            .build();
  }

  @Bean(name = JOB_NAME + "_analysisStep")
  public Step analysisStep() {
    return stepBuilderFactory.get(JOB_NAME + "_analysisStep")
            .tasklet((stepContribution, chunkContext) -> {              
              log.info(JOB_NAME + "_analysisStep");
              if(analysisForSellMarketService.analysis()){
                stepContribution.setExitStatus(ExitStatus.COMPLETED);  
              } else {
                stepContribution.setExitStatus(ExitStatus.FAILED);  
              }
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean(name = JOB_NAME + "_sellStep")
  public Step sellStep() {
    return stepBuilderFactory.get(JOB_NAME + "_sellStep")
            .tasklet((stepContribution, chunkContext) -> {
              log.info(JOB_NAME + "_sellStep");
              upbitApi.sellMarket();
              return RepeatStatus.FINISHED;
            }).build();
  }
}
