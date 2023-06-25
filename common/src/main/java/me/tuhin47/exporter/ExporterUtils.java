package me.tuhin47.exporter;

import org.springframework.context.ApplicationContext;

public class ExporterUtils {

    public static DataExporter getDataExporter(ApplicationContext applicationContext, ExporterType exporterType) {

        switch (exporterType) {
            case EXCEL:
                return applicationContext.getBean(ExporterType.Constants.EXCEL, ExcelGenerator.class);
            default:
                throw new IllegalArgumentException("No Implementation found");
        }

    }

}
