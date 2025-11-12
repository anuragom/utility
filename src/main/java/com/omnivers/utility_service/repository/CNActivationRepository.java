package com.omnivers.utility_service.repository;

import com.omnivers.utility_service.model.CNReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CNActivationRepository extends JpaRepository<CNReport, String> {

    @Modifying
    @Query(value = """
    UPDATE OPS_CN_D SET 
        CN_PKG = :packageCount, 
        CN_QUANTITY = :quantity, 
        CN_ASN_NO = :asnNo, 
        CN_PACKING_TYPE = :packingType, 
        CN_ITEM_DESCRIPT = :itemDescription,
        CN_LENGTH = :length, 
        CN_WIDTH = :width, 
        CN_HEIGHT = :height,
        CN_ACTUAL_WEIGHT = :actualWeight, 
        CN_CHARGED_WEIGHT = :chargedWeight,
        CN_WEIGHT_PER_CFT = :cftWeight, 
        CN_PO_NUMBER = :poNumber
    WHERE CN_CN_NO = :cnNo 
      AND "EWAY_BILL_#" = :ewayBillNo
    """, nativeQuery = true)
    int updateCNDetail(
            @Param("cnNo") Long cnNo,
            @Param("ewayBillNo") String ewayBillNo,
            @Param("packageCount") Integer packageCount,
            @Param("quantity") Integer quantity,
            @Param("asnNo") String asnNo,
            @Param("packingType") Integer packingType,
            @Param("itemDescription") String itemDescription,
            @Param("length") BigDecimal length,
            @Param("width") BigDecimal width,
            @Param("height") BigDecimal height,
            @Param("actualWeight") BigDecimal actualWeight,
            @Param("chargedWeight") BigDecimal chargedWeight,
            @Param("cftWeight") BigDecimal cftWeight,
            @Param("poNumber") String poNumber
    );

    @Query(value = """
        SELECT A.CN_CN_NO, CN_DATE, CN_TIME, MANUAL_NO, MANUAL_DATE,
               cn_dest_branch_code, cn_source_branch_code,
               CNR, CNRADD, CEE,
               CASE
                   WHEN cn_consignee_code = 999999 THEN (
                       SELECT CN_DLY_NAME||','||CN_DLY_ADDRESS1||','||CN_DLY_ADDRESS2||','||CN_DLY_NEAR_BY
                                  ||','||CN_DLY_CITY||','||CN_DLY_DISTRICT||','||CN_DLY_STATE||','||CN_DLY_PIN||','||
                              CN_DLY_CONTRACT_NO
                       FROM ops_cn_dly_address dly
                       WHERE dly.CN_CN_NO = A.CN_CN_NO
                         AND rownum = 1
                   )
                   ELSE CEEADD
               END CEEADD,
               BILL_PARTY, BILL_PARTY_ADD,
               SYSCDS_CODE_DESC, FROMSOURCE, TODIST, MR_MR_NO,
               TO_CHAR(MR_DATE,'dd.mm.rrrr') MR_DATE,
               TOT_FRIGHT, ST_CHRG, MR_CHEQUE_NO, MR_CHEQUE_DATE,
               A.CN_ENTER_BY, A.CN_TOTAL_TOTAL,
               CNR_CUST_CST_NO, CNR_CUST_LST_NO, CNR_CUST_PHN_NO,
               CEE_CUST_CST_NO, CEE_CUST_LST_NO, CEE_CUST_PHONE_NO,
               A.CN_DELIVERY_CHARGES_A, A.CN_COLLECTION_CHARGES_A,
               A.CN_S_TAX_A, A.CN_S_TAX, MODVAT, SURFACE, DELEVERY_NS,
               CN_PKG, CN_PACKING_TYPE, SUBSTR(CN_ITEM_DESCRIPT,1,20),
               SUBSTR(CN_INVOICE_NO,1,25) CN_INVOICE_NO, CN_INVOICE_DATE,
               CN_PO_NUMBER, CN_GROSS_VALUE, CN_NET_VALUE,
               CN_PART_NUMBER, CN_QUANTITY,
               CN_WEIGHT_PER_CFT, CN_LENGTH, CN_WIDTH, CN_HEIGHT,
               CN_ACTUAL_WEIGHT, CN_CHARGED_WEIGHT,
               CN_LENGTH||'*'||' '||CN_WIDTH||'*'||' '||CN_HEIGHT DIMESION,
               A.CN_RATE_PER_KG_A, A.CN_FOV_A,
               A.CN_DEMURAGE_CHARGES_A, A.CN_OCTROI_CHARGES_A,
               A.CN_OCTROI_SERV_CHARGES_A, A.CN_COD_OTHER_A,
               A.CN_T_CHARGES_A, A.CN_DETENTION_CHARGES_A,
               A.CN_LOGISTICS_CHARGES_A, A.CN_HAMALI_A,
               A.CN_SURCHARGE_A, cn_consignor_code, cn_consignee_code,
               cn_blling_party_code, load_type, REMARKS, service_tax_paid_by,
               CN_PRINT_FLAG, SUBSTR(SURFACE,1,1) MODE_PRE,
               B.CN_SGST_RATE, B.CN_SGST_AMT, B.CN_CGST_RATE, B.CN_CGST_AMT,
               B.CN_IGST_RATE, B.CN_IGST_AMT,
               C.BRANCH_BRANCH_ADDR1 || ' ' || C.BRANCH_BRANCH_ADDR2 || ' ' || C.BRANCH_BRANCH_ADDR3 BRANCH_ADDR,
               CN_BR_NO, A.EWAY_BILL_#, A.EWAY_BILL_DATE, A.EWAY_BILL_VALID_DATE,
               NVL(MANUAL_NO, A.CN_CN_NO) MAN_COMP,
               A.CN_LAST_LONG, A.CN_CFT_RATE,
               CASE WHEN a.modvat IN ('1','2','3') THEN 'Retail' END retail_status
        FROM VW_CN_CEREATION A
                 JOIN OPS_CN_FRIGHT B ON A.CN_CN_NO = B.CN_CN_NO
                 JOIN COR_BRANCH_M C ON A.CN_BR_NO = C.BRANCH_BRANCH_CODE
        WHERE A.CN_CN_NO = :CN_NO
           OR MANUAL_NO = :CN_NO
        ORDER BY CN_CN_NO
        """, nativeQuery = true)
    List<Object[]> getCNDetail(@Param("CN_NO") Long cnNo);

}
