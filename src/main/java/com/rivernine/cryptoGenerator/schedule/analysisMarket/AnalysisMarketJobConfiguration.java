// package com.rivernine.cryptoGenerator.schedule.analysisMarket;

// import javax.persistence.EntityManagerFactory;

// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.Step;
// import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
// import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @RequriedArgsConstructor
// @Configuration
// public class AnalysisMarketJobConfiguration {
//   private final JobBuilderFactory jobBuilderFactory;
//   private final StepBuilderFactory stepBuilderFactory;
//   private final EntityManagerFactory entityManagerFactory;

//   private int chunkSize = 10;

//   @Bean
//   public Job analysisMarketJob() {
//     return jobBuilderFactory.get("analysisMarketJob")
//             .start(analysisStep())
//             .on("FAILED")
//             .end()
//             .from(analysisStep())
//             .on("*")
//             .to(tradeStep())
//             .end()
//             .build();
//   }

//   @Bean
//   public Step analysisStep() {
//     return stepBuilderFactory.get("analysisStep")
//             .tasklet((stepContribution, chunkContext) -> {
//               // 분석 단계
//             })            
//             .build();
//   }

//   @Bean
//   public Step tradeStep() {
//     return stepBuilderFactory.get("tradeStep")
//             .tasklet((stepContribution, chunkContext) -> {
//               // 거래 단계
//             })            
//             .build();
//   }
// }
