package me.tuhin47.config.converter;

import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class StringToInstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(@Nullable String source) {
        if (source == null) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(source);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
