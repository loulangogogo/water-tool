package io.github.loulangogogo.water.tool;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Objects;

/*********************************************************
 ** 对象通用工具类 ，包括判空、克隆、序列化等操作
 **
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class ObjectTool {

    /**
     * 判断对象是否是指定类的实例
     *
     * @param obj  要进行判断的对象
     * @param clzz 被判断的类型
     * @return 对象是否是类的实例
     * @author :loulan
     */
    public static boolean isInstanceof(Object obj, Class clzz) {
        return clzz.isInstance(obj);
    }

    /**
     * 将对象转化为字符串
     *
     * @param obj 要进行转化的对象
     * @return 转化后的字符串
     * @author :loulan
     */
    public static String toString(Object obj) {
        return Objects.toString(obj);
    }

    /**
     * 比较两个对象是否相等
     *
     * @param obj1 要比较的对象1
     * @param obj2 要比较的对象2
     * @return 两个对象是否相等
     * @author :loulan
     */
    public static boolean equals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否不相等
     *
     * @param obj1 要比较的对象1
     * @param obj2 要比较的对象2
     * @return 两个对象是否不相等
     * @author :loulan
     */
    public static boolean notEquals(Object obj1, Object obj2) {
        return !equals(obj1, obj2);
    }

    /**
     * 判断对象是否为null
     *
     * @param obj 要进行判断的对象
     * @return 对象是否为null
     * @author :loulan
     */
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否不为null
     *
     * @param obj 要进行判断的对象
     * @return 对象是否不为null
     * @author :loulan
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 进行对象克隆(需要继承cloneable，否则返回null)
     *
     * @param <T> 泛型
     * @param obj 要进行克隆的对象
     * @return 克隆出来额对象
     * @author :loulan
     */
    public static <T> T clone(T obj) {
        return ObjectUtils.clone(obj);
    }

    /**
     * 进行对象深度克隆
     *
     * @param <T> 继承{@link Serializable}的泛型
     * @param obj 要进行深度克隆的对象
     * @return 克隆出来额对象
     * @author :loulan
     */
    public static <T extends Serializable> T deepClone(T obj) {
        T clone = (T) SerializationUtils.clone(obj);
        return clone;
    }
}
