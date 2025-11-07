package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNReportFilterRequest;
import com.omnivers.utility_service.dto.PageResponse;
import com.omnivers.utility_service.repository.CNReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CNReportServiceImpl implements CNReportService {

    private final CNReportRepository cnReportRepository;
    
    private static final List<String> VALID_DATE_TYPES = Arrays.asList("CN_DATE", "EWB_DATE");
    private static final List<String> VALID_CN_STATUSES = Arrays.asList("ACTIVATED", "DRAFT");
    private static final List<String> VALID_EWB_STATUSES = Arrays.asList("", "EXPIRED");
    
    /**
     * Validates and normalizes filter request parameters
     * @param request the filter request to validate
     * @throws IllegalArgumentException if validation fails
     */
    public void validateAndNormalizeRequest(CNReportFilterRequest request) {
        // Validate dateType
        if (request.getDateType() != null && !request.getDateType().trim().isEmpty()) {
            String upperDateType = request.getDateType().trim().toUpperCase();
            if (!VALID_DATE_TYPES.contains(upperDateType)) {
                throw new IllegalArgumentException(
                    String.format("Invalid dateType: '%s'. Valid values are: %s", 
                        request.getDateType(), String.join(", ", VALID_DATE_TYPES)));
            }
            request.setDateType(upperDateType);
        }
        
        // Validate cnStatus (required field)
        if (request.getCnStatus() == null || request.getCnStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("cnStatus is required and cannot be null or empty");
        }
        String upperCnStatus = request.getCnStatus().trim().toUpperCase();
        if (!VALID_CN_STATUSES.contains(upperCnStatus)) {
            throw new IllegalArgumentException(
                String.format("Invalid cnStatus: '%s'. Valid values are: %s", 
                    request.getCnStatus(), String.join(", ", VALID_CN_STATUSES)));
        }
        request.setCnStatus(upperCnStatus);
        
        // Validate ewbStatus
        if (request.getEwbStatus() != null) {
            String trimmedEwbStatus = request.getEwbStatus().trim();
            if (trimmedEwbStatus.isEmpty()) {
                request.setEwbStatus("");
            } else {
                String upperEwbStatus = trimmedEwbStatus.toUpperCase();
                if (!VALID_EWB_STATUSES.contains(upperEwbStatus)) {
                    throw new IllegalArgumentException(
                        String.format("Invalid ewbStatus: '%s'. Valid values are: (empty string) or 'EXPIRED'", 
                            request.getEwbStatus()));
                }
                request.setEwbStatus(upperEwbStatus);
            }
        }
        
        // Validate pagination parameters
        if (request.getPage() != null && request.getPage() < 0) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 0");
        }
        
        if (request.getSize() != null && (request.getSize() < 1 || request.getSize() > 100)) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }
    }

    @Override
    public PageResponse<CNReportDTO> getFilteredCNReports(CNReportFilterRequest request) {
        // Validate and normalize request parameters
        validateAndNormalizeRequest(request);
        
        // Set default values
        String cnStatus = request.getCnStatus();
        // For DRAFT CN, dateType is not used, but we need a default for SQL query
        // For ACTIVATED CN, default to CN_DATE if not provided
        String dateType = request.getDateType() != null ? request.getDateType() : 
                ("DRAFT".equalsIgnoreCase(cnStatus) ? "EWB_DATE" : "CN_DATE");
        String ewbStatus = request.getEwbStatus();
        Integer page = request.getPage() != null ? request.getPage() : 0;
        Integer size = request.getSize() != null ? request.getSize() : 10;

        List<Object[]> allResults = cnReportRepository.findCNReports(
                cnStatus,
                dateType,
                ewbStatus,
                request.getFromDate(),
                request.getToDate()
        );

        // Get total count
        Long totalCount = cnReportRepository.countCNReports(
                cnStatus,
                dateType,
                ewbStatus,
                request.getFromDate(),
                request.getToDate()
        );

        // Apply pagination manually
        int start = page * size;
        int end = Math.min(start + size, allResults.size());
        List<Object[]> paginatedResults = (start < allResults.size()) 
                ? allResults.subList(start, end) 
                : List.of();

        // Convert to DTOs
        List<CNReportDTO> content = paginatedResults.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        // Calculate pagination metadata
        int totalPages = (int) Math.ceil((double) totalCount / size);
        boolean hasNext = page < totalPages - 1;
        boolean hasPrevious = page > 0;

        return PageResponse.<CNReportDTO>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalCount)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .build();
    }

    private CNReportDTO mapToDTO(Object[] row) {
        return CNReportDTO.builder()
                .cnNo(getStringValue(row[0]))
                .cnDate(getStringValue(row[1]))
                .ewayNo(getStringValue(row[2]))
                .srcBranch(getStringValue(row[3]))
                .destBranch(getStringValue(row[4]))
                .fromAddress(getStringValue(row[5]))
                .toAddress(getStringValue(row[6]))
                .cnPkg(getIntegerValue(row[7]))
                .noOfInvoice(getIntegerValue(row[8]))
                .chargedWeight(getDoubleValue(row[9]))
                .cnFreight(getDoubleValue(row[10]))
                .invList(getStringValue(row[11]))
                .invValue(getDoubleValue(row[12]))
                .build();
    }

    private String getStringValue(Object value) {
        return value != null ? value.toString() : null;
    }

    private Integer getIntegerValue(Object value) {
        if (value == null) return null;
        if (value instanceof BigInteger) {
            return ((BigInteger) value).intValue();
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    private Double getDoubleValue(Object value) {
        if (value == null) return null;
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue();
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return Double.parseDouble(value.toString());
    }
}

