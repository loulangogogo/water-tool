package io.github.loulangogogo.water.test.math;

import io.github.loulangogogo.water.math.BigDecimalTool;
import io.github.loulangogogo.water.math.MathTool;
import io.github.loulangogogo.water.math.WeightRandom;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * 测试{@link MathTool}和{@link BigDecimalTool}类的功能。
 */
public class MathToolTest {

    // MathTool tests
    /**
     * 测试{@link MathTool#random}方法，验证返回[0,1)范围的随机数。
     */
    @Test
    public void testRandom() {
        double r = MathTool.random();
        assertTrue(r >= 0 && r < 1);
    }

    /**
     * 测试{@link MathTool#random}方法，验证返回[0,指定上限)范围的随机数。
     */
    @Test
    public void testRandom_intRange() {
        double r = MathTool.random(100);
        assertTrue(r >= 0 && r < 100);
    }

    /**
     * 测试{@link MathTool#random}方法，验证上限为0时返回0。
     */
    @Test
    public void testRandom_intRange_zero() {
        double r = MathTool.random(0);
        assertEquals(0.0, r, 0.0);
    }

    /**
     * 测试{@link MathTool#random}方法，验证返回[下限,上限)范围的随机数。
     */
    @Test
    public void testRandom_intRangeBounds() {
        double r = MathTool.random(10, 20);
        assertTrue(r >= 10 && r < 20);
    }

    /**
     * 测试{@link MathTool#random}方法，验证double上限参数的随机数。
     */
    @Test
    public void testRandom_doubleRange() {
        double r = MathTool.random(50.5);
        assertTrue(r >= 0 && r < 50.5);
    }

    /**
     * 测试{@link MathTool#random}方法，验证double范围参数的随机数。
     */
    @Test
    public void testRandom_doubleRangeBounds() {
        double r = MathTool.random(10.5, 20.5);
        assertTrue(r >= 10.5 && r < 20.5);
    }

    /**
     * 测试{@link MathTool#randomInt}方法，验证返回[0,上限)范围的随机整数。
     */
    @Test
    public void testRandomInt() {
        int r = MathTool.randomInt(100);
        assertTrue(r >= 0 && r < 100);
    }

    /**
     * 测试{@link MathTool#randomInt}方法，验证返回[下限,上限)范围的随机整数。
     */
    @Test
    public void testRandomInt_bounds() {
        int r = MathTool.randomInt(10, 20);
        assertTrue(r >= 10 && r < 20);
    }

    /**
     * 测试{@link MathTool#randomWeight}方法，验证按权重随机选择。
     */
    @Test
    public void testRandomWeight() {
        WeightRandom<String> wr = new WeightRandom<>();
        wr.put("A", 10);
        wr.put("B", 20);
        wr.put("C", 30);
        // Run many times to verify it works
        for (int i = 0; i < 100; i++) {
            String result = MathTool.randomWeight(wr);
            assertNotNull(result);
            assertTrue(result.equals("A") || result.equals("B") || result.equals("C"));
        }
    }

    /**
     * 测试{@link MathTool#randomWeight}方法，验证单一选项必定被选中。
     */
    @Test
    public void testRandomWeight_single() {
        WeightRandom<String> wr = new WeightRandom<>();
        wr.put("only", 100);
        String result = MathTool.randomWeight(wr);
        assertEquals("only", result);
    }

    /**
     * 测试{@link MathTool#randomPercent}方法，验证权重总和不足100%时可能返回null。
     */
    @Test
    public void testRandomPercent() {
        WeightRandom<String> wr = new WeightRandom<>();
        wr.put("A", 10.0);
        wr.put("B", 20.0);
        wr.put("C", 30.0);
        // Total = 60%, so 40% chance of null
        for (int i = 0; i < 100; i++) {
            String result = MathTool.randomPercent(wr);
            if (result != null) {
                assertTrue(result.equals("A") || result.equals("B") || result.equals("C"));
            }
        }
    }

    /**
     * 测试{@link MathTool#randomPercent}方法，验证权重总和超过100%时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testRandomPercent_over100() {
        WeightRandom<String> wr = new WeightRandom<>();
        wr.put("A", 60.0);
        wr.put("B", 50.0);
        MathTool.randomPercent(wr);
    }

    /**
     * 测试{@link MathTool#random}方法，验证负数上限抛出异常。
     */
    @Test(expected = Exception.class)
    public void testRandom_negativeRange() {
        MathTool.random(-1);
    }

    // BigDecimalTool tests
    /**
     * 测试{@link BigDecimalTool#valueOf}方法，验证字符串转BigDecimal。
     */
    @Test
    public void testValueOf_string() {
        BigDecimal bd = BigDecimalTool.valueOf("123.45");
        assertEquals(new BigDecimal("123.45"), bd);
    }

    /**
     * 测试{@link BigDecimalTool#valueOf}方法，验证int转BigDecimal。
     */
    @Test
    public void testValueOf_int() {
        BigDecimal bd = BigDecimalTool.valueOf(100);
        assertEquals(new BigDecimal(100), bd);
    }

    /**
     * This test detects the precision bug in BigDecimalTool.valueOf(float/double).
     * new BigDecimal(0.1d) does NOT equal 0.1, it equals 0.10000000000000000555...
     * BigDecimal.valueOf(0.1d) correctly equals 0.1.
     */
    @Test
    public void testValueOf_double_precision() {
        BigDecimal bd = BigDecimalTool.valueOf(0.1);
        // If the implementation uses new BigDecimal(double), this will fail
        // because 0.1 as double has precision issues
        BigDecimal expected = new BigDecimal("0.1");
        assertEquals("BigDecimalTool.valueOf(double) should produce precise value",
                expected, bd);
    }

    /**
     * Same precision test for float.
     */
    @Test
    public void testValueOf_float_precision() {
        BigDecimal bd = BigDecimalTool.valueOf(0.1f);
        BigDecimal expected = new BigDecimal("0.1");
        assertEquals("BigDecimalTool.valueOf(float) should produce precise value",
                expected, bd);
    }
}
