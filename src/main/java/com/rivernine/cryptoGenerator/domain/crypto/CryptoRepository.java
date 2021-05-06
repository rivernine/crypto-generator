package com.rivernine.cryptoGenerator.domain.crypto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long>{
  
}
