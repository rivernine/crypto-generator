package com.rivernine.cryptoGenerator.schedule;

import java.util.List;
import java.util.Map;

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
    CandleDto lastCandle;
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

    level = scaleTradeStatusProperties.getLevel();

    try {
      switch(statusProperties.getCurrentStatus()) {
        case -1:  
          // [ init step ]
          lastCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
          lastConclusionTime = scaleTradeStatusProperties.getLastConclusionTime();
          if( lastConclusionTime.equals(lastCandle.getCandleDateTime())) {
            log.info("Rest for a few minutes");
          } else {
            log.info("[changeStatus: -1 -> 0] [currentStatus: "+statusProperties.getCurrentStatus()+"] [analysisCandles step] ");
            statusProperties.init();
            scaleTradeStatusProperties.init();
            statusProperties.setCurrentStatus(0);
          }
          break;
        case 0: 
          // [ analysisCandles step ]
          candles = analysisForScaleTradingJobConfiguration.getRecentCandlesJob(3);
          if(analysisForScaleTradingJobConfiguration.analysisCandlesJob(candles, 3)) {
            log.info("It's time to bid!!");
            log.info("[changeStatus: 0 -> 10] [currentStatus: "+statusProperties.getCurrentStatus()+"] [bid step] ");
            statusProperties.setCurrentStatus(10);
          }
          break;
        case 10:
          // [ bid step ]
          lastCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
          orderChanceDtoForBid = ordersJobConfiguration.getOrdersChanceForBidJob(market);
          myTotalBalance = orderChanceDtoForBid.getBalance();
          bidBalance = scaleTradeStatusProperties.getBalancePerLevel().get(scaleTradeStatusProperties.getLevel());
          myTotalBalanced = Double.parseDouble(myTotalBalance);
          bidBalanced = Double.parseDouble(bidBalance);
          if(myTotalBalanced.compareTo(bidBalanced) != -1) {
            Double endingPrice = lastCandle.getTradePrice();
            String bidVolume = String.format("%.8f", bidBalanced / endingPrice);
            
            ordersBidResponseDto = ordersJobConfiguration.bidJob(market, bidVolume, endingPrice.toString());
            if(ordersBidResponseDto.getSuccess()) {
              log.info("[changeStatus: 10 -> 30] [currentStatus: "+statusProperties.getCurrentStatus()+"] [wait step] ");
              ordersBidResponseDto.setTradePrice(endingPrice);
              scaleTradeStatusProperties.addBidInfoPerLevel(ordersBidResponseDto);
              scaleTradeStatusProperties.setBidTime(lastCandle.getCandleDateTime());
              scaleTradeStatusProperties.setWaitingBidOrder(true);
              statusProperties.setCurrentStatus(30);
            } else {
              log.info("Error during bidding");
            }
          } else {
            log.info("Not enough money. Loss cut");
            log.info("[changeStatus: 10 -> 999] [currentStatus: "+statusProperties.getCurrentStatus()+"] [loss cut step] ");
            statusProperties.setCurrentStatus(999);
          }
          break;      
        
        case 20:
          // [ ask step ]
          orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
          if(Double.parseDouble(orderChanceDtoForAsk.getBalance()) * Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice()) > 5000.0){
            askPrice = analysisForScaleTradingJobConfiguration.getAskPriceJob(orderChanceDtoForAsk);
            ordersAskResponseDto = ordersJobConfiguration.askJob(market, orderChanceDtoForAsk.getBalance(), askPrice);
            if(ordersAskResponseDto.getSuccess()) {
              log.info("[changeStatus: 20 -> 30] [currentStatus: "+statusProperties.getCurrentStatus()+"] [wait step] ");
              scaleTradeStatusProperties.addAskInfoPerLevel(ordersAskResponseDto);
              scaleTradeStatusProperties.setWaitingAskOrder(true);
              statusProperties.setCurrentStatus(30);
            } else {
              log.info("Error during asking");
            }
          } else {
            log.info("Not enough coin balance");
          }
          break;
        
        case 30:
          // [ wait step ]
          // about bid
          if(scaleTradeStatusProperties.getWaitingBidOrder()) {
            ordersBidResponseDto = scaleTradeStatusProperties.getBidInfoPerLevel().get(level);
            uuid = ordersBidResponseDto.getUuid();
            OrdersResponseDto newOrders = ordersJobConfiguration.getOrderJob(uuid);
            if(ordersBidResponseDto.getTrades().size() != newOrders.getTrades().size()) {
              log.info("Success bidding!!");              
              scaleTradeStatusProperties.updateBidInfoPerLevel(newOrders, level);
              scaleTradeStatusProperties.updateNewTrade();
              if(scaleTradeStatusProperties.getWaitingAskOrder()) {
                log.info("[changeStatus: 11 -> 41] [currentStatus: "+statusProperties.getCurrentStatus()+"] [cancel ask order for bid step] ");
                statusProperties.setCurrentStatus(41);
              } else {
                log.info("[changeStatus: 11 -> 20] [currentStatus: "+statusProperties.getCurrentStatus()+"] [ask step] ");
                statusProperties.setCurrentStatus(20);
              }
            } else {
              log.info("Wait for bid");
            }
            if(ordersBidResponseDto.getState().equals("done")) {
              scaleTradeStatusProperties.setWaitingBidOrder(false);
            }
          }

          // about ask
          if(scaleTradeStatusProperties.getWaitingAskOrder()) {
            orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
            ordersAskResponseDto = scaleTradeStatusProperties.getAskInfoPerLevel().get(level);
            lastCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
            uuid = ordersAskResponseDto.getUuid();
            lastBidTime = scaleTradeStatusProperties.getBidTime();
            
            if( !lastBidTime.equals(lastCandle.getCandleDateTime()) && 
                lastCandle.getFlag() == -1 &&
                analysisForScaleTradingJobConfiguration.compareCurPriceLastBidTradePrice(lastCandle.getTradePrice(), ordersBidResponseDto.getTradePrice())) {
              log.info("[changeStatus: 30 -> 42] [currentStatus: "+statusProperties.getCurrentStatus()+"] [cancel ask order step] ");
              statusProperties.setCurrentStatus(42);
            } else {
              orderResponseDto = ordersJobConfiguration.getOrderJob(uuid);
              if(orderResponseDto.getState().equals("wait")) {
                log.info("Wait for ask");
              } else if(orderResponseDto.getState().equals("done")) {
                log.info("Success asking!!");
                log.info("[changeStatus: 30 -> -1] [currentStatus: "+statusProperties.getCurrentStatus()+"] [init step] ");
                scaleTradeStatusProperties.setLastConclusionTime(lastCandle.getCandleDateTime());
                statusProperties.setCurrentStatus(-1);
              }
            }
          }
          
          break;          
        case 41:
          // [ cancel ask order for bid step ]
          uuid = scaleTradeStatusProperties.getAskInfoPerLevel().get(level).getUuid();
          cancelOrderResponse = ordersJobConfiguration.deleteOrderJob(uuid);
          if(cancelOrderResponse.getSuccess()){
            log.info("Success cancel order!!");
            log.info("[changeStatus: 41 -> 20] [currentStatus: "+statusProperties.getCurrentStatus()+"] [ask step] ");
            scaleTradeStatusProperties.setWaitingAskOrder(false);
            statusProperties.setCurrentStatus(20);
          } else {
            log.info("Error during cancelOrder");
          }
          break;  
        case 42:
          // [ cancel ask order for scale trade step ]
          uuid = scaleTradeStatusProperties.getAskInfoPerLevel().get(level).getUuid();
          cancelOrderResponse = ordersJobConfiguration.deleteOrderJob(uuid);
          if(cancelOrderResponse.getSuccess()){
            scaleTradeStatusProperties.increaseLevel();
            scaleTradeStatusProperties.setWaitingAskOrder(false);
            statusProperties.setCurrentStatus(10);
            log.info("[changeStatus: 42 -> 10] [currentStatus: "+statusProperties.getCurrentStatus()+"] [bid step] ");
          } else {
            log.info("Error during cancelOrder");
          }
          break;
          // orderChanceDtoForBid = ordersJobConfiguration.getOrdersChanceForBidJob(market);
          // if(orderChanceDtoForBid.getLocked().equals("0.0")) {
          //   statusProperties.setCurrentStatus(20);
          //   log.info("[changeStatus: 11 -> 20] [currentStatus: "+statusProperties.getCurrentStatus()+"] [ask step] ");
          // } else {
          //   log.info("Locked my balance: " + orderChanceDtoForBid.getLocked());
          // }
          // break;
        // case 130:
        //   // [ wait step ]
        //   orderChanceDtoForAsk = ordersJobConfiguration.getOrdersChanceForAskJob(market);
        //   lastCandle = analysisForScaleTradingJobConfiguration.getLastCandleJob();
        //   uuid = scaleTradeStatusProperties.getAskInfoPerLevel().get(level).getUuid();
        //   lastBidTime = scaleTradeStatusProperties.getBidTime();
          
        //   if( !lastBidTime.equals(lastCandle.getCandleDateTime()) && 
        //       lastCandle.getFlag() == -1 &&
        //       analysisForScaleTradingJobConfiguration.compareCurPriceAvgBuyPrice(lastCandle.getTradePrice(), Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice()))
        //       // lastCandle.getTradePrice().compareTo(Double.parseDouble(orderChanceDtoForAsk.getAvgBuyPrice())) == -1 
        //       ) {
        //     statusProperties.setCurrentStatus(31);
        //     log.info("[changeStatus: 30 -> 31] [currentStatus: "+statusProperties.getCurrentStatus()+"] [cancel ask order step] ");
        //   } else {
        //     orderResponseDto = ordersJobConfiguration.getOrderJob(uuid);
        //     if(orderResponseDto.getState().equals("wait")) {
        //       log.info(lastCandle.toString());
        //       log.info("Keep waiting");
        //     } else if(orderResponseDto.getState().equals("done")) {
        //       log.info("Ask conclusion!!");
        //       scaleTradeStatusProperties.setLastConclusionTime(lastCandle.getCandleDateTime());
        //       statusProperties.setCurrentStatus(-1);
        //       log.info("[changeStatus: 30 -> -1] [currentStatus: "+statusProperties.getCurrentStatus()+"] [init step] ");
        //     }
        //   }
        //   break;
        
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