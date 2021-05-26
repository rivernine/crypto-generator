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
              if(statusProperties.getCurrentStatus() != 11){
                stepContribution.setExitStatus(ExitStatus.FAILED);  
                return RepeatStatus.FINISHED;
              }
              if(analysisForAskMarketService.analysis(market, statusProperties)) {
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
              if( !statusProperties.getAskRunning() || statusProperties.getAskPending() ) {
                statusProperties.setAskRunning(true);
                statusProperties.setAskPending(true);

                // tmp
                // String myTotalBalance = statusProperties.getOrdersChanceDtoForAsk().getTradeVolume();
                String myTotalBalance = "1.0";

                AskMarketResponseDto askMarketResponseDto = analysisForAskMarketService.ask(market, myTotalBalance);
                if(askMarketResponseDto.getSuccess()){
                  log.info("Success request ask, UUID: " + askMarketResponseDto.getUuid());
                  log.info("Set status (11 -> 20)");
                  stepContribution.setExitStatus(ExitStatus.COMPLETED);   
                  statusProperties.setCurrentStatus(20);
                  statusProperties.setAskPending(false);
                } else {
                  log.info("Failed request ask");
                  stepContribution.setExitStatus(ExitStatus.FAILED); 
                }
                statusProperties.setAskRunning(false);
              } else {
                log.info("Another asking is running");
                stepContribution.setExitStatus(ExitStatus.FAILED); 
              }
              return RepeatStatus.FINISHED;
            }).build();
  }

}
