package me.tuhin47.exporter;

import org.springframework.context.ApplicationContext;

public class ExporterUtils {

    public static <T extends ExporterDTO> DataExporter<T> getDataExporter(ApplicationContext applicationContext, ExporterType exporterType) {

        switch (exporterType) {
            case EXCEL:
                return applicationContext.getBean(ExporterType.Constants.EXCEL, ExcelGenerator.class);
            case CSV:
                return applicationContext.getBean(ExporterType.Constants.CSV, CSVGenerator.class);
            case PDF:
                break;
        }
        
        throw new IllegalArgumentException("No Implementation found");
    }

}
