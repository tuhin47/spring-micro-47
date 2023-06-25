package me.tuhin47.exporter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

public interface DataExporter{
    <T extends ExcelDTO> byte[] generate(List<T> records);
    String getExtension();

    default HttpHeaders getHTTPHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName + getExtension());

        return headers;
    }
    default HttpHeaders getHTTPHeaders() {
        return getHTTPHeaders(getFileName());
    }

    default String getFileName() {
        return "data";
    }

}
