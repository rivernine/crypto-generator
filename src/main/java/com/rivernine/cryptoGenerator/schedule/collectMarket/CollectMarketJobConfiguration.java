package com.rivernine.cryptoGenerator.schedule.collectMarket;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CollectMarketSaveDto;
import com.rivernine.cryptoGenerator.schedule.collectMarket.service.CollectMarketService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CollectMarketJobConfiguration {

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

	@Value("${markets}")
	private String markets;  
  
  private final CollectMarketService collectMarketService;

  @Scheduled(fixedRateString = "${schedule.collectDelay}")
  public void collectMarketJob() {
		JsonObject result = new JsonObject();    

    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);

    for( int i = 0; i < jsonObjectArray.length; i++ ){
      JsonObject jsonObject = jsonObjectArray[i];
      CollectMarketSaveDto collectMarketSaveDto = CollectMarketSaveDto.builder()
                                      .market(jsonObject.get("market").getAsString())
                                      .trade_date_kst(jsonObject.get("trade_date_kst").getAsString())
                                      .trade_time_kst(jsonObject.get("trade_time_kst").getAsString())
                                      .price(jsonObject.get("trade_price").getAsDouble())
                                      .trade_volume(jsonObject.get("trade_volume").getAsDouble())
                                      .acc_trade_volume(jsonObject.get("acc_trade_volume").getAsDouble())
                                      .acc_trade_volume_24h(jsonObject.get("acc_trade_volume_24h").getAsDouble())
                                      .build();
      collectMarketService.save(collectMarketSaveDto);
      result.addProperty(jsonObject.get("market").getAsString(), jsonObject.get("trade_price").getAsDouble());
    }
  } 

  @Scheduled(fixedDelay = 10000)
  public void checkCollectMarketJob() {
    List<Crypto> cryptoList = collectMarketService.findAll();

    for( Crypto crypto: cryptoList ) {
      System.out.println(crypto.getMarket() + " " + crypto.getTrade_date_kst() + ":" + crypto.getTrade_time_kst());
      System.out.println(String.format("%.4f", crypto.getPrice()) + " || " + String.format("%.8f", crypto.getTrade_volume()));
    }
  }
}