package com.rivernine.crypto.web.controller;

import com.rivernine.crypto.web.dto.CryptoResponseDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {

  // private final CryptoService cryptoService;

  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }

  @GetMapping("/hello/dto")
  public CryptoResponseDto cryptoDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
    return new CryptoResponseDto(name, amount);
  }
  
  // @PutMapping("/api/v1/posts")
  // public Long save(@RequestBody CryptoSaveRequestDto requestDto){
  //   return cryptoService.save(requestDto);
  // }

}
