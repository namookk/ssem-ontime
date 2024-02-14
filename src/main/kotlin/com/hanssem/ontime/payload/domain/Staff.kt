package com.hanssem.ontime.payload.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

@Entity
class Staff(
    email: String,
    name: String,
    socialKey: String
): ModificationAuditingEntity() {
    @Id
    @Column(name = "staff_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var email: String = email
        protected set

    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(nullable = false)
    var socialKey: String = socialKey
        protected set
}
