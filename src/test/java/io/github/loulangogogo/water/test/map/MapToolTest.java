package io.github.loulangogogo.water.test.map;

import io.github.loulangogogo.water.map.MapTool;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 测试{@link MapTool}类的功能。
 */
public class MapToolTest {

    /**
     * 测试MapTool.isEmpty方法，验证传入null时返回true的场景。
     */
    @Test
    public void testIsEmpty_null() {
        assertTrue(MapTool.isEmpty(null));
    }

    /**
     * 测试MapTool.isEmpty方法，验证空Map返回true的场景。
     */
    @Test
    public void testIsEmpty_empty() {
        assertTrue(MapTool.isEmpty(new HashMap<>()));
    }

    /**
     * 测试MapTool.isEmpty方法，验证非空Map返回false的场景。
     */
    @Test
    public void testIsEmpty_nonEmpty() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        assertFalse(MapTool.isEmpty(map));
    }

    /**
     * 测试MapTool.isNotEmpty方法，验证非空Map返回true的场景。
     */
    @Test
    public void testIsNotEmpty() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        assertTrue(MapTool.isNotEmpty(map));
    }

    /**
     * 测试MapTool.isNotEmpty方法，验证传入null时返回false的场景。
     */
    @Test
    public void testIsNotEmpty_null() {
        assertFalse(MapTool.isNotEmpty(null));
    }

    /**
     * 测试MapTool.map方法，验证创建空HashMap实例的场景。
     */
    @Test
    public void testMap() {
        Map<String, Object> map = MapTool.map();
        assertNotNull(map);
        assertTrue(map.isEmpty());
        assertTrue(map instanceof HashMap);
    }

    /**
     * 测试MapTool.of方法，验证通过键值对快速创建单元素Map的场景。
     */
    @Test
    public void testOf() {
        Map<String, String> map = MapTool.of("key", "value");
        assertEquals(1, map.size());
        assertEquals("value", map.get("key"));
    }

    /**
     * 测试MapTool.underlineToCamel方法，验证下划线键名转为驼峰的场景。
     */
    @Test
    public void testUnderlineToCamel() {
        Map<String, String> source = new HashMap<>();
        source.put("user_name", "loulan");
        source.put("user_age", "18");

        Map<String, String> result = MapTool.underlineToCamel(source);
        assertEquals("loulan", result.get("userName"));
        assertEquals("18", result.get("userAge"));
        assertNull(result.get("user_name"));
    }

    /**
     * 测试MapTool.underlineToCamel方法，验证空Map转换后仍为空Map的场景。
     */
    @Test
    public void testUnderlineToCamel_empty() {
        Map<String, String> source = new HashMap<>();
        Map<String, String> result = MapTool.underlineToCamel(source);
        assertTrue(result.isEmpty());
    }
}
