package io.github.loulangogogo.water.test.bean;

import io.github.loulangogogo.water.bean.BeanTool;
import io.github.loulangogogo.water.bean.JsonBeanTool;
import io.github.loulangogogo.water.bean.SerializeTool;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

import static org.junit.Assert.*;

/**
 * 测试{@link BeanTool}、{@link JsonBeanTool}、{@link SerializeTool}类的功能。
 */
public class BeanToolTest {

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;

        public User() {}

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
    }

    public static class Address implements Serializable {
        private static final long serialVersionUID = 1L;
        private String city;
        private String street;

        public Address() {}

        public Address(String city, String street) {
            this.city = city;
            this.street = street;
        }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
    }

    // BeanTool tests
    /**
     * 测试BeanTool.copy方法，验证Bean到Bean的属性拷贝场景。
     */
    @Test
    public void testCopy_beanToBean() {
        User source = new User("loulan", 18);
        User target = new User();
        BeanTool.copy(source, target);
        assertEquals("loulan", target.getName());
        assertEquals(18, target.getAge());
    }

    /**
     * 测试BeanTool.copy方法，验证Bean到Bean并返回新实例的场景。
     */
    @Test
    public void testCopy_beanToBean_returnNew() {
        User source = new User("loulan", 18);
        User target = BeanTool.copy(source, User.class);
        assertEquals("loulan", target.getName());
        assertEquals(18, target.getAge());
    }

    /**
     * 测试BeanTool.copy方法，验证Bean到Map的属性拷贝场景。
     */
    @Test
    public void testCopy_beanToMap() {
        User user = new User("loulan", 18);
        Map<String, Object> map = new HashMap<>();
        BeanTool.copy(user, map);
        assertEquals("loulan", map.get("name"));
        assertEquals(18, map.get("age"));
    }

    /**
     * 测试BeanTool.copy方法，验证Bean到Map并返回新Map实例的场景。
     */
    @Test
    public void testCopy_beanToMap_return() {
        User user = new User("loulan", 18);
        Map<String, Object> map = BeanTool.copy(user);
        assertEquals("loulan", map.get("name"));
        assertEquals(18, map.get("age"));
    }

    /**
     * 测试BeanTool.copy方法，验证Map到Bean的属性拷贝场景。
     */
    @Test
    public void testCopy_mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        User user = BeanTool.copy(map, User.class);
        assertEquals("loulan", user.getName());
        assertEquals(18, user.getAge());
    }

    /**
     * 测试BeanTool.copy方法，验证Map列表到Bean列表的拷贝场景。
     */
    @Test
    public void testCopy_list() {
        List<Map<String, Object>> source = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "a");
        m1.put("age", 1);
        source.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("name", "b");
        m2.put("age", 2);
        source.add(m2);

        List<User> result = BeanTool.copy(source, User.class);
        assertEquals(2, result.size());
        assertEquals("a", result.get(0).getName());
        assertEquals("b", result.get(1).getName());
    }

    /**
     * 测试BeanTool.copy方法，验证Map集合到Bean集合的拷贝场景。
     */
    @Test
    public void testCopy_set() {
        Set<Map<String, Object>> source = new LinkedHashSet<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "a");
        m1.put("age", 1);
        source.add(m1);

        Set<User> result = BeanTool.copy(source, User.class);
        assertEquals(1, result.size());
    }

    /**
     * 测试BeanTool.copy方法，验证Bean列表到Map列表的拷贝场景。
     */
    @Test
    public void testCopy_listBeanToMap() {
        List<User> source = new ArrayList<>();
        source.add(new User("a", 1));
        source.add(new User("b", 2));

        List<Map<String, Object>> result = BeanTool.copy(source);
        assertEquals(2, result.size());
        assertEquals("a", result.get(0).get("name"));
    }

    /**
     * 测试BeanTool.copy方法，验证Bean集合到Map集合的拷贝场景。
     */
    @Test
    public void testCopy_setBeanToMap() {
        Set<User> source = new LinkedHashSet<>();
        source.add(new User("a", 1));

        Set<Map<String, Object>> result = BeanTool.copy(source);
        assertEquals(1, result.size());
    }

    /**
     * 测试BeanTool.fastCopy方法，验证Map列表到Bean列表的快速拷贝场景。
     */
    @Test
    public void testFastCopy_list() {
        List<Map<String, Object>> source = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "a");
        m1.put("age", 1);
        source.add(m1);

        List<User> result = BeanTool.fastCopy(source, User.class);
        assertEquals(1, result.size());
        assertEquals("a", result.get(0).getName());
    }

    /**
     * 测试BeanTool.fastCopy方法，验证Bean列表到Map列表的快速拷贝场景。
     */
    @Test
    public void testFastCopy_listBeanToMap() {
        List<User> source = new ArrayList<>();
        source.add(new User("a", 1));

        List<Map<String, Object>> result = BeanTool.fastCopy(source);
        assertEquals(1, result.size());
        assertEquals("a", result.get(0).get("name"));
    }

    /**
     * 测试BeanTool.beanToMap方法，验证Bean转为Map的场景。
     */
    @Test
    public void testBeanToMap() {
        User user = new User("loulan", 18);
        Map<String, Object> map = BeanTool.beanToMap(user);
        assertEquals("loulan", map.get("name"));
        assertEquals(18, map.get("age"));
    }

    /**
     * 测试BeanTool.beanToMap方法，验证Bean转换到指定Map实例的场景。
     */
    @Test
    public void testBeanToMap_targetMap() {
        User user = new User("loulan", 18);
        Map<String, Object> target = new HashMap<>();
        BeanTool.beanToMap(user, target);
        assertEquals("loulan", target.get("name"));
    }

    /**
     * 测试BeanTool.copy方法，验证null源对象抛出异常的场景。
     */
    @Test(expected = Exception.class)
    public void testCopy_nullSource() {
        BeanTool.copy(null, new User());
    }

    /**
     * 测试BeanTool.copy方法，验证null目标对象抛出异常的场景。
     */
    @Test(expected = Exception.class)
    public void testCopy_nullTarget() {
        BeanTool.copy(new User(), (Object) null);
    }

    // JsonBeanTool tests
    /**
     * 测试JsonBeanTool.copy方法，验证Map到Bean的JSON方式拷贝场景。
     */
    @Test
    public void testJsonBeanTool_copy_mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        User target = new User();
        JsonBeanTool.copy(map, target);
        assertEquals("loulan", target.getName());
        assertEquals(18, target.getAge());
    }

    /**
     * 测试JsonBeanTool.copy方法，验证Map到Bean并返回新实例的JSON方式拷贝场景。
     */
    @Test
    public void testJsonBeanTool_copy_mapToBean_return() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        User user = JsonBeanTool.copy(map, User.class);
        assertEquals("loulan", user.getName());
        assertEquals(18, user.getAge());
    }

    /**
     * 测试JsonBeanTool.mapToBean方法，验证Map到Bean的JSON方式转换场景。
     */
    @Test
    public void testJsonBeanTool_mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        User target = new User();
        JsonBeanTool.mapToBean(map, target);
        assertEquals("loulan", target.getName());
    }

    /**
     * 测试JsonBeanTool.mapToBean方法，验证Map到Bean并返回新实例的JSON方式转换场景。
     */
    @Test
    public void testJsonBeanTool_mapToBean_return() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        User user = JsonBeanTool.mapToBean(map, User.class);
        assertEquals("loulan", user.getName());
    }

    // SerializeTool tests
    /**
     * 测试SerializeTool.clone方法，验证序列化深拷贝的场景。
     */
    @Test
    public void testSerialize_clone() {
        User original = new User("loulan", 18);
        User cloned = SerializeTool.clone(original);
        assertEquals(original.getName(), cloned.getName());
        assertEquals(original.getAge(), cloned.getAge());
        assertNotSame(original, cloned);
    }

    /**
     * 测试SerializeTool.serialize方法，验证对象序列化为字节数组的场景。
     */
    @Test
    public void testSerialize_bytes() {
        User user = new User("loulan", 18);
        byte[] bytes = SerializeTool.serialize(user);
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    /**
     * 测试SerializeTool.serialize和deserialize方法，验证序列化与反序列化的场景。
     */
    @Test
    public void testSerialize_deserialize() {
        User user = new User("loulan", 18);
        byte[] bytes = SerializeTool.serialize(user);
        User deserialized = SerializeTool.deserialize(bytes);
        assertEquals("loulan", deserialized.getName());
        assertEquals(18, deserialized.getAge());
    }

    /**
     * 测试SerializeTool.serialize方法，验证对象序列化到输出流的场景。
     */
    @Test
    public void testSerialize_toOutputStream() throws Exception {
        User user = new User("loulan", 18);
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        SerializeTool.serialize(user, baos);
        assertTrue(baos.size() > 0);
    }

    /**
     * 测试SerializeTool.deserialize方法，验证从输入流反序列化对象的场景。
     */
    @Test
    public void testSerialize_deserializeInputStream() throws Exception {
        User user = new User("loulan", 18);
        byte[] bytes = SerializeTool.serialize(user);
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(bytes);
        User deserialized = SerializeTool.deserialize(bais);
        assertEquals("loulan", deserialized.getName());
    }
}
