package com.hanssem.ontime.repository

import com.hanssem.ontime.payload.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WorkingTimeRepository: JpaRepository<WorkingTime, WorkingTimePK> {

    fun findAllByStaffAndCalendarIn(staff: Staff, calendars: List<Calendar>) : List<WorkingTime>
    fun findAllByStaffAndCalendar_WorkingDateBetween(staff: Staff, startWorkingDate: LocalDate, endWorkingDate: LocalDate): List<WorkingTime>
}