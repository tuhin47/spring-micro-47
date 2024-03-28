package me.tuhin47.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class InstantUtil {

    public static Instant startOfMonth() {
        return startOfInstant(ZoneId.systemDefault(), TemporalAdjusters.firstDayOfMonth());
    }

    public static Instant startOfYear() {
        return startOfInstant(ZoneId.systemDefault(), TemporalAdjusters.firstDayOfYear());
    }

    public static Instant startOfInstant(ZoneId zoneId, TemporalAdjuster adjuster) {
        ZonedDateTime startOfMonth = ZonedDateTime.now(zoneId)
                                                  .with(adjuster)
                                                  .toLocalDate()
                                                  .atStartOfDay(zoneId);
        return startOfMonth.toInstant();
    }

    public static Instant endOfCurrentMonth() {
        return endOfInstant(ZoneId.systemDefault(), TemporalAdjusters.lastDayOfMonth());
    }

    public static Instant endOfCurrentYear() {
        return endOfInstant(ZoneId.systemDefault(), TemporalAdjusters.lastDayOfYear());
    }

    public static Instant endOfInstant(ZoneId zoneId, TemporalAdjuster adjuster) {
        ZonedDateTime endOfMonth = ZonedDateTime.now(zoneId)
                                                .with(adjuster)
                                                .toLocalDate()
                                                .atTime(23, 59, 59, 999999999)
                                                .atZone(zoneId);
        return endOfMonth.toInstant();
    }
}