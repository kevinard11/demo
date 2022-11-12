package com.yukiii.demo.feign;

import com.yukiii.demo.dto.client.ClientDemoReadDtoResponse;
import com.yukiii.demo.feign.config.ClientDemoConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
  name = "DemoClient",
  url = "${client.demo.url}",
  configuration = ClientDemoConfig.class
)
public interface DemoClient {
  static final String ENDPOINT_DEMO = "/demo/{id}";

  @GetMapping(value = ENDPOINT_DEMO)
  ClientDemoReadDtoResponse getDemoById(
    @PathVariable(name = "id") Long id
  );

}
