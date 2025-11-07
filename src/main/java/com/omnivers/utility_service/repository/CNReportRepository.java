package com.omnivers.utility_service.repository;

import com.omnivers.utility_service.model.CNReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CNReportRepository extends JpaRepository<CNReport, String> {

    @Query(value = """
        SELECT A.CN_CN_NO, 
               TO_CHAR(A.CN_ENTER_DATE,'DD/MM/YY HH24:MI') CN_DATE,
               B.EWAY_BILL_# EWAY_NO, 
               A.CN_SOURCE_BRANCH_CODE SRC, 
               A.CN_DESTINATION_BRANCH_CODE DEST, 
               D.CUSTOMER_CUSTOMER_NAME||' '||D.CUSTOMER_CUSTOMER_ADDRESS||' '||D.CUSTOMER_PIN_CODE FROM_ADDRESS,
               C.CN_DLY_NAME||' '||C.CN_DLY_ADDRESS1||' '||C.CN_DLY_PIN TO_ADDRESS,
               SUM(B.CN_PKG) CN_PKG, 
               COUNT(CN_INVOICE_NO) NO_OF_INVOICE, 
               SUM(CN_CHARGED_WEIGHT) CHARGED_WEIGHT, 
               E.CN_TOTAL_TOTAL CN_FRIGHT,
               LISTAGG(CN_INVOICE_NO,',') WITHIN GROUP(ORDER BY A.CN_CN_NO) INV_LIST,
               SUM(CN_GROSS_VALUE) INV_VALUE
        FROM OPS_CN_M A  
        JOIN OPS_CN_D B ON B.CN_CN_NO = A.CN_CN_NO
        JOIN OPS_CN_FRIGHT E ON E.CN_CN_NO = A.CN_CN_NO
        JOIN OPS_CN_DLY_ADDRESS C ON C.CN_CN_NO = A.CN_CN_NO 
        JOIN COR_CUSTOMER_M D ON D.CUSTOMER_CUSTOMER_CODE = A.CN_CONSIGNOR_CODE
        WHERE A.ENTER_FORM_NAME = 'EWB'
        AND (
            (:cnStatus = 'ACTIVATED' AND A.CN_CN_STATUS NOT IN(7,2))
            OR (:cnStatus = 'DRAFT' AND A.CN_CN_STATUS = 7)
        )
        AND (
            (:fromDate IS NULL AND :toDate IS NULL)
            OR (
                CASE 
                    WHEN A.CN_CN_STATUS NOT IN(7,2) AND :dateType = 'CN_DATE' THEN NVL(A.CN_MAMUAL_CN_DATE, TRUNC(A.CN_CN_DATE))
                    WHEN A.CN_CN_STATUS NOT IN(7,2) AND :dateType = 'EWB_DATE' THEN TRUNC(B.EWAY_BILL_DATE)
                    WHEN A.CN_CN_STATUS = 7 AND (:ewbStatus IS NULL OR :ewbStatus = '') THEN TRUNC(B.EWAY_BILL_DATE)
                    WHEN A.CN_CN_STATUS = 7 AND :ewbStatus = 'EXPIRED' THEN TRUNC(B.EWAY_BILL_VALID_DATE)
                    ELSE TRUNC(B.EWAY_BILL_DATE)
                END BETWEEN :fromDate AND :toDate
            )
        )   
        GROUP BY A.CN_CN_NO, B.EWAY_BILL_#, A.CN_SOURCE_BRANCH_CODE, A.CN_DESTINATION_BRANCH_CODE, 
                 D.CUSTOMER_CUSTOMER_NAME||' '||D.CUSTOMER_CUSTOMER_ADDRESS||' '||D.CUSTOMER_PIN_CODE,
                 C.CN_DLY_NAME||' '||C.CN_DLY_ADDRESS1||' '||C.CN_DLY_PIN,
                 TO_CHAR(A.CN_ENTER_DATE,'DD/MM/YY HH24:MI'), E.CN_TOTAL_TOTAL
        ORDER BY A.CN_CN_NO
        """, nativeQuery = true)
    List<Object[]> findCNReports(
            @Param("cnStatus") String cnStatus,
            @Param("dateType") String dateType,
            @Param("ewbStatus") String ewbStatus,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    @Query(value = """
        SELECT COUNT(DISTINCT A.CN_CN_NO)
        FROM OPS_CN_M A  
        JOIN OPS_CN_D B ON B.CN_CN_NO = A.CN_CN_NO
        JOIN OPS_CN_FRIGHT E ON E.CN_CN_NO = A.CN_CN_NO
        JOIN OPS_CN_DLY_ADDRESS C ON C.CN_CN_NO = A.CN_CN_NO 
        JOIN COR_CUSTOMER_M D ON D.CUSTOMER_CUSTOMER_CODE = A.CN_CONSIGNOR_CODE
        WHERE A.ENTER_FORM_NAME = 'EWB'
        AND (
            (:cnStatus = 'ACTIVATED' AND A.CN_CN_STATUS NOT IN(7,2))
            OR (:cnStatus = 'DRAFT' AND A.CN_CN_STATUS = 7)
        )
        AND (
            (:fromDate IS NULL AND :toDate IS NULL)
            OR (
                CASE 
                    WHEN A.CN_CN_STATUS NOT IN(7,2) AND :dateType = 'CN_DATE' THEN NVL(A.CN_MAMUAL_CN_DATE, TRUNC(A.CN_CN_DATE))
                    WHEN A.CN_CN_STATUS NOT IN(7,2) AND :dateType = 'EWB_DATE' THEN TRUNC(B.EWAY_BILL_DATE)
                    WHEN A.CN_CN_STATUS = 7 AND (:ewbStatus IS NULL OR :ewbStatus = '') THEN TRUNC(B.EWAY_BILL_DATE)
                    WHEN A.CN_CN_STATUS = 7 AND :ewbStatus = 'EXPIRED' THEN TRUNC(B.EWAY_BILL_VALID_DATE)
                    ELSE TRUNC(B.EWAY_BILL_DATE)
                END BETWEEN :fromDate AND :toDate
            )
        )
        """, nativeQuery = true)
    Long countCNReports(
            @Param("cnStatus") String cnStatus,
            @Param("dateType") String dateType,
            @Param("ewbStatus") String ewbStatus,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );


    @Modifying
    @Query(value = """
        UPDATE OPS_CN_M SET 
            CN_CN_STATUS = 1, 
            CN_FREIGHT_PAID_MODE = CASE WHEN :isFoc = 1 THEN 6 ELSE :freightType END, 
            CN_BLLING_PARTY_CODE = CASE WHEN :freightType IN (3,4) THEN :customerCode ELSE CN_BLLING_PARTY_CODE END,
            CN_TPTR_MODE = :transportMode, 
            CN_LOAD_TYPE = :loadType, 
            CN_VAS_AMOUNT_TYPE = :collectionMode,
            CN_EXTRA_APPVL_AMT = :collectionAmount
        WHERE CN_CN_NO = :cnNo
        """, nativeQuery = true)
    int updateCNMaster(
            @Param("cnNo") Long cnNo,
            @Param("isFoc") Integer isFoc,
            @Param("freightType") Integer freightType,
            @Param("customerCode") Integer customerCode,
            @Param("transportMode") Integer transportMode,
            @Param("loadType") Integer loadType,
            @Param("collectionMode") String collectionMode,
            @Param("collectionAmount") BigDecimal collectionAmount
    );
}

