package me.tuhin47.exporter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface DataExporter<T extends ExporterDTO>{
    byte[] generate(List<? extends T> listRecords);
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

    default T getFirstRecord(List<? extends T> listRecords) {
        return listRecords.stream().findFirst().orElseThrow(() -> new RuntimeException("No record found"));
    }

    default List<String> getHeaders(List<? extends T> listRecords) {
        return getFirstRecord(listRecords).getHeaders();
    }

    default List<Function<Object, ?>> getFunctions(List<? extends T> listRecords) {
        var firstRecord = getFirstRecord(listRecords);
        var fields = firstRecord.getFields();
        var fieldFunctionTreeMap = new ArrayList<Function<Object, ?>>();

        for (Field field : fields) {
            Function<Object, ?> fieldExtractor = record -> {
                try {
                    field.setAccessible(true);
                    return field.get(record);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            };

            fieldFunctionTreeMap.add(fieldExtractor);
        }
        return fieldFunctionTreeMap;
    }
}
