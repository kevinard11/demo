package com.yukiii.demo.feign.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientDemoConfig {

  @Bean(name = "requestInterceptor")
  public RequestInterceptor requestInterceptor(){
    return new ClientDemoInterceptor(null, null);
  }

  @Bean(name = "errorDecoder")
  public ErrorDecoder errorDecoder(){
    return new ClientDemoErrorDecoder();
  }
}
