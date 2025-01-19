package me.tuhin47.config.exception.common;

import me.tuhin47.config.exception.apierror.EntityNotFoundException;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static <T> Supplier<RuntimeException> getExceptionSupplier(T val, String attribute, String entityName) {
        return () -> new EntityNotFoundException(entityName, attribute != null ? attribute : "id", val.toString());
    }

    public static <T> Supplier<RuntimeException> getExceptionSupplier(T val, String entityName) {
        return getExceptionSupplier(val, null, entityName);
    }
}
