package com.rivernine.cryptoGenerator.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.TradeDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Component
@NoArgsConstructor
@Getter
@Setter
public class ScaleTradeStatusProperties {

  @Value("${upbit.markets}")
  private List<String> markets;
  public int level = 0;
  public String usedBalance = "0.0";
  public String usedFee = "0.0";  
  public String bidTime = "0000-00-00T00:00:00";
  public String lastConclusionTime = "0000-00-00T00:00:00";
  public Map<Integer, OrdersResponseDto> bidInfoPerLevel = new HashMap<>();
  public Map<Integer, OrdersResponseDto> askInfoPerLevel = new HashMap<>();
  // public Map<LocalDateTime, CandleDto> candleDtoMap = new HashMap<>();
  public Map<String, Map<LocalDateTime, CandleDto>> candleDtoMaps = new HashMap<>();
  public Map<String, TradeDto> tradesStatus = new HashMap<>();
  public List<String> balancePerLevel = new ArrayList<>(
    Arrays.asList("10000.0", "20000.0", "30000.0", "40000.0", "50000.0"));
  public Boolean waitingBidOrder = false;
  public Boolean waitingAskOrder = false;
  public Boolean startTrading = false;

  public void increaseLevel() {
    this.level++;
  }

  public void addBalance(String balance) {
    this.usedBalance = Double.toString(Double.parseDouble(this.usedBalance) + Double.parseDouble(balance));
  }

  public void addFee(String fee) {
    this.usedFee = Double.toString(Double.parseDouble(this.usedFee) + Double.parseDouble(fee));
  }

  public void addBidInfoPerLevel(OrdersResponseDto ordersResponseDto) {
    this.bidInfoPerLevel.put(this.level, ordersResponseDto);
  }

  public void updateBidInfoPerLevel(OrdersResponseDto ordersResponseDto, Integer level) {
    this.bidInfoPerLevel.put(level, ordersResponseDto);
  }

  public void addAskInfoPerLevel(OrdersResponseDto ordersResponseDto) {
    this.askInfoPerLevel.put(this.level, ordersResponseDto);
  }

  public void addCandlesDtoMaps(String market, LocalDateTime key, CandleDto candleDto) {
    if(!this.candleDtoMaps.get(market).containsKey(key)) {
      Map<LocalDateTime, CandleDto> candleDtoMap = this.candleDtoMaps.get(market);
      candleDtoMap.put(key, candleDto);
      this.candleDtoMaps.put(market, candleDtoMap);
    }
  }

  public void updateNewTrade() {
    OrdersResponseDto orders = this.bidInfoPerLevel.get(this.level);
    Map<String, TradeDto> trades = orders.getTrades();
    for(String key: trades.keySet()) {
      if(!this.tradesStatus.containsKey(key)) {
        TradeDto trade = trades.get(key);
        String bidBalance = trade.getFunds();
        String paidFee = Double.toString((Double.parseDouble(bidBalance) * 0.0005));
        this.tradesStatus.put(trade.getUuid(), trade);
        addBalance(bidBalance);
        addFee(paidFee);
      }
    }
  }

  // public void printCandlesDtoMap() {
  //   List<LocalDateTime> keys = new ArrayList<>(this.candleDtoMap.keySet());
  //   DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
  //   keys.sort((s1, s2) -> s1.format(formatter).compareTo(s2.format(formatter)));
    
  //   for(LocalDateTime key: keys) {
  //     log.info(this.candleDtoMap.get(key).toString());
  //   }
  // }

  public void init() {
    this.level = 0;
    this.usedBalance = "0.0";
    this.usedFee = "0.0";
    this.bidTime = "0000-00-00T00:00:00";    
    this.lastConclusionTime = "0000-00-00T00:00:00";
    // this.candleDtoMap = new HashMap<>();  
    this.bidInfoPerLevel = new HashMap<>();
    this.askInfoPerLevel = new HashMap<>();
    this.tradesStatus = new HashMap<>();
    this.waitingBidOrder = false;
    this.waitingAskOrder = false;
    this.startTrading = false;
    this.candleDtoMaps = new HashMap<>();  
    for(String market: markets) {
      this.candleDtoMaps.put(market, new HashMap<>());
    }
  }
}
