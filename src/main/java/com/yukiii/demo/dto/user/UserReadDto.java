package com.yukiii.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yukiii.demo.dto.demo.DemoReadDto;
import com.yukiii.demo.dto.kyc.KycReadDto;
import com.yukiii.demo.dto.role.RoleReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "username")
  private String username;

  @JsonProperty(value = "demo")
  private List<DemoReadDto> demo;

  @JsonProperty(value = "kyc")
  private KycReadDto kyc;

  @JsonProperty(value = "role")
  private List<RoleReadDto> role;


}
