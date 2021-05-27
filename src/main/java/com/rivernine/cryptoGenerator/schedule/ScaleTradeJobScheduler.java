package com.rivernine.cryptoGenerator.schedule;

import java.util.List;

import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.AnalysisForScaleTradingJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.exchange.ExchangeJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.exchange.dto.ExchangeResponseDto;
import com.rivernine.cryptoGenerator.schedule.getCandle.GetCandleJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.ordersChance.OrdersChanceJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;

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
  private final ExchangeJobConfiguration exchangeJobConfiguration;

  private final CryptoApi cryptoApi;

  @Scheduled(fixedDelay = 1000)
  public void runGetCandlesJob() {
    log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [getCandlesJob market/minutes/count] ");
    getCandleJobConfiguration.getCandlesJob(market, "1", "3");
    // getCandleJobConfiguration.printCandlesJob();
  }

  @Scheduled(fixedDelay = 1000)
  public void runScaleTradingJob() {
    try {
      switch(statusProperties.getCurrentStatus()) {
        case -1:
          // statusProperties.init();
          scaleTradeStatusProperties.init();
          // go to 0
          statusProperties.setCurrentStatus(0);
          break;
        case 0:        
          log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [analysisCandles] ");
          List<CandleDto> candles = analysisForScaleTradingJobConfiguration.getRecentCandlesJob("1", 2);
          if(analysisForScaleTradingJobConfiguration.analysisCandlesJob(candles)) {
            // go to 10
            statusProperties.setCurrentStatus(10);
          } else {
            log.info("Stay");
          }
          break;
        case 10:
          log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [bid step] ");
          OrdersChanceDto orderChanceDtoForBid = ordersChanceJobConfiguration.getOrdersChanceForBidJob(market);
          int currentLevel = scaleTradeStatusProperties.getLevel();
          String myTotalBalance = orderChanceDtoForBid.getBalance();
          String bidBalance = scaleTradeStatusProperties.getBalancePerLevel().get(currentLevel);

          if(Double.parseDouble(myTotalBalance) > Double.parseDouble(bidBalance)) {
            ExchangeResponseDto exchangeBidResponseDto = exchangeJobConfiguration.bidJob(market, bidBalance);
            if(exchangeBidResponseDto.getSuccess()) {
              scaleTradeStatusProperties.addBidInfoPerLevel(exchangeBidResponseDto);
              scaleTradeStatusProperties.addBalance(bidBalance);
              scaleTradeStatusProperties.addFee(exchangeBidResponseDto.getPaidFee());
              // go to 20
              statusProperties.setCurrentStatus(20);
            } else {
              log.info("Error during bidding");
            }
          } else {
            log.info("Not enough balance");
          }
          break;
        case 20:
          log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [ask step] ");
          OrdersChanceDto orderChanceDtoForAsk = ordersChanceJobConfiguration.getOrdersChanceForAskJob(market);
          if(Double.parseDouble(orderChanceDtoForAsk.getBalance()) * Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice()) > 5000.0){
            String askPrice = analysisForScaleTradingJobConfiguration.getAskPriceJob(orderChanceDtoForAsk);
            log.info("market:volume:price");
            log.info(market + ":" + orderChanceDtoForAsk.getBalance() + ":" + askPrice);
            ExchangeResponseDto exchangeAskResponseDto = exchangeJobConfiguration.askJob(market, orderChanceDtoForAsk.getBalance(), askPrice);
            if(exchangeAskResponseDto.getSuccess()) {
              scaleTradeStatusProperties.addAskInfoPerLevel(exchangeAskResponseDto);
              // go to 999
              statusProperties.setCurrentStatus(999);
            } else {
              log.info("Error during asking");
            }
          } else {
            log.info("Not enough coin balance");
          }
          break;
        // case 2:
        //   log.info("[ask] ");
        //   break;
        // case 3:
        //   log.info("[analysisCandles to bid/ask]");
        //   break;
        case 999:
          log.info("Done!");
          break; 
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}