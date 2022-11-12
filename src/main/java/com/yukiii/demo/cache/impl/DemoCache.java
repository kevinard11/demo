package com.yukiii.demo.cache.impl;

import com.yukiii.demo.cache.Cache;
import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("demoCache")
public class DemoCache implements Cache<String, Demo> {

  @Value("${setting.cache.default.ttl-seconds}")
  private int ttlSeconds;

  @Qualifier("demoRedisTemplate")
  @Autowired
  private RedisTemplate<String, Demo> demoRedisTemplate;

  @Override
  public void set(String key, Demo value) {
    key = buildKeyFromDemoId(key);
    demoRedisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
  }

  @Override
  public Demo get(String key) {
    key = buildKeyFromDemoId(key);
    return demoRedisTemplate.opsForValue().get(key);
  }

  @Override
  public boolean exist(String key) {
    key = buildKeyFromDemoId(key);
    return Boolean.TRUE.equals(demoRedisTemplate.hasKey(key));
  }

  @Override
  public long bust() {
    var setKeys = demoRedisTemplate
      .getRequiredConnectionFactory()
      .getConnection()
      .keys(buildPatternFromDemoId().getBytes());

    List<String> listKeys = new ArrayList<>();
    if(setKeys != null && !setKeys.isEmpty()){
      setKeys.forEach(keys -> listKeys.add(getKey(new String(keys))));
    }

    return demoRedisTemplate.delete(listKeys);
  }

  private String buildKeyFromDemoId(String key){
    return String.join("_", AppConstant.PREFIX_REDIS_DEMO, AppConstant.PREFIX_REDIS_DEMO_ID, key);
  }

  private String buildPatternFromDemoId(){
    return PATTERN + buildKeyFromDemoId(PATTERN);
  }

  private String getKey(String key){
    return key.substring(key.indexOf(String.join("_", AppConstant.PREFIX_REDIS_DEMO, AppConstant.PREFIX_REDIS_DEMO_ID)));
  }
}
