package com.omnivers.utility_service.mapper;

import com.omnivers.utility_service.dto.CNReportDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class CNReportMapper {

    public CNReportDTO mapToDTO(Object[] row) {
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


