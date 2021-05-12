// package com.rivernine.cryptoGenerator.schedule.tradeMarket;

// @RequiredArgsConstructor
// @Component
// public class TradeMarketJobConfiguration {

//   RestTemplate restTemplate = new RestTemplate();
//   Gson gson = new Gson();

// 	@Value("${upbit.markets}")
// 	private String markets;  

//   private final CollectMarketService collectMarketService;

//   // @Scheduled(fixedRateString = "${schedule.collectDelay}")
//   public void collectMarketJob() {
// 		// JsonObject result = new JsonObject();    
//     String jsonString = restTemplate.getForObject("https://api.upbit.com/v1/ticker?markets=" + markets, String.class);
//     JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);

//     for( int i = 0; i < jsonObjectArray.length; i++ ){
//       JsonObject jsonObject = jsonObjectArray[i];
//       CollectMarketSaveDto collectMarketSaveDto = CollectMarketSaveDto.builder()
//                                       .market(jsonObject.get("market").getAsString())
//                                       .tradeDate(jsonObject.get("trade_date_kst").getAsString()+jsonObject.get("trade_time_kst").getAsString())
//                                       .price(jsonObject.get("trade_price").getAsDouble())
//                                       .tradeVolume(jsonObject.get("trade_volume").getAsDouble())
//                                       .accTradeVolume(jsonObject.get("acc_trade_volume").getAsDouble())
//                                       .accTradeVolume24h(jsonObject.get("acc_trade_volume_24h").getAsDouble())
//                                       .build();
//       collectMarketService.save(collectMarketSaveDto);
//       // System.out.println(collectMarketSaveDto);
//       // result.addProperty(jsonObject.get("market").getAsString(), jsonObject.get("trade_price").getAsDouble());
//     }
//   } 