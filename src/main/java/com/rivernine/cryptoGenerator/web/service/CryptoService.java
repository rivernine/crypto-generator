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
  public CryptoId save(CryptoSaveDto requestDto){
    return cryptoRepository.save(requestDto.toEntity()).getCryptoId();
  }

  @Transactional
  public CryptoResponseDto findById(Long id) {
    Crypto entity = cryptoRepository.findById(id)
                      .orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다. id=" + id));

    return new CryptoResponseDto(entity);
  }
}
