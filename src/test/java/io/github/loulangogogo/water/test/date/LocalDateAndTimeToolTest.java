package io.github.loulangogogo.water.test.date;

import io.github.loulangogogo.water.date.LocalDateAndTimeTool;
import org.junit.Test;

import java.time.*;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * 测试{@link LocalDateAndTimeTool}类的功能。
 */
public class LocalDateAndTimeToolTest {

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDate}方法，验证Date转LocalDate。
     */
    @Test
    public void testToLocalDate_fromDate() {
        Date date = new Date();
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(date);
        assertNotNull(localDate);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDate}方法，验证带时区参数Date转LocalDate。
     */
    @Test
    public void testToLocalDate_fromDate_withZone() {
        Date date = new Date();
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(date, ZoneId.of("Asia/Shanghai"));
        assertNotNull(localDate);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDateTime}方法，验证Date转LocalDateTime。
     */
    @Test
    public void testToLocalDateTime_fromDate() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateAndTimeTool.toLocalDateTime(date);
        assertNotNull(localDateTime);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDateTime}方法，验证带时区参数Date转LocalDateTime。
     */
    @Test
    public void testToLocalDateTime_fromDate_withZone() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateAndTimeTool.toLocalDateTime(date, ZoneId.of("Asia/Shanghai"));
        assertNotNull(localDateTime);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDate}方法，验证时间戳转LocalDate。
     */
    @Test
    public void testToLocalDate_fromMillis() {
        long millis = System.currentTimeMillis();
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(millis);
        assertNotNull(localDate);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDate}方法，验证带时区参数时间戳转LocalDate。
     */
    @Test
    public void testToLocalDate_fromMillis_withZone() {
        long millis = System.currentTimeMillis();
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(millis, ZoneId.of("Asia/Shanghai"));
        assertNotNull(localDate);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDateTime}方法，验证时间戳转LocalDateTime。
     */
    @Test
    public void testToLocalDateTime_fromMillis() {
        long millis = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateAndTimeTool.toLocalDateTime(millis);
        assertNotNull(localDateTime);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDateTime}方法，验证带时区参数时间戳转LocalDateTime。
     */
    @Test
    public void testToLocalDateTime_fromMillis_withZone() {
        long millis = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateAndTimeTool.toLocalDateTime(millis, ZoneId.of("Asia/Shanghai"));
        assertNotNull(localDateTime);
    }

    /**
     * 测试{@link LocalDateAndTimeTool#toLocalDate}方法，验证已知时间戳转LocalDate的准确性。
     */
    @Test
    public void testToLocalDate_knownMillis() {
        // Jan 1, 2024 00:00:00 UTC
        long millis = Instant.parse("2024-01-01T00:00:00Z").toEpochMilli();
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(millis, ZoneId.of("UTC"));
        assertEquals(LocalDate.of(2024, 1, 1), localDate);
    }
}
