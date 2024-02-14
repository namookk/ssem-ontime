package com.hanssem.ontime.service

import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.AuthDto

interface StaffService {
    fun getOrCreateStaff(googleDto: AuthDto.GoogleLoginDto) : Staff
}