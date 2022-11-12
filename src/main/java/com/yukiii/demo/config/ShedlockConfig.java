package com.yukiii.demo.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class ShedlockConfig {

  @Bean("lockProvide")
  public LockProvider lockProvider(
    @Qualifier("customJedisConnectionFactory")JedisConnectionFactory customJedisConnectionFactory
  ) {
    return new RedisLockProvider(customJedisConnectionFactory);
  }
}
