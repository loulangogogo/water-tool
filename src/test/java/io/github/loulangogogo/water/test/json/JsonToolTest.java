package io.github.loulangogogo.water.test.json;

import io.github.loulangogogo.water.json.JSON;
import io.github.loulangogogo.water.json.JsonMap;
import io.github.loulangogogo.water.json.JsonTool;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 测试{@link JsonTool}、{@link JsonMap}、{@link JSON}类的功能。
 */
public class JsonToolTest {

    public static class Person {
        private String name;
        private int age;
        private LocalDate birthDate;
        private LocalDateTime createTime;

        public Person() {}

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
        public LocalDateTime getCreateTime() { return createTime; }
        public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    }

    // JsonTool tests
    /**
     * 测试toJson方法，验证对象序列化为JSON字符串的场景。
     */
    @Test
    public void testToJson() {
        Person p = new Person("loulan", 18);
        String json = JsonTool.toJson(p);
        assertNotNull(json);
        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"loulan\""));
    }

    /**
     * 测试toJsonString方法，验证字符串值转为JSON格式字符串的场景。
     */
    @Test
    public void testToJsonString() {
        String json = JsonTool.toJsonString("hello");
        assertEquals("\"hello\"", json);
    }

    /**
     * 测试toJsonBytes方法，验证对象序列化为JSON字节数组的场景。
     */
    @Test
    public void testToJsonBytes() {
        byte[] bytes = JsonTool.toJsonBytes("hello");
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    /**
     * 测试toJson方法，验证null值序列化为"null"字符串的场景。
     */
    @Test
    public void testToJson_null() {
        String json = JsonTool.toJson(null);
        assertEquals("null", json);
    }

    /**
     * 测试parseObj方法，验证JSON字符串反序列化为对象的场景。
     */
    @Test
    public void testParseObj() {
        String json = "{\"name\":\"loulan\",\"age\":18}";
        Person p = JsonTool.parseObj(json, Person.class);
        assertEquals("loulan", p.getName());
        assertEquals(18, p.getAge());
    }

    /**
     * 测试parseObj方法，验证JSON字节数组反序列化为对象的场景。
     */
    @Test
    public void testParseObj_bytes() {
        String json = "{\"name\":\"loulan\",\"age\":18}";
        Person p = JsonTool.parseObj(json.getBytes(), Person.class);
        assertEquals("loulan", p.getName());
        assertEquals(18, p.getAge());
    }

    /**
     * 测试parseList方法，验证JSON数组字符串反序列化为对象列表的场景。
     */
    @Test
    public void testParseList() {
        String json = "[{\"name\":\"a\",\"age\":1},{\"name\":\"b\",\"age\":2}]";
        List<Person> list = JsonTool.parseList(json, Person.class);
        assertEquals(2, list.size());
        assertEquals("a", list.get(0).getName());
        assertEquals("b", list.get(1).getName());
    }

    /**
     * 测试parseList方法，验证JSON数组字节反序列化为对象列表的场景。
     */
    @Test
    public void testParseList_bytes() {
        String json = "[{\"name\":\"a\",\"age\":1}]";
        List<Person> list = JsonTool.parseList(json.getBytes(), Person.class);
        assertEquals(1, list.size());
    }

    /**
     * 测试parseMap方法，验证JSON字符串反序列化为Map的场景。
     */
    @Test
    public void testParseMap() {
        String json = "{\"key\":\"value\",\"num\":123}";
        Map<String, Object> map = JsonTool.parseMap(json);
        assertEquals("value", map.get("key"));
        assertEquals(123, map.get("num"));
    }

    /**
     * 测试parseMap方法，验证JSON字节数组反序列化为Map的场景。
     */
    @Test
    public void testParseMap_bytes() {
        String json = "{\"key\":\"value\"}";
        Map<String, Object> map = JsonTool.parseMap(json.getBytes());
        assertEquals("value", map.get("key"));
    }

    /**
     * 测试parseMap方法，验证指定泛型类型反序列化为Map的场景。
     */
    @Test
    public void testParseMap_typed() {
        String json = "{\"count\":42}";
        Map<String, Integer> map = JsonTool.parseMap(json, Integer.class);
        assertEquals(Integer.valueOf(42), map.get("count"));
    }

    /**
     * 测试parseMap方法，验证指定泛型类型从字节数组反序列化为Map的场景。
     */
    @Test
    public void testParseMap_typedBytes() {
        String json = "{\"count\":42}";
        Map<String, Integer> map = JsonTool.parseMap(json.getBytes(), Integer.class);
        assertEquals(Integer.valueOf(42), map.get("count"));
    }

    /**
     * 测试parseJsonMap方法，验证JSON字符串反序列化为JsonMap的场景。
     */
    @Test
    public void testParseJsonMap() {
        String json = "{\"key\":\"value\"}";
        JsonMap jsonMap = JsonTool.parseJsonMap(json);
        assertEquals("value", jsonMap.get("key"));
        assertTrue(jsonMap instanceof JsonMap);
    }

    /**
     * 测试parseJsonMap方法，验证JSON字节数组反序列化为JsonMap的场景。
     */
    @Test
    public void testParseJsonMap_bytes() {
        String json = "{\"key\":\"value\"}";
        JsonMap jsonMap = JsonTool.parseJsonMap(json.getBytes());
        assertEquals("value", jsonMap.get("key"));
    }

    /**
     * 测试parseListJsonMap方法，验证JSON数组字符串反序列化为JsonMap列表的场景。
     */
    @Test
    public void testParseListJsonMap() {
        String json = "[{\"a\":1},{\"b\":2}]";
        List<JsonMap> list = JsonTool.parseListJsonMap(json);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).get("a"));
    }

    /**
     * 测试parseListJsonMap方法，验证JSON数组字节反序列化为JsonMap列表的场景。
     */
    @Test
    public void testParseListJsonMap_bytes() {
        String json = "[{\"a\":1}]";
        List<JsonMap> list = JsonTool.parseListJsonMap(json.getBytes());
        assertEquals(1, list.size());
    }

    /**
     * 测试parse方法，验证使用TypeReference反序列化为对象的场景。
     */
    @Test
    public void testParse_withTypeReference() {
        String json = "{\"name\":\"test\",\"age\":10}";
        Person p = JsonTool.parse(json, new com.fasterxml.jackson.core.type.TypeReference<Person>() {});
        assertEquals("test", p.getName());
    }

    /**
     * 测试parse方法，验证使用TypeReference从字节数组反序列化为对象的场景。
     */
    @Test
    public void testParse_bytesWithTypeReference() {
        String json = "{\"name\":\"test\",\"age\":10}";
        Person p = JsonTool.parse(json.getBytes(), new com.fasterxml.jackson.core.type.TypeReference<Person>() {});
        assertEquals("test", p.getName());
    }

    /**
     * 测试parse方法，验证使用JavaType反序列化为集合的场景。
     */
    @Test
    public void testParse_javaType() {
        String json = "[1,2,3]";
        List<Integer> list = JsonTool.parse(json, JsonTool.getTypeFactory().constructCollectionType(List.class, Integer.class));
        assertEquals(3, list.size());
    }

    /**
     * 测试parse方法，验证使用JavaType从字节数组反序列化为集合的场景。
     */
    @Test
    public void testParse_bytesJavaType() {
        String json = "[1,2,3]";
        List<Integer> list = JsonTool.parse(json.getBytes(), JsonTool.getTypeFactory().constructCollectionType(List.class, Integer.class));
        assertEquals(3, list.size());
    }

    /**
     * 测试getTypeFactory方法，验证获取TypeFactory实例的场景。
     */
    @Test
    public void testGetFactory() {
        assertNotNull(JsonTool.getTypeFactory());
    }

    // JSON (extends JsonTool) tests
    /**
     * 测试JSON类继承自JsonTool的场景，验证静态方法可正常调用。
     */
    @Test
    public void testJsonInherited() {
        String json = JSON.toJson("hello");
        assertEquals("\"hello\"", json);
    }

    // Date serialization tests
    /**
     * 测试LocalDate序列化，验证日期字段正确序列化为"yyyy-MM-dd"格式。
     */
    @Test
    public void testLocalDateSerialization() {
        Person p = new Person();
        p.setName("test");
        p.setBirthDate(LocalDate.of(2024, 1, 15));
        String json = JsonTool.toJson(p);
        assertTrue(json.contains("2024-01-15"));
    }

    /**
     * 测试LocalDate反序列化，验证"yyyy-MM-dd"格式字符串正确反序列化为LocalDate。
     */
    @Test
    public void testLocalDateDeserialization() {
        String json = "{\"name\":\"test\",\"birthDate\":\"2024-01-15\"}";
        Person p = JsonTool.parseObj(json, Person.class);
        assertEquals(LocalDate.of(2024, 1, 15), p.getBirthDate());
    }

    /**
     * 测试LocalDateTime序列化，验证日期时间字段正确序列化为"yyyy-MM-dd HH:mm:ss"格式。
     */
    @Test
    public void testLocalDateTimeSerialization() {
        Person p = new Person();
        p.setName("test");
        p.setCreateTime(LocalDateTime.of(2024, 1, 15, 10, 30, 45));
        String json = JsonTool.toJson(p);
        assertTrue(json.contains("2024-01-15 10:30:45"));
    }

    /**
     * 测试LocalDateTime反序列化，验证"yyyy-MM-dd HH:mm:ss"格式字符串正确反序列化为LocalDateTime。
     */
    @Test
    public void testLocalDateTimeDeserialization() {
        String json = "{\"name\":\"test\",\"createTime\":\"2024-01-15 10:30:45\"}";
        Person p = JsonTool.parseObj(json, Person.class);
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 30, 45), p.getCreateTime());
    }

    /**
     * 测试LocalTime序列化，验证时间字段正确序列化为"HH:mm:ss"格式。
     */
    @Test
    public void testLocalTimeSerialization() {
        class TimeBean {
            private LocalTime time;
            public LocalTime getTime() { return time; }
            public void setTime(LocalTime time) { this.time = time; }
        }
        TimeBean bean = new TimeBean();
        bean.setTime(LocalTime.of(14, 30, 0));
        String json = JsonTool.toJson(bean);
        assertTrue(json.contains("14:30:00"));
    }

    /**
     * 测试JsonMap的instance方法，验证获取空JsonMap实例并添加数据的场景。
     */
    @Test
    public void testJsonMapInstance() {
        JsonMap jsonMap = JsonMap.instance();
        jsonMap.put("key", "value");
        assertEquals("value", jsonMap.get("key"));
    }

    /**
     * 测试JsonMap的instance方法，验证从已有Map创建JsonMap实例的场景。
     */
    @Test
    public void testJsonMapFromMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("key", "value");
        JsonMap jsonMap = JsonMap.instance(map);
        assertEquals("value", jsonMap.get("key"));
    }

    /**
     * 测试JsonMap的getString方法，验证获取字符串和数字转字符串的场景。
     */
    @Test
    public void testJsonMapGetString() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("str", "hello");
        jsonMap.put("num", 123);
        assertEquals("hello", jsonMap.getString("str"));
        assertEquals("123", jsonMap.getString("num"));
    }

    /**
     * 测试JsonMap的getString方法，验证获取不存在的键返回null的场景。
     */
    @Test
    public void testJsonMapGetString_null() {
        JsonMap jsonMap = new JsonMap();
        assertNull(jsonMap.getString("nonexistent"));
    }

    /**
     * 测试JsonMap的getInt方法，验证获取整型值的场景。
     */
    @Test
    public void testJsonMapGetInt() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("num", 42);
        assertEquals(Integer.valueOf(42), jsonMap.getInt("num"));
    }

    /**
     * 测试JsonMap的getInt方法，验证获取不存在的键返回null的场景。
     */
    @Test
    public void testJsonMapGetInt_null() {
        JsonMap jsonMap = new JsonMap();
        assertNull(jsonMap.getInt("nonexistent"));
    }

    /**
     * 测试JsonMap的getLong方法，验证获取长整型值的场景。
     */
    @Test
    public void testJsonMapGetLong() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("num", 100L);
        assertEquals(Long.valueOf(100), jsonMap.getLong("num"));
    }

    /**
     * 测试JsonMap的getFloat方法，验证获取浮点型值的场景。
     */
    @Test
    public void testJsonMapGetFloat() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("num", 3.14f);
        assertEquals(Float.valueOf(3.14f), jsonMap.getFloat("num"), 0.001);
    }

    /**
     * 测试JsonMap的getDouble方法，验证获取双精度浮点型值的场景。
     */
    @Test
    public void testJsonMapGetDouble() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("num", 3.14);
        assertEquals(Double.valueOf(3.14), jsonMap.getDouble("num"), 0.001);
    }

    /**
     * 测试JsonMap的getBigDecimal方法，验证获取BigDecimal值的场景。
     */
    @Test
    public void testJsonMapGetBigDecimal() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("num", "123.45");
        assertEquals(new java.math.BigDecimal("123.45"), jsonMap.getBigDecimal("num"));
    }

    /**
     * 测试JsonMap的getJsonBytes方法，验证获取对象JSON字节数组的场景。
     */
    @Test
    public void testJsonMapGetJsonBytes() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("obj", "test");
        byte[] bytes = jsonMap.getJsonBytes("obj");
        assertNotNull(bytes);
    }

    /**
     * 测试JsonMap的getJsonString方法，验证获取对象JSON字符串的场景。
     */
    @Test
    public void testJsonMapGetJsonString() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("obj", "test");
        String json = jsonMap.getJsonString("obj");
        assertEquals("\"test\"", json);
    }

    /**
     * 测试JsonMap的getJson方法，验证获取对象JSON字符串的场景。
     */
    @Test
    public void testJsonMapGetJson() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("obj", "test");
        String json = jsonMap.getJson("obj");
        assertEquals("\"test\"", json);
    }

    /**
     * 测试JsonMap构造函数，验证无参构造后正常添加数据的场景。
     */
    @Test
    public void testJsonMapConstructor() {
        JsonMap jsonMap = new JsonMap();
        jsonMap.put("key", "value");
        assertEquals("value", jsonMap.get("key"));
    }
}
