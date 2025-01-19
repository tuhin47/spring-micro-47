package me.tuhin47.config.exception.common;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

//TODO : Generic Exception Pattern With default format
public class AuthServiceExceptions {

    public static final Function<String, Supplier<? extends RuntimeException>> USER_NOT_FOUND = id -> ExceptionUtils.getExceptionSupplier(id, "USER");
    public static final Function<Long, Supplier<? extends RuntimeException>> PRIVILEGE_NOT_FOUND = id -> ExceptionUtils.getExceptionSupplier(id, "PRIVILEGE");
    public static final BiFunction<Long, String, Supplier<? extends RuntimeException>> ROLE_NOT_FOUND = (val, attribute) -> ExceptionUtils.getExceptionSupplier(val, attribute, "ROLE");
}
