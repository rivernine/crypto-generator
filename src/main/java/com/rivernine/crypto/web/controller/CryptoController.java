package com.rivernine.crypto.web.controller;

import com.rivernine.crypto.service.CryptoService;
import com.rivernine.crypto.web.dto.CryptoSaveRequestDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CryptoController {

  private final CryptoService cryptoService;

  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }

  @PutMapping("/api/v1/posts")
  public Long save(@RequestBody CryptoSaveRequestDto requestDto){
    return cryptoService.save(requestDto);
  }

}
