package com.rivernine.cryptoGenerator.schedule.collectMarket.service;

import java.util.List;

import javax.transaction.Transactional;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.collectMarket.dto.CollectMarketSaveDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CollectMarketService {
  private final CryptoRepository cryptoRepository;

  @Transactional
  public String save(CollectMarketSaveDto collectMarketSaveDto){
    return cryptoRepository.save(collectMarketSaveDto.toEntity()).getMarket();
  }

  @Transactional
  public List<Crypto> findAll() {
    return cryptoRepository.findAll();
  }
}
