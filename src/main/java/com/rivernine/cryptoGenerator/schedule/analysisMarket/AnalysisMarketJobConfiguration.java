package com.rivernine.cryptoGenerator.schedule.analysisMarket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
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
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final AnalysisMarketService analysisMarketService;
  
  @Value("${chunkSize}")
  private int chunkSize;
  @Value("${analysisScope}")
  private int analysisScope;

  @Bean
  public Job analysisMarketJob() {
    return jobBuilderFactory.get("analysisMarketJob")
            .start(analysisStep())
            .on("FAILED")
            .end()
            .from(analysisStep())
            .on("*")
            .to(tradeStep())
            .end()
            .build();
  }

  @Bean
  public Step analysisStep() {
    return stepBuilderFactory.get("analysisStep")
            .tasklet((stepContribution, chunkContext) -> {
              // 분석 단계
              List<Crypto> list = analysisMarketService.findByTradeDateAfter(LocalDateTime.now().minusNanos(analysisScope));
              stepContribution.setExitStatus(ExitStatus.FAILED);
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step tradeStep() {
    return stepBuilderFactory.get("tradeStep")
            .tasklet((stepContribution, chunkContext) -> {
              // 거래 단계
              return RepeatStatus.FINISHED;
            }).build();
  }
}
