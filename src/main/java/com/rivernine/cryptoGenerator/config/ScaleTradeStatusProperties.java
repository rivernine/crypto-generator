package com.rivernine.cryptoGenerator.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
  // 0: getOrderChance
  // 10: analysisForBid
  // 11: bid
  // 12: listen
  public int currentStatus;
  public Map<LocalDateTime, CandleDto> candleDtoMap = new HashMap<>();

  public void addCandlesDtoMap(LocalDateTime key, CandleDto candleDto) {
    if(!candleDtoMap.containsKey(key)) {
      candleDtoMap.put(key, candleDto);
    }
  }
  
  public void printCandlesDtoMap() {
    for(LocalDateTime key: candleDtoMap.keySet()) {
      log.info(candleDtoMap.get(key).toString());
    }
  }
}
