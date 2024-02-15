package com.hanssem.ontime.controller

import com.hanssem.ontime.payload.dto.WorkingTimeDto
import com.hanssem.ontime.service.WorkingTimeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.YearMonth

@RestController
@RequestMapping("/api/working-times")
@Tag(
        name = "근무시간",
        description = "온타임 근무시간 관련 Api",
)
@ApiResponses(
        ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
)
class WorkingTimeController(val workingTimeService: WorkingTimeService) {

    @Operation(summary = "이번달 근무시간 조회")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            ApiResponse(responseCode = "404", description = "NOT FOUND"),
            ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    )
    @GetMapping
    fun getWorkingTimes(): ResponseEntity<List<WorkingTimeDto>> {
        return ResponseEntity.ok(workingTimeService.getWorkingTimes(YearMonth.now()))
    }

    @Operation(summary = "근무시간 저장")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            ApiResponse(responseCode = "404", description = "NOT FOUND"),
            ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    )
    @PutMapping
    fun mergeWorkingTime(
            @Valid @Parameter(name = "근무일(yyyy-MM-dd)", required = true, example = "2024-02-13")
            @RequestBody request: WorkingTimeDto.MergeWorkingTimeRequest,
    ): ResponseEntity<WorkingTimeDto> {
        return ResponseEntity.ok(workingTimeService.mergeWorkingTime(request))
    }

    @Operation(summary = "근무시간 통계")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            ApiResponse(responseCode = "404", description = "NOT FOUND"),
            ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    )
    @GetMapping("/statistics")
    fun getWorkingTimeStatistics(): ResponseEntity<WorkingTimeDto.WorkingTimeStatisticsDto> {
        return ResponseEntity.ok(workingTimeService.getWorkingTimeStatistics(YearMonth.now()))
    }


}