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

  public OrdersChanceDto ordersChanceDtoForAsk;
}
