package com.rivernine.cryptoGenerator.schedule.analysisForAskMarket;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForAskMarket.dto.AskMarketResponseDto;
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
  private final StatusProperties statusProperties;
  
  @Value("${schedule.chunkSize}")
  private int chunkSize;
  @Value("${upbit.market}")
  private String market;
  private String volume;

  @Bean(name = JOB_NAME)
  public Job analysisForAskMarketJob() {
    return jobBuilderFactory.get(JOB_NAME)
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(askStep())
            .on("FAILED")
            .end()
            .from(askStep())
            .on("*")
            .to(setStatusStep())
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
              AskMarketResponseDto askMarketResponseDto = analysisForAskMarketService.ask(market, volume);
              if(askMarketResponseDto.getSuccess()){
                log.info("Success request ask, UUID: " + askMarketResponseDto.getUuid());
                stepContribution.setExitStatus(ExitStatus.COMPLETED);   
              } else {
                log.info("Failed request ask");
                stepContribution.setExitStatus(ExitStatus.FAILED); 
              }
              return RepeatStatus.FINISHED;
            }).build();
  }


  @Bean(name = JOB_NAME + "_setStatusStep")
  public Step setStatusStep() {
    return stepBuilderFactory.get(JOB_NAME + "_setStatusStep")
            .tasklet((stepContribution, chunkContext) -> {
              log.info(JOB_NAME + "_setStatusStep");
              log.info("Set status (11 -> 20)");
              statusProperties.setCurrentStatus(20);
              stepContribution.setExitStatus(ExitStatus.COMPLETED); 
              return RepeatStatus.FINISHED;
            }).build();
  }
}
