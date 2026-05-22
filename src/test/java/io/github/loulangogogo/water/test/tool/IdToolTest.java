package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.tool.CharsetTool;
import io.github.loulangogogo.water.tool.IdTool;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.IdTool}类的功能。
 */
public class IdToolTest {

    /**
     * 测试randomUUID方法，验证生成标准UUID格式。
     */
    @Test
    public void testRandomUUID() {
        String uuid = IdTool.randomUUID();
        assertNotNull(uuid);
        assertEquals(36, uuid.length());
        // UUID format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        assertTrue(uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
    }

    /**
     * 测试simpleUUID方法，验证生成无连字符的32位UUID。
     */
    @Test
    public void testSimpleUUID() {
        String uuid = IdTool.simpleUUID();
        assertNotNull(uuid);
        assertEquals(32, uuid.length());
        // No dashes
        assertFalse(uuid.contains("-"));
        assertTrue(uuid.matches("[0-9a-f]{32}"));
    }

    /**
     * 测试simpleUUID方法，验证多次生成的UUID具有唯一性。
     */
    @Test
    public void testSimpleUUID_uniqueness() {
        String uuid1 = IdTool.simpleUUID();
        String uuid2 = IdTool.simpleUUID();
        assertNotEquals(uuid1, uuid2);
    }
}
