package com.omnivers.utility_service.controller;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;
import com.omnivers.utility_service.service.CNActivationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cn")
@RequiredArgsConstructor
public class CNActivationController {

    private final CNActivationService cnActivationService;

    @PostMapping(value = "/activate")
    public ResponseEntity<ApiResponse<Object>> activateCN(
            @Valid @RequestBody CNActivationRequest request
    ) {
        try {
            ApiResponse<Object> response = cnActivationService.activateCN(request);
            
            if ("failure".equals(response.getStatus())) {
                return ResponseEntity.badRequest().body(response);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.failure(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = e.getClass().getSimpleName() + ": " + 
                        (e.getCause() != null ? e.getCause().getMessage() : "Unknown error");
            }
            return ResponseEntity.status(500)
                    .body(ApiResponse.failure("Error activating CN: " + errorMessage));
        }
    }
}

