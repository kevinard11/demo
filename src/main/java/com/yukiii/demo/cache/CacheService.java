package com.yukiii.demo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component("cacheService")
public class CacheService {

  @Qualifier("cacheManager")
  @Autowired
  private CacheManager cacheManager;

  public void bust(){

    cacheManager
      .getCacheNames()
      .forEach(name -> {
        cacheManager.getCache(name).clear();
      });

  }
}
