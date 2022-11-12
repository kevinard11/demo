package com.yukiii.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthSignInDto {

  @NotBlank
  @JsonProperty(value = "username")
  private String username;

  @NotBlank
  @JsonProperty(value = "password")
  private String password;
}
