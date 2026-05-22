package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.tool.CharsetTool;
import io.github.loulangogogo.water.tool.IdTool;
import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.ObjectTool}类的功能。
 */
public class ObjectToolTest {

    /**
     * 测试isNull方法，验证传入null时返回true。
     */
    @Test
    public void testIsNull_withNull() {
        assertTrue(ObjectTool.isNull(null));
    }

    /**
     * 测试isNull方法，验证传入非null对象时返回false。
     */
    @Test
    public void testIsNull_withNonNull() {
        assertFalse(ObjectTool.isNull(""));
        assertFalse(ObjectTool.isNull(0));
        assertFalse(ObjectTool.isNull(new Object()));
    }

    /**
     * 测试isNotNull方法，验证传入null时返回false。
     */
    @Test
    public void testIsNotNull_withNull() {
        assertFalse(ObjectTool.isNotNull(null));
    }

    /**
     * 测试isNotNull方法，验证传入非null对象时返回true。
     */
    @Test
    public void testIsNotNull_withNonNull() {
        assertTrue(ObjectTool.isNotNull(""));
        assertTrue(ObjectTool.isNotNull(0));
        assertTrue(ObjectTool.isNotNull(new Object()));
    }

    /**
     * 测试equals方法，验证两个null值相等。
     */
    @Test
    public void testEquals_bothNull() {
        assertTrue(ObjectTool.equals(null, null));
    }

    /**
     * 测试equals方法，验证一方为null时返回false。
     */
    @Test
    public void testEquals_oneNull() {
        assertFalse(ObjectTool.equals(null, "test"));
        assertFalse(ObjectTool.equals("test", null));
    }

    /**
     * 测试equals方法，验证相同值返回true。
     */
    @Test
    public void testEquals_sameValues() {
        assertTrue(ObjectTool.equals("test", "test"));
        assertTrue(ObjectTool.equals(123, 123));
    }

    /**
     * 测试equals方法，验证不同值返回false。
     */
    @Test
    public void testEquals_differentValues() {
        assertFalse(ObjectTool.equals("test", "other"));
    }

    /**
     * 测试notEquals方法，验证不同值和相同值的判断。
     */
    @Test
    public void testNotEquals() {
        assertTrue(ObjectTool.notEquals("a", "b"));
        assertFalse(ObjectTool.notEquals("a", "a"));
    }

    /**
     * 测试toString方法，验证对象转字符串及null值处理。
     */
    @Test
    public void testToString() {
        assertEquals("hello", ObjectTool.toString("hello"));
        assertEquals("null", ObjectTool.toString(null));
        assertEquals("123", ObjectTool.toString(123));
    }

    /**
     * 测试isInstanceof方法，验证类型匹配和不匹配的场景。
     */
    @Test
    public void testIsInstanceof() {
        assertTrue(ObjectTool.isInstanceof("test", String.class));
        assertFalse(ObjectTool.isInstanceof("test", Integer.class));
    }

    /**
     * 测试getOrDefault方法，验证非null值返回原值。
     */
    @Test
    public void testGetOrDefault_withValue() {
        assertEquals("hello", ObjectTool.getOrDefault("hello", "default"));
    }

    /**
     * 测试getOrDefault方法，验证null值返回默认值。
     */
    @Test
    public void testGetOrDefault_withNull() {
        assertEquals("default", ObjectTool.getOrDefault(null, "default"));
    }

    /**
     * 测试clone方法，验证数组浅拷贝的正确性。
     */
    @Test
    public void testClone() {
        String[] arr = {"a", "b"};
        String[] cloned = ObjectTool.clone(arr);
        assertArrayEquals(arr, cloned);
        assertNotSame(arr, cloned);
    }

    /**
     * 测试deepClone方法，验证对象深拷贝的正确性。
     */
    @Test
    public void testDeepClone() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        list.add("a");
        list.add("b");
        @SuppressWarnings("unchecked")
        java.util.ArrayList<String> cloned = ObjectTool.deepClone(list);
        assertEquals(list, cloned);
        assertNotSame(list, cloned);
    }
}
