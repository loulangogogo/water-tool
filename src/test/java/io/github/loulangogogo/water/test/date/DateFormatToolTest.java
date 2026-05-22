package io.github.loulangogogo.water.test.date;

import io.github.loulangogogo.water.date.DateFormatTool;
import io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * 测试{@link DateFormatTool}和{@link io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool}类的功能。
 */
public class DateFormatToolTest {

    /**
     * 测试{@link DateFormatTool#format}方法，验证yyyy-MM-dd HH:mm:ss格式。
     */
    @Test
    public void testDateFormat() {
        Calendar cal = new GregorianCalendar(2024, 0, 15, 10, 30, 45);
        Date date = cal.getTime();
        String formatted = DateFormatTool.format(date, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2024-01-15 10:30:45", formatted);
    }

    /**
     * 测试{@link DateFormatTool#format}方法，验证yyyy/MM/dd格式。
     */
    @Test
    public void testFormatDateOnly() {
        Calendar cal = new GregorianCalendar(2024, 5, 1);
        Date date = cal.getTime();
        String formatted = DateFormatTool.format(date, "yyyy/MM/dd");
        assertEquals("2024/06/01", formatted);
    }

    /**
     * 测试{@link DateFormatTool#format}方法，验证ISO8601格式。
     */
    @Test
    public void testFormatISO8601() {
        Calendar cal = new GregorianCalendar(2024, 0, 15);
        Date date = cal.getTime();
        String formatted = DateFormatTool.format(date, "yyyy-MM-dd'T'HH:mm:ss");
        assertNotNull(formatted);
    }

    // LocalDateAndTimeFormatTool tests
    /**
     * 测试{@link io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool#format}方法，验证LocalDate格式化。
     */
    @Test
    public void testLocalFormat_localDate() {
        String formatted = LocalDateAndTimeFormatTool.format(
                java.time.LocalDate.of(2024, 1, 15), "yyyy-MM-dd");
        assertEquals("2024-01-15", formatted);
    }

    /**
     * 测试{@link io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool#format}方法，验证LocalTime格式化。
     */
    @Test
    public void testLocalFormat_localTime() {
        String formatted = LocalDateAndTimeFormatTool.format(
                java.time.LocalTime.of(14, 30, 0), "HH:mm:ss");
        assertEquals("14:30:00", formatted);
    }

    /**
     * 测试{@link io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool#format}方法，验证LocalDateTime格式化。
     */
    @Test
    public void testLocalFormat_localDateTime() {
        String formatted = LocalDateAndTimeFormatTool.format(
                java.time.LocalDateTime.of(2024, 1, 15, 14, 30, 0), "yyyy-MM-dd HH:mm:ss");
        assertEquals("2024-01-15 14:30:00", formatted);
    }

    /**
     * 测试{@link io.github.loulangogogo.water.date.LocalDateAndTimeFormatTool#format}方法，验证时间戳格式化。
     */
    @Test
    public void testLocalFormat_millis() {
        // Jan 15, 2024 00:00:00 UTC
        long millis = java.time.Instant.parse("2024-01-15T00:00:00Z").toEpochMilli();
        String formatted = LocalDateAndTimeFormatTool.format(millis, "yyyy-MM-dd HH:mm:ss");
        assertNotNull(formatted);
        assertTrue(formatted.contains("2024"));
    }
}
