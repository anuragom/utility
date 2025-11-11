package com.omnivers.utility_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CNResponseDTO {

    private Long cnNo;

    private String sourceBranch;
    private String destinationBranch;

    private String consignorCode;
    private String consignorName;
    private String consignorAddress;
    private String consignorPincode;

    private String consigneeCode;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneePincode;

    private String collectMode;
    private BigDecimal collectAmount;

    private Integer totalBox;
    private Integer totalInvoice;
    private BigDecimal totalWeight;

    private List<CNInvoiceDTO> invoices;
}

