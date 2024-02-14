package com.hanssem.ontime.service

import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.JsonWebTokenDto

interface JsonWebTokenService {
    fun generateToken(staff: Staff) : JsonWebTokenDto
}