package io.github.loulangogogo.water.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/*********************************************************
 ** 日期类型{@link Date} 的工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class DateTool {

    /**
     * 将字符串格(指定的格式)的日期解析为{@link Date}类型
     *
     * @param str           字符串日期
     * @param parsePatterns 可能的日期格式
     * @return {@link Date}
     * @throws ParseException 日期格式解析异常
     * @author :loulan
     */
    public static Date parseDate(final String str, final String... parsePatterns) throws ParseException {
        return DateUtils.parseDate(str, parsePatterns);
    }

    /**
     * 将字符串格(指定的格式)的日期解析为(指定位置，设计到时区){@link Date}类型
     *
     * @param str           字符串日期
     * @param locale        指定的位置
     * @param parsePatterns 可能的日期格式
     * @return {@link Date}
     * @throws ParseException 日期格式解析异常
     * @author :loulan
     */
    public static Date parseDate(final String str, final Locale locale, final String... parsePatterns) throws ParseException {
        return DateUtils.parseDate(str, locale, parsePatterns);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addYears(final Date date, final int amount) {
        return DateUtils.addYears(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addMonths(final Date date, final int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addWeeks(final Date date, final int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addDays(final Date date, final int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addHours(final Date date, final int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addMinutes(final Date date, final int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addSeconds(final Date date, final int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 在指定的日期上添加指定数值的日期
     *
     * @param date   将要被添加日期的日期对象
     * @param amount 要添加的日期数值
     * @return 添加之后的日期对象
     * @author :loulan
     */
    public static Date addMilliseconds(final Date date, final int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }

    /**
     * 设置指定日期的年份
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setYears(final Date date, final int amount) {
        return DateUtils.setYears(date, amount);
    }

    /**
     * 设置指定日期的月份
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setMonths(final Date date, final int amount) {
        return DateUtils.setMonths(date, amount);
    }

    /**
     * 设置指定日期的天，要按照指定月份的最大天内设置
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setDays(final Date date, final int amount) {
        return DateUtils.setDays(date, amount);
    }

    /**
     * 设置指定日期的小时
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setHours(final Date date, final int amount) {
        return DateUtils.setHours(date, amount);
    }

    /**
     * 设置指定日期的分钟
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setMinutes(final Date date, final int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    /**
     * 设置指定日期的秒
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setSeconds(final Date date, final int amount) {
        return DateUtils.setSeconds(date, amount);
    }

    /**
     * 设置指定日期的毫秒
     *
     * @param date   将要被设置的日期
     * @param amount 要设置的数值
     * @return 设置完成的日期
     * @author :loulan
     */
    public static Date setMilliseconds(final Date date, final int amount) {
        return DateUtils.setMilliseconds(date, amount);
    }

    /**
     * 转换为Calendear类型
     *
     * @param date 要进行转换的日期
     * @return {@link Calendar}对象
     * @author :loulan
     */
    public static Calendar toCalendar(final Date date) {
        return DateUtils.toCalendar(date);
    }

    /**
     * 转换为指定时区Calendear类型
     *
     * @param date 要进行转换的日期
     * @return {@link Calendar}对象
     * @author :loulan
     */
    public static Calendar toCalendar(final Date date, final TimeZone tz) {
        return DateUtils.toCalendar(date, tz);
    }

    /**
     * 根据毫秒数获取日期对象
     *
     * @param millis 毫秒数
     * @return {@link Date} 对象
     * @author :loulan
     */
    public static Date get(long millis) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.getTime();
    }
}
