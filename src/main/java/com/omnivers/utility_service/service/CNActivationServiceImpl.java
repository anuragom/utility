package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.ApiResponse;
import com.omnivers.utility_service.dto.CNActivationRequest;
import com.omnivers.utility_service.repository.CNActivationRepository;
import com.omnivers.utility_service.repository.CNReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CNActivationServiceImpl implements CNActivationService {

    private final CNActivationRepository cnActivationRepository;
    private final CNReportRepository cnReportRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Object> activateCN(CNActivationRequest request) {

        Integer isFoc = (request.getIsFoc() != null && request.getIsFoc()) ? 1 : 0;
        Integer freightType = request.getFreightType();
        Integer customerCode = request.getCustomerCode();

        // --- Step 1: Update CN Master Table (OPS_CN_M)
        int masterUpdated = cnReportRepository.updateCNMaster(
                request.getCnNo(),
                isFoc,
                freightType,
                customerCode,
                request.getTransportMode(),
                request.getLoadType(),
                request.getCollectionMode(),
                request.getCollectionAmount() != null ? BigDecimal.valueOf(request.getCollectionAmount()) : null
        );

        if (masterUpdated == 0) {
            throw new IllegalArgumentException("CN number not found or could not be updated: " + request.getCnNo());
        }

        // --- Step 2: Update CN Detail Table (OPS_CN_D)
        int detailUpdated = cnActivationRepository.updateCNDetail(
                request.getCnNo(),
                request.getEwayBillNo(),
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
            throw new IllegalArgumentException(
                    "CN detail not found for CN number: " + request.getCnNo() +
                            " and E-way bill: " + request.getEwayBillNo()
            );
        }

        return ApiResponse.success("CN activated successfully", null);
    }
}
