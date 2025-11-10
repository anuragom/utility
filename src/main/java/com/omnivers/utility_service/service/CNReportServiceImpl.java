package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNReportFilterDTO;
import com.omnivers.utility_service.mapper.CNReportMapper;
import com.omnivers.utility_service.repository.CNReportRepository;
import com.omnivers.utility_service.util.DateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CNReportServiceImpl implements CNReportService {

    private final CNReportRepository cnReportRepository;
    private final CNReportMapper cnReportMapper;

    @Override
    public Page<CNReportDTO> getFilteredCNReports(CNReportFilterDTO filterDTO, Integer page, Integer size) {

        LocalDate fromDate = DateParser.parseDate(filterDTO.getFromDate());
        LocalDate toDate = DateParser.parseDate(filterDTO.getToDate());

        if ((filterDTO.getFromDate() != null && filterDTO.getToDate() == null) ||
                (filterDTO.getFromDate() == null && filterDTO.getToDate() != null)) {
            throw new IllegalArgumentException("Both fromDate and toDate must be provided together");
        }

        if (filterDTO.getFromDate() != null && filterDTO.getToDate() != null &&
                fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }


        String dateType = filterDTO.getDateType() != null ? filterDTO.getDateType().trim().toUpperCase() : "CN_DATE";
        String ewbStatus = filterDTO.getEwbStatus() != null ? filterDTO.getEwbStatus().trim().toUpperCase() : null;
        String cnStatus = filterDTO.getCnStatus().trim().toUpperCase(); // ACTIVATED or DRAFT

        int pageNum = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size > 0 && size <= 100 ? size : 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Object[]> resultPage = cnReportRepository.findCNReports(
                cnStatus, dateType, ewbStatus, fromDate, toDate, pageable
        );

        return resultPage.map(cnReportMapper::mapToDTO);
    }

    @Override
    public Page<CNReportDTO> getActivatedCNReports(String fromDate, String toDate, String dateType, Integer page, Integer size) {
        LocalDate fromDateParsed = DateParser.parseDate(fromDate);
        LocalDate toDateParsed = DateParser.parseDate(toDate);

        if ((fromDate != null && toDate == null) || (fromDate == null && toDate != null)) {
            throw new IllegalArgumentException("Both fromDate and toDate must be provided together");
        }

        if (fromDateParsed != null && toDateParsed != null && fromDateParsed.isAfter(toDateParsed)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }

        String dateTypeUpper = dateType != null ? dateType.trim().toUpperCase() : "CN_DATE";

        int pageNum = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size > 0 && size <= 100 ? size : 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Object[]> resultPage = cnReportRepository.findActivatedCNReports(
                dateTypeUpper, fromDateParsed, toDateParsed, pageable
        );

        return resultPage.map(cnReportMapper::mapToDTO);
    }

    @Override
    public Page<CNReportDTO> getDraftCNReports(String fromDate, String toDate, String ewbStatus, Integer page, Integer size) {
        LocalDate fromDateParsed = DateParser.parseDate(fromDate);
        LocalDate toDateParsed = DateParser.parseDate(toDate);

        if ((fromDate != null && toDate == null) || (fromDate == null && toDate != null)) {
            throw new IllegalArgumentException("Both fromDate and toDate must be provided together");
        }

        if (fromDateParsed != null && toDateParsed != null && fromDateParsed.isAfter(toDateParsed)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }

        String ewbStatusUpper = ewbStatus != null ? ewbStatus.trim().toUpperCase() : null;

        int pageNum = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size > 0 && size <= 100 ? size : 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Object[]> resultPage = cnReportRepository.findDraftCNReports(
                ewbStatusUpper, fromDateParsed, toDateParsed, pageable
        );

        return resultPage.map(cnReportMapper::mapToDTO);
    }
}
