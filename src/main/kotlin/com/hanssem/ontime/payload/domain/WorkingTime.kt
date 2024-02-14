package com.hanssem.ontime.payload.domain

import com.hanssem.ontime.payload.dto.WorkingTimeDto
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDate
import java.time.LocalDateTime

@IdClass(WorkingTimePK::class)
@Entity
class WorkingTime(
    staff: Staff,
    calendar: Calendar,
    workingMinutes: Int
): ModificationAuditingEntity() {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    val staff: Staff = staff

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "working_date", nullable = false)
    val calendar: Calendar = calendar

    @Column(name="working_minutes", nullable = false)
    var workingMinutes: Int = workingMinutes

    fun modifyWorkingMinutes(workingMinutes: Int) {
        this.workingMinutes = workingMinutes
    }
}
