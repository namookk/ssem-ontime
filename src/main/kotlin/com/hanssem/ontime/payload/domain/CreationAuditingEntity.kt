package com.hanssem.ontime.payload.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class CreationAuditingEntity(
        createBy: Long? = 0L,
        createAt: LocalDateTime = LocalDateTime.now()
) {

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @CreatedBy
        @Column(name = "create_id", nullable = false)
        var createBy: Long? = createBy
                set(value) {
                        field = value
                }

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        @CreatedDate
        @Column(name = "create_at", nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
        var createAt: LocalDateTime = createAt
                set(value) {
                        field = value
                }
}