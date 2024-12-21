package me.tuhin47.config.exception.common;

import me.tuhin47.config.exception.apierror.EntityNotFoundException;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class AuthServiceExceptions {

    public static final Function<String, Supplier<? extends RuntimeException>> USER_NOT_FOUND = id -> getExceptionSupplier(id, "USER");
    public static final Function<Long, Supplier<? extends RuntimeException>> PRIVILEGE_NOT_FOUND = id -> getExceptionSupplier(id, "PRIVILEGE");

    public static final BiFunction<Long, String, Supplier<? extends RuntimeException>> ROLE_NOT_FOUND = (val, attribute) -> getExceptionSupplier(val, attribute, "ROLE");


    public static <T> Supplier<RuntimeException> getExceptionSupplier(T val, String attribute, String entityName) {
        return () -> new EntityNotFoundException(entityName, attribute != null ? attribute : "id", val.toString());
    }

    public static <T> Supplier<RuntimeException> getExceptionSupplier(T val, String entityName) {
        return getExceptionSupplier(val, null, entityName);
    }
}
