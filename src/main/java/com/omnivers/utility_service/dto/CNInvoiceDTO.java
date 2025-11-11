package com.omnivers.utility_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CNInvoiceDTO {

    private String invoiceNo;
    private String ewayBillNo;
    private String poNumber;

    private BigDecimal netValue;
    private BigDecimal grossValue;

    private Integer pkgCount;
    private Integer quantity;
    private String skuCode;
    private String pkgType;
    private String itemDescription;

    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal cftWeight;
    private BigDecimal actualWeight;
    private BigDecimal chargedWeight;
}

