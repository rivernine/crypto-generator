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

  @Test
  public void changeAbleOrderPrice_test( ) {
    String price = "98778";
    Double result;
    Double priceD = Double.parseDouble(price);
    Double orderUnit; 
    if(priceD.compareTo(0.0) != -1 && priceD.compareTo(10.0) == -1) {
      orderUnit = 0.01;
    } else if(priceD.compareTo(10.0) != -1 && priceD.compareTo(100.0) == -1) {
      orderUnit = 0.1;
    } else if(priceD.compareTo(100.0) != -1 && priceD.compareTo(1000.0) == -1) {
      orderUnit = 1.0;
    } else if(priceD.compareTo(1000.0) != -1 && priceD.compareTo(10000.0) == -1) {
      orderUnit = 5.0;
    } else if(priceD.compareTo(10000.0) != -1 && priceD.compareTo(100000.0) == -1) {
      orderUnit = 10.0;
    } else if(priceD.compareTo(100000.0) != -1 && priceD.compareTo(500000.0) == -1) {
      orderUnit = 50.0;
    } else if(priceD.compareTo(500000.0) != -1 && priceD.compareTo(1000000.0) == -1) {
      orderUnit = 100.0;
    } else if(priceD.compareTo(1000000.0) != -1 && priceD.compareTo(2000000.0) == -1) {
      orderUnit = 500.0;
    } else {
      orderUnit = 1000.0;
    }

    Double printTest = priceD / orderUnit;
    Double printTest2 = priceD % orderUnit;

    System.out.println("priceD / orderUnit");
    System.out.println(printTest.toString());
    System.out.println("priceD % orderUnit");
    System.out.println(printTest2.toString());

    Double mod = priceD % orderUnit;
    if(mod.compareTo(0.0) == 0) {
      System.out.println("if");
      result = priceD;
    } else {
      System.out.println("else");
      Double tmp = priceD / orderUnit;
      result = tmp.intValue() * orderUnit + orderUnit;
    }

    System.out.println("priceD : orderUnit : result");
    System.out.println(priceD.toString() + " : " + orderUnit.toString() + " : " + result.toString());
  }

}
// 2021-05-24T10:55:00
// 2021-05-24T16:06:07.188026600
