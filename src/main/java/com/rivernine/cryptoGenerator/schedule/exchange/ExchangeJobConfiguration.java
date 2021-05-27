package com.rivernine.cryptoGenerator.schedule.exchange;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.schedule.exchange.dto.ExchangeResponseDto;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExchangeJobConfiguration {
  
  private final CryptoApi cryptoApi;

  public ExchangeResponseDto bidJob(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.bid(market, price);
  }

  public ExchangeResponseDto askJob(String market, String volume, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.ask(market, volume, price);
  }
}