package com.microservice.orderservice;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import me.tuhin47.utils.ExcelDTO;
import me.tuhin47.utils.ExcelGenerator;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) throws Throwable {
        var val = Record.builder().a(1).b(2L).c("C").d(Instant.now()).build();
        var records = Collections.singletonList(val);

        var recordExcelGenerator = new ExcelGenerator<>(records);
//        recordExcelGenerator.generate();
    }
}

@RequiredArgsConstructor
class ExportToCSV<T extends List> {

    private final CustomFormatter customFormatter;

    public void extracted(T vals) throws IllegalAccessException {
        for (Object val : vals) {
            for (Field declaredField : val.getClass().getDeclaredFields()) {
                var o = declaredField.get(val);
                System.out.print(customFormatter.getFormattedValue(declaredField.getName(), o));
            }
        }
    }

    public void functional(T vals) throws Throwable {

        Field[] fields = vals.stream().findFirst().orElseThrow(() -> new RuntimeException("Issue"))
                                    .getClass().getDeclaredFields();

        vals.forEach(val -> {
            for (Field field : fields) {
                String fieldName = field.getName();


                Function<Object, ?> fieldExtractor = record -> {
                    try {
                        field.setAccessible(true);
                        return field.get(record);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };

                System.out.println(customFormatter.getFormattedValue(fieldName, fieldExtractor.apply(val)));

            }
        });

    }
}

@FunctionalInterface
interface CustomFormatter {
    String getFormattedValue(String fieldName, Object value);
}

class DateFormatter implements CustomFormatter {

    @Override
    public String getFormattedValue(String fieldName, Object value) {


        if (value instanceof Instant) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                    .withLocale(Locale.US)
                    .withZone(ZoneId.systemDefault());

            return formatter.format(((Instant) value));
        }

        return fieldName+ " - " + value.toString();
    }
}


@Builder
class Record implements ExcelDTO {
    private final int a;
    private final long b;
    private final String c;
    private final Instant d;

    @Override
    public List<String> getHeaders() {
        return Arrays.asList("A", "B", "C", "D");
    }
}