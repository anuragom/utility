package com.omnivers.utility_service.controller;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNReportFilterDTO;
import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.service.CNReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cn-report")
@RequiredArgsConstructor
@Slf4j
public class CNReportController {

    private final CNReportService cnReportService;

    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<CNReportDTO>>> filterCNReports(
            @Valid @RequestBody CNReportFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            Page<CNReportDTO> result = cnReportService.getFilteredCNReports(filterDTO, page, size);
            return ResponseEntity.ok(ApiResponse.success("CN reports retrieved successfully", result));
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid request for CN reports: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
        } catch (Exception ex) {
            log.error("Error retrieving CN reports", ex);
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.failure("Error retrieving CN reports: " + errorMessage));
        }
    }

    @GetMapping(value = "/activated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<CNReportDTO>>> getActivatedCNReports(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
                @RequestParam(required = false, defaultValue = "CN_DATE") String dateType,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            Page<CNReportDTO> result = cnReportService.getActivatedCNReports(fromDate, toDate, dateType, page, size);
            return ResponseEntity.ok(ApiResponse.success("Activated CN reports retrieved successfully", result));
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid request for activated CN reports: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
        } catch (Exception ex) {
            log.error("Error retrieving activated CN reports", ex);
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.failure("Error retrieving activated CN reports: " + errorMessage));
        }
    }

    @GetMapping(value = "/draft", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Page<CNReportDTO>>> getDraftCNReports(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String ewbStatus,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            Page<CNReportDTO> result = cnReportService.getDraftCNReports(fromDate, toDate, ewbStatus, page, size);
            return ResponseEntity.ok(ApiResponse.success("Draft CN reports retrieved successfully", result));
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid request for draft CN reports: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
        } catch (Exception ex) {
            log.error("Error retrieving draft CN reports", ex);
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.failure("Error retrieving draft CN reports: " + errorMessage));
        }
    }
}
