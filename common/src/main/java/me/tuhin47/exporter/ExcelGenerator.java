package me.tuhin47.exporter;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Log4j2
public class ExcelGenerator<T extends ExcelExporterDTO> implements DataExporter<T> {

    protected void writeHeader(List<? extends T> listRecords, XSSFWorkbook workbook, XSSFSheet sheet) {
//        var firstRecord = getFirstRecord(listRecords);
        var row = sheet.createRow(0);
        var style = workbook.createCellStyle();
        var font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        var headers = getHeaders(listRecords);

        for (int i = 0; i < headers.size(); i++) {
            createCell(row, i, headers.get(i), style, sheet);
        }

    }

    protected void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
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


    public void writeRows(List<? extends T> listRecords, XSSFWorkbook workbook, XSSFSheet sheet) {
        var style = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        var fieldFunctionTreeMap = getFunctions(listRecords);

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
    public byte[] generate(List<? extends T> listRecords) {
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