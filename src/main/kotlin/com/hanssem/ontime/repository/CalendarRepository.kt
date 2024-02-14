package com.hanssem.ontime.repository

import com.hanssem.ontime.payload.YN
import com.hanssem.ontime.payload.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CalendarRepository: JpaRepository<Calendar, LocalDate> {
    fun findAllByWorkingDateBetweenAndHolidayYN(startWorkingDate: LocalDate, endWorkingDate: LocalDate, holidayYN: YN): List<Calendar>
    fun findAllByWorkingDateBetween(startWorkingDate: LocalDate, endWorkingDate: LocalDate): List<Calendar>
}