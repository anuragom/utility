package com.omnivers.utility_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CNReportFilterRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String dateType; // "CN_DATE" or "EWB_DATE" for activated CN
    private String cnStatus; // "ACTIVATED" or "DRAFT"
    private String ewbStatus; // For draft CN: "" or "EXPIRED"
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;
}

