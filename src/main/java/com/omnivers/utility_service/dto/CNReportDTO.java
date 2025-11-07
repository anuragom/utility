package com.omnivers.utility_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CNReportDTO {
    private String cnNo;
    private String cnDate;
    private String ewayNo;
    private String srcBranch;
    private String destBranch;
    private String fromAddress;
    private String toAddress;
    private Integer cnPkg;
    private Integer noOfInvoice;
    private Double chargedWeight;
    private Double cnFreight;
    private String invList;
    private Double invValue;
}
