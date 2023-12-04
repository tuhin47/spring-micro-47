package me.tuhin47.exporter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExporterUtils {

    private final ApplicationContext applicationContext;
    public <T extends ExporterDTO> DataExporter<T> getDataExporter(ExporterType exporterType) {

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
