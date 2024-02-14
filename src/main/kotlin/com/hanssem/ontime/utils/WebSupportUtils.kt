package com.hanssem.ontime.utils

import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.repository.StaffRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function

@Component
class WebSupportUtils(
        val staffRepository: StaffRepository
) {
    fun getLoginAuthDto(): Optional<AuthDto> {
        return Optional.ofNullable<SecurityContext>(SecurityContextHolder.getContext())
                .map<Authentication> { it.authentication }
                .filter { it.isAuthenticated }
                .map<Any> { it.principal }
                .filter { it is AuthDto }
                .map(Function { AuthDto::class.java.cast(it) })
    }

    fun getLoginStaff(): Staff {
        val authDto = getLoginAuthDto()
                .orElseThrow { AccessDeniedException("권한이 없습니다.") }

        val staffId: Long = authDto.id
        return staffRepository.findById(staffId)
                .orElseThrow { EntityNotFoundException("%d 유효하지 않은 사용자 아이디입니다.".formatted(staffId)) }
    }
}