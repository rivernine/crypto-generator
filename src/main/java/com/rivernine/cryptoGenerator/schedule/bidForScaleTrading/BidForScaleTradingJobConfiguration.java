package com.rivernine.cryptoGenerator.schedule.bidForScaleTrading;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.rivernine.cryptoGenerator.common.CryptoApi;
import com.rivernine.cryptoGenerator.common.dto.BidMarketResponseDto;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BidForScaleTradingJobConfiguration {
  
  private final CryptoApi cryptoApi;

  public BidMarketResponseDto bidJob(String market, String price) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return cryptoApi.bid(market, price);
  }
}
