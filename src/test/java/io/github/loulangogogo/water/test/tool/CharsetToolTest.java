package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.tool.CharsetTool;
import io.github.loulangogogo.water.enums.CharsetEnum;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.CharsetTool}类的功能。
 */
public class CharsetToolTest {

    /**
     * 测试charset常量，验证UTF8编码一致性。
     */
    @Test
    public void testCharsetUTF8() {
        assertEquals(StandardCharsets.UTF_8, CharsetEnum.UTF8.getCharset());
        assertEquals(StandardCharsets.UTF_8, CharsetEnum.UTF_8.getCharset());
    }

    /**
     * 测试charset常量，验证GBK编码一致性。
     */
    @Test
    public void testCharsetGBK() {
        assertEquals(CharsetTool.CHARSET_GBK, CharsetEnum.GBK.getCharset());
    }

    /**
     * 测试defaultCharset方法，验证返回系统默认字符集。
     */
    @Test
    public void testDefaultCharset() {
        Charset defaultCharset = CharsetTool.defaultCharset();
        assertNotNull(defaultCharset);
        assertEquals(Charset.defaultCharset(), defaultCharset);
    }

    /**
     * 测试CHARSET_UTF_8常量，验证与StandardCharsets.UTF_8一致。
     */
    @Test
    public void testCharsetUtf8Constant() {
        assertEquals(StandardCharsets.UTF_8, CharsetTool.CHARSET_UTF_8);
    }
}
