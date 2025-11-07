package com.omnivers.utility_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CNReportFilterDTO {
    
    @JsonProperty("fromDate")
    private String fromDate;
    
    @JsonProperty("toDate")
    private String toDate;
    
    @JsonProperty("dateType")
    private String dateType; // "CN_DATE" or "EWB_DATE" for activated CN
    
    @JsonProperty("cnStatus")
    private String cnStatus; // "ACTIVATED" or "DRAFT"
    
    @JsonProperty("ewbStatus")
    private String ewbStatus; // For draft CN: "" or "EXPIRED"
}

