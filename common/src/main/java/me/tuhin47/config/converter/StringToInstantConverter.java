package me.tuhin47.config.converter;

import org.springframework.core.convert.converter.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class StringToInstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(String source) {
        LocalDate localDate = LocalDate.parse(source);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
