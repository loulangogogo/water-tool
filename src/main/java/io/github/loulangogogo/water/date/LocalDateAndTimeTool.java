package io.github.loulangogogo.water.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/*********************************************************
 ** 本地日期和时间的工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class LocalDateAndTimeTool {

    /**
     * 将{@link Date}对象转换为本地日期
     *
     * @param date 将要被转换的{@link Date}对象
     * @return 本地日期对象
     * @author :loulan
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * 根据时区Id将{@link Date}对象转换为本地日期
     *
     * @param date   将要被转换的{@link Date}对象
     * @param zoneId 本地时区ID
     * @return 本地日期对象
     * @author :loulan
     */
    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * 将{@link Date}对象转换为本地日期时间
     *
     * @param date 将要被转换的{@link Date}对象
     * @return 本地日期时间对象
     * @author :loulan
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, ZoneId.systemDefault());
    }

    /**
     * 根据时区Id将{@link Date}对象转换为本地日期时间
     *
     * @param date   将要被转换的{@link Date}对象
     * @param zoneId 本地时区ID
     * @return 本地日期时间对象
     * @author :loulan
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将毫秒值转换为本地日期
     *
     * @param millis 毫秒数
     * @return 本地日期
     * @author :loulan
     */
    public static LocalDate toLocalDate(long millis) {
        return toLocalDate(millis, ZoneId.systemDefault());
    }

    /**
     * 根据时区ID将毫秒值转换为本地日期
     *
     * @param millis 毫秒数
     * @param zoneId 时区ID
     * @return 本地日期
     * @author :loulan
     */
    public static LocalDate toLocalDate(long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate();
    }

    /**
     * 将毫秒值转换为本地日期时间
     *
     * @param millis 毫秒数
     * @return 本地日期
     * @author :loulan
     */
    public static LocalDateTime toLocalDateTime(long millis) {
        return toLocalDateTime(millis, ZoneId.systemDefault());
    }

    /**
     * 根据时区ID将毫秒值转换为本地日期时间
     *
     * @param millis 毫秒数
     * @param zoneId 时区ID
     * @return 本地日期
     * @author :loulan
     */
    public static LocalDateTime toLocalDateTime(long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDateTime();
    }
}
