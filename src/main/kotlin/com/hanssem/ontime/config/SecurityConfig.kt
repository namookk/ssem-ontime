package com.hanssem.ontime.config

import com.hanssem.ontime.config.authentication.JwtAuthenticationProvider
import com.hanssem.ontime.config.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.web.cors.CorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig(final val authenticationManagerBuilder: AuthenticationManagerBuilder, final val jsonWebTokenProvider: JwtAuthenticationProvider, val corsConfigurationSource: CorsConfigurationSource) {

    init {
        this.authenticationManagerBuilder.authenticationProvider(jsonWebTokenProvider)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .authorizeHttpRequests {
                    it
                            .requestMatchers(
                                    "/api-docs/json/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui/index.html", "/h2-console/**", "/auth/**",
                                    "/webjars/**", "/swagger/**", "/health-check", "/time", "/error")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                }
                .cors(withDefaults())
                .headers { it.addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) }
                .formLogin { it.disable() }
                .httpBasic { it.disable() }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .addFilterAfter(JwtAuthenticationFilter(authenticationManagerBuilder.getOrBuild()), LogoutFilter::class.java)

        return http.build()
    }
}