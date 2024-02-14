package com.hanssem.ontime.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Value("\${spring.profiles.active:Unknown}")
    private lateinit var activeProfiles: String

    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
                .info(apiInfo())
                .components(Components())
    }

    private fun apiInfo(): Info {
        return Info()
                .title("${activeProfiles} 샘타임 API GateWay")
                .version("v1")
                .description("Ssem-Ontime API Documentation")
                .license(License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"))
                .contact(Contact()
                        .name("기간계시스템 개발부")
                        .url("")
                        .email("nwhwang@hanssem.com"))
    }
}