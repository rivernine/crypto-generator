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

  private final CryptoApi cryptoApi;

  public void collectMarketJob(String market) {
    JsonObject jsonObject = cryptoApi.getMarket(market);
    CollectMarketSaveDto collectMarketSaveDto = CollectMarketSaveDto.builder()
                                    .market(jsonObject.get("market").getAsString())
                                    .tradeDate(jsonObject.get("trade_date_kst").getAsString()+jsonObject.get("trade_time_kst").getAsString())
                                    .price(jsonObject.get("trade_price").getAsDouble())
                                    .tradeVolume(jsonObject.get("trade_volume").getAsDouble())
                                    .accTradeVolume(jsonObject.get("acc_trade_volume").getAsDouble())
                                    .accTradeVolume24h(jsonObject.get("acc_trade_volume_24h").getAsDouble())
                                    .build();
    collectMarketService.save(collectMarketSaveDto);
  } 

  // public void checkCollectMarketJob() {
  //   List<Crypto> cryptoList = collectMarketService.findAll();

  //   for( Crypto crypto: cryptoList ) {
  //     System.out.println(crypto.getMarket() + " " + crypto.getTradeDate());
  //     System.out.println(String.format("%.4f", crypto.getPrice()) + " || " + String.format("%.8f", crypto.getTradeVolume()));
  //   }
  // }
}