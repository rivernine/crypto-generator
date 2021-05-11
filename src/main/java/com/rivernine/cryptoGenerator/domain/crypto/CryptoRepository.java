package com.rivernine.cryptoGenerator.domain.crypto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long>{
  List<Crypto> findByTradeDateBetween(LocalDateTime startDate, LocalDateTime endDate);
  List<Crypto> findByTradeDateAfter(LocalDateTime date);
}
