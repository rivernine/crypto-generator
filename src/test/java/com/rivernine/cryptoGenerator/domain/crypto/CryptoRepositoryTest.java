package com.rivernine.cryptoGenerator.domain.crypto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CryptoRepositoryTest {
  
  @Autowired
  CryptoRepository cryptoRepository;
  
  @AfterEach
  public void cleanup() {
    cryptoRepository.deleteAll();
  }

  // @Test
  // public void crypto_save() {
  //   CryptoId cryptoId = new CryptoId()
  //   String market = "KRW-BTC";
  //   Double price = 60000000.000000;
    
  //   cryptoRepository.save(Crypto.builder()
  //                               .cryptoId(cryptoId)
  //                               .price(price)
  //                               .build());
    
  //   List<Crypto> cryptoList = cryptoRepository.findAll();

  //   Crypto crypto = cryptoList.get(0);
  //   assertThat(crypto.getMarket()).isEqualTo(market);
  //   assertThat(crypto.getPrice()).isEqualTo(price);
  // }
}
