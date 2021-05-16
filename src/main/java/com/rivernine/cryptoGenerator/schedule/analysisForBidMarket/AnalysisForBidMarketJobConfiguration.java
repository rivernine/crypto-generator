package com.rivernine.cryptoGenerator.schedule.analysisForBidMarket;

import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.dto.BidMarketResponseDto;
import com.rivernine.cryptoGenerator.schedule.analysisForBidMarket.service.AnalysisForBidMarketService;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AnalysisForBidMarketJobConfiguration {
  public static final String JOB_NAME = "analysisForBidMarket";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final AnalysisForBidMarketService analysisForBidMarketService;
  private final StatusProperties statusProperties;

  @Value("${schedule.chunkSize}")
  private int chunkSize;
  @Value("${upbit.market}")
  private String market;
  @Value("${upbit.price}")
  private int price;

  @Bean(name = JOB_NAME)
  @Primary
  public Job analysisForBidMarketJob() {
    return jobBuilderFactory.get(JOB_NAME)
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(bidStep())
            .on("FAILED")
            .end()
            .from(bidStep())
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
              if(analysisForBidMarketService.analysis()){
                stepContribution.setExitStatus(ExitStatus.COMPLETED);  
              } else {
                stepContribution.setExitStatus(ExitStatus.FAILED);
              }
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean(name = JOB_NAME + "_bidStep")
  public Step bidStep() {
    return stepBuilderFactory.get(JOB_NAME + "_bidStep")
            .tasklet((stepContribution, chunkContext) -> {
              log.info(JOB_NAME + "_bidStep");
              BidMarketResponseDto bidMarketResponseDto = analysisForBidMarketService.bid(market, Integer.toString(price));
              if(bidMarketResponseDto.getSuccess()){
                log.info("Success request bid, UUID: " + bidMarketResponseDto.getUuid());
                stepContribution.setExitStatus(ExitStatus.COMPLETED);   
              } else {
                log.info("Failed request bid");
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
              log.info("Set status (1 -> 10)");
              statusProperties.setCurrentStatus(10);
              stepContribution.setExitStatus(ExitStatus.COMPLETED); 
              return RepeatStatus.FINISHED;
            }).build();
  }
}
