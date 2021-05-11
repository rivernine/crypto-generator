package com.rivernine.cryptoGenerator.domain.crypto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long>{

  List<Crypto> findByTradeDateBetween(String startDate, String endDate);
}
