package com.csbaic.jd.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间工具类
 */
public final class TimeUtils {


    /**
     * 将{@link LocalDateTime}转换成UTC时间
     * @param dateTime
     * @param zoneId 时区
     * @return
     */
    public static long toMillis(LocalDateTime dateTime , ZoneId zoneId){
        return dateTime
                .atZone(zoneId)
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 将{@link LocalDateTime}转换成UTC时间
     * @param dateTime
     * @return
     */
    public static long toMillis(LocalDateTime dateTime ){
        return toMillis(dateTime, ZoneId.systemDefault());
    }

    /**
     * {@link long}转换成{@link LocalDateTime}
     * @param timeInMillis
     * @param zoneId
     * @return
     */
    public static LocalDateTime toLocalDateTime(long timeInMillis, ZoneId zoneId){
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timeInMillis),
                zoneId
        );
    }


    /**
     * {@link long}转换成{@link LocalDateTime}
     * @param timeInMillis
     * @return
     */
    public static LocalDateTime toLocalDateTime(long timeInMillis){
        return toLocalDateTime(timeInMillis, ZoneId.systemDefault());
    }

}
