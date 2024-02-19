package com.hanssem.ontime.service.impl

import com.fasterxml.jackson.core.JsonFactory
import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.service.GoogleConnectService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Service
class GoogleConnectServiceImpl: GoogleConnectService{

    @Value("\${google.oauth2.url}")
    private lateinit var authUrl: String

    @Value("\${google.oauth2.client-id}")
    private lateinit var clientId: String

    @Value("\${google.oauth2.client-secret}")
    private lateinit var clientSecret: String

    override fun authorize(code: String, redirectUri: String): AuthDto.GoogleResponse? {
        val webClient = WebClient.create(authUrl)

        val parameters: MultiValueMap<String, String> = LinkedMultiValueMap()
        parameters.add("code", code)
        parameters.add("client_id", clientId)
        parameters.add("client_secret", clientSecret)
        parameters.add("redirect_uri", redirectUri)
        parameters.add("grant_type", "authorization_code")

        return webClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(parameters))
                .retrieve()
                .bodyToMono(AuthDto.GoogleResponse::class.java)
                .block()
    }
}