package com.rivernine.cryptoGenerator.schedule.eventListen;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.common.CryptoApi;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventListener {

  private final CryptoApi cryptoApi;

  public void getMyOrder(String uuid) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    JsonObject response = cryptoApi.getOrder(uuid);
    if(!response.has("error")) {
      
    } else {
      log.info(response.get("error").getAsString());
    }
  }
}
