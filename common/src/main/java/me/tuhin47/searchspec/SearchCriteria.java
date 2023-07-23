package me.tuhin47.searchspec;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * Project Name     : dynamic-where
 * Date Time        : 6/10/2020
 *
 * @author Teten Nugraha
 */
@Value
@Slf4j
public class SearchCriteria {

    String key;
    String value;
    SearchOperation operation;
    String groupId;
    String className;

    public <Y extends Comparable<? super Y>> Y getObjectValue() {

        if (className == null) return null;
        Class<?> classFromName = SearchHelper.getClassFromName(getClassName());
        if (className == null || !SearchHelper.isClassImplementedOrSubclassOf(classFromName, Comparable.class)) {
            return null;
        }

        try {
            if (SearchHelper.checkValidDate(value)) {
                return (Y) Instant.parse(value);
            } else if (SearchHelper.isClassImplementedOrSubclassOf(classFromName, Double.class) && SearchHelper.isValidNumber(value)) {
                return (Y) Double.valueOf(value);
            } else if (SearchHelper.isClassImplementedOrSubclassOf(classFromName, Long.class) && SearchHelper.isValidNumber(value)) {
                return (Y) Long.valueOf(value);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

}
