package com.omnivers.utility_service.repository;

import com.omnivers.utility_service.model.CNReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

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

}
