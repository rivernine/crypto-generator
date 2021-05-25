package com.rivernine.cryptoGenerator.schedule;

import java.util.List;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.AnalysisForScaleTradingJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.getCandle.GetCandleJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.OrdersChanceJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDtoForBid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScaleTradeJobScheduler {

  @Value("${upbit.market}")	
  private String market;  

  private final StatusProperties statusProperties;
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;
  private final GetCandleJobConfiguration getCandleJobConfiguration;
  private final AnalysisForScaleTradingJobConfiguration analysisForScaleTradingJobConfiguration;
  private final OrdersChanceJobConfiguration ordersChanceJobConfiguration;

  @Scheduled(fixedDelay = 1000)
  public void runGetCandlesJob() {
    log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [getCandlesJob market/minutes/count] ");
    getCandleJobConfiguration.getCandlesJob(market, "1", "2");
    getCandleJobConfiguration.printCandlesJob();
  }

  @Scheduled(fixedDelay = 1000)
  public void runAnalysisCandlesJob() {
    log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [getRecentCandlesJob] ");
    List<CandleDto> candles = analysisForScaleTradingJobConfiguration.getRecentCandlesJob("1", 2);
    if(analysisForScaleTradingJobConfiguration.analysisCandlesJob(candles)) {
      log.info("Bid");
    } else {
      log.info("Stay");
    }
  }

  // @Scheduled(fixedDelay = 1000)
  public void runScaleTradingJob() {
    try {
      switch(statusProperties.getCurrentStatus()) {
        case -1:
          scaleTradeStatusProperties.init();
          break;
        case 0:        
          log.info("[analysisCandles to bid");
          runAnalysisCandlesJob();
          break;
        case 1:
          log.info("[bid] ");
          break;
        case 11:
          log.info("[listen to complete]");
          break;
        case 2:
          log.info("[ask] ");
          break;
        case 3:
          log.info("[analysisCandles to bid/ask]");
          break;
        case 6:
          log.info("Done!");
          break; 
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}