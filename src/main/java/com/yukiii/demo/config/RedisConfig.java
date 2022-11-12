package com.yukiii.demo.config;

import com.yukiii.demo.entity.Demo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Value("${setting.cache.default.ttl-seconds}")
  private int ttlSeconds;

  @Bean(name = "customJedisConnectionFactory")
  public JedisConnectionFactory customJedisConnectionFactory(){
    RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);
    return new JedisConnectionFactory(standaloneConfiguration);
  }

  private RedisTemplate setTemplate(
    RedisTemplate redisTemplate,
    @Qualifier("customJedisConnectionFactory") JedisConnectionFactory customJedisConnectionFactory
  ) {
    redisTemplate.setConnectionFactory(customJedisConnectionFactory);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean(name = "demoRedisTemplate")
  public RedisTemplate<String, Demo> demoRedisTemplate(
    @Qualifier("customJedisConnectionFactory") JedisConnectionFactory customJedisConnectionFactory
  ) {
    RedisTemplate<String, Demo> redisTemplate = new RedisTemplate<>();
    return setTemplate(redisTemplate, customJedisConnectionFactory);
  }

  @Bean(name = "configurationMap")
  public Map<String, RedisCacheConfiguration> configurationMap(){
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    configurationMap.put(
      "demoCache",
      RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttlSeconds))
    );

    return configurationMap;
  }

  @Bean(name = "redisCacheManagerBuilderCustomizer")
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(
    @Qualifier("configurationMap") Map<String, RedisCacheConfiguration> configurationMap
  ){
    return builder -> {
      builder.withInitialCacheConfigurations(configurationMap);
    };
  }

  @Bean(name = "cacheManager")
  public CacheManager cacheManager(
    @Qualifier("customJedisConnectionFactory") JedisConnectionFactory customJedisConnectionFactory,
    @Qualifier("configurationMap") Map<String, RedisCacheConfiguration> configurationMap
  ) {
    return RedisCacheManager
      .builder()
      .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(customJedisConnectionFactory))
      .withInitialCacheConfigurations(configurationMap)
      .build();
  }
}
