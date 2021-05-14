package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket;

import com.rivernine.cryptoGenerator.common.UpbitApi;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.service.AnalysisForAskMarketService;

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
public class AnalysisForAskMarketJobConfiguration {
  public static final String JOB_NAME = "analysisForAskMarket";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final AnalysisForAskMarketService analysisForAskMarketService;
  private final UpbitApi upbitApi;
  
  @Value("${schedule.chunkSize}")
  private int chunkSize;

  @Bean(name = JOB_NAME)
  public Job analysisForAskMarketJob() {
    return jobBuilderFactory.get(JOB_NAME)
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(askStep())
            .end()
            .build();
  }

  @Bean(name = JOB_NAME + "_analysisStep")
  public Step analysisStep() {
    return stepBuilderFactory.get(JOB_NAME + "_analysisStep")
            .tasklet((stepContribution, chunkContext) -> {              
              log.info(JOB_NAME + "_analysisStep");
              if(analysisForAskMarketService.analysis()){
                stepContribution.setExitStatus(ExitStatus.COMPLETED);  
              } else {
                stepContribution.setExitStatus(ExitStatus.FAILED);  
              }
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean(name = JOB_NAME + "_askStep")
  public Step askStep() {
    return stepBuilderFactory.get(JOB_NAME + "_askStep")
            .tasklet((stepContribution, chunkContext) -> {
              log.info(JOB_NAME + "_askStep");
              // upbitApi.sellMarket();
              return RepeatStatus.FINISHED;
            }).build();
  }
}
