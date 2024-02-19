package com.hanssem.ontime.service.impl

import com.hanssem.ontime.payload.YN
import com.hanssem.ontime.payload.domain.Calendar
import com.hanssem.ontime.payload.domain.WorkingTime
import com.hanssem.ontime.payload.domain.WorkingTimePK
import com.hanssem.ontime.payload.dto.WorkingTimeDto
import com.hanssem.ontime.payload.mapper.WorkingTimeMapper
import com.hanssem.ontime.repository.CalendarRepository
import com.hanssem.ontime.repository.WorkingTimeRepository
import com.hanssem.ontime.service.WorkingTimeService
import com.hanssem.ontime.utils.WebSupportUtils
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters.lastDayOfMonth

@Service
class WorkingTimeServiceImpl(
        val workingTimeRepository: WorkingTimeRepository,
        val calendarRepository: CalendarRepository,
        val webSupportUtils: WebSupportUtils
) : WorkingTimeService {

    final val ONE_HOUR_MINUTE:Int = 60
    final val REQUIRED_WORKING_MINUTE:Int = ONE_HOUR_MINUTE * 8

    override fun getWorkingTimes(yearMonth: YearMonth): List<WorkingTimeDto> {
        val staff = webSupportUtils.getLoginStaff()
        val startWorkingDate = LocalDate.of(yearMonth.year, yearMonth.month, 1)
        val endWorkingDate = startWorkingDate.with(lastDayOfMonth())
        val calendars = calendarRepository.findAllByWorkingDateBetween(startWorkingDate, endWorkingDate)
        val workingTimes = workingTimeRepository.findAllByStaffAndCalendarIn(staff, calendars)

        val workingTimeMap: Map<Calendar, WorkingTime> = workingTimes.associateBy { it.calendar }
        val result = arrayListOf<WorkingTimeDto>()
        for(calendar in calendars) {
            val workingTime:WorkingTime? = workingTimeMap.get(calendar)
            if(workingTime == null) {
                result.add(WorkingTimeDto.emptyInstance(calendar.workingDate, calendar.holidayYN))
                continue
            }
            result.add(WorkingTimeMapper.INSTANCE.toDto(workingTime))
        }

        return result
    }

    @Transactional
    override fun mergeWorkingTime(request: WorkingTimeDto.MergeWorkingTimeRequest): WorkingTimeDto {
        val staff = webSupportUtils.getLoginStaff()
        val workingTimePK = WorkingTimePK(staff.id!!, request.workingDate)
        val workingTime = workingTimeRepository.findById(workingTimePK)
                .orElseGet{
                    val calendar = calendarRepository.findById(request.workingDate)
                            .orElseThrow{EntityNotFoundException("존재하지 않은 근무일정입니다.")}
                    workingTimeRepository.save(WorkingTime(staff, calendar, request.getWorkingMinutes()))
                }

        workingTime.modifyWorkingMinutes(request.getWorkingMinutes())
        return WorkingTimeMapper.INSTANCE.toDto(workingTime)
    }

    override fun getWorkingTimeStatistics(yearMonth: YearMonth): WorkingTimeDto.WorkingTimeStatisticsDto {
        val staff = webSupportUtils.getLoginStaff()
        val startWorkingDate = LocalDate.of(yearMonth.year, yearMonth.month, 1)
        val endWorkingDate = startWorkingDate.with(lastDayOfMonth())

        val workingTimes = workingTimeRepository.findAllByStaffAndCalendar_WorkingDateBetween(staff, startWorkingDate, endWorkingDate)

        val workingCalendars = calendarRepository.findAllByWorkingDateBetweenAndHolidayYN(startWorkingDate, LocalDate.now(), YN.N)
        val requiredWorkingMinutes = (workingCalendars.size - 1) * REQUIRED_WORKING_MINUTE
        val workingMinutes = workingTimes.map{it.workingMinutes}.sum()

        return WorkingTimeDto.WorkingTimeStatisticsDto(
                workingYearMonth = yearMonth,
                extraAddWorkingMinutes = workingMinutes - requiredWorkingMinutes
        )
    }
}