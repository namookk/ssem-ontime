package com.hanssem.ontime.config.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken: AbstractAuthenticationToken {

    private lateinit var jsonWebToken: String
    private var principal: Any? = null
    private var credentials: Any? = null

    constructor(jsonWebToken: String):super(null) {
        this.jsonWebToken = jsonWebToken
        this.isAuthenticated = false
    }

    constructor(principal: Any, credentials: String, authorities: Collection<out GrantedAuthority>) : super(authorities) {
        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = true
    }

    override fun getCredentials(): Any? {
       return credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    fun getJsonWebToken(): String {
        return jsonWebToken
    }
}