package com.yukiii.demo.config;

import com.yukiii.demo.migration.FlywayCallback;
import org.flywaydb.core.api.callback.Callback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayCallbackConfig {

  @Bean("callback")
  public Callback callback(){
    return new FlywayCallback();
  }
}
