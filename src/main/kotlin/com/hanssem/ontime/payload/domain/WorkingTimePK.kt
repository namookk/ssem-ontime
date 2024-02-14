package com.hanssem.ontime.payload.domain

import java.io.Serializable
import java.time.LocalDate

class WorkingTimePK(
        val staff: Long = 0L,
        val calendar: LocalDate = LocalDate.now()
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkingTimePK

        if (staff != other.staff) return false
        if (calendar != other.calendar) return false

        return true
    }

    override fun hashCode(): Int {
        var result = staff.hashCode()
        result = 31 * result + calendar.hashCode()
        return result
    }
}