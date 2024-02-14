package com.hanssem.ontime.payload.mapper

import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.AuthDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface AuthMapper {

    companion object {
        val INSTANCE: AuthMapper = Mappers.getMapper(AuthMapper::class.java)
    }

    fun toDto(staff: Staff) : AuthDto
}