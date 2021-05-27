package com.rivernine.cryptoGenerator.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rivernine.cryptoGenerator.common.dto.ExchangeResponseDto;
import com.rivernine.cryptoGenerator.schedule.getCandle.dto.CandleDto;

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
  public List<String> balancePerLevel = new ArrayList<>(
    Arrays.asList("6000.0", "20000.0", "100000.0", "500000.0", "2000000.0"));
  public List<ExchangeResponseDto> bidInfoPerLevel = new ArrayList<>();
  public List<ExchangeResponseDto> askInfoPerLevel = new ArrayList<>();
  public Map<LocalDateTime, CandleDto> candleDtoMap = new HashMap<>();

  public void increaseLevel() {
    this.level++;
  }

  public void addBalance(String balance) {
    this.usedBalance = Double.toString(Double.parseDouble(this.usedBalance) + Double.parseDouble(balance));
  }

  public void addFee(String fee) {
    this.usedFee = Double.toString(Double.parseDouble(this.usedFee) + Double.parseDouble(fee));
  }

  public void addBidInfoPerLevel(ExchangeResponseDto exchangeResponseDto) {
    this.bidInfoPerLevel.add(exchangeResponseDto);
  }

  public void addAskInfoPerLevel(ExchangeResponseDto exchangeResponseDto) {
    this.askInfoPerLevel.add(exchangeResponseDto);
  }

  public void addCandlesDtoMap(LocalDateTime key, CandleDto candleDto) {
    if(!this.candleDtoMap.containsKey(key)) {
      this.candleDtoMap.put(key, candleDto);
    }
  }
  
  public void printCandlesDtoMap() {
    for(LocalDateTime key: this.candleDtoMap.keySet()) {
      log.info(this.candleDtoMap.get(key).toString());
    }
  }

  public void init() {
    this.level = 0;
    this.usedBalance = "0.0";
    this.usedFee = "0.0";
    this.balancePerLevel = new ArrayList<>(
      Arrays.asList("6000.0", "20000.0", "100000.0", "500000.0", "2000000.0"));
    this.candleDtoMap = new HashMap<>();
    this.bidInfoPerLevel = new ArrayList<>();
    this.askInfoPerLevel = new ArrayList<>();
  }
}
