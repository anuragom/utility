package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNReportFilterDTO;
import org.springframework.data.domain.Page;

public interface CNReportService {
    Page<CNReportDTO> getFilteredCNReports(CNReportFilterDTO filterDTO, Integer page, Integer size);
}

