package com.omnivers.utility_service.mapper;

import com.omnivers.utility_service.dto.CNInvoiceDTO;
import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.UIManager.getString;
import static org.hibernate.jpa.internal.util.ConfigurationHelper.getInteger;

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

    public CNResponseDTO mapToCNResponse(List<Object[]> results) {
        if (results == null || results.isEmpty()) return null;

        Object[] first = results.get(0);

        return CNResponseDTO.builder()
                .cnNo(getLongValue(first[0]))
                .sourceBranch(getStringValue(first[1]))
                .destinationBranch(getStringValue(first[2]))

                .consignorCode(getStringValue(first[3]))
                .consignorName(getStringValue(first[4]))
                .consignorAddress(getStringValue(first[5]))
                .consignorPincode(getStringValue(first[6]))

                .consigneeCode(getStringValue(first[7]))
                .consigneeName(getStringValue(first[8]))
                .consigneeAddress(getStringValue(first[9]))
                .consigneePincode(getStringValue(first[10]))

                .collectMode(getStringValue(first[11]))
                .collectAmount(getBigDecimalValue(first[12]))

                .totalBox(getIntegerValue(first[26]))
                .totalInvoice(getIntegerValue(first[27]))
                .totalWeight(getBigDecimalValue(first[28]))

                .invoices(mapInvoices(results))
                .build();
    }

    private List<CNInvoiceDTO> mapInvoices(List<Object[]> results) {
        return results.stream()
                .map(r -> CNInvoiceDTO.builder()
                        .invoiceNo(getStringValue(r[13]))
                        .ewayBillNo(getStringValue(r[14]))
                        .poNumber(getStringValue(r[15]))
                        .netValue(getBigDecimalValue(r[16]))
                        .grossValue(getBigDecimalValue(r[17]))
                        .pkgCount(getIntegerValue(r[18]))
                        .quantity(getIntegerValue(r[19]))
                        .skuCode(getStringValue(r[20]))
                        .pkgType(getStringValue(r[21]))
                        .itemDescription(getStringValue(r[22]))
                        .length(getBigDecimalValue(r[23]))
                        .width(getBigDecimalValue(r[24]))
                        .height(getBigDecimalValue(r[25]))
                        .cftWeight(getBigDecimalValue(r[26]))
                        .actualWeight(getBigDecimalValue(r[27]))
                        .chargedWeight(getBigDecimalValue(r[28]))
                        .build())
                .collect(Collectors.toList());
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
    private BigDecimal getBigDecimalValue(Object value) {
        if (value == null) return null;
        if (value instanceof BigDecimal bd) return bd;
        if (value instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return new BigDecimal(value.toString());
    }

    private Long getLongValue(Object value) {
        if (value == null) return null;
        if (value instanceof BigDecimal bd) return bd.longValue();
        if (value instanceof Number n) return n.longValue();
        return Long.parseLong(value.toString());
    }
}


