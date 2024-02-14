package com.hanssem.ontime.payload.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.hanssem.ontime.payload.YN
import com.hanssem.ontime.payload.dto.WorkingTimeDto
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Calendar(
    workingDate: LocalDate,
    holidayYN: YN
): CreationAuditingEntity() {

    @Id
    @Column(name = "working_date", nullable = false)
    val workingDate: LocalDate = workingDate

    @Enumerated(EnumType.STRING)
    @Column(name="holidy_yn", nullable = false)
    val holidayYN: YN = holidayYN
}
