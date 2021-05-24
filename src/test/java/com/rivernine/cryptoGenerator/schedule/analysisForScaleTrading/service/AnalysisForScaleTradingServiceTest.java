package com.rivernine.cryptoGenerator.schedule.analysisForScaleTrading.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AnalysisForScaleTradingServiceTest {
  
  String currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString() + ":00";

  @Test
  public void printDate(){
    LocalDateTime test = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    
    System.out.println(currentTime);
    System.out.println(test);
    // System.out.println(currentTime.truncatedTo(ChronoUnit.SECONDS).withSecond(00));
    // System.out.println(currentTime.withNano(0).withSecond(00));
  }
}
// 2021-05-24T10:55:00
// 2021-05-24T16:06:07.188026600
