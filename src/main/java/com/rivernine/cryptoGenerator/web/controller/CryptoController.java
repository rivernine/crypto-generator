package com.rivernine.cryptoGenerator.web.controller;

import java.util.List;

import com.google.gson.Gson;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.web.service.CryptoService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CryptoController {
  
  private final CryptoService cryptoService;

  RestTemplate restTemplate = new RestTemplate();
  Gson gson = new Gson();

  @Value("${upbit.accessKey}")
  private String accessKey;

  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }

  @GetMapping("/accessKey")
  public String getAccessKey(){
    return accessKey;
  }

  // @GetMapping("/price/{startDate}")
  // public List<Crypto> findByTradeDateBetween( @PathVariable String startDate){
  //   return cryptoService.findByTradeDateBetween(startDate);
  // }

  // private final CryptoService cryptoService;

  // @GetMapping("/hello/dto")
  // public CryptoResponseDto cryptoDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
  //   return new CryptoResponseDto(name, amount);
  // }

  // @PutMapping("/api/v1/posts")
  // public Long save(@RequestBody CryptoSaveRequestDto requestDto){
  //   return cryptoService.save(requestDto);
  // }

}
