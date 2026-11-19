package com.example.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelTableManager {

    /**
     * Reads a tabular Excel sheet and maps each data row to its column header.
     *
     * @param filePath Path to the Excel file.
     * @return A List of Maps representing rows (Key = Header, Value = Cell Value).
     * @throws IOException If the file cannot be accessed or read.
     */
    public static List<Map<String, String>> readTable(String filePath) throws IOException {
        List<Map<String, String>> tableData = new ArrayList<>();

        ClassLoader classLoader = ExcelTableManager.class.getClassLoader();
        InputStream file = classLoader.getResourceAsStream(filePath);

        // If file doesn't exist, return an empty list safely
        if (file==null) {
            return tableData;
        }

        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() == 0) {
                return tableData;
            }

            DataFormatter dataFormatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return tableData;
            }

            // Parse headers
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(dataFormatter.formatCellValue(cell).trim());
            }
            int totalColumns = headers.size();

            // Parse data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) {
                    continue;
                }

                Map<String, String> rowMap = new LinkedHashMap<>();
                boolean isRowEmpty = true;

                for (int colIndex = 0; colIndex < totalColumns; colIndex++) {
                    String headerName = headers.get(colIndex);
                    Cell cell = currentRow.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = dataFormatter.formatCellValue(cell).trim();

                    rowMap.put(headerName, cellValue);
                    if (!cellValue.isEmpty()) {
                        isRowEmpty = false;
                    }
                }

                if (!isRowEmpty) {
                    tableData.add(rowMap);
                }
            }
        }
        return tableData;
    }

    /**
     * Writes tabular data into an Excel file. Automatically creates headers from the keys.
     *
     * @param filePath  Path where the Excel file should be saved.
     * @param tableData A List of Maps containing the tabular data rows.
     * @throws IOException If the file cannot be created or written to.
     */
    public static void writeTable(String filePath, List<Map<String, String>> tableData) throws IOException {
        if (tableData == null || tableData.isEmpty()) {
            throw new IllegalArgumentException("Table data cannot be null or empty.");
        }

        // Create a new XSSFWorkbook (.xlsx format)
        try (Workbook workbook = WorkbookFactory.create(true);
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Data Sheet");

            // 1. Generate Header Row dynamically from the keys of the first row map
            Set<String> headerKeys = tableData.get(0).keySet();
            List<String> headers = new ArrayList<>(headerKeys);

            Row headerRow = sheet.createRow(0);

            // Apply bold formatting to headers for standard table layout
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int col = 0; col < headers.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers.get(col));
                cell.setCellStyle(headerStyle);
            }

            // 2. Populate Data Rows
            int rowIdx = 1;
            for (Map<String, String> rowData : tableData) {
                Row currentRow = sheet.createRow(rowIdx++);

                for (int col = 0; col < headers.size(); col++) {
                    String currentHeader = headers.get(col);
                    Cell cell = currentRow.createCell(col);

                    // Fallback to empty string if a map does not contain this header value
                    String value = rowData.getOrDefault(currentHeader, "");
                    cell.setCellValue(value);
                }
            }

            // 3. Auto-fit columns to content for visual cleanup
            for (int col = 0; col < headers.size(); col++) {
                sheet.autoSizeColumn(col);
            }

            // Save workbook contents to disk
            workbook.write(fileOut);
        }
    }
}

