package io.github.loulangogogo.water.test.date;

import io.github.loulangogogo.water.date.DateTool;
import io.github.loulangogogo.water.date.LocalDateAndTimeTool;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * 测试{@link DateTool}类的功能。
 */
public class DateToolTest {

    /**
     * 测试{@link DateTool#parseDate}方法，验证解析正确格式的日期字符串。
     */
    @Test
    public void testParseDate() throws ParseException {
        Date date = DateTool.parseDate("2024-01-15", "yyyy-MM-dd");
        assertNotNull(date);
    }

    /**
     * 测试{@link DateTool#parseDate}方法，验证非法格式抛出ParseException。
     */
    @Test(expected = ParseException.class)
    public void testParseDate_invalidFormat() throws ParseException {
        DateTool.parseDate("not-a-date", "yyyy-MM-dd");
    }

    /**
     * 测试{@link DateTool#addYears}方法，验证年份增加。
     */
    @Test
    public void testAddYears() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.addYears(date, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(2025, cal.get(Calendar.YEAR));
    }

    /**
     * 测试{@link DateTool#addMonths}方法，验证月份增加。
     */
    @Test
    public void testAddMonths() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.addMonths(date, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(1, cal.get(Calendar.MONTH));
    }

    /**
     * 测试{@link DateTool#addDays}方法，验证天数增加。
     */
    @Test
    public void testAddDays() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.addDays(date, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 测试{@link DateTool#addWeeks}方法，验证周数增加（7天）。
     */
    @Test
    public void testAddWeeks() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.addWeeks(date, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(8, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 测试{@link DateTool#addHours}方法，验证小时增加。
     */
    @Test
    public void testAddHours() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        Date date = cal.getTime();
        Date result = DateTool.addHours(date, 2);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(12, cal2.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 测试{@link DateTool#addMinutes}方法，验证分钟增加。
     */
    @Test
    public void testAddMinutes() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 30, 0);
        Date date = cal.getTime();
        Date result = DateTool.addMinutes(date, 15);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(45, cal2.get(Calendar.MINUTE));
    }

    /**
     * 测试{@link DateTool#addSeconds}方法，验证秒数增加。
     */
    @Test
    public void testAddSeconds() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        Date date = cal.getTime();
        Date result = DateTool.addSeconds(date, 30);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(30, cal2.get(Calendar.SECOND));
    }

    /**
     * 测试{@link DateTool#addMilliseconds}方法，验证毫秒增加。
     */
    @Test
    public void testAddMilliseconds() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        Date result = DateTool.addMilliseconds(date, 500);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(500, cal2.get(Calendar.MILLISECOND));
    }

    /**
     * 测试{@link DateTool#setYears}方法，验证设置年份。
     */
    @Test
    public void testSetYears() {
        Date date = new Date(2020 - 1900, 0, 1);
        Date result = DateTool.setYears(date, 2024);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(2024, cal.get(Calendar.YEAR));
    }

    /**
     * 测试{@link DateTool#setMonths}方法，验证设置月份。
     */
    @Test
    public void testSetMonths() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.setMonths(date, 6);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(6, cal.get(Calendar.MONTH));
    }

    /**
     * 测试{@link DateTool#setDays}方法，验证设置日期。
     */
    @Test
    public void testSetDays() {
        Date date = new Date(2024 - 1900, 0, 1);
        Date result = DateTool.setDays(date, 15);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 测试{@link DateTool#setHours}方法，验证设置小时。
     */
    @Test
    public void testSetHours() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        Date date = cal.getTime();
        Date result = DateTool.setHours(date, 15);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(15, cal2.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 测试{@link DateTool#setMinutes}方法，验证设置分钟。
     */
    @Test
    public void testSetMinutes() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        Date date = cal.getTime();
        Date result = DateTool.setMinutes(date, 45);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(45, cal2.get(Calendar.MINUTE));
    }

    /**
     * 测试{@link DateTool#setSeconds}方法，验证设置秒数。
     */
    @Test
    public void testSetSeconds() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        Date date = cal.getTime();
        Date result = DateTool.setSeconds(date, 30);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(30, cal2.get(Calendar.SECOND));
    }

    /**
     * 测试{@link DateTool#setMilliseconds}方法，验证设置毫秒。
     */
    @Test
    public void testSetMilliseconds() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, 0, 1, 10, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        Date result = DateTool.setMilliseconds(date, 500);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(result);
        assertEquals(500, cal2.get(Calendar.MILLISECOND));
    }

    /**
     * 测试{@link DateTool#toCalendar}方法，验证Date转Calendar。
     */
    @Test
    public void testToCalendar() {
        Date date = new Date();
        Calendar cal = DateTool.toCalendar(date);
        assertNotNull(cal);
        assertEquals(date.getTime(), cal.getTimeInMillis());
    }

    /**
     * 测试{@link DateTool#toCalendar}方法，验证带时区参数转Calendar。
     */
    @Test
    public void testToCalendar_withTimezone() {
        Date date = new Date();
        Calendar cal = DateTool.toCalendar(date, TimeZone.getTimeZone("Asia/Shanghai"));
        assertNotNull(cal);
        assertEquals("Asia/Shanghai", cal.getTimeZone().getID());
    }

    /**
     * 测试{@link DateTool#get}方法，验证通过时间戳获取Date对象。
     */
    @Test
    public void testGet() {
        long millis = System.currentTimeMillis();
        Date date = DateTool.get(millis);
        assertEquals(millis, date.getTime());
    }

    /**
     * 测试{@link DateTool#parseDate}方法，验证带Locale参数解析日期。
     */
    @Test
    public void testParseDate_withLocale() throws ParseException {
        Date date = DateTool.parseDate("January 15, 2024", java.util.Locale.ENGLISH, "MMMM dd, yyyy");
        assertNotNull(date);
    }
}
