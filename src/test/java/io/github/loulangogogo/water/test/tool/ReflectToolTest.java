package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.tool.ReflectTool;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.ReflectTool}类的功能。
 */
public class ReflectToolTest {

    // Test class for reflection
    public static class TestBean {
        private String name;
        private int age;
        protected String protectedField;

        public TestBean() {}

        public TestBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private TestBean(String name) {
            this.name = name;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        private String privateMethod(String prefix) {
            return prefix + name;
        }

        public String greet(String greeting) {
            return greeting + ", " + name;
        }
    }

    public static class TestChildBean extends TestBean {
        private String extra;
        public String getExtra() { return extra; }
        public void setExtra(String extra) { this.extra = extra; }
    }

    /**
     * 测试getField方法，验证获取类中指定字段。
     */
    @Test
    public void testGetField() {
        Field field = ReflectTool.getField(TestBean.class, "name");
        assertNotNull(field);
        assertEquals("name", field.getName());
    }

    /**
     * 测试getField方法，验证继承链中查找字段。
     */
    @Test
    public void testGetField_inherited() {
        Field field = ReflectTool.getField(TestChildBean.class, "name");
        assertNotNull(field);
    }

    /**
     * 测试getField方法，验证字段不存在时返回null。
     */
    @Test
    public void testGetField_notFound() {
        Field field = ReflectTool.getField(TestBean.class, "nonexistent");
        assertNull(field);
    }

    /**
     * 测试getFields方法，验证获取类的所有字段。
     */
    @Test
    public void testGetFields() {
        Field[] fields = ReflectTool.getFields(TestBean.class);
        assertNotNull(fields);
        assertTrue(fields.length >= 3); // name, age, protectedField
    }

    /**
     * 测试getFieldValue方法，验证获取对象字段值。
     */
    @Test
    public void testGetFieldValue() {
        TestBean bean = new TestBean("loulan", 18);
        Object value = ReflectTool.getFieldValue(bean, "name");
        assertEquals("loulan", value);
    }

    /**
     * 测试setFieldValue方法，验证设置对象字段值。
     */
    @Test
    public void testSetFieldValue() {
        TestBean bean = new TestBean();
        ReflectTool.setFieldValue(bean, "name", "test");
        assertEquals("test", bean.getName());
    }

    /**
     * 测试setFieldValue方法，验证使用Field对象设置字段值。
     */
    @Test
    public void testSetFieldValue_withField() {
        TestBean bean = new TestBean();
        Field field = ReflectTool.getField(TestBean.class, "age");
        ReflectTool.setFieldValue(bean, field, 25);
        assertEquals(25, bean.getAge());
    }

    /**
     * 测试getConstructor方法，验证获取无参构造器。
     */
    @Test
    public void testGetConstructor() {
        Constructor<TestBean> ctor = ReflectTool.getConstructor(TestBean.class);
        assertNotNull(ctor);
    }

    /**
     * 测试getConstructor方法，验证获取带参构造器。
     */
    @Test
    public void testGetConstructor_withParams() {
        Constructor<TestBean> ctor = ReflectTool.getConstructor(TestBean.class, String.class, int.class);
        assertNotNull(ctor);
    }

    /**
     * 测试getConstructor方法，验证构造器不存在时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testGetConstructor_notFound() {
        ReflectTool.getConstructor(TestBean.class, double.class);
    }

    /**
     * 测试getConstructors方法，验证获取类的所有构造器。
     */
    @Test
    public void testGetConstructors() {
        Constructor<?>[] ctors = ReflectTool.getConstructors(TestBean.class);
        assertNotNull(ctors);
        assertTrue(ctors.length >= 2);
    }

    /**
     * 测试getConstructors方法，验证传入null类返回null。
     */
    @Test
    public void testGetConstructors_nullClass() {
        Constructor<?>[] ctors = ReflectTool.getConstructors(null);
        assertNull(ctors);
    }

    /**
     * 测试getMethod方法，验证获取无参方法。
     */
    @Test
    public void testGetMethod() {
        Method method = ReflectTool.getMethod(TestBean.class, "getName");
        assertNotNull(method);
    }

    /**
     * 测试getMethod方法，验证获取带参方法。
     */
    @Test
    public void testGetMethod_withParams() {
        Method method = ReflectTool.getMethod(TestBean.class, "setName", String.class);
        assertNotNull(method);
    }

    /**
     * 测试getMethods方法，验证获取类的所有方法。
     */
    @Test
    public void testGetMethods() {
        Method[] methods = ReflectTool.getMethods(TestBean.class);
        assertNotNull(methods);
        assertTrue(methods.length > 0);
    }

    /**
     * 测试getMethods方法，验证按名称筛选方法。
     */
    @Test
    public void testGetMethods_byName() {
        Method[] methods = ReflectTool.getMethods(TestBean.class, "getName");
        assertNotNull(methods);
        assertEquals(1, methods.length);
        assertEquals("getName", methods[0].getName());
    }

    /**
     * 测试getMethodOne方法，验证获取指定名称的单个方法。
     */
    @Test
    public void testGetMethodOne() {
        Method method = ReflectTool.getMethodOne(TestBean.class, "greet");
        assertNotNull(method);
        assertEquals("greet", method.getName());
    }

    /**
     * 测试invoke方法，验证使用Method对象调用方法。
     */
    @Test
    public void testInvoke() {
        TestBean bean = new TestBean("loulan", 18);
        Method method = ReflectTool.getMethod(TestBean.class, "greet", String.class);
        String result = ReflectTool.invoke(bean, method, "Hello");
        assertEquals("Hello, loulan", result);
    }

    /**
     * 测试invoke方法，验证按方法名调用方法。
     */
    @Test
    public void testInvoke_byName() {
        TestBean bean = new TestBean("loulan", 18);
        String result = ReflectTool.invoke(bean, "greet", new Object[]{"Hi"}, new Class[]{String.class});
        assertEquals("Hi, loulan", result);
    }

    /**
     * 测试invoke方法，验证调用私有方法。
     */
    @Test
    public void testInvoke_privateMethod() {
        TestBean bean = new TestBean("loulan", 18);
        Method method = ReflectTool.getMethod(TestBean.class, "privateMethod", String.class);
        String result = ReflectTool.invoke(bean, method, "Mr.");
        assertEquals("Mr.loulan", result);
    }

    /**
     * 测试setAccessible方法，验证设置方法可访问性。
     */
    @Test
    public void testSetAccessible() {
        Method method = ReflectTool.getMethod(TestBean.class, "privateMethod", String.class);
        assertFalse(method.isAccessible());
        ReflectTool.setAccessible(method);
        assertTrue(method.isAccessible());
    }

    /**
     * 测试getField方法，验证传入null类返回null。
     */
    @Test
    public void testGetDeclaredField_nullClass() {
        Field field = ReflectTool.getField(null, "name");
        assertNull(field);
    }

    /**
     * 测试getMethod方法，验证传入null类返回null。
     */
    @Test
    public void testGetDeclaredMethod_nullClass() {
        Method method = ReflectTool.getMethod(null, "getName");
        assertNull(method);
    }
}
