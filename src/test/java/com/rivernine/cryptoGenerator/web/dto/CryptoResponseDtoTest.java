package com.rivernine.cryptoGenerator.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

public class CryptoResponseDtoTest {

  // @Test
  // public void testForLombok() {
  //   String name = "test";
  //   int amount = 1000;

  //   CryptoResponseDto dto = new CryptoResponseDto(name, amount);

  //   assertThat(dto.getName()).isEqualTo(name);
  //   assertThat(dto.getAmount()).isEqualTo(amount);
  // }
  
  @Test
  public void convert_string_to_json() throws Exception {
    String jsonArrayString = "{ \"name\": \"Jack\", \"java\": true }";
    JsonObject jsonObject = new Gson().fromJson(jsonArrayString, JsonObject.class);

    assertThat(jsonObject.get("name").getAsString()).isEqualTo("Jack");
  }

  @Test
  public void convert_array_to_json() throws Exception {
    Gson gson = new Gson();
    String jsonString = "[{ \"name\": \"Jack\", \"java\": true }]";

    JsonObject[] jsonObjectArray = gson.fromJson(jsonString, JsonObject[].class);

    for( int i = 0; i < jsonObjectArray.length; i++ ){
      JsonObject jsonObject = jsonObjectArray[i];
      assertThat(jsonObject.get("name").getAsString()).isEqualTo("Jack");
    }
  }
}
