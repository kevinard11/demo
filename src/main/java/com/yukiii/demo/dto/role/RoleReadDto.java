package com.yukiii.demo.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleReadDto {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "role")
  private String role;

  @JsonProperty(value = "description")
  private String description;
}
