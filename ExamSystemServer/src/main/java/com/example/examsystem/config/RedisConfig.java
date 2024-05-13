package com.example.examsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Class_Info ExamSystem 类描述
 * @Author 费基辉
 * @Date 2024/4/27 18:10
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

    // jedis连接词
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        System.out.println("Jedis连接词进入");
        return jedisPoolConfig;
    }

    // jedis连接工厂
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//        System.out.println("JedisConnectionFactory连接工厂进入");
        return jedisConnectionFactory;

    }

    // redisTemplate序列化
    @Bean
    public RedisTemplate<String , Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String , Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        System.out.println("redisTemplate进入");
        return redisTemplate;
    }

}
