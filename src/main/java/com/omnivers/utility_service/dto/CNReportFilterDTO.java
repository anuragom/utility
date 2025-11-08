package com.omnivers.utility_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CNReportFilterDTO {

    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])-([A-Z]{3})-\\d{4}$",
            message = "fromDate must be in DD-MMM-YYYY format, e.g., 01-NOV-2025"
    )
    @JsonProperty("fromDate")
    private String fromDate;

    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])-([A-Z]{3})-\\d{4}$",
            message = "toDate must be in DD-MMM-YYYY format, e.g., 01-NOV-2025"
    )
    @JsonProperty("toDate")
    private String toDate;

    @JsonProperty("dateType")
    private String dateType; // CN_DATE or EWB_DATE

    @NotNull(message = "cnStatus is required")
    @JsonProperty("cnStatus")
    private String cnStatus; // ACTIVATED or DRAFT

    @JsonProperty("ewbStatus")
    private String ewbStatus; // For draft CN: "" or "EXPIRED"
}
