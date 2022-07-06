package com.yukiii.demo.dto.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoCreateDto {

    @JsonProperty(value = "demo_id")
    private String demoId;

    @JsonProperty(value = "demo_name")
    private String demoName;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "level")
    private int level;

    @JsonProperty(value = "active")
    private boolean active;
}
