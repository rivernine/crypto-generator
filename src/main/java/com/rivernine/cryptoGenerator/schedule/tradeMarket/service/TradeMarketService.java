package com.rivernine.cryptoGenerator.schedule.tradeMarket.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.rivernine.cryptoGenerator.domain.expected.Expected;
import com.rivernine.cryptoGenerator.domain.expected.ExpectedRepository;
import com.rivernine.cryptoGenerator.schedule.tradeMarket.dto.ExpectedResponseDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TradeMarketService {
  private final ExpectedRepository expectedRepository;

  @Transactional
  public ExpectedResponseDto expectedPrice(String market) {
    Optional<Expected> entity = expectedRepository.findById(market);
    if (entity.isPresent()){
      return new ExpectedResponseDto(entity.get());
    } else {
      return new ExpectedResponseDto(new Expected(market, -1.0));
    }
  }
}
