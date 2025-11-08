package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;
import com.omnivers.utility_service.repository.CNActivationRepository;
import com.omnivers.utility_service.repository.CNReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CNActivationServiceImpl implements CNActivationService {

    private final CNActivationRepository cnActivationRepository;
    private final CNReportRepository cnReportRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Object> activateCN(Long cnNo, String ewayBillNo, CNActivationRequest request) {

        Integer isFoc = (request.getIsFoc() != null && request.getIsFoc()) ? 1 : 0;

        int masterUpdated = cnReportRepository.updateCNMaster(
                cnNo,
                isFoc,
                request.getFreightType(),
                request.getCustomerCode(),
                request.getTransportMode(),
                request.getLoadType(),
                request.getCollectionMode(),
                request.getCollectionAmount() != null ? BigDecimal.valueOf(request.getCollectionAmount()) : null
        );

        if (masterUpdated == 0) {
            log.warn("CN Master not found or could not be updated for CN: {}.", cnNo);
            throw new IllegalArgumentException("CN Master not found or could not be updated for CN: " + cnNo);
        }

        int detailUpdated = cnActivationRepository.updateCNDetail(
                cnNo,
                ewayBillNo,
                request.getPackageCount(),
                request.getQuantity(),
                request.getAsnNo(),
                request.getPackingType(),
                request.getItemDescription(),
                request.getLength() != null ? BigDecimal.valueOf(request.getLength()) : null,
                request.getWidth() != null ? BigDecimal.valueOf(request.getWidth()) : null,
                request.getHeight() != null ? BigDecimal.valueOf(request.getHeight()) : null,
                request.getActualWeight() != null ? BigDecimal.valueOf(request.getActualWeight()) : null,
                request.getChargedWeight() != null ? BigDecimal.valueOf(request.getChargedWeight()) : null,
                request.getCftWeight() != null ? BigDecimal.valueOf(request.getCftWeight()) : null,
                request.getPoNumber()
        );

        if (detailUpdated == 0) {
            log.warn("CN Detail update failed for CN: {} E-way: {}.", cnNo, ewayBillNo);
            throw new IllegalArgumentException("Failed to update CN detail for CN: " + cnNo + " and E-way bill: " + ewayBillNo);
        }

        return ApiResponse.success("CN activated successfully", null);
    }
}
