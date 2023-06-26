package me.tuhin47.exporter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ExcelExporterDTO extends ExporterDTO {

    @JsonIgnore
    default String getSheetName() {
        String value = "Sheet";

        if (getClass().isAnnotationPresent(ExcelSheet.class)) {
            var annotation = getClass().getAnnotation(ExcelSheet.class);
            value = annotation.value();
        }
        return value;
    }
}
