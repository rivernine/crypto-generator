package com.rivernine.cryptoGenerator.web.service;

import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoId;
import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.web.dto.CryptoResponseDto;
import com.rivernine.cryptoGenerator.web.dto.CryptoSaveDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CryptoService {
  private final CryptoRepository cryptoRepository;

  @Transactional
  public String save(CryptoSaveDto requestDto){
    return cryptoRepository.save(requestDto.toEntity()).getMarket();
  }

  @Transactional
  public CryptoResponseDto findByMarket(String market) {
    Crypto entity = cryptoRepository.findByMarket(market);

    return new CryptoResponseDto(entity);
  }
}
