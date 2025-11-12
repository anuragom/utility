package com.omnivers.utility_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CNDetailDTO {

    private Long cnNo;
    private LocalDate cnDate;
    private LocalTime cnTime;
    private String manualNo;
    private LocalDate manualDate;
    private String cnDestBranchCode;
    private String cnSourceBranchCode;
    private String cnr;
    private String cnrAddress;
    private String cee;
    private String ceeAddress;
    private String billParty;
    private String billPartyAddress;
    private String syscdsCodeDesc;
    private String fromSource;
    private String toDist;
    private String mrNo;
    private LocalDate mrDate;
    private BigDecimal totFreight;
    private BigDecimal stChrg;
    private String mrChequeNo;
    private LocalDate mrChequeDate;
    private String cnEnterBy;
    private BigDecimal cnTotal;
    private String cnrCustCstNo;
    private String cnrCustLstNo;
    private String cnrCustPhoneNo;
    private String ceeCustCstNo;
    private String ceeCustLstNo;
    private String ceeCustPhoneNo;
    private BigDecimal cnDeliveryCharges;
    private BigDecimal cnCollectionCharges;
    private BigDecimal cnSTaxA;
    private BigDecimal cnSTax;
    private String modvat;
    private String surface;
    private String deliveryNs;
    private Integer cnPkg;
    private Integer cnPackingType;
    private String cnItemDescription;
    private String cnInvoiceNo;
    private LocalDate cnInvoiceDate;
    private String cnPoNumber;
    private BigDecimal cnGrossValue;
    private BigDecimal cnNetValue;
    private String cnPartNumber;
    private Integer cnQuantity;
    private BigDecimal cnWeightPerCft;
    private BigDecimal cnLength;
    private BigDecimal cnWidth;
    private BigDecimal cnHeight;
    private BigDecimal cnActualWeight;
    private BigDecimal cnChargedWeight;
    private String dimension;
    private BigDecimal cnRatePerKg;
    private BigDecimal cnFov;
    private BigDecimal cnDemurageCharges;
    private BigDecimal cnOctroiCharges;
    private BigDecimal cnOctroiServCharges;
    private BigDecimal cnCodOther;
    private BigDecimal cnTCharges;
    private BigDecimal cnDetentionCharges;
    private BigDecimal cnLogisticsCharges;
    private BigDecimal cnHamali;
    private BigDecimal cnSurcharge;
    private Integer cnConsignorCode;
    private Integer cnConsigneeCode;
    private Integer cnBillingPartyCode;
    private String loadType;
    private String remarks;
    private String serviceTaxPaidBy;
    private Boolean cnPrintFlag;
    private String modePre;
    private BigDecimal cnSgstRate;
    private BigDecimal cnSgstAmt;
    private BigDecimal cnCgstRate;
    private BigDecimal cnCgstAmt;
    private BigDecimal cnIgstRate;
    private BigDecimal cnIgstAmt;
    private String branchAddress;
    private String cnBrNo;
    private String ewayBillNo;
    private LocalDate ewayBillDate;
    private LocalDate ewayBillValidDate;
    private String manualComp;
    private BigDecimal cnLastLong;
    private BigDecimal cnCftRate;
    private String retailStatus;
}
