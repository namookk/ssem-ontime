package com.hanssem.ontime.service.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.payload.dto.JsonWebTokenDto
import com.hanssem.ontime.service.AuthService
import com.hanssem.ontime.service.GoogleConnectService
import com.hanssem.ontime.service.JsonWebTokenService
import com.hanssem.ontime.service.StaffService
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
        private val googleConnectService: GoogleConnectService,
        private val staffService: StaffService,
        private val jsonWebTokenService: JsonWebTokenService
): AuthService {
    override fun signIn(request: AuthDto.OAuthCallbackRequestDto): JsonWebTokenDto {
        val googleLoginDto: AuthDto.GoogleLoginDto = signInByGoogle(request)
        val staff = staffService.getOrCreateStaff(googleLoginDto)
        return jsonWebTokenService.generateToken(staff)
    }

    private fun signInByGoogle(request: AuthDto.OAuthCallbackRequestDto): AuthDto.GoogleLoginDto {
        val code = request.code
        val redirectUrl = request.redirectUri

        val jsonBody = googleConnectService.authorize(code, redirectUrl)?.getIdTokenData()
        val objectMapper = getObjectMapper()
        val originGoogleLoginDto = objectMapper.readValue(jsonBody, AuthDto.OriginGoogleLoginDto::class.java)

        return AuthDto.GoogleLoginDto.newInstance(originGoogleLoginDto)
    }

    private fun getObjectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModules(KotlinModule(nullIsSameAsDefault = true))
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        return objectMapper
    }
}