package me.tuhin47.utils;

import java.util.List;

public interface ExcelDTO {
    default String getSheetName() {
        return "Sheet";
    }

//    TODO : Headers should be managed by annotations
    List<String> getHeaders();
}
