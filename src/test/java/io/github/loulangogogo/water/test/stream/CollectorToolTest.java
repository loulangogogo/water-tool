package io.github.loulangogogo.water.test.stream;

import io.github.loulangogogo.water.stream.CollectorTool;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * 测试{@link CollectorTool}类的功能。
 */
public class CollectorToolTest {

    /**
     * 测试CollectorTool.toList方法，验证流元素收集为List的场景。
     */
    @Test
    public void testToList() {
        List<String> result = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.toList());
        assertEquals(3, result.size());
        assertTrue(result.contains("a"));
    }

    /**
     * 测试CollectorTool.toSet方法，验证流元素收集为Set并自动去重的场景。
     */
    @Test
    public void testToSet() {
        Set<String> result = Arrays.asList("a", "b", "a").stream()
                .collect(CollectorTool.toSet());
        assertEquals(2, result.size());
    }

    /**
     * 测试CollectorTool.toStr方法，验证流元素无分隔符拼接为字符串的场景。
     */
    @Test
    public void testToStr() {
        String result = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.toStr());
        assertEquals("abc", result);
    }

    /**
     * 测试CollectorTool.toStr方法，验证使用分隔符拼接流元素为字符串的场景。
     */
    @Test
    public void testToStr_delimiter() {
        String result = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.toStr(", "));
        assertEquals("a, b, c", result);
    }

    /**
     * 测试CollectorTool.toStr方法，验证使用分隔符和前缀后缀拼接流元素为字符串的场景。
     */
    @Test
    public void testToStr_delimiterPrefixSuffix() {
        String result = Arrays.asList("a", "b").stream()
                .collect(CollectorTool.toStr(", ", "[", "]"));
        assertEquals("[a, b]", result);
    }

    /**
     * 测试CollectorTool.toMap方法，验证流元素收集为Map的场景。
     */
    @Test
    public void testToMap() {
        Map<String, Integer> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.toMap(s -> s, String::length));
        assertEquals(Integer.valueOf(1), result.get("a"));
        assertEquals(Integer.valueOf(3), result.get("ccc"));
    }

    /**
     * 测试CollectorTool.toMap方法，验证带合并函数处理key冲突时收集为Map的场景。
     */
    @Test
    public void testToMap_withMerge() {
        Map<Integer, String> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.toMap(String::length, s -> s, (s1, s2) -> s1));
        assertEquals(3, result.size());
    }

    /**
     * 测试CollectorTool.toMap方法，验证自定义Map供应器收集为LinkedHashMap的场景。
     */
    @Test
    public void testToMap_withMapSupplier() {
        LinkedHashMap<String, Integer> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.toMap(s -> s, String::length, (v1, v2) -> v1, LinkedHashMap::new));
        assertTrue(result instanceof LinkedHashMap);
    }

    /**
     * 测试CollectorTool.toConcurrentMap方法，验证流元素收集为ConcurrentMap的场景。
     */
    @Test
    public void testToConcurrentMap() {
        ConcurrentMap<String, Integer> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.toConcurrentMap(s -> s, String::length, (v1, v2) -> v1));
        assertEquals(Integer.valueOf(1), result.get("a"));
    }

    /**
     * 测试CollectorTool.groupingBy方法，验证按分类函数分组为Map的场景。
     */
    @Test
    public void testGroupingBy() {
        Map<Integer, List<String>> result = Arrays.asList("a", "bb", "ccc", "dd").stream()
                .collect(CollectorTool.groupingBy(String::length));
        assertEquals(1, result.get(1).size());
        assertEquals(2, result.get(2).size());
        assertEquals(1, result.get(3).size());
    }

    /**
     * 测试CollectorTool.groupingBy方法，验证带下游收集器分组的场景。
     */
    @Test
    public void testGroupingBy_withDownstream() {
        Map<Integer, Set<String>> result = Arrays.asList("a", "bb", "ccc", "dd").stream()
                .collect(CollectorTool.groupingBy(String::length, Collectors.toSet()));
        assertTrue(result.get(2) instanceof Set);
    }

    /**
     * 测试CollectorTool.groupingBy方法，验证自定义Map工厂分组的场景。
     */
    @Test
    public void testGroupingBy_withMapFactory() {
        TreeMap<Integer, List<String>> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.groupingBy(String::length, TreeMap::new, Collectors.toList()));
        assertTrue(result instanceof TreeMap);
    }

    /**
     * 测试CollectorTool.groupingByConcurrent方法，验证并发分组的场景。
     */
    @Test
    public void testGroupingByConcurrent() {
        ConcurrentMap<Integer, List<String>> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.groupingByConcurrent(String::length));
        assertNotNull(result.get(1));
    }

    /**
     * 测试CollectorTool.mapping方法，验证先映射元素再收集的场景。
     */
    @Test
    public void testMapping() {
        List<Integer> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.mapping(String::length, CollectorTool.toList()));
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    /**
     * 测试CollectorTool.maxBy方法，验证获取流中最大元素的场景。
     */
    @Test
    public void testMaxBy() {
        Optional<String> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.maxBy(Comparator.comparingInt(String::length)));
        assertTrue(result.isPresent());
        assertEquals("ccc", result.get());
    }

    /**
     * 测试CollectorTool.minBy方法，验证获取流中最小元素的场景。
     */
    @Test
    public void testMinBy() {
        Optional<String> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.minBy(Comparator.comparingInt(String::length)));
        assertTrue(result.isPresent());
        assertEquals("a", result.get());
    }

    /**
     * 测试CollectorTool.partitioningBy方法，验证按谓词条件将流元素分为true/false两组的场景。
     */
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<String>> result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.partitioningBy(s -> s.length() > 1));
        assertEquals(1, result.get(false).size());
        assertEquals(2, result.get(true).size());
    }

    /**
     * 测试CollectorTool.collectingAndThen方法，验证先收集再对结果进行转换的场景。
     */
    @Test
    public void testCollectingAndThen() {
        String result = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.collectingAndThen(
                        CollectorTool.toList(),
                        list -> String.join("-", list)
                ));
        assertEquals("a-b-c", result);
    }

    /**
     * 测试CollectorTool.reducing方法，验证使用归约函数对流元素进行归约的场景。
     */
    @Test
    public void testReducing() {
        Optional<Integer> result = Arrays.asList(1, 2, 3, 4).stream()
                .collect(CollectorTool.reducing(Integer::sum));
        assertEquals(Integer.valueOf(10), result.get());
    }

    /**
     * 测试CollectorTool.reducing方法，验证使用初始值对流元素进行归约的场景。
     */
    @Test
    public void testReducing_withIdentity() {
        Integer result = Arrays.asList(1, 2, 3).stream()
                .collect(CollectorTool.reducing(100, Integer::sum));
        assertEquals(Integer.valueOf(106), result);
    }

    /**
     * 测试CollectorTool.reducing方法，验证带映射函数对流元素进行归约的场景。
     */
    @Test
    public void testReducing_withMapper() {
        Integer result = Arrays.asList("a", "bb", "ccc").stream()
                .collect(CollectorTool.reducing(0, String::length, Integer::sum));
        assertEquals(Integer.valueOf(6), result);
    }

    /**
     * 测试CollectorTool.summarizingInt方法，验证对流元素进行int统计汇总的场景。
     */
    @Test
    public void testSummarizingInt() {
        IntSummaryStatistics stats = Arrays.asList(1, 2, 3, 4, 5).stream()
                .collect(CollectorTool.summarizingInt(Integer::intValue));
        assertEquals(5, stats.getCount());
        assertEquals(15, stats.getSum());
        assertEquals(3.0, stats.getAverage(), 0.001);
    }

    /**
     * 测试CollectorTool.summarizingDouble方法，验证对流元素进行double统计汇总的场景。
     */
    @Test
    public void testSummarizingDouble() {
        DoubleSummaryStatistics stats = Arrays.asList(1.0, 2.0, 3.0).stream()
                .collect(CollectorTool.summarizingDouble(Double::doubleValue));
        assertEquals(3, stats.getCount());
        assertEquals(6.0, stats.getSum(), 0.001);
    }

    /**
     * 测试CollectorTool.summarizingLong方法，验证对流元素进行long统计汇总的场景。
     */
    @Test
    public void testSummarizingLong() {
        LongSummaryStatistics stats = Arrays.asList(1L, 2L, 3L).stream()
                .collect(CollectorTool.summarizingLong(Long::longValue));
        assertEquals(3, stats.getCount());
        assertEquals(6, stats.getSum());
    }

    /**
     * 测试CollectorTool.summingInt方法，验证对流元素进行int求和的场景。
     */
    @Test
    public void testSummingInt() {
        Integer sum = Arrays.asList(1, 2, 3).stream()
                .collect(CollectorTool.summingInt(Integer::intValue));
        assertEquals(Integer.valueOf(6), sum);
    }

    /**
     * 测试CollectorTool.summingDouble方法，验证对流元素进行double求和的场景。
     */
    @Test
    public void testSummingDouble() {
        Double sum = Arrays.asList(1.0, 2.0, 3.0).stream()
                .collect(CollectorTool.summingDouble(Double::doubleValue));
        assertEquals(Double.valueOf(6.0), sum);
    }

    /**
     * 测试CollectorTool.summingLong方法，验证对流元素进行long求和的场景。
     */
    @Test
    public void testSummingLong() {
        Long sum = Arrays.asList(1L, 2L, 3L).stream()
                .collect(CollectorTool.summingLong(Long::longValue));
        assertEquals(Long.valueOf(6), sum);
    }

    /**
     * 测试CollectorTool.averagingInt方法，验证对流元素进行int求平均值的场景。
     */
    @Test
    public void testAveragingInt() {
        Double avg = Arrays.asList(1, 2, 3).stream()
                .collect(CollectorTool.averagingInt(Integer::intValue));
        assertEquals(2.0, avg, 0.001);
    }

    /**
     * 测试CollectorTool.averagingDouble方法，验证对流元素进行double求平均值的场景。
     */
    @Test
    public void testAveragingDouble() {
        Double avg = Arrays.asList(1.0, 2.0, 3.0).stream()
                .collect(CollectorTool.averagingDouble(Double::doubleValue));
        assertEquals(2.0, avg, 0.001);
    }

    /**
     * 测试CollectorTool.averagingLong方法，验证对流元素进行long求平均值的场景。
     */
    @Test
    public void testAveragingLong() {
        Double avg = Arrays.asList(1L, 2L, 3L).stream()
                .collect(CollectorTool.averagingLong(Long::longValue));
        assertEquals(2.0, avg, 0.001);
    }

    /**
     * 测试CollectorTool.counting方法，验证对流元素进行计数统计的场景。
     */
    @Test
    public void testCounting() {
        Long count = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.counting());
        assertEquals(Long.valueOf(3), count);
    }

    /**
     * 测试CollectorTool.toCollection方法，验证使用自定义集合供应器收集元素的场景。
     */
    @Test
    public void testToCollection() {
        LinkedHashSet<String> result = Arrays.asList("a", "b").stream()
                .collect(CollectorTool.toCollection(LinkedHashSet::new));
        assertTrue(result instanceof LinkedHashSet);
    }

    /**
     * 测试CollectorTool.toArray方法，验证将流元素收集为数组的场景。
     */
    @Test
    public void testToArray() {
        String[] result = Arrays.asList("a", "b", "c").stream()
                .collect(CollectorTool.toArray(size -> new String[size]));
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }
}
