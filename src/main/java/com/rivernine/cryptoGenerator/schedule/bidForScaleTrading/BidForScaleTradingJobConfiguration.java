package com.rivernine.cryptoGenerator.schedule.bidForScaleTrading;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rivernine.cryptoGenerator.common.CryptoApi;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BidForScaleTradingJobConfiguration {
  
  private final CryptoApi cryptoApi;

  public void bidJob(String market, String price, String volume) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    cryptoApi.bid(market, price, volume);
  }
}
