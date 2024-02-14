package com.hanssem.ontime.config.filter

import com.hanssem.ontime.config.authentication.JwtAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(val authenticationManager: AuthenticationManager): OncePerRequestFilter() {

    private val AUTHORIZATION_HEADER: String = "Authorization";
    private val BEARER_PREFIX: String = "Bearer "
    private val BEARER_PREFIX_LENGTH: Int = 7

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val jwt:String? = resolveToken(request)
        if(StringUtils.hasText(jwt)) {
            try {
                val jwtAuthenticationToken = JwtAuthenticationToken(jwt!!)
                val authentication = authenticationManager.authenticate(jwtAuthenticationToken)
                SecurityContextHolder.getContext().authentication = authentication
            }catch (authenticationException: AuthenticationException) {
                SecurityContextHolder.clearContext()
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        var bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX_LENGTH);
        }
        return null
    }
}