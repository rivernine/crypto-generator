package com.rivernine.cryptoGenerator.schedule.collectMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CollectMarketSaveDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CollectMarketService {

  private final CryptoRepository cryptoRepository;
  private final CryptoApi cryptoApi;
  
  public CollectMarketSaveDto getMarket(String market) {
    JsonObject jsonObject = cryptoApi.getMarket(market);
    return  CollectMarketSaveDto.builder()
              .market(jsonObject.get("market").getAsString())
              .tradeDate(jsonObject.get("trade_date_kst").getAsString()+jsonObject.get("trade_time_kst").getAsString())
              .price(jsonObject.get("trade_price").getAsDouble())
              .tradeVolume(jsonObject.get("trade_volume").getAsDouble())
              .accTradeVolume(jsonObject.get("acc_trade_volume").getAsDouble())
              .accTradeVolume24h(jsonObject.get("acc_trade_volume_24h").getAsDouble())
              .build();
  }

  @Transactional
  public void save(CollectMarketSaveDto collectMarketSaveDto){
    cryptoRepository.save(collectMarketSaveDto.toEntity());
  }

  @Transactional
  public List<Crypto> findAll() {
    return cryptoRepository.findAll();
  }
}
