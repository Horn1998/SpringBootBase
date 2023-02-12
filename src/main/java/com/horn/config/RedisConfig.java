//package com.horn.Config;/*
// *@Author: horn
// *@DATE: 2023/1/7 0007 22:17
// *@Description
// *@Version 1.0
// */
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.net.UnknownHostException;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
//            throws UnknownHostException {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//
//        // json序列化
//        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>();
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectJackson2JsonRedisSerializer.setObjectMapper(om);
//        // String序列化
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        //key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        //hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        //value的序列化方式为jackSon
//        template.setValueSerializer(objectJackson2JsonRedisSerializer);
//        //hash的value也采用jackSon
//        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
//
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//}
