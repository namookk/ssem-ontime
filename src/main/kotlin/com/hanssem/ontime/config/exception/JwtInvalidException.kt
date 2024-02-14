package com.hanssem.ontime.config.exception

import org.springframework.security.core.AuthenticationException

class JwtInvalidException: AuthenticationException {
    constructor(msg: String):super(msg)
    constructor(msg: String, cause: Throwable):super(msg, cause)
}