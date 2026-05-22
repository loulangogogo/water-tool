package io.github.loulangogogo.water.test.collection;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.collection.CollectionTool;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 测试{@link ArrayTool}、{@link CollTool}、{@link CollectionTool}类的功能。
 */
public class CollectionToolTest {

    // ArrayTool tests
    /**
     * 测试{@link ArrayTool#asList}方法，验证变参数组转列表。
     */
    @Test
    public void testAsList() {
        List<String> list = ArrayTool.asList("a", "b", "c");
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
    }

    /**
     * 测试{@link ArrayTool#asList}方法，验证空参返回null。
     */
    @Test
    public void testAsList_empty() {
        List<String> list = ArrayTool.asList();
        // Returns null for empty
        assertNull(list);
    }

    /**
     * 测试{@link ArrayTool#asList}方法，验证null数组返回null。
     */
    @Test
    public void testAsList_null() {
        String[] arr = null;
        List<String> list = ArrayTool.asList(arr);
        assertNull(list);
    }

    /**
     * 测试{@link ArrayTool#isEmpty}方法，验证null和空数组返回true，非空数组返回false。
     */
    @Test
    public void testIsEmpty() {
        assertTrue(ArrayTool.isEmpty(null));
        assertTrue(ArrayTool.isEmpty(new String[0]));
        assertFalse(ArrayTool.isEmpty(new String[]{"a"}));
    }

    /**
     * 测试{@link ArrayTool#isNotEmpty}方法，验证非空数组返回true，null返回false。
     */
    @Test
    public void testIsNotEmpty() {
        assertTrue(ArrayTool.isNotEmpty(new String[]{"a"}));
        assertFalse(ArrayTool.isNotEmpty(null));
    }

    /**
     * 测试{@link ArrayTool#stream}方法，验证非空数组返回Stream。
     */
    @Test
    public void testStream() {
        assertNotNull(ArrayTool.stream(new String[]{"a", "b"}));
    }

    /**
     * 测试{@link ArrayTool#stream}方法，验证null数组返回null。
     */
    @Test
    public void testStream_null() {
        assertNull(ArrayTool.stream(null));
    }

    /**
     * 测试{@link ArrayTool#parallelStream}方法，验证非空数组返回并行Stream。
     */
    @Test
    public void testParallelStream() {
        assertNotNull(ArrayTool.parallelStream(new String[]{"a", "b"}));
    }

    /**
     * 测试{@link ArrayTool#parallelStream}方法，验证null数组返回null。
     */
    @Test
    public void testParallelStream_null() {
        assertNull(ArrayTool.parallelStream(null));
    }

    /**
     * 测试{@link ArrayTool#add}方法，验证向数组添加单个元素。
     */
    @Test
    public void testAdd() {
        String[] arr = ArrayTool.add(new String[]{"a"}, "b");
        assertEquals(2, arr.length);
    }

    /**
     * 测试{@link ArrayTool#add}方法，验证向null数组添加元素。
     */
    @Test
    public void testAdd_emptyArray() {
        Object[] arr = ArrayTool.add(null, "a");
        assertEquals(1, arr.length);
        assertEquals("a", arr[0]);
    }

    /**
     * 测试{@link ArrayTool#addAll}方法，验证向数组添加多个元素。
     */
    @Test
    public void testAddAll() {
        String[] arr = ArrayTool.addAll(new String[]{"a"}, "b", "c");
        assertEquals(3, arr.length);
    }

    /**
     * 测试{@link ArrayTool#addAll}方法，验证向null数组添加多个元素。
     */
    @Test
    public void testAddAll_emptyArray() {
        String[] arr = ArrayTool.addAll(null, "a", "b");
        assertEquals(2, arr.length);
    }

    /**
     * 测试{@link ArrayTool#of}方法，验证创建指定元素的数组。
     */
    @Test
    public void testOf() {
        String[] arr = ArrayTool.of("a", "b");
        assertEquals(2, arr.length);
    }

    // CollTool tests
    /**
     * 测试{@link CollTool#removeRepeat}方法，验证根据key去重。
     */
    @Test
    public void testRemoveRepeat() {
        class Item {
            String id;
            Item(String id) { this.id = id; }
        }
        List<Item> list = new java.util.ArrayList<>();
        list.add(new Item("1"));
        list.add(new Item("1"));
        list.add(new Item("2"));
        List<Item> result = CollTool.removeRepeat(list, item -> item.id);
        assertEquals(2, result.size());
    }

    /**
     * 测试{@link CollTool#removeRepeat}方法，验证空列表去重返回空列表。
     */
    @Test
    public void testRemoveRepeat_empty() {
        List<String> result = CollTool.removeRepeat(new java.util.ArrayList<>(), s -> s);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        // Should return a new list, not the input reference
        assertNotSame(result, new java.util.ArrayList<>());
    }

    /**
     * 测试{@link CollTool#keepRepeat}方法，验证保留重复元素。
     */
    @Test
    public void testKeepRepeat() {
        List<String> list = new java.util.ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("a");
        List<String> result = CollTool.keepRepeat(list, s -> s);
        assertEquals(2, result.size()); // "a" appears twice more
        assertEquals("a", result.get(0));
        assertEquals("a", result.get(1));
    }

    /**
     * 测试{@link CollTool#keepRepeat}方法，验证空列表无重复元素。
     */
    @Test
    public void testKeepRepeat_empty() {
        List<String> result = CollTool.keepRepeat(new java.util.ArrayList<>(), s -> s);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * 测试{@link CollTool#keepRepeat}方法，验证单元素列表无重复元素。
     */
    @Test
    public void testKeepRepeat_singleElement() {
        List<String> list = new java.util.ArrayList<>();
        list.add("a");
        List<String> result = CollTool.keepRepeat(list, s -> s);
        assertTrue(result.isEmpty());
    }

    /**
     * 测试{@link CollTool#of}方法，验证创建指定元素的列表。
     */
    @Test
    public void testCollToolOf() {
        List<String> list = CollTool.of("a", "b", "c");
        assertEquals(3, list.size());
    }

    /**
     * 测试{@link CollTool#of}方法，验证空参创建空列表。
     */
    @Test
    public void testOf_empty() {
        List<String> list = CollTool.of();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    // Deprecated CollectionTool tests
    /**
     * 测试{@link CollectionTool#isEmpty}方法（已废弃），验证null和空集合返回true。
     */
    @Test
    public void testDeprecatedCollectionToolIsEmpty() {
        assertTrue(CollectionTool.isEmpty(null));
        assertTrue(CollectionTool.isEmpty(new java.util.ArrayList<>()));
        assertFalse(CollectionTool.isEmpty(java.util.Arrays.asList("a")));
    }

    /**
     * 测试{@link CollectionTool#isNotEmpty}方法（已废弃），验证非空集合返回true。
     */
    @Test
    public void testDeprecatedCollectionToolIsNotEmpty() {
        assertTrue(CollectionTool.isNotEmpty(java.util.Arrays.asList("a")));
        assertFalse(CollectionTool.isNotEmpty(new java.util.ArrayList<>()));
    }

    /**
     * 测试{@link CollectionTool#list}方法（已废弃），验证创建空列表。
     */
    @Test
    public void testDeprecatedCollectionToolList() {
        List<String> list = CollectionTool.list();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * 测试{@link CollectionTool#set}方法（已废弃），验证创建空Set。
     */
    @Test
    public void testDeprecatedCollectionToolSet() {
        Set<String> set = CollectionTool.set();
        assertNotNull(set);
        assertTrue(set.isEmpty());
    }

    /**
     * 测试{@link CollectionTool#arrayList}方法（已废弃），验证创建ArrayList实例。
     */
    @Test
    public void testDeprecatedCollectionToolArrayList() {
        List<String> list = CollectionTool.arrayList();
        assertTrue(list instanceof java.util.ArrayList);
    }

    /**
     * 测试{@link CollectionTool#linkedList}方法（已废弃），验证创建LinkedList实例。
     */
    @Test
    public void testDeprecatedCollectionToolLinkedList() {
        List<String> list = CollectionTool.linkedList();
        assertTrue(list instanceof java.util.LinkedList);
    }

    /**
     * 测试{@link CollectionTool#hashSet}方法（已废弃），验证创建HashSet实例。
     */
    @Test
    public void testDeprecatedCollectionToolHashSet() {
        Set<String> set = CollectionTool.hashSet();
        assertTrue(set instanceof java.util.HashSet);
    }

    /**
     * 测试{@link CollectionTool#linkedHashSet}方法（已废弃），验证创建LinkedHashSet实例。
     */
    @Test
    public void testDeprecatedCollectionToolLinkedHashSet() {
        Set<String> set = CollectionTool.linkedHashSet();
        assertTrue(set instanceof java.util.LinkedHashSet);
    }
}
