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
  @Value("${upbit.balance}")
  private Double balance;

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
            .end()
            .build();
  }

  @Bean(name = JOB_NAME + "_analysisStep")
  public Step analysisStep() {
    return stepBuilderFactory.get(JOB_NAME + "_analysisStep")
            .tasklet((stepContribution, chunkContext) -> {     
              if(statusProperties.getCurrentStatus() != 1){
                stepContribution.setExitStatus(ExitStatus.FAILED);  
                return RepeatStatus.FINISHED;
              }         
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
              if( !statusProperties.getBidRunning() || statusProperties.getBidPending() ){
                statusProperties.setBidRunning(true);
                statusProperties.setBidPending(true);

                String myBalance = statusProperties.getOrdersChanceDtoForBid().getBalance();
                String myBalanceExceptFee = Double.toString(Double.parseDouble(myBalance) * 0.95);
                if(balance != -1.0){
                  myBalanceExceptFee = Double.toString(balance);
                }
                BidMarketResponseDto bidMarketResponseDto = analysisForBidMarketService.bid(market, myBalanceExceptFee);

                if(bidMarketResponseDto.getSuccess()){
                  log.info("Success request bid, UUID: " + bidMarketResponseDto.getUuid());
                  log.info("Set status (1 -> 10)");
                  stepContribution.setExitStatus(ExitStatus.COMPLETED);
                  statusProperties.setUuid(bidMarketResponseDto.getUuid());
                  statusProperties.setBidPending(false);
                  statusProperties.setCurrentStatus(10);
                } else {
                  log.info("Failed request bid");
                  stepContribution.setExitStatus(ExitStatus.FAILED); 
                }
                statusProperties.setBidRunning(false);
              } else {
                log.info("Another bidding is running");
                stepContribution.setExitStatus(ExitStatus.FAILED); 
              }
              return RepeatStatus.FINISHED;
            }).build();
  }

}
