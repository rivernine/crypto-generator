package com.rivernine.cryptoGenerator.config;

import com.rivernine.cryptoGenerator.schedule.ordersChance.dto.OrdersChanceDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Component
@NoArgsConstructor
@Getter
@Setter
public class StatusProperties {

  @Value("${testParameter.currentStatus}")
  public int currentStatus;
  public Double usedBalance;

  public Boolean bidRunning = false;
  public Boolean bidPending =false;
  public Boolean askRunning = false;
  public Boolean askPending =false;

  public OrdersChanceDto ordersChanceDtoForBid;
  public OrdersChanceDto ordersChanceDtoForAsk;

  public void init(){
    this.currentStatus = -1;
    this.usedBalance = 0.0;
    this.bidRunning = false;
    this.bidPending = false;
    this.askRunning = false;
    this.askPending = false;
    this.ordersChanceDtoForAsk = null;
    this.ordersChanceDtoForBid = null;
  }
}
