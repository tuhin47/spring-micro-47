package me.tuhin47.config.exception.apierror;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private HttpStatus status = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String simpleName, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(simpleName, toMap(String.class, String.class, searchParamsMap)));
    }

    public EntityNotFoundException(Class<?> clazz, HttpStatus status, String... searchParamsMap) {
        this(clazz.getSimpleName(), searchParamsMap);
        this.status = status;
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1) throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2)
                        .map(i -> i * 2)
                        .collect(HashMap::new, (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }

}
