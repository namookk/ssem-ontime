package com.hanssem.ontime.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = arrayOf("com.hanssem.ontime.payload.domain"))
@EnableJpaRepositories(basePackages = arrayOf("com.hanssem.ontime.repository"))
class DatabaseConfig