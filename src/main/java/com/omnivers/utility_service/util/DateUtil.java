package com.omnivers.utility_service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateUtil {
    
    private static final List<DateTimeFormatter> DATE_FORMATTERS = new ArrayList<>();
    
    static {
        // Oracle date format: DD-MON-YYYY (e.g., 01-OCT-2025) - using English locale for month abbreviations
        // Create case-insensitive formatters using DateTimeFormatterBuilder
        DATE_FORMATTERS.add(new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MMM-yyyy")
                .toFormatter(Locale.ENGLISH));
        DATE_FORMATTERS.add(new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d-MMM-yyyy")
                .toFormatter(Locale.ENGLISH));
        DATE_FORMATTERS.add(new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MMM-yy")
                .toFormatter(Locale.ENGLISH));
        DATE_FORMATTERS.add(new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d-MMM-yy")
                .toFormatter(Locale.ENGLISH));
        // ISO format: YYYY-MM-DD
        DATE_FORMATTERS.add(DateTimeFormatter.ISO_LOCAL_DATE);
        // Other common formats
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    /**
     * Parses a date string in multiple formats (Oracle format DD-MON-YYYY, ISO, etc.)
     * @param dateString the date string to parse
     * @return LocalDate if parsing succeeds
     * @throws IllegalArgumentException if the date string cannot be parsed
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        
        String trimmedDate = dateString.trim().toUpperCase();
        
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(trimmedDate, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        
        throw new IllegalArgumentException(
            String.format("Invalid date format: '%s'. Supported formats: DD-MON-YYYY (e.g., 01-OCT-2025), YYYY-MM-DD, DD/MM/YYYY, DD-MM-YYYY", 
                dateString)
        );
    }
    
    /**
     * Parses a date string from request parameter, handling URL encoding if needed
     * @param dateString the date string from request parameter
     * @return LocalDate if parsing succeeds, null if dateString is null or empty
     * @throws IllegalArgumentException if the date string cannot be parsed
     */
    public static LocalDate parseDateFromRequest(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        
        // Handle URL encoding if needed (Spring usually handles this automatically)
        String dateToParse = dateString;
        try {
            if (dateString.contains("%")) {
                dateToParse = java.net.URLDecoder.decode(dateString, java.nio.charset.StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            // If decoding fails, use original string
            dateToParse = dateString;
        }
        
        return parseDate(dateToParse);
    }
    
    /**
     * Parses and validates both fromDate and toDate, and validates the date range
     * @param fromDateString the from date string
     * @param toDateString the to date string
     * @return array with [fromDate, toDate], either can be null
     * @throws IllegalArgumentException if dates cannot be parsed or range is invalid
     */
    public static LocalDate[] parseAndValidateDateRange(String fromDateString, String toDateString) {
        LocalDate fromDate = parseDateFromRequest(fromDateString);
        LocalDate toDate = parseDateFromRequest(toDateString);
        
        validateDateRange(fromDate, toDate);
        
        return new LocalDate[]{fromDate, toDate};
    }
    
    /**
     * Validates that fromDate is before or equal to toDate
     */
    public static void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate must be before or equal to toDate");
        }
    }
}

