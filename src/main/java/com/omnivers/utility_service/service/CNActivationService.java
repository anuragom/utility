package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;

public interface CNActivationService {
    ApiResponse<Object> activateCN(CNActivationRequest request);
}

