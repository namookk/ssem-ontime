package com.hanssem.ontime.payload.dto

data class JsonWebTokenDto(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String
) {
}