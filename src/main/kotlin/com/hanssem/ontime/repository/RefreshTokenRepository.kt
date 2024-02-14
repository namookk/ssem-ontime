package com.hanssem.ontime.repository

import com.hanssem.ontime.payload.domain.RefreshToken
import com.hanssem.ontime.payload.domain.Staff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByStaffId(staffId: Long): Optional<RefreshToken>
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken>
    fun deleteByStaffId(staffId: Long)
}