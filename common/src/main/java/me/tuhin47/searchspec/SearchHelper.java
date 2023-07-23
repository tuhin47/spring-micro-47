package me.tuhin47.searchspec;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SearchHelper {
    public static boolean checkValidDate(String dateString) {
        try {
            DateTimeFormatter.ISO_INSTANT.parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Class<?> getClassFromName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Invalid Class name " + className);
        }
    }

    public static boolean isClassImplementedOrSubclassOf(Class<?> clazz, Class<?> assignable) {
        return assignable.isAssignableFrom(clazz);
    }


}
