package com.hanssem.ontime.config.authentication

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hanssem.ontime.config.exception.JwtInvalidException
import com.hanssem.ontime.payload.dto.AuthDto
import io.jsonwebtoken.*
import io.jsonwebtoken.impl.security.ProviderSecretKey
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtAuthenticationProvider(@Value("\${jwt.secretKey}") secretKey: String): AuthenticationProvider{

    private val KEY_ROLE = "role"
    private val MEMBER_KEY = "mbr"
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey))

    override fun authenticate(authentication: Authentication): Authentication {
        val claims: Claims
        val authDto: AuthDto
        try{
            val jsonWebToken = (authentication as JwtAuthenticationToken).getJsonWebToken()
            claims = Jwts.parser().verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jsonWebToken).body
            val objectMapper = getObjectMapper()
            try{
                val authDtoJsonStr = objectMapper.writeValueAsString(claims.get(MEMBER_KEY))
                authDto = objectMapper.readValue(authDtoJsonStr, AuthDto::class.java)
            }catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
        } catch (signatureException: SignatureException) {
            throw JwtInvalidException("유효하지 않은 토큰입니다.", signatureException)
        } catch (expiredJwtException: ExpiredJwtException) {
            throw JwtInvalidException("토큰의 유효기간이 만료되었습니다.", expiredJwtException)
        } catch (malformedJwtException: MalformedJwtException) {
            throw JwtInvalidException("잘못된 형식의 토큰입니다.", malformedJwtException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw JwtInvalidException("유효하지 않은 정보입니다.", illegalArgumentException)
        }

        return JwtAuthenticationToken(authDto, "", createGrantedAuthorities(claims))
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return JwtAuthenticationToken::class.java.isAssignableFrom(authentication!!)
    }

    private fun createGrantedAuthorities(claims: Claims): Collection<out GrantedAuthority> {
        val role = claims[KEY_ROLE] as String
        val grantedAuthorities = ArrayList<GrantedAuthority>()
        grantedAuthorities.add { role }
        return grantedAuthorities
    }

    private fun getObjectMapper(): ObjectMapper {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModules(KotlinModule(nullIsSameAsDefault = true))
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        return objectMapper
    }
}