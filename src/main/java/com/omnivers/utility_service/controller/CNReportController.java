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
}
