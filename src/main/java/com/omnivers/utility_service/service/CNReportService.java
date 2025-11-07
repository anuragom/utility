package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNReportFilterRequest;
import com.omnivers.utility_service.dto.PageResponse;

public interface CNReportService {
    PageResponse<CNReportDTO> getFilteredCNReports(CNReportFilterRequest request);
}

