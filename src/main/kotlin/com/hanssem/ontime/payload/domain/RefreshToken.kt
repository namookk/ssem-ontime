package com.hanssem.ontime.payload.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity
class RefreshToken(
    staffId: Long,
    refreshToken: String,
    expiryDate: Instant
): ModificationAuditingEntity() {
    @Id
    @Column(name = "refresh_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var staffId: Long = staffId
        protected set

    @Column(nullable = false)
    var refreshToken: String = refreshToken
        protected set

    @Column(nullable = false)
    var expiryDate: Instant = expiryDate
        protected set

    companion object {
        fun createInstance(staffId: Long, refreshToken: String, expiry: Date): RefreshToken {
            val expiryDate: Instant = Instant.ofEpochMilli(expiry.time)
            return  RefreshToken(
                    staffId = staffId,
                    refreshToken = refreshToken,
                    expiryDate = expiryDate
            )
        }
    }

    fun update(refreshToken: String, expiry: Date) {
        this.refreshToken = refreshToken
        this.expiryDate = Instant.ofEpochMilli(expiry.time)
    }

    fun expired(): Boolean {
        return expiryDate.compareTo(Instant.now()) < 0
    }
}
