package com.hanssem.ontime.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableRedisRepositories
class RedisRepositoryConfig(
        @Value("\${spring.data.redis.host:localhost}") private val redisHost: String,
        @Value("\${spring.data.redis.port:6379}") private val redisPort: String,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort.toInt())
    }

    @Bean
    fun redisTemplate(): RedisTemplate<ByteArray,ByteArray>{
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        return redisTemplate
    }
}