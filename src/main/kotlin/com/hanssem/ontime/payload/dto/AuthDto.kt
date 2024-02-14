package com.hanssem.ontime.payload.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets

data class AuthDto(
        @Schema(title = "아이디")
        val id: Long,

        @Schema(title = "이메일")
        val email: String,

        @Schema(title = "이름")
        val name: String) {

    data class OAuthCallbackRequestDto(
            @NotBlank
            @Schema(title = "리다이렉트 URI", required = true)
            val redirectUri: String,

            @NotBlank
            @Schema(title = "CODE 값", required = true)
            val code: String
    )

    data class GoogleResponse(
            val access_token: String,
            val refresh_token: String?,
            val expires_in: Long,
            val scope: String,
            val token_type: String,
            val id_token: String
    ) {
        fun getIdTokenData(): String? {
            if (!StringUtils.hasText(id_token)) {
                return null
            }
            val tokens = id_token.split(".")
            return String(Base64.decodeBase64(tokens[1]), StandardCharsets.UTF_8)
        }
    }

    data class OriginGoogleLoginDto(
        var iss: String? = null,
        var azp: String? = null,
        var aud: String? = null,
        val sub: String,
        val email: String,
        var emailVerified: Boolean? = null,
        var atHash: String? = null,
        val name: String,
        var picture: String? = null,
        var givenName: String? = null,
        var familyName: String? = null,
        var locale: String? = null,
        var iat: Long? = null,
        var exp: Long? = null,
        var hd: String? = null,
    )

    data class GoogleLoginDto(
            val socialKey: String,
            val email: String,
            val name: String
    ) {
        companion object {
            fun newInstance(originGoogleLoginDto: OriginGoogleLoginDto): GoogleLoginDto {
                return GoogleLoginDto(originGoogleLoginDto.sub!!, originGoogleLoginDto.email!!, originGoogleLoginDto.name!!)
            }
        }
    }
}