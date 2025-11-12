package com.omnivers.utility_service.controller;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;
import com.omnivers.utility_service.dto.CNDetailDTO;
import com.omnivers.utility_service.service.CNActivationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cn")
@RequiredArgsConstructor
@Slf4j
public class CNActivationController {

    private final CNActivationService cnActivationService;

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<Object>> activateCN(
            @RequestParam("cnNo") Long cnNo,
            @RequestParam("ewayBillNo") String ewayBillNo,
            @Valid @RequestBody CNActivationRequest requestBody
    ) {
        try {
            ApiResponse<Object> response = cnActivationService.activateCN(cnNo, ewayBillNo, requestBody);
            if ("failure".equalsIgnoreCase(response.getStatus())) {
                log.warn("Activation failed for CN: {} E-way: {}. Reason: {}", cnNo, ewayBillNo, response.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
            log.info("CN activated successfully: {} E-way: {}", cnNo, ewayBillNo);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Invalid request for CN activation: {} E-way: {}. Error: {}", cnNo, ewayBillNo, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(e.getMessage()));

        } catch (Exception e) {
            log.error("Unexpected error while activating CN: {} E-way: {}", cnNo, ewayBillNo, e);
            String errorMsg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure(errorMsg));
        }
    }


    @GetMapping("/print/{cnNo}")
    public ResponseEntity<ApiResponse<List<CNDetailDTO>>> getCN(@PathVariable Long cnNo) {
        try {
            if (cnNo == null || cnNo <= 0) {
                throw new IllegalArgumentException("Invalid CN number: " + cnNo);
            }
            List<CNDetailDTO> cnDetails = cnActivationService.getCNDetail(cnNo);

            if (cnDetails == null || cnDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.failure("CN not found: " + cnNo));
            }
            return ResponseEntity.ok(ApiResponse.success("CN fetched successfully", cnDetails));

        } catch (Exception e) {
            log.error("Error fetching CN: {}", cnNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure("Error fetching CN: " + e.getMessage()));
        }
    }
}
