package me.tuhin47.exception.common;

import me.tuhin47.exception.EntityNotFoundException;

import java.util.function.Function;

public class UserServiceExceptions {
    public static final Function<String, RuntimeException> USER_NOT_FOUND = id -> new EntityNotFoundException("USER", "id", id);
}
