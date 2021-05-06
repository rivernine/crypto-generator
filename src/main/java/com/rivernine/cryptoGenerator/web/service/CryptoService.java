package com.rivernine.cryptoGenerator.web.service;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.web.dto.CryptoSaveDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CryptoService {
  private final CryptoRepository cryptoRepository;

  @Transactional
  public Long save(CryptoSaveDto requestDto){
    return cryptoRepository.save(requestDto.toEntity()).getId();
  }
}
