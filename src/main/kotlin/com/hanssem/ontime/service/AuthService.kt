package com.hanssem.ontime.service

import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.payload.dto.JsonWebTokenDto

interface AuthService {
    fun signIn(request: AuthDto.OAuthCallbackRequestDto): JsonWebTokenDto
}