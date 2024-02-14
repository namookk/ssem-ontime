package com.hanssem.ontime.payload.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.hanssem.ontime.payload.YN
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

data class WorkingTimeDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(title = "근무일", example = "2024-02-13")
        val workingDate: LocalDate,

        @Schema(hidden = true)
        @JsonIgnore
        val workingMinutes: Int = 0,

        @Schema(title = "휴일여부", example = "true")
        val holiday: Boolean,
) {

    @Schema(title = "요일", example = "MONDAY")
    var dayOfWeek: DayOfWeek? = null
        get() {
            return workingDate.dayOfWeek
        }
        private set

    @Schema(title = "근무시간", required = false, example = "7시간 20분")
    var workingTime: String? = null
        get(){
            if(workingMinutes == 0) {
                return null
            }

            return LocalTime.of(workingMinutes / 60, workingMinutes % 60)
                    .format(DateTimeFormatter.ofPattern("HH시간 mm분"))
        }
        private set

    companion object{
        fun emptyInstance(workingDate: LocalDate, holidayYN: YN): WorkingTimeDto {
            return WorkingTimeDto(
                    workingDate = workingDate,
                    holiday = YN.toBoolean(holidayYN)
            )
        }
    }

    data class MergeWorkingTimeRequest(
            @Schema(title = "근무일(yyyy-MM-dd)", example = "2024-02-10")
            val workingDate: LocalDate,

            @Min(value = 0, message = "근무시간_시는 0~23 사이의 숫자입니다.")
            @Max(value = 23, message = "근무시간_시는 0~23 사이의 숫자입니다.")
            @Schema(title = "근무시간_시", example = "8")
            val hour: Int,

            @Min(value = 0, message = "근무시간_분은 0~59 사이의 숫자입니다.")
            @Max(value = 59, message = "근무시간_분은 0~59 사이의 숫자입니다.")
            @Schema(title = "근무시간_분", example = "45")
            val minute: Int,
    ) {

        @Schema(hidden = true)
        fun getWorkingMinutes(): Int {
            return hour * 60 + minute
        }
    }

    data class WorkingTimeStatisticsDto(
            @JsonFormat(pattern = "yyyy-MM")
            @Schema(title = "근무월(yyyy-MM)", example = "2024-02")
            val workingYearMonth: YearMonth,

            @Schema(hidden = true)
            @JsonIgnore
            val extraAddWorkingMinutes: Int = 0,
    ) {

        @Schema(title = "여분의 추가근무시간", example = "40시간")
        var extraAddWorkingTime: String? = null
            get(){
                if(extraAddWorkingMinutes == 0) {
                    return null
                }

                if(extraAddWorkingMinutes < 0) {
                    val minutes = extraAddWorkingMinutes * -1
                    return "-" + LocalTime.of(minutes / 60, minutes % 60)
                            .format(DateTimeFormatter.ofPattern("HH시간 mm분"))
                }

                return LocalTime.of(extraAddWorkingMinutes / 60, extraAddWorkingMinutes % 60)
                        .format(DateTimeFormatter.ofPattern("HH시간 mm분"))
            }
            private set
    }
}