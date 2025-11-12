package com.omnivers.utility_service.mapper;

import com.omnivers.utility_service.dto.CNDetailDTO;
import com.omnivers.utility_service.dto.CNInvoiceDTO;
import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


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


    public List<CNDetailDTO> mapToCNDetail(List<Object[]> results) {
        return results.stream().map(row -> {
            CNDetailDTO dto = new CNDetailDTO();

            dto.setCnNo(getLongValue(row[0]));
            dto.setCnDate(toLocalDate(row[1]));
            dto.setCnTime(toLocalTime(row[2]));
            dto.setManualNo(getStringValue(row[3]));
            dto.setManualDate(toLocalDate(row[4]));
            dto.setCnDestBranchCode(getStringValue(row[5]));
            dto.setCnSourceBranchCode(getStringValue(row[6]));
            dto.setCnr(getStringValue(row[7]));
            dto.setCnrAddress(getStringValue(row[8]));
            dto.setCee(getStringValue(row[9]));
            dto.setCeeAddress(getStringValue(row[10]));
            dto.setBillParty(getStringValue(row[11]));
            dto.setBillPartyAddress(getStringValue(row[12]));
            dto.setSyscdsCodeDesc(getStringValue(row[13]));
            dto.setFromSource(getStringValue(row[14]));
            dto.setToDist(getStringValue(row[15]));
            dto.setMrNo(getStringValue(row[16]));
            dto.setMrDate(toLocalDate(row[17]));
            dto.setTotFreight(getBigDecimalValue(row[18]));
            dto.setStChrg(getBigDecimalValue(row[19]));
            dto.setMrChequeNo(getStringValue(row[20]));
            dto.setMrChequeDate(toLocalDate(row[21]));
            dto.setCnEnterBy(getStringValue(row[22]));
            dto.setCnTotal(getBigDecimalValue(row[23]));
            dto.setCnrCustCstNo(getStringValue(row[24]));
            dto.setCnrCustLstNo(getStringValue(row[25]));
            dto.setCnrCustPhoneNo(getStringValue(row[26]));
            dto.setCeeCustCstNo(getStringValue(row[27]));
            dto.setCeeCustLstNo(getStringValue(row[28]));
            dto.setCeeCustPhoneNo(getStringValue(row[29]));
            dto.setCnDeliveryCharges(getBigDecimalValue(row[30]));
            dto.setCnCollectionCharges(getBigDecimalValue(row[31]));
            dto.setCnSTaxA(getBigDecimalValue(row[32]));
            dto.setCnSTax(getBigDecimalValue(row[33]));
            dto.setModvat(getStringValue(row[34]));
            dto.setSurface(getStringValue(row[35]));
            dto.setDeliveryNs(getStringValue(row[36]));
            dto.setCnPkg(getIntegerValue(row[37]));
            dto.setCnPackingType(getIntegerValue(row[38]));
            dto.setCnItemDescription(getStringValue(row[39]));
            dto.setCnInvoiceNo(getStringValue(row[40]));
            dto.setCnInvoiceDate(toLocalDate(row[41]));
            dto.setCnPoNumber(getStringValue(row[42]));
            dto.setCnGrossValue(getBigDecimalValue(row[43]));
            dto.setCnNetValue(getBigDecimalValue(row[44]));
            dto.setCnPartNumber(getStringValue(row[45]));
            dto.setCnQuantity(getIntegerValue(row[46]));
            dto.setCnWeightPerCft(getBigDecimalValue(row[47]));
            dto.setCnLength(getBigDecimalValue(row[48]));
            dto.setCnWidth(getBigDecimalValue(row[49]));
            dto.setCnHeight(getBigDecimalValue(row[50]));
            dto.setCnActualWeight(getBigDecimalValue(row[51]));
            dto.setCnChargedWeight(getBigDecimalValue(row[52]));
            dto.setDimension(getStringValue(row[53]));
            dto.setCnRatePerKg(getBigDecimalValue(row[54]));
            dto.setCnFov(getBigDecimalValue(row[55]));
            dto.setCnDemurageCharges(getBigDecimalValue(row[56]));
            dto.setCnOctroiCharges(getBigDecimalValue(row[57]));
            dto.setCnOctroiServCharges(getBigDecimalValue(row[58]));
            dto.setCnCodOther(getBigDecimalValue(row[59]));
            dto.setCnTCharges(getBigDecimalValue(row[60]));
            dto.setCnDetentionCharges(getBigDecimalValue(row[61]));
            dto.setCnLogisticsCharges(getBigDecimalValue(row[62]));
            dto.setCnHamali(getBigDecimalValue(row[63]));
            dto.setCnSurcharge(getBigDecimalValue(row[64]));
            dto.setCnConsignorCode(getIntegerValue(row[65]));
            dto.setCnConsigneeCode(getIntegerValue(row[66]));
            dto.setCnBillingPartyCode(getIntegerValue(row[67]));
            dto.setLoadType(row[68] != null ? row[68].toString() : null);
            dto.setRemarks(getStringValue(row[69]));
            dto.setServiceTaxPaidBy(getStringValue(row[70]));
            dto.setCnPrintFlag(row[71] != null ? Boolean.valueOf(row[71].toString()) : null);
            dto.setModePre(getStringValue(row[72]));
            dto.setCnSgstRate(getBigDecimalValue(row[73]));
            dto.setCnSgstAmt(getBigDecimalValue(row[74]));
            dto.setCnCgstRate(getBigDecimalValue(row[75]));
            dto.setCnCgstAmt(getBigDecimalValue(row[76]));
            dto.setCnIgstRate(getBigDecimalValue(row[77]));
            dto.setCnIgstAmt(getBigDecimalValue(row[78]));
            dto.setBranchAddress(getStringValue(row[79]));
            dto.setCnBrNo(getStringValue(row[80]));
            dto.setEwayBillNo(getStringValue(row[81]));
            dto.setEwayBillDate(toLocalDate(row[82]));
            dto.setEwayBillValidDate(toLocalDate(row[83]));
            dto.setManualComp(getStringValue(row[84]));
            dto.setCnLastLong(getBigDecimalValue(row[85]));
            dto.setCnCftRate(getBigDecimalValue(row[86]));
            dto.setRetailStatus(getStringValue(row[87]));

            return dto;
        }).collect(Collectors.toList());
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

    private LocalDate toLocalDate(Object obj) {
        if (obj instanceof Date d) return d.toLocalDate();
        if (obj instanceof Timestamp t) return t.toLocalDateTime().toLocalDate();
        return null;
    }

    private LocalTime toLocalTime(Object obj) {
        if (obj instanceof Timestamp t) return t.toLocalDateTime().toLocalTime();
        return null;
    }
}


