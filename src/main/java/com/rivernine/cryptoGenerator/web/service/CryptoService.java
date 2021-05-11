package com.rivernine.cryptoGenerator.web.service;

import java.util.List;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.web.dto.CryptoResponseDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CryptoService {
  private final CryptoRepository cryptoRepository;

  // @Transactional
  // public String save(CryptoSaveDto requestDto){
  //   return cryptoRepository.save(requestDto.toEntity()).getMarket();
  // }

  @Transactional
  public List<Crypto> findByTradeDateBetween(String startDate) {
    return cryptoRepository.findByTradeDateBetween(startDate, "20220101000000");
  }
}
