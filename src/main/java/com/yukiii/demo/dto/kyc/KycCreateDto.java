package com.yukiii.demo.dto.kyc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KycCreateDto {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "email")
  private String email;
}
