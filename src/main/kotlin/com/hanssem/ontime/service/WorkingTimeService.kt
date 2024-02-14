package com.hanssem.ontime.service

import com.hanssem.ontime.payload.dto.WorkingTimeDto
import java.time.YearMonth

interface WorkingTimeService {
    fun getWorkingTimes(yearMonth: YearMonth): List<WorkingTimeDto>
    fun mergeWorkingTime(request: WorkingTimeDto.MergeWorkingTimeRequest): WorkingTimeDto
    fun getWorkingTimeStatistics(yearMonth: YearMonth): WorkingTimeDto.WorkingTimeStatisticsDto
}