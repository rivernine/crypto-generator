package com.rivernine.crypto.web.controller;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.json.JSONParser;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CryptoController {
  
  @Value("${upbit.accessKey}")
  private String accessKey;


  @GetMapping("/getAccessKey")
  public String getAccessKey(){
    return accessKey;
  }

  // markets: KRW-BTC 형식으로 기입. ','로 구분하여 다중처리도 가능
  @GetMapping("/price")
  public String getPrice(@RequestParam(value = "markets", defaultValue = "KRW-BTC") String markets){
    final String uri = "https://api.upbit.com/v1/ticker?markets=" + markets;
    RestTemplate restTemplate = new RestTemplate();
    JSONParser jsonParser = new JSONParser();
    ObjectMapper mapper = new ObjectMapper();

    String resultString = restTemplate.getForObject(uri, String.class);
    JSONObject obj =  (JSONObject)jsonParser.parse(jsonString);
    List<HashMap<String, Object>> listobj = obj;
    System.out.println(obj);
    System.out.println(listobj);

    return test;
  }

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
