package com.rivernine.cryptoGenerator.schedule.collectMarket;

import java.util.List;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CollectMarketSaveDto;
import com.rivernine.cryptoGenerator.schedule.collectMarket.service.CollectMarketService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CollectMarketJobConfiguration {

  private final CollectMarketService collectMarketService;

  public void collectMarketJob(String market) {
    CollectMarketSaveDto collectMarketSaveDto = collectMarketService.getMarket(market);
    collectMarketService.save(collectMarketSaveDto);
  } 

  public void getCandlesJob(String market, String minutes, String count) {
    collectMarketService.getCandles(market, minutes, count);
  }

  public void printCandlesJob() {
    collectMarketService.printCandles();
  }
}