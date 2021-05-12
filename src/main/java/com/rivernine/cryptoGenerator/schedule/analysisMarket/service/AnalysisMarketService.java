package com.rivernine.cryptoGenerator.schedule.analysisMarket.service;

import java.time.LocalDateTime;
import java.util.List;

import com.rivernine.cryptoGenerator.domain.crypto.CryptoRepository;
import com.rivernine.cryptoGenerator.schedule.analysisMarket.dto.AnalysisMarketResponseDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnalysisMarketService {
  private final CryptoRepository cryptoRepository;

  @Transactional
  public List<AnalysisMarketResponseDto> findByTradeDateAfter(LocalDateTime date) {
    return cryptoRepository.findByTradeDateAfter(date);
  }
}
