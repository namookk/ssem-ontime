package com.hanssem.ontime.service.impl

import com.hanssem.ontime.payload.domain.RefreshToken
import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.JsonWebTokenDto
import com.hanssem.ontime.payload.mapper.AuthMapper
import com.hanssem.ontime.repository.RefreshTokenRepository
import com.hanssem.ontime.service.JsonWebTokenService
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey


@Service
class JsonWebTokenServiceImpl(
        val refreshTokenRepository: RefreshTokenRepository
) : JsonWebTokenService {
    private val GRANT_TYPE = "Bearer"
    private val KEY_ROLE = "role"
    private val MEMBER_KEY = "mbr"
    private val ONE_SECONDS_MS = 1000
    private val ONE_MINUTE_SEC = 60 * ONE_SECONDS_MS
    private val EXPIRE_MIN = 10
    private val REFRESH_EXPIRE_MIN = 20160

    @Value("\${jwt.secretKey}")
    private lateinit var secretKeyStr: String

    @Value("\${jwt.secretKey}")
    private lateinit var refreshSecretKeyStr: String

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyStr))
    private val refreshSecretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(refreshSecretKeyStr))

    override fun generateToken(staff: Staff): JsonWebTokenDto {
        val accessToken = createAccessToken(staff)
        val refreshToken = createRefreshToken(staff)
        return JsonWebTokenDto(
                grantType = GRANT_TYPE,
                accessToken, refreshToken
        )
    }

    private fun createAccessToken(staff: Staff): String {
        val now = Date()
        val expiration: Date = Date(
                now.time + ONE_MINUTE_SEC.toLong() * EXPIRE_MIN)
        return Jwts.builder()
                .setSubject(staff.email)
                .claim(KEY_ROLE, "USER")
                .claim(MEMBER_KEY, AuthMapper.INSTANCE.toDto(staff))
                .issuedAt(now)
                .expiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    private fun createRefreshToken(staff: Staff): String {
        val now = Date()
        val expiration = Date(now.time + ONE_MINUTE_SEC * REFRESH_EXPIRE_MIN)

        val entity = refreshTokenRepository.findByStaffId(staff.id!!)
                .orElseGet {
                    val refreshToken = buildRefreshJwtToken(now, expiration)
                    RefreshToken.createInstance(staff.id!!, refreshToken, expiration)
                }

        if (entity.expired()) {
            val refreshToken = buildRefreshJwtToken(now, expiration)
            entity.update(refreshToken, expiration)
        }

        return refreshTokenRepository.save(entity).refreshToken
    }

    private fun buildRefreshJwtToken(issueAt: Date, expiration: Date): String {
        return Jwts.builder()
                .issuedAt(issueAt)
                .expiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(SignatureAlgorithm.HS512, refreshSecretKey)
                .compact();
    }
}