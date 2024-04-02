package org.playground.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory(){
//        RedisStandaloneConfiguration redisStandaloneConfiguration =
//                new RedisStandaloneConfiguration();
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    RedisTemplate<Long, Object> redisTemplate(){
//        RedisTemplate<Long, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        return redisTemplate;
//    }
//}
