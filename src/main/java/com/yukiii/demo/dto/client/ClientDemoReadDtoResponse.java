package com.yukiii.demo.dto.client;

import com.yukiii.demo.dto.demo.DemoReadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDemoReadDtoResponse {

  private Date timestamp;
  private String message;
  private DemoReadDto data;
}
