package com.rivernine.cryptoGenerator.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;
import com.rivernine.cryptoGenerator.schedule.orders.dto.OrdersResponseDto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Component
@NoArgsConstructor
@Getter
@Setter
public class ScaleTradeStatusProperties {

  public int level = 0;
  public String usedBalance = "0.0";
  public String usedFee = "0.0";  
  public String bidTime = "0000-00-00T00:00:00";
  public String lastConclusionTime = "0000-00-00T00:00:00";
  public Map<Integer, OrdersResponseDto> bidInfoPerLevel = new HashMap<>();
  public Map<Integer, OrdersResponseDto> askInfoPerLevel = new HashMap<>();
  public Map<LocalDateTime, CandleDto> candleDtoMap = new HashMap<>();
  public List<String> balancePerLevel = new ArrayList<>(
    Arrays.asList("160000.0", "320000.0", "480000.0", "740000.0", "900000.0", "1060000.0"));

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

  public void addAskInfoPerLevel(OrdersResponseDto ordersResponseDto) {
    this.askInfoPerLevel.put(this.level, ordersResponseDto);
  }

  public void addCandlesDtoMap(LocalDateTime key, CandleDto candleDto) {
    if(!this.candleDtoMap.containsKey(key)) {
      this.candleDtoMap.put(key, candleDto);
    }
  }
  
  public void printCandlesDtoMap() {
    List<LocalDateTime> keys = new ArrayList<>(this.candleDtoMap.keySet());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    keys.sort((s1, s2) -> s1.format(formatter).compareTo(s2.format(formatter)));
    
    for(LocalDateTime key: keys) {
      log.info(this.candleDtoMap.get(key).toString());
    }
  }

  public void init() {
    this.level = 0;
    this.usedBalance = "0.0";
    this.usedFee = "0.0";
    this.bidTime = "0000-00-00T00:00:00";    
    this.lastConclusionTime = "0000-00-00T00:00:00";
    this.candleDtoMap = new HashMap<>();  
    this.bidInfoPerLevel = new HashMap<>();
    this.askInfoPerLevel = new HashMap<>();
  }
}
