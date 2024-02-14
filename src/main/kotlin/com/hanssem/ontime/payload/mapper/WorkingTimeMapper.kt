package com.hanssem.ontime.payload.mapper

import com.hanssem.ontime.payload.YN
import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.domain.WorkingTime
import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.payload.dto.WorkingTimeDto
import com.hanssem.ontime.payload.mapper.util.EnumYNMapper
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper(uses = [EnumYNMapper::class])
interface WorkingTimeMapper {

    companion object {
        val INSTANCE: WorkingTimeMapper = Mappers.getMapper(WorkingTimeMapper::class.java)
    }

    @Mapping(target = "workingDate", source = "calendar.workingDate")
    @Mapping(target = "workingMinutes", source = "workingMinutes")
    @Mapping(target = "holiday", source = "calendar.holidayYN", qualifiedBy = [EnumYNMapper.ToBoolean::class])
    fun toDto(workingTime: WorkingTime) : WorkingTimeDto
}