package me.tuhin47.exporter;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

@Log4j2
public class ExcelGenerator implements DataExporter{

    protected <T extends ExcelDTO> T getFirstRecord(List<T> listRecords) {
        return listRecords.stream().findFirst().orElseThrow(() -> new RuntimeException("No record found"));
    }

    private <T extends ExcelDTO> void writeHeader(List<T> listRecords, XSSFWorkbook workbook, XSSFSheet sheet) {
        var firstRecord = getFirstRecord(listRecords);
        var row = sheet.createRow(0);
        var style = workbook.createCellStyle();
        var font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        var headers = firstRecord.getHeaders();

        for (int i = 0; i < headers.size(); i++) {
            createCell(row, i, headers.get(i), style, sheet);
        }

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }


    public <T extends ExcelDTO> void writeRows(List<T> listRecords, XSSFWorkbook workbook, XSSFSheet sheet) {
        var firstRecord = getFirstRecord(listRecords);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        var fields = firstRecord.getFields();

        var fieldFunctionTreeMap = new ArrayList<Function<Object, ?>>();
        for (Field field : fields) {
            Function<Object, ?> fieldExtractor = record -> {
                try {
                    field.setAccessible(true);
                    return field.get(record);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            };

            fieldFunctionTreeMap.add(fieldExtractor);
        }

        for (int rowCount = 0, listRecordsSize = listRecords.size(); rowCount < listRecordsSize; rowCount++) {
            T val = listRecords.get(rowCount);
            Row row = sheet.createRow(rowCount + 1);
            int columnCount = 0;

            for (Function<Object, ?> function : fieldFunctionTreeMap) {
                createCell(row, columnCount++, function.apply(val), style, sheet);
            }
        }

    }

    @Override
    public <T extends ExcelDTO> byte[] generate(List<T> listRecords) {
        if (listRecords.isEmpty()) {
            log.error("No record found");
        }
        var workbook = new XSSFWorkbook();
        var firstRecord = getFirstRecord(listRecords);
        var sheet = workbook.createSheet(firstRecord.getSheetName());
        writeHeader(listRecords, workbook, sheet);
        writeRows(listRecords, workbook, sheet);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public String getExtension() {
        return ".xlsx";
    }
}