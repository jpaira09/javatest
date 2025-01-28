package org.dtna.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.dtna.dto.technician_interface.SSIUserDataDto;
import org.dtna.dto.technician_interface.SSLCertificationDataDto;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;



@Component
@Slf4j
public class ExcelFileReadHelper {
    public List<SSLCertificationDataDto> readSSICertificationFile(InputStream stream)
    {
        log.info("Reading SSI Certification File");
        List<SSLCertificationDataDto> certificationDataList = new ArrayList<>();

        try (
             Workbook workbook = new XSSFWorkbook(stream)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) { // Skip header row
                    isFirstRow = false;
                    continue;
                }

                SSLCertificationDataDto dto = new SSLCertificationDataDto();
                dto.setUserName(getCellValueAsString(row.getCell(0))); // Username column
                dto.setTrack(getCellValueAsString(row.getCell(1)));    // Track column
                dto.setSsi(getCellValueAsString(row.getCell(2)));      // SSI column
                dto.setDate(getCellValueAsString(row.getCell(3)));     // Date column
                dto.setRenewDate(getCellValueAsString(row.getCell(4))); // Renew Date column
                dto.setExpiryDate(getCellValueAsString(row.getCell(5))); // Expiry Date column

                certificationDataList.add(dto);
            }
        } catch (IOException e) {
            log.info("Error reading the Excel file: {}", e.getMessage());
            e.printStackTrace();
        }

        return certificationDataList;


    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Check if the cell contains a date
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Format the date as needed, e.g., "yyyy-MM-dd"
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public List<SSIUserDataDto>readSSIUserData(InputStream stream)
    {
        log.info("Reading SSI User Data File");
        List<SSIUserDataDto> ssiUserDataDtosList = new ArrayList<>();

        try (
             Workbook workbook = new XSSFWorkbook(stream)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) { // Skip header row
                    isFirstRow = false;
                    continue;
                }

               SSIUserDataDto ssiUserDataDto = new SSIUserDataDto();
                ssiUserDataDto.setUserName(getCellValueAsString(row.getCell(0)));
                ssiUserDataDto.setFirstName(getCellValueAsString(row.getCell(1)));
                ssiUserDataDto.setLastName(getCellValueAsString(row.getCell(2)));
                ssiUserDataDtosList.add(ssiUserDataDto);
            }
        } catch (IOException e) {
            log.info("Error reading the Excel file: {}", e.getMessage());
            e.printStackTrace();
        }

        return ssiUserDataDtosList;

    }
}