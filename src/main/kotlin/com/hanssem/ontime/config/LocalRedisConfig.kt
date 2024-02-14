package com.hanssem.ontime.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("local") //profile이 local일때만 활성화
@Configuration
class LocalRedisConfig(
        @Value("\${spring.data.redis.port:6379}") private val redisPort: Int
) {
    private val redisServer: RedisServer = RedisServer(redisPort)

    @PostConstruct
    private fun startRedis(){
        redisServer.start()
    }

    @PreDestroy
    private fun stopRedis(){
        redisServer.stop()
    }
}