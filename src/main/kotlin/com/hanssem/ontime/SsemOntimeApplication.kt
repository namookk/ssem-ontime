package com.hanssem.ontime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class SsemOntimeApplication

fun main(args: Array<String>) {
    runApplication<SsemOntimeApplication>(*args)
}
