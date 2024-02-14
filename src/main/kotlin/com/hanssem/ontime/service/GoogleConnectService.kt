package com.hanssem.ontime.service

import com.hanssem.ontime.payload.dto.AuthDto

interface GoogleConnectService {
    fun authorize(code: String, redirectUri: String) : AuthDto.GoogleResponse?
}