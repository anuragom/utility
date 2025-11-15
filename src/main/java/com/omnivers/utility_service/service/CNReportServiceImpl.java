package com.omnivers.utility_service.service;

import com.omnivers.utility_service.dto.CNReportDTO;
import com.omnivers.utility_service.dto.CNReportFilterDTO;
import com.omnivers.utility_service.dto.CNResponseDTO;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        String cnStatus = filterDTO.getCnStatus().trim().toUpperCase(); // ACTIVATED or DRAFT
        String searchText = filterDTO.getSearchText() != null ? filterDTO.getSearchText().trim() : null;
        List<Integer> sourceBranchCodes = filterDTO.getSourceBranchCodes() != null ? filterDTO.getSourceBranchCodes() : Collections.emptyList();
        List<String> ewbStatusList =
                filterDTO.getEwbStatus() != null
                        ? filterDTO.getEwbStatus().stream()
                        .map(String::toUpperCase)
                        .toList()
                        : Collections.emptyList();


        int srcEmpty = (sourceBranchCodes == null || sourceBranchCodes.isEmpty()) ? 1 : 0;
        log.info("ewbStatusList: {}",ewbStatusList);

        int pageNum = page != null && page >= 0 ? page : 0;
        int pageSize = size != null && size > 0 && size <= 100 ? size : 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<Object[]> resultPage = cnReportRepository.findCNReports(
                cnStatus, dateType, ewbStatusList, fromDate, toDate, searchText,sourceBranchCodes,srcEmpty,pageable);

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
    @Override
    public CNResponseDTO getCNDetails(Long cnNo) {
        if (cnNo == null || cnNo <= 0) {
            throw new IllegalArgumentException("CN number must be a positive non-null value");
        }

        try {
            List<Object[]> results = cnReportRepository.findCNDetailsByCnNo(cnNo);

            if (results == null || results.isEmpty()) {
                throw new IllegalStateException("No CN details found for CN number: " + cnNo);
            }

            return cnReportMapper.mapToCNResponse(results);

        } catch (IllegalArgumentException e) {
            log.error("Invalid input for CN details fetch: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching CN details for CN number: {}", cnNo, e);
            throw new RuntimeException("Unexpected error occurred while fetching CN details");
        }
    }


}
