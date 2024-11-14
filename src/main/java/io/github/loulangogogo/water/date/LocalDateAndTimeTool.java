package io.github.loulangogogo.water.date;

import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/*********************************************************
 ** 本地日期和时间的工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class LocalDateAndTimeTool {

    /**
     * 将日期时间字符串转换为LocalDateTime对象
     *
     * @param dateTime 日期时间字符串，如"2023-10-01T12:00:00"
     * @param pattern 日期时间字符串的格式模式，用于解析字符串
     * @return 解析后的LocalDateTime对象，如果输入字符串为空则返回null
     * @author loulan
     */
    public static LocalDateTime toLocalDateTime(String dateTime, String pattern) {
        if (StrTool.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将日期时间字符串转换为LocalDateTime对象，默认格式yyyy-MM-dd HH:mm:ss"
     *
     * @param dateTime 日期时间字符串，默认格式为"yyyy-MM-dd HH:mm:ss"
     * @return 解析后的LocalDateTime对象
     * @author loulan
     */
    public static LocalDateTime toLocalDateTime(String dateTime) {
        return toLocalDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将日期字符串转换为LocalDate对象
     *
     * @param date 日期字符串，如"2023-10-01"
     * @param pattern 日期字符串的格式模式，用于解析字符串
     * @return 解析后的LocalDate对象，如果输入字符串为空则返回null
     * @author loulan
     */
    public static LocalDate toLocalDate(String date, String pattern) {
        if (StrTool.isEmpty(date)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 将日期字符串转换为LocalDate对象
     * 此方法用于简化日期字符串到LocalDate对象的转换过程,默认格式 yyyy-MM-dd
     *
     * @param date 日期字符串，格式为yyyy-MM-dd
     * @return 解析后的LocalDate对象
     * @author loulan
     */
    public static LocalDate toLocalDate(String date) {
        return toLocalDate(date,"yyyy-MM-dd");
    }

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
