package io.github.loulangogogo.water.bean;

import io.github.loulangogogo.water.exception.CopyPropertieException;
import io.github.loulangogogo.water.json.JSON;
import io.github.loulangogogo.water.json.JsonTool;

import java.util.Map;

/*********************************************************
 ** 使用json转化的对象工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class JsonBeanTool {

    /**
     * Map到对象的属性复制，<br>
     * 替换了{@link BeanTool#copy(Map, Object)},因为直接的转换存在类型上的问题比较多。
     * 目前使用的方式效率上可能低些，内部方式采用的是json数据转换。
     *
     * @param source 复制的源对象（只能是{@link Map}集合）
     * @param target 复制的目标对象(只能是bean对象)
     * @author :loulan
     */
    public static void copy(Map<String, ? extends Object> source, Object target) {
        mapToBean(source, target);
    }


    /**
     * 将Map集合数据复制到指定类的对象里，<br>
     * 功能等同于{@link JsonBeanTool#mapToBean(Map, Class)}<br>
     * 替换了{@link BeanTool#copy(Map, Class)},因为直接的转换存在类型上的问题比较多。
     * 目前使用的方式效率上可能低些，内部方式采用的是json数据转换。
     *
     * @param <T>    泛型
     * @param source 复制的源对象
     * @param clzz   目标对象的类（必须是可以实例化的bean的类对象）
     * @return class类文件对应的对象
     * @author :loulan
     */
    public static <T> T copy(Map<String, ? extends Object> source, Class<T> clzz) {
        return mapToBean(source, clzz);
    }

    /**
     * 将map集合数据复制到对象属性里面.<br>
     * 替换了{@link BeanTool#mapToBean(Map, Object)},因为直接的转换存在类型上的问题比较多。
     * 目前使用的方式效率上可能低些，内部方式采用的是json数据转换。
     *
     * @param sourceMap 源map对象数据
     * @param targetObj 要整合数据的对象（只能是bean对象）
     * @author :loulan
     */
    public static void mapToBean(Map<String, ? extends Object> sourceMap, Object targetObj) {
        BeanTool.copy(mapToBean(sourceMap, targetObj.getClass()), targetObj);
    }

    /**
     * 将map集合数据复制到对象属性里面.<br>
     * 替换了{@link BeanTool#mapToBean(Map, Class)},因为直接的转换存在类型上的问题比较多。
     * 目前使用的方式效率上可能低些，内部方式采用的是json数据转换。
     *
     * @param <T>       泛型
     * @param sourceMap 复制的源对象
     * @param clzz      目标对象的类（只能是bean对象的类对象）
     * @return class类文件对应的对象
     * @author :loulan
     */
    public static <T> T mapToBean(Map<String, ? extends Object> sourceMap, Class<T> clzz) {
        try {
            return JSON.parseObj(JsonTool.toJsonBytes(sourceMap), clzz);
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }
}
