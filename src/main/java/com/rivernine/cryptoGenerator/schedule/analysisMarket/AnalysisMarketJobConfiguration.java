package com.rivernine.cryptoGenerator.schedule.analysisMarket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.expected.ExpectedRepository;
import com.rivernine.cryptoGenerator.schedule.analysisMarket.dto.AnalysisMarketResponseDto;
import com.rivernine.cryptoGenerator.schedule.analysisMarket.service.AnalysisMarketService;

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
public class AnalysisMarketJobConfiguration {
  // private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  // private final EntityManagerFactory entityManagerFactory;
  private final AnalysisMarketService analysisMarketService;
  
  @Value("${schedule.chunkSize}")
  private int chunkSize;
  @Value("${schedule.analysisScope}")
  private int analysisScope;

  @Bean
  public Job analysisMarketJob() {
    return jobBuilderFactory.get("analysisMarketJob")
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(saveStep())
            .end()
            .build();
  }

  // Analysis
  @Bean
  public Step analysisStep() {
    return stepBuilderFactory.get("analysisStep")
            .tasklet((stepContribution, chunkContext) -> {              
              System.out.println(LocalDateTime.now().minusSeconds(analysisScope));
              // List<AnalysisMarketResponseDto> analysisMarketList = analysisMarketService.findByTradeDateAfter(LocalDateTime.now().minusSeconds(analysisScope));
              // for( AnalysisMarketResponseDto analysisMarket: analysisMarketList ){
              //   System.out.println(analysisMarket);
              // }
              stepContribution.setExitStatus(ExitStatus.FAILED);
              return RepeatStatus.FINISHED;
            }).build();
  }

  // Save
  @Bean
  public Step saveStep() {
    return stepBuilderFactory.get("saveStep")
            .tasklet((stepContribution, chunkContext) -> {
              // ExpectedRepository.save(Expected().builder().build())
              return RepeatStatus.FINISHED;
            }).build();
  }
}
