package com.omnivers.utility_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "OPS_CN_D")
@Data
public class CNActivation {
    @Id
    private String cnNo;
    @Column(name = "CN_COMPANY_CODE")
    private Integer cnCompanyCode;

    @Column(name = "CN_BRANCH_CODE")
    private Integer cnBranchCode;

    @Column(name = "CN_CN_NO")
    private Long cnCnNo;

    @Column(name = "CN_PKG")
    private Integer cnPkg;

    @Column(name = "CN_PACKING_TYPE")
    private Short cnPackingType;

    @Column(name = "CN_LOCATION_CODE")
    private Short cnLocationCode;

    @Size(max = 500)
    @Column(name = "CN_ITEM_DESCRIPT", length = 500)
    private String cnItemDescript;

    @Size(max = 50)
    @Column(name = "CN_INVOICE_NO", length = 50)
    private String cnInvoiceNo;

    @Column(name = "CN_INVOICE_DATE")
    private LocalDate cnInvoiceDate;

    @Size(max = 16)
    @Column(name = "CN_PO_NUMBER", length = 16)
    private String cnPoNumber;

    @Size(max = 20)
    @Column(name = "CN_PART_NUMBER", length = 20)
    private String cnPartNumber;

    @Column(name = "CN_QUANTITY")
    private Integer cnQuantity;

    @Column(name = "CN_WEIGHT_PER_CFT", precision = 12, scale = 2)
    private BigDecimal cnWeightPerCft;

    @Column(name = "CN_LENGTH", precision = 12, scale = 2)
    private BigDecimal cnLength;

    @Column(name = "CN_WIDTH", precision = 12, scale = 2)
    private BigDecimal cnWidth;

    @Column(name = "CN_HEIGHT", precision = 12, scale = 2)
    private BigDecimal cnHeight;

    @Column(name = "CN_UNIT")
    private Short cnUnit;

    @Column(name = "CN_ACTUAL_WEIGHT", precision = 12, scale = 2)
    private BigDecimal cnActualWeight;

    @Column(name = "CN_CHARGED_WEIGHT", precision = 12, scale = 2)
    private BigDecimal cnChargedWeight;

    @Size(max = 25)
    @Column(name = "CN_ENTER_BY", length = 25)
    private String cnEnterBy;

    @Column(name = "CN_ENTER_DATE")
    private LocalDate cnEnterDate;

    @Column(name = "CN_CFT_RATE", precision = 12, scale = 2)
    private BigDecimal cnCftRate;

    @Column(name = "CN_CFT_UNIT")
    private Short cnCftUnit;

    @Size(max = 20)
    @Column(name = "CN_ASN_NO", length = 20)
    private String cnAsnNo;

    @Column(name = "CN_GROSS_VALUE", precision = 12, scale = 2)
    private BigDecimal cnGrossValue;

    @Column(name = "CN_NET_VALUE", precision = 12, scale = 2)
    private BigDecimal cnNetValue;

    @Size(max = 12)
    @Column(name = "\"EWAY_BILL_#\"", length = 12)
    private String ewayBill;

    @Column(name = "EWAY_BILL_DATE")
    private LocalDate ewayBillDate;

    @Column(name = "EWAY_BILL_VALID_DATE")
    private LocalDate ewayBillValidDate;

    @Column(name = "TATA_PNO_ACT_CBM_B", precision = 16, scale = 5)
    private BigDecimal tataPnoActCbmB;

    @Column(name = "TATA_PNO_VOL_CBM_B", precision = 16, scale = 5)
    private BigDecimal tataPnoVolCbmB;

    @Column(name = "TATA_PNO_CHA_CBM_B", precision = 16, scale = 5)
    private BigDecimal tataPnoChaCbmB;

    @Column(name = "TATA_PNO_RT", precision = 8, scale = 2)
    private BigDecimal tataPnoRt;

    @Column(name = "TATA_PNO_ACT_CBM", precision = 16, scale = 12)
    private BigDecimal tataPnoActCbm;

    @Column(name = "TATA_PNO_VOL_CBM", precision = 16, scale = 12)
    private BigDecimal tataPnoVolCbm;

    @Column(name = "TATA_PNO_CHA_CBM", precision = 16, scale = 12)
    private BigDecimal tataPnoChaCbm;

    @Column(name = "T_CN_LENGTH", precision = 10, scale = 2)
    private BigDecimal tCnLength;

    @Column(name = "T_CN_WIDTH", precision = 10, scale = 2)
    private BigDecimal tCnWidth;

    @Column(name = "T_CN_HEIGHT", precision = 10, scale = 2)
    private BigDecimal tCnHeight;

    @Column(name = "T_CN_CFT_RATE", precision = 10, scale = 2)
    private BigDecimal tCnCftRate;

    @Column(name = "T_CN_WEIGHT_PER_CFT", precision = 12, scale = 2)
    private BigDecimal tCnWeightPerCft;

    @Column(name = "T_CN_PER_QTY_CBM_AMT", precision = 12, scale = 2)
    private BigDecimal tCnPerQtyCbmAmt;

    @Column(name = "LAST_UPDATED_ON")
    private Instant lastUpdatedOn;

    @NotNull
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "INV_DN_NO", length = 20)
    private String invDnNo;

    @Size(max = 200)
    @Column(name = "CN_CHAR_01", length = 200)
    private String cnChar01;

    @Size(max = 200)
    @Column(name = "CN_CHAR_02", length = 200)
    private String cnChar02;

    @Size(max = 200)
    @Column(name = "CN_CHAR_03", length = 200)
    private String cnChar03;

    @Size(max = 200)
    @Column(name = "CN_CHAR_04", length = 200)
    private String cnChar04;

    @Column(name = "CN_DATE_01")
    private LocalDate cnDate01;

    @Column(name = "CN_DATE_02")
    private LocalDate cnDate02;

    @Column(name = "CN_DATE_03")
    private LocalDate cnDate03;

    @Column(name = "CN_DATE_04")
    private LocalDate cnDate04;

    @Column(name = "CN_NUMBER_01")
    private Long cnNumber01;

    @Column(name = "CN_NUMBER_02")
    private Long cnNumber02;

    @Column(name = "CN_NUMBER_03")
    private Long cnNumber03;

    @Column(name = "CN_NUMBER_04")
    private Long cnNumber04;
}

