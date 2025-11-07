package com.omnivers.utility_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CNActivationRequest {
    private Long cnNo;                // CN_CN_NO NUMBER(16)
    private String ewayBillNo;        // EWAY_BILL_# VARCHAR2(12)
    private Boolean isFoc;            // Optional
    private Integer freightType;      // NUMBER(2) or NUMBER(1-2)
    private Integer customerCode;     // NUMBER(6)
    private Integer transportMode;    // NUMBER(2)
    private Integer loadType;         // NUMBER(2)
    private String collectionMode;    // VARCHAR2
    private Double collectionAmount;  // NUMBER(12,2)
    private String skuCode;           // Optional
    private Integer packingType;      // CN_PACKING_TYPE NUMBER(2)
    private String itemDescription;   // CN_ITEM_DESCRIPT VARCHAR2(500)
    private Double length;            // CN_LENGTH NUMBER(12,2)
    private Double width;             // CN_WIDTH NUMBER(12,2)
    private Double height;            // CN_HEIGHT NUMBER(12,2)
    private Double actualWeight;      // CN_ACTUAL_WEIGHT NUMBER(12,2)
    private Double chargedWeight;     // CN_CHARGED_WEIGHT NUMBER(12,2)
    private Double cftWeight;         // CN_WEIGHT_PER_CFT NUMBER(12,2)
    private String poNumber;          // CN_PO_NUMBER VARCHAR2(16)
    private Integer packageCount;     // CN_PKG NUMBER(5)
    private Integer quantity;         // CN_QUANTITY NUMBER(8)
    private String asnNo;             // CN_ASN_NO VARCHAR2(20)
}

