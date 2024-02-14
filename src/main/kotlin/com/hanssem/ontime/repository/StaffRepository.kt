package com.hanssem.ontime.repository

import com.hanssem.ontime.payload.domain.Staff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StaffRepository: JpaRepository<Staff, Long> {
    fun findBySocialKey(socialKey: String): Optional<Staff>
}