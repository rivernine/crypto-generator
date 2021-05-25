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
  public void runGetRecentCandlesJob() {
    log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [getRecentCandlesJob] ");
    List<CandleDto> candles = analysisForScaleTradingJobConfiguration.getRecentCandlesJob("1", 2);
    if(analysisForScaleTradingJobConfiguration.analysisCandlesJob(candles)) {
      log.info("Bid");
    } else {
      log.info("Stay");
    }
  }

  public void runGetOrdersChanceForBidJob() {
    OrdersChanceDtoForBid ordersChanceDtoForBid = ordersChanceJobConfiguration.getOrdersChanceForBidJob(market);
  }


  // @Scheduled(fixedDelay = 1000)
  public void runScaleTradingJob() {
    try {
      switch(statusProperties.getCurrentStatus()) {
        case 0:        
          log.info("[currentStatus: 0] [getCandlesJob market/minutes/count] ");
          break;
        case 1:
          log.info("[currentStatus: 1] [analysisForBidMarketJob] ");
          break;
        case 10:
          log.info("[currentStatus: 10] [getOrdersChanceForAskJob] ");
          break;
        case 11:
          log.info("[currentStatus: 11] [analysisForAskMarketJob]");
          break;
        case 20:
          log.info("[currentStatus: 20] Done!");
          break;                
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}