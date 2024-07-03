package com.implement.examples.config;

import java.time.Duration;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

  CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
  Cache<String,String> examplecache=cacheManager.createCache("indraCache", CacheConfigurationBuilder.
		  newCacheConfigurationBuilder(String.class , String.class ,
		  ResourcePoolsBuilder.heap(100)).
		  withExpiry(ExpiryPolicyBuilder.
		  timeToLiveExpiration(Duration.ofSeconds(15))));
  
  public String get(String key) {
	 return examplecache.get(key);
  }
  
  public void put(String key, String value) {
	  examplecache.put(key, value);
  }
  
  public void clear(String key) {
	  examplecache.remove(key);
  }
  
}

