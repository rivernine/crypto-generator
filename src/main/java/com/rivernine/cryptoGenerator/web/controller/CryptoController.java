package com.rivernine.cryptoGenerator.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rivernine.cryptoGenerator.domain.crypto.Crypto;
import com.rivernine.cryptoGenerator.web.dto.CryptoSaveDto;
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

  // markets: KRW-BTC 형식으로 기입. ','로 구분하여 다중처리도 가능
  @GetMapping("/price/{markets}")
  public String getPrice(@PathVariable("markets") String markets) {  
    JsonObject result = new JsonObject();    

    String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);

    for( int i = 0; i < jsonObjectArray.length; i++ ){
      JsonObject jsonObject = jsonObjectArray[i];
      cryptoService.save(CryptoSaveDto.builder()
                          .market(jsonObject.get("market").getAsString())
                          .price(jsonObject.get("trade_price").getAsDouble())
                          .build());
      result.addProperty(jsonObject.get("market").getAsString(), jsonObject.get("trade_price").getAsDouble());
    }

    return result.toString();
  }

  // @GetMapping("/findAll")
  // public 

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
