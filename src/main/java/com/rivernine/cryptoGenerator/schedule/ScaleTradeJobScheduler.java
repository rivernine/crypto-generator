package com.rivernine.cryptoGenerator.schedule;

import java.util.List;

import com.rivernine.cryptoGenerator.config.ScaleTradeStatusProperties;
import com.rivernine.cryptoGenerator.config.StatusProperties;
import com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.AnalysisForScaleTradingJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.getCandle.GetCandleJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.OrdersJobConfiguration;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersChanceDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;

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
  @Value("${upbit.candleMinutes}")
  private String candleMinutes;

  private final StatusProperties statusProperties;
  private final ScaleTradeStatusProperties scaleTradeStatusProperties;

  private final GetCandleJobConfiguration getCandleJobConfiguration;
  private final AnalysisForScaleTradingJobConfiguration analysisForScaleTradingJobConfiguration;
  private final OrdersJobConfiguration ordersJobConfiguration;

  @Scheduled(fixedDelay = 1000)
  public void runGetCandlesJob() {
    // log.info("[currentStatus: "+statusProperties.getCurrentStatus()+"] [getCandlesJob] ");
    getCandleJobConfiguration.getCandlesJob(market, candleMinutes, "3");
    // getCandleJobConfiguration.printCandlesJob();
  }

  @Scheduled(fixedDelay = 1000)
  public void runScaleTradingJob() {
    List<CandleDto> candles;
    CandleDto curCandle;
    OrdersChanceDto orderChanceDtoForBid;
    OrdersChanceDto orderChanceDtoForAsk;
    OrdersResponseDto ordersBidResponseDto;
    OrdersResponseDto ordersAskResponseDto;
    OrdersResponseDto orderResponseDto;
    OrdersResponseDto cancelOrderResponse;
    int level;
    String lastBidTime;
    String lastConclusionTime;
    String myTotalBalance;
    String bidBalance;
    String uuid;
    String askPrice;
    Double myTotalBalanced;
    Double bidBalanced;

    

    try {
      switch(statusProperties.getCurrentStatus()) {
        case -1:  
          // [ init step ]
          curCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
          lastConclusionTime = scaleTradeStatusProperties.getLastConclusionTime();
          if( lastConclusionTime.equals(curCandle.getCandleDateTime())) {
            log.info("Rest for a few minutes");
          } else {
            statusProperties.init();
            scaleTradeStatusProperties.init();
            statusProperties.setCurrentStatus(0);
            log.info("[changeStatus: -1 -> 0] [currentStatus: "+statusProperties.getCurrentStatus()+"] [analysisCandles step] ");
          }
          break;
        case 0: 
          // [ analysisCandles step ]
          candles = analysisForScaleTradingJobConfiguration.getRecentCandlesJob(2);
          if(analysisForScaleTradingJobConfiguration.analysisCandlesJob(candles, 2)) {
            statusProperties.setCurrentStatus(10);
            log.info("It's time to bid!!");
            log.info("[changeStatus: 0 -> 10] [currentStatus: "+statusProperties.getCurrentStatus()+"] [bid step] ");
          }
          break;
        case 10:
          // [ bid step ]
          curCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
          orderChanceDtoForBid = ordersJobConfiguration.getOrdersChanceForBidJob(market);
          myTotalBalance = orderChanceDtoForBid.getBalance();
          bidBalance = scaleTradeStatusProperties.getBalancePerLevel().get(scaleTradeStatusProperties.getLevel());
          myTotalBalanced = Double.parseDouble(myTotalBalance);
          bidBalanced = Double.parseDouble(bidBalance);
          if(myTotalBalanced.compareTo(bidBalanced) != -1) {
            ordersBidResponseDto = ordersJobConfiguration.bidJob(market, bidBalance);
            if(ordersBidResponseDto.getSuccess()) {
              scaleTradeStatusProperties.addBidInfoPerLevel(ordersBidResponseDto);
              scaleTradeStatusProperties.addBalance(bidBalance);
              scaleTradeStatusProperties.addFee(ordersBidResponseDto.getPaidFee());
              scaleTradeStatusProperties.setBidTime(curCandle.getCandleDateTime());
              statusProperties.setCurrentStatus(11);
              log.info("[changeStatus: 10 -> 11] [currentStatus: "+statusProperties.getCurrentStatus()+"] [wait for locked step] ");
            } else {
              log.info("Error during bidding");
            }
          } else {
            log.info("Not enough money. Loss cut");
            statusProperties.setCurrentStatus(999);
            log.info("[changeStatus: 10 -> 999] [currentStatus: "+statusProperties.getCurrentStatus()+"] [loss cut step] ");
          }
          break;
        case 11:
          // [ wait for locked step ]
          orderChanceDtoForBid = ordersJobConfiguration.getOrdersChanceForBidJob(market);
          if(orderChanceDtoForBid.getLocked().equals("0.0")) {
            statusProperties.setCurrentStatus(20);
            log.info("[changeStatus: 11 -> 20] [currentStatus: "+statusProperties.getCurrentStatus()+"] [ask step] ");
          } else {
            log.info("Locked my balance: " + orderChanceDtoForBid.getLocked());
          }
          break;
        case 20:
          // [ ask step ]
          orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
          if(Double.parseDouble(orderChanceDtoForAsk.getBalance()) * Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice()) > 5000.0){
            askPrice = analysisForScaleTradingJobConfiguration.getAskPriceJob(orderChanceDtoForAsk);
            ordersAskResponseDto = ordersJobConfiguration.askJob(market, orderChanceDtoForAsk.getBalance(), askPrice);
            if(ordersAskResponseDto.getSuccess()) {
              scaleTradeStatusProperties.addAskInfoPerLevel(ordersAskResponseDto);
              statusProperties.setCurrentStatus(30);
              log.info("[changeStatus: 20 -> 30] [currentStatus: "+statusProperties.getCurrentStatus()+"] [wait step] ");
            } else {
              log.info("Error during asking");
            }
          } else {
            log.info("Not enough coin balance");
          }
          break;
        case 30:
          // [ wait step ]
          orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
          curCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
          level = scaleTradeStatusProperties.getLevel();
          uuid = scaleTradeStatusProperties.getAskInfoPerLevel().get(level).getUuid();
          lastBidTime = scaleTradeStatusProperties.getBidTime();
          
          if( !lastBidTime.equals(curCandle.getCandleDateTime()) && 
              curCandle.getFlag() == -1 &&
              curCandle.getTradePrice().compareTo(Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice())) == -1 ) {
            statusProperties.setCurrentStatus(31);
            log.info("[changeStatus: 30 -> 31] [currentStatus: "+statusProperties.getCurrentStatus()+"] [cancel ask order step] ");
          } else {
            orderResponseDto = ordersJobConfiguration.getOrderJob(uuid);
            if(orderResponseDto.getState().equals("wait")) {
              log.info(curCandle.toString());
              log.info("Keep waiting");
            } else if(orderResponseDto.getState().equals("done")) {
              log.info("Ask conclusion!!");
              scaleTradeStatusProperties.setLastConclusionTime(curCandle.getCandleDateTime());
              statusProperties.setCurrentStatus(-1);
              log.info("[changeStatus: 30 -> -1] [currentStatus: "+statusProperties.getCurrentStatus()+"] [init step] ");
            }
          }
          break;
        case 31:
          // [ cancel ask order step ]
          level = scaleTradeStatusProperties.getLevel();
          uuid = scaleTradeStatusProperties.getAskInfoPerLevel().get(level).getUuid();
          cancelOrderResponse = ordersJobConfiguration.deleteOrderJob(uuid);
          log.info("level: " + level + ", uuid: " + uuid);
          log.info(cancelOrderResponse.toString());
          if(cancelOrderResponse.getSuccess()){
            scaleTradeStatusProperties.increaseLevel();  
            statusProperties.setCurrentStatus(10);
            log.info("[changeStatus: 31 -> 10] [currentStatus: "+statusProperties.getCurrentStatus()+"] [bid step] ");
          } else {
            log.info("Error during cancelOrder");
          }
          break;
        case 999:   
          // [ loss cut step ]
          orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
          if(Double.parseDouble(orderChanceDtoForAsk.getBalance()) * Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice()) > 5000.0){
            ordersAskResponseDto = ordersJobConfiguration.askJob(market, orderChanceDtoForAsk.getBalance());
            if(ordersAskResponseDto.getSuccess()) {
              statusProperties.setCurrentStatus(-1);
              log.info("[changeStatus: 999 -> -1] [currentStatus: "+statusProperties.getCurrentStatus()+"] [init step] ");
            } else {
              log.info("Error during asking");
            }
          } else {
            log.info("Not enough coin balance");
          }
          break; 
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }
}