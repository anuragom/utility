package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;
import com.omnivers.utility_service.dto.CNDetailDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CNActivationService {
    ApiResponse<Object> activateCN(Long cnNo, String ewayBillNo, CNActivationRequest request);

    List<CNDetailDTO> getCNDetail(Long cnNo);
}

