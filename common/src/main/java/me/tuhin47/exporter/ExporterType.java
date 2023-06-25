package me.tuhin47.exporter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExporterType {
    EXCEL(Constants.EXCEL),
    CSV(Constants.CSV),
    PDF(Constants.PDF);
    private final String type;

    public static class Constants {
        public static final String EXCEL = "EXCEL";
        public static final String CSV = "CSV";
        public static final String PDF = "PDF";
    }
}
