package me.tuhin47.exporter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportColumn {

    String value();

    /**
     * @return 0 based sort order
     */
    int sortOrder() default -1;
}
