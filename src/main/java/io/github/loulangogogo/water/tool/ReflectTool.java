package io.github.loulangogogo.water.tool;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.exception.ReflectException;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/*********************************************************
 ** 反射工具类
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class ReflectTool {

    /**
     * 根据字段名获取字段对象
     *
     * @param clzz      类对象
     * @param fieldName 字段名称
     * @return 字段对象
     * @author :loulan
     */
    public static Field getField(Class<?> clzz, String fieldName) {
        return getDeclaredField(clzz, fieldName);
    }

    /**
     * 获取某个类的素有字段
     *
     * @param clzz 类对象
     * @return 该类的所有字段
     * @author :loulan
     */
    public static Field[] getFields(Class<?> clzz) {
        return getDeclaredFields(clzz);
    }

    /**
     * 获取对象的属性值
     *
     * @param obj       属性对象
     * @param fieldName 要获取值的字段名称
     * @return 对象的对应属性值
     * @author :loulan
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getField(obj.getClass(), fieldName);
        try {
            Object value = setAccessible(field).get(obj);
            return value;
        } catch (IllegalAccessException e) {
            throw new ReflectException("throw IllegalAccessException.", e);
        }
    }

    /**
     * 设置对象指定字段的属性值
     *
     * @param obj       要被进行设置的对象
     * @param fieldName 指定的字段的名称
     * @param value     值
     * @author :loulan
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        // 对象不能为null
        AssertTool.notNull(obj, "The obj cannot be null!");
        // 字段对象不能为null
        AssertTool.notEmpty(fieldName, "The fieldName cannot be empty!");

        Field field = getField(obj.getClass(), fieldName);
        AssertTool.notNull(field, "cannot find this fieldName!");
        setFieldValue(obj, field, value);
    }

    /**
     * 设置对象指定字段的属性值
     * (如果值的类型和字段的类型不匹配，会尝试进行类型转换，如果转换正常就正常执行，转换失败就抛出异常)
     *
     * @param obj   要被进行设置的对象
     * @param field 指定的字段
     * @param value 值
     * @author :loulan
     */
    public static void setFieldValue(Object obj, Field field, Object value) {
        // 对象不能为null
        AssertTool.notNull(obj, "The obj cannot be null!");
        // 字段对象不能为null
        AssertTool.notNull(field, "The field cannot be null!");

        try {
            // 判断值value的类型和要设置的字段的类型是否匹配，如果不匹配则尝试转换（转换失败抛出异常）
            Class<?> fieldType = field.getType();
            if (ObjectTool.isNull(value) || false == fieldType.isAssignableFrom(value.getClass())) {
                // 数据值为null（int等基本数据类型要转换成指定的数值）,或者数据类型不匹配就尝试转换
                Object convertValue = ConvertUtils.convert(value, fieldType);
                value = ObjectTool.isNotNull(convertValue) ? convertValue : value;
            }

            // 开放访问权限
            setAccessible(field);
            field.set(obj, value);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取类的指定参数的构造方法，并设置可自动访问
     *
     * @param clzz           获取构造方法的类
     * @param parameterTypes 参数类型
     * @return 构造对象
     * @author :loulan
     */
    public static <T> Constructor<T> getConstructor(Class<T> clzz, Class<?>... parameterTypes) {
        if (ObjectTool.isNull(clzz)) {
            return null;
        }

        try {
            Constructor<T> declaredConstructor = clzz.getDeclaredConstructor(parameterTypes);
            setAccessible(declaredConstructor);
            return declaredConstructor;
        } catch (NoSuchMethodException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 获取指定类的构造方法对象列表
     *
     * @param clzz 指定的类
     * @return 构造对象列表
     * @author :loulan
     */
    public static Constructor<?>[] getConstructors(Class<?> clzz) {
        if (ObjectTool.isNull(clzz)) {
            return null;
        }

        return clzz.getDeclaredConstructors();
    }

    /**
     * 获取指定类的指定方法对象
     *
     * @param clzz       指定的类
     * @param methodName 方法名称
     * @param paramTypes 参数类型
     * @return 指定方法的对象
     * @author :loulan
     */
    public static Method getMethod(Class<?> clzz, String methodName, Class<?>... paramTypes) {
        return getDeclaredMethod(clzz, methodName, paramTypes);
    }

    /**
     * 获取指定的类的所有方法的对象
     *
     * @param clzz 指定的类
     * @return 方法对象数组
     * @author :loulan
     */
    public static Method[] getMethods(Class<?> clzz) {
        return getDeclaredMethods(clzz);
    }

    /**
     * 获取指定类的指定名称的所有方法对象
     *
     * @param clzz       指定的类
     * @param methodName 方法名称
     * @return 方法对象数组
     * @author :loulan
     */
    public static Method[] getMethods(Class<?> clzz, String methodName) {
        AssertTool.notNull(clzz, "Class cannot be null!");

        Method[] methods = getMethods(clzz);
        if (ArrayTool.isNotEmpty(methods)) {
            return StrTool.isEmpty(methodName) ? methods : Arrays.stream(methods).filter(method -> StrTool.equals(methodName, method.getName())).toArray(value -> new Method[value]);
        }
        return null;
    }

    /**
     * 获取指定类的指定方法名称的方法对象，只获取一个（如果有多个则随机获取一个），如果找不到对应的方法则返回{@code null}
     *
     * @param clzz       指定的类
     * @param methodName 方法名称
     * @return 指定方法的对象
     * @author :loulan
     */
    public static Method getMethodOne(Class<?> clzz, String methodName) {
        Method[] methods = getMethods(clzz);
        if (ArrayTool.isNotEmpty(methods)) {
            Optional<Method> firstMethodOpt = Arrays.stream(methods).filter(method -> StrTool.equals(methodName, method.getName())).findFirst();
            if (firstMethodOpt.isPresent()) {
                return firstMethodOpt.get();
            }
        }
        return null;
    }

    /**
     * 执行指定对象的指定方法对象
     *
     * @param obj    要被执行方法的对象
     * @param method 要被执行的方法对象
     * @param args   方法对应的参数
     * @return 方法返回值
     * @author :loulan
     */
    public static <T> T invoke(Object obj, Method method, Object... args) {
        try {
            setAccessible(method);
            return (T) method.invoke(obj, args);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 执行指定对象的指定方法对象
     *
     * @param obj        要被执行方法的对象
     * @param methodName 方法对象的名称
     * @param args       方法对应的参数
     * @return 方法返回值
     * @author :loulan
     */
    public static <T> T invoke(Object obj, String methodName, Object[] args, Class<?>[] paramTypes) {
        AssertTool.notNull(obj, "Object cannot be null!");
        AssertTool.notEmpty(methodName, "methodName cannot be empty!");
        Method method = getMethod(obj.getClass(), methodName, paramTypes);
        return invoke(obj, method, args);
    }

    /**
     * 设置方法为可访问（私有方法可以被外部调用）
     *
     * @param <T>              AccessibleObject的子类，比如Class、Method、Field等
     * @param accessibleObject 可设置访问权限的对象，比如Class、Method、Field等
     * @return 被设置可访问的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (ObjectTool.isNotNull(accessibleObject) && false == accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

    /**
     * 获取所有的字段属性。
     *
     * @param clzz 指定的类
     * @return 所有的属性对象数组
     * @author :loulan
     */
    private static Field[] getDeclaredFields(Class<?> clzz) {
        if (ObjectTool.isNull(clzz)) return null;
        Class<?> searchType = clzz;
        Field[] fields = ArrayTool.of();
        do {
            Field[] declaredFields = searchType.getDeclaredFields();
            fields = ArrayTool.addAll(fields, declaredFields);
            searchType = searchType.getSuperclass();
        } while (ObjectTool.isNotNull(searchType));
        return fields;
    }

    /**
     * 获取所有的字段属性。如果不存在就返回null
     *
     * @param clzz      指定的类
     * @param fieldName 属性名称
     * @return 对应的属性对象
     * @author :loulan
     */
    private static Field getDeclaredField(Class<?> clzz, String fieldName) {
        if (ObjectTool.isNull(clzz) || StrTool.isEmpty(fieldName)) return null;
        Class<?> searchType = clzz;
        do {
            try {
                Field declaredField = searchType.getDeclaredField(fieldName);
                if (ObjectTool.isNotNull(declaredField)) {
                    return declaredField;
                }
            } catch (NoSuchFieldException e) {
                // 如果未找到指定字段发生异常，那么继续向父类查找，这里不进行抛出
            }
            searchType = searchType.getSuperclass();
        } while (ObjectTool.isNotNull(searchType));
        return null;
    }

    /**
     * 获取类所有的方法
     *
     * @param clzz 指定的类
     * @return 指定类的所有方法对象数组
     * @author :loulan
     */
    private static Method[] getDeclaredMethods(Class<?> clzz) {
        if (ObjectTool.isNull(clzz)) return null;
        Class<?> searchType = clzz;
        Method[] methods = ArrayTool.of();
        do {
            Method[] declaredMethods = searchType.getDeclaredMethods();
            methods = ArrayTool.addAll(methods, declaredMethods);
            searchType = searchType.getSuperclass();
        } while (ObjectTool.isNotNull(searchType));
        return methods;
    }

    /**
     * 获取指定的方法，如果不存在就返回null。
     *
     * @param clzz       指定的类
     * @param methodName 方法名称
     * @param paramTypes 参数类型
     * @return 指定方法的对象
     * @author :loulan
     */
    private static Method getDeclaredMethod(Class<?> clzz, String methodName, Class<?>... paramTypes) {
        if (ObjectTool.isNull(clzz) || StrTool.isEmpty(methodName)) return null;
        Class<?> searchType = clzz;
        do {
            try {
                Method declaredMethod = searchType.getDeclaredMethod(methodName, paramTypes);
                if (ObjectTool.isNotNull(declaredMethod)) {
                    return declaredMethod;
                }
            } catch (NoSuchMethodException e) {
                // 如果未找到指定字段发生异常，那么继续向父类查找，这里不进行抛出
            }
            searchType = searchType.getSuperclass();
        } while (ObjectTool.isNotNull(searchType));
        // 如果没有找到就返回null
        return null;
    }
}
