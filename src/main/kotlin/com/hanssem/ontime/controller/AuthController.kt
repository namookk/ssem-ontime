package com.hanssem.ontime.controller

import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.payload.dto.JsonWebTokenDto
import com.hanssem.ontime.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증/인가", description = "회원가입, 로그인, 토큰 재발행")
@ApiResponses(
        ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
)
class AuthController(
        private val authService: AuthService
) {

    @Operation(summary = "구글 로그인 콜백 API")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "201", description = "CREATED"),
            ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            ApiResponse(responseCode = "404", description = "NOT FOUND"),
            ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    )
    @PostMapping("/google/callback")
    fun signIn(
            @Parameter(name = "OAuth2 Callback 요청 정보", required = true)
            @Valid @RequestBody request: AuthDto.OAuthCallbackRequestDto
    ): ResponseEntity<JsonWebTokenDto> {
        return ResponseEntity.ok(authService.signIn(request))
    }
}