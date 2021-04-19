package com.rivernine.crypto.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CryptoResponseDtoTest {

  @Test
  public void testForLombok() {
    String name = "test";
    int amount = 1000;

    CryptoResponseDto dto = new CryptoResponseDto(name, amount);

    assertThat(dto.getName()).isEqualTo(name);
    assertThat(dto.getAmount()).isEqualTo(amount);
  }
  
}
