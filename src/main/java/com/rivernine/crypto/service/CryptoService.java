package com.rivernine.crypto.service;

import com.rivernine.crypto.web.dto.CryptoSaveRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CryptoService {
  private final CryptoRepository cryptoRepository;

  @Transactional
  public Long save(CryptoSaveRequestDto requestDto){
    return cryptoRepository.save(requestDto.toEntity()).getId();
  }
}
