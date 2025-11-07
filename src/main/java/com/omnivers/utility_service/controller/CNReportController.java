package com.omnivers.utility_service.controller;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNReportFilterDTO;
import com.omnivers.utility_service.dto.CNReportFilterRequest;
import com.omnivers.utility_service.dto.PageResponse;
import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.service.CNReportService;
import com.omnivers.utility_service.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cn-report")
@RequiredArgsConstructor
public class CNReportController {

    private final CNReportService cnReportService;

    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<PageResponse<CNReportDTO>>> filterCNReports(
            @Valid @RequestBody CNReportFilterDTO filterDTO,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        try {
            // Parse and validate dates
            LocalDate[] dates = DateUtil.parseAndValidateDateRange(
                    filterDTO.getFromDate(), 
                    filterDTO.getToDate()
            );
            
            // Build request object
            CNReportFilterRequest request = CNReportFilterRequest.builder()
                    .fromDate(dates[0])
                    .toDate(dates[1])
                    .dateType(filterDTO.getDateType())
                    .cnStatus(filterDTO.getCnStatus())
                    .ewbStatus(filterDTO.getEwbStatus())
                    .page(page)
                    .size(size)
                    .build();

            // Get filtered results (validation happens in service layer)
            PageResponse<CNReportDTO> result = cnReportService.getFilteredCNReports(request);
            return ResponseEntity.ok(ApiResponse.success("CN reports retrieved successfully", result));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.failure(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace for debugging
            String errorMessage = e.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = e.getClass().getSimpleName() + ": " + 
                        (e.getCause() != null ? e.getCause().getMessage() : "Unknown error");
            }
            return ResponseEntity.status(500)
                    .body(ApiResponse.failure("Error retrieving CN reports: " + errorMessage));
        }
    }
}
