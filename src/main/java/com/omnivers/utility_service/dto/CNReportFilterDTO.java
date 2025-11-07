package com.omnivers.utility_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    
    @NotNull
    private String cnStatus; // "ACTIVATED" or "DRAFT"
    
    @JsonProperty("ewbStatus")
    private String ewbStatus; // For draft CN: "" or "EXPIRED"
}

