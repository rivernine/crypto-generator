package com.rivernine.cryptoGenerator.config;

import java.util.ArrayList;
import java.util.List;

import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.GetCandlesDto;

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
  // 0: getOrderChance
  // 10: analysisForBid
  // 11: bid
  // 12: listen
  public int currentStatus;
  public List<GetCandlesDto> getCandlesDtoList = new ArrayList<>();

  public void setCandlesDtoList(GetCandlesDto getCandlesDto){
    getCandlesDtoList.add(getCandlesDto);
  }
}
