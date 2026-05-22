package io.github.loulangogogo.water.test.exception;

import io.github.loulangogogo.water.exception.ExceptionTool;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 测试{@link ExceptionTool}类的功能。
 */
public class ExceptionToolTest {

    /**
     * 测试ExceptionTool.getStackTrace方法，验证获取普通异常的堆栈信息。
     */
    @Test
    public void testGetStackTrace() {
        try {
            throw new RuntimeException("test exception");
        } catch (Exception e) {
            String stackTrace = ExceptionTool.getStackTrace(e);
            assertNotNull(stackTrace);
            assertTrue(stackTrace.contains("java.lang.RuntimeException"));
            assertTrue(stackTrace.contains("test exception"));
        }
    }

    /**
     * 测试ExceptionTool.getStackTrace方法，验证传入null时返回null的场景。
     */
    @Test
    public void testGetStackTrace_null() {
        assertNull(ExceptionTool.getStackTrace(null));
    }

    /**
     * 测试ExceptionTool.getStackTrace方法，验证嵌套异常包含Cause信息的场景。
     */
    @Test
    public void testGetStackTrace_nested() {
        try {
            try {
                throw new IllegalArgumentException("inner cause");
            } catch (Exception inner) {
                throw new RuntimeException("outer", inner);
            }
        } catch (Exception e) {
            String stackTrace = ExceptionTool.getStackTrace(e);
            assertNotNull(stackTrace);
            assertTrue(stackTrace.contains("outer"));
            assertTrue(stackTrace.contains("inner cause"));
            assertTrue(stackTrace.contains("Caused by"));
        }
    }

    /**
     * 测试ExceptionTool.getStackTrace方法，验证堆栈信息中包含行号和文件名。
     */
    @Test
    public void testGetStackTrace_containsLineNumber() {
        try {
            throw new NullPointerException("test");
        } catch (Exception e) {
            String stackTrace = ExceptionTool.getStackTrace(e);
            // Stack trace should contain line number info
            assertTrue(stackTrace.contains("ExceptionToolTest.java"));
        }
    }
}
