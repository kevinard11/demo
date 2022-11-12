package com.yukiii.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

  @JsonProperty(value = "username")
  private String username;

  @JsonProperty(value = "password")
  private String password;
}
