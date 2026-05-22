package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.exception.AssertException;
import io.github.loulangogogo.water.tool.AssertTool;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.AssertTool}类的功能。
 */
public class AssertToolTest {

    /**
     * 测试notNull方法，验证非null值不抛出异常。
     */
    @Test
    public void testNotNull_pass() {
        AssertTool.notNull("test", "should not throw");
    }

    /**
     * 测试notNull方法，验证null值抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotNull_fail() {
        AssertTool.notNull(null, "value cannot be null");
    }

    /**
     * 测试isNull方法，验证null值不抛出异常。
     */
    @Test
    public void testIsNull_pass() {
        AssertTool.isNull(null, "should be null");
    }

    /**
     * 测试isNull方法，验证非null值抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsNull_fail() {
        AssertTool.isNull("test", "should be null");
    }

    /**
     * 测试notEmpty方法，验证非空字符串不抛出异常。
     */
    @Test
    public void testNotEmpty_string() {
        AssertTool.notEmpty("hello", "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证空字符串抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotEmpty_string_fail() {
        AssertTool.notEmpty("", "should not be empty");
    }

    /**
     * 测试isEmpty方法，验证空字符串不抛出异常。
     */
    @Test
    public void testIsEmpty_string() {
        AssertTool.isEmpty("", "should be empty");
    }

    /**
     * 测试isEmpty方法，验证非空字符串抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsEmpty_string_fail() {
        AssertTool.isEmpty("hello", "should be empty");
    }

    /**
     * 测试notEmpty方法，验证非空集合不抛出异常。
     */
    @Test
    public void testNotEmpty_collection() {
        AssertTool.notEmpty(Arrays.asList("a"), "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证空集合抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotEmpty_collection_fail() {
        AssertTool.notEmpty(new ArrayList<>(), "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证非空Map不抛出异常。
     */
    @Test
    public void testNotEmpty_map() {
        Map<String, Object> map = new HashMap<>();
        map.put("k", "v");
        AssertTool.notEmpty(map, "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证空Map抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotEmpty_map_fail() {
        AssertTool.notEmpty(new HashMap<>(), "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证非空数组不抛出异常。
     */
    @Test
    public void testNotEmpty_array() {
        AssertTool.notEmpty(new String[]{"a"}, "should not be empty");
    }

    /**
     * 测试notEmpty方法，验证空数组抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotEmpty_array_fail() {
        AssertTool.notEmpty(new String[0], "should not be empty");
    }

    /**
     * 测试isTrue方法，验证true值不抛出异常。
     */
    @Test
    public void testIsTrue_pass() {
        AssertTool.isTrue(true, "should be true");
    }

    /**
     * 测试isTrue方法，验证false值抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsTrue_fail() {
        AssertTool.isTrue(false, "should be true");
    }

    /**
     * 测试isFalse方法，验证false值不抛出异常。
     */
    @Test
    public void testIsFalse_pass() {
        AssertTool.isFalse(false, "should be false");
    }

    /**
     * 测试isFalse方法，验证true值抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsFalse_fail() {
        AssertTool.isFalse(true, "should be false");
    }

    /**
     * 测试notBlank方法，验证非空白字符串不抛出异常。
     */
    @Test
    public void testNotBlank() {
        AssertTool.notBlank("hello", "should not be blank");
    }

    /**
     * 测试notBlank方法，验证空白字符串抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotBlank_fail() {
        AssertTool.notBlank("  ", "should not be blank");
    }

    /**
     * 测试isBlank方法，验证空白字符串不抛出异常。
     */
    @Test
    public void testIsBlank() {
        AssertTool.isBlank("  ", "should be blank");
    }

    /**
     * 测试isBlank方法，验证非空白字符串抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsBlank_fail() {
        AssertTool.isBlank("hello", "should be blank");
    }

    /**
     * 测试after方法，验证Date类型的after断言通过。
     */
    @Test
    public void testAfter_date() {
        Date a = new Date(2000, 1, 1);
        Date b = new Date(100, 1, 1);
        AssertTool.after(a, b, "a should be after b");
    }

    /**
     * 测试after方法，验证Date类型的after断言失败。
     */
    @Test(expected = AssertException.class)
    public void testAfter_date_fail() {
        Date a = new Date(100, 1, 1);
        Date b = new Date(2000, 1, 1);
        AssertTool.after(a, b, "a should be after b");
    }

    /**
     * 测试before方法，验证Date类型的before断言通过。
     */
    @Test
    public void testBefore_date() {
        Date a = new Date(100, 1, 1);
        Date b = new Date(2000, 1, 1);
        AssertTool.before(a, b, "a should be before b");
    }

    /**
     * 测试between方法，验证Date类型的between断言通过。
     */
    @Test
    public void testBetween_date() {
        Date start = new Date(100, 0, 1);
        Date mid = new Date(100, 6, 1);
        Date end = new Date(100, 11, 31);
        AssertTool.between(mid, start, end, "mid should be between start and end");
    }

    /**
     * 测试after方法，验证LocalDateTime类型的after断言通过。
     */
    @Test
    public void testAfter_localDateTime() {
        LocalDateTime a = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime b = LocalDateTime.of(2023, 1, 1, 0, 0);
        AssertTool.after(a, b, "a should be after b");
    }

    /**
     * 测试before方法，验证LocalDateTime类型的before断言通过。
     */
    @Test
    public void testBefore_localDateTime() {
        LocalDateTime a = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime b = LocalDateTime.of(2024, 1, 1, 0, 0);
        AssertTool.before(a, b, "a should be before b");
    }

    /**
     * 测试between方法，验证LocalDateTime类型的between断言通过。
     */
    @Test
    public void testBetween_localDateTime() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime mid = LocalDateTime.of(2023, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 1, 0, 0);
        AssertTool.between(mid, start, end, "mid should be between");
    }

    /**
     * 测试after方法，验证LocalDate类型的after断言通过。
     */
    @Test
    public void testAfter_localDate() {
        LocalDate a = LocalDate.of(2024, 1, 1);
        LocalDate b = LocalDate.of(2023, 1, 1);
        AssertTool.after(a, b, "a should be after b");
    }

    /**
     * 测试before方法，验证LocalDate类型的before断言通过。
     */
    @Test
    public void testBefore_localDate() {
        LocalDate a = LocalDate.of(2023, 1, 1);
        LocalDate b = LocalDate.of(2024, 1, 1);
        AssertTool.before(a, b, "a should be before b");
    }

    /**
     * 测试between方法，验证LocalDate类型的between断言通过。
     */
    @Test
    public void testBetween_localDate() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate mid = LocalDate.of(2023, 6, 1);
        LocalDate end = LocalDate.of(2024, 1, 1);
        AssertTool.between(mid, start, end, "mid should be between");
    }

    /**
     * 测试after方法，验证LocalTime类型的after断言通过。
     */
    @Test
    public void testAfter_localTime() {
        LocalTime a = LocalTime.of(12, 0);
        LocalTime b = LocalTime.of(8, 0);
        AssertTool.after(a, b, "a should be after b");
    }

    /**
     * 测试before方法，验证LocalTime类型的before断言通过。
     */
    @Test
    public void testBefore_localTime() {
        LocalTime a = LocalTime.of(8, 0);
        LocalTime b = LocalTime.of(12, 0);
        AssertTool.before(a, b, "a should be before b");
    }

    /**
     * 测试between方法，验证LocalTime类型的between断言通过。
     */
    @Test
    public void testBetween_localTime() {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime mid = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(12, 0);
        AssertTool.between(mid, start, end, "mid should be between");
    }

    /**
     * 测试isMatch方法，验证正则匹配通过。
     */
    @Test
    public void testIsMatch() {
        AssertTool.isMatch("abc123", "\\w+", "should match");
    }

    /**
     * 测试isMatch方法，验证正则匹配失败抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testIsMatch_fail() {
        AssertTool.isMatch("abc123", "^\\d+$", "should match");
    }

    /**
     * 测试notMatch方法，验证正则不匹配通过。
     */
    @Test
    public void testNotMatch() {
        AssertTool.notMatch("abc", "^\\d+$", "should not match");
    }

    /**
     * 测试notMatch方法，验证正则匹配时抛出AssertException。
     */
    @Test(expected = AssertException.class)
    public void testNotMatch_fail() {
        AssertTool.notMatch("123", "^\\d+$", "should not match");
    }
}
