package com.omnivers.utility_service.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CNActivationRequest {

    @NotNull(message = "Freight type is required")
    private Integer freightType;      // NUMBER(1-2)

    @NotNull(message = "Customer code is required")
    private Integer customerCode;     // NUMBER(6)

    @NotNull(message = "Transport mode is required")
    private Integer transportMode;    // NUMBER(2)

    @NotNull(message = "Load type is required")
    private Integer loadType;         // NUMBER(2)

    @NotBlank(message = "Collection mode is required")
    private String collectionMode;    // VARCHAR2

    @PositiveOrZero(message = "Collection amount cannot be negative")
    private Double collectionAmount;  // NUMBER(12,2), optional

    private Boolean isFoc;            // Optional

    // CN Details
    @Positive(message = "Package count must be positive")
    private Integer packageCount;     // CN_PKG NUMBER(5), optional

    @Positive(message = "Quantity must be positive")
    private Integer quantity;         // CN_QUANTITY NUMBER(8), optional

    private String asnNo;             // CN_ASN_NO VARCHAR2(20), optional

    private Integer packingType;      // CN_PACKING_TYPE NUMBER(2), optional

    @Size(max = 500, message = "Item description max 500 characters")
    private String itemDescription;   // CN_ITEM_DESCRIPT VARCHAR2(500), optional

    @PositiveOrZero(message = "Length must be positive")
    private Double length;            // CN_LENGTH NUMBER(12,2), optional

    @PositiveOrZero(message = "Width must be positive")
    private Double width;             // CN_WIDTH NUMBER(12,2), optional

    @PositiveOrZero(message = "Height must be positive")
    private Double height;            // CN_HEIGHT NUMBER(12,2), optional

    @PositiveOrZero(message = "Actual weight must be positive")
    private Double actualWeight;      // CN_ACTUAL_WEIGHT NUMBER(12,2), optional

    @PositiveOrZero(message = "Charged weight must be positive")
    private Double chargedWeight;     // CN_CHARGED_WEIGHT NUMBER(12,2), optional

    @PositiveOrZero(message = "CFT weight must be positive")
    private Double cftWeight;         // CN_WEIGHT_PER_CFT NUMBER(12,2), optional

    @Size(max = 16, message = "PO number max 16 characters")
    private String poNumber;          // CN_PO_NUMBER VARCHAR2(16), optional

    @Size(max = 50, message = "SKU code max 50 characters")
    private String skuCode;           // Optional
}
