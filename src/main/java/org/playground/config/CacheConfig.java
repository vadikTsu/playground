package org.playground.config;

import org.hibernate.annotations.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//@EnableCaching
//@Configuration
public class CacheConfig {
//
//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cache = new SimpleCacheManager();
//        cache.setCaches(Arrays.asList(
//                new ConcurrentMapCache("bids"),
//                new ConcurrentMapCache("lots")
//        ));
//        return cache;
//    }
}
