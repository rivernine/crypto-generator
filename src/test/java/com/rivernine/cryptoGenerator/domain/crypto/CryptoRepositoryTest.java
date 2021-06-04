// package com.rivernine.cryptoGenerator.domain.crypto;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// public class CryptoRepositoryTest {
  
//   @Autowired
//   CryptoRepository cryptoRepository;
  
//   @AfterEach
//   public void cleanup() {
//     cryptoRepository.deleteAll();
//   }

//   @Test
//   public void crypto_save() {
//     // cryptoRepository.save(Crypto.builder()
//     //                         .market("KRW-BTC")
//     //                         .trade_date_kst("20210509")
//     //                         .trade_time_kst("220227")
//     //                         .price(60000000.0000)
//     //                         .trade_volume(1000.0000)
//     //                         .acc_trade_volume(2000.0000)
//     //                         .acc_trade_volume_24h(3000.0000)
//     //                         .build());
    
//     // List<Crypto> cryptoList = cryptoRepository.findAll();

//     // Crypto crypto = cryptoList.get(0);
//     // assertThat(crypto.getMarket()).isEqualTo("KRW-BTC");
//     // assertThat(crypto.getPrice()).isEqualTo(60000000.0000);
//   }
// }
