package io.github.loulangogogo.water.bean;

import io.github.loulangogogo.water.collection.CollectionTool;
import io.github.loulangogogo.water.exception.CopyPropertieException;
import io.github.loulangogogo.water.stream.CollectorTool;
import io.github.loulangogogo.water.tool.ObjectTool;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/*********************************************************
 ** 对象工具类
 **
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class BeanTool {
    /**
     * 进行对象属性复制
     *
     * @param source 复制的源对象(可以是bean对象，也可以是map集合)
     * @param target 复制的目标对象（只能bean对象）
     * @author :loulan
     */
    public static void copy(Object source, Object target) {
        try {
            if (ObjectTool.isNull(source) || ObjectTool.isNull(target)) {
                throw new NullPointerException("'source' object or 'target' object is null");
            } else {
                BeanPropertiesTool.copyProperties(source, target);
            }
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * Map到对象的属性复制，{@link BeanTool#copy(Object, Object)}也有map到bean的属性复制功能。
     * <p>
     * map到对象的转化问题比较多，比如日期等类型的转换就会出现问题，所以这里标注过期。
     * 可以使用owner-json里面的【io.gitee.loulan_yxq.owner.json.bean.JsonBeanTool】
     * 工具类进行map到对象的转化。
     *
     * @param source 复制的源对象（只能是{@link Map}集合）
     * @param target 复制的目标对象(只能是bean对象)
     * @author :loulan
     */
    @Deprecated
    public static void copy(Map<String, ? extends Object> source, Object target) {
        mapToBean(source, target);
    }

    /**
     * 将对象属性复制添加到Map集合对象，功能等同于{@link BeanTool#beanToMap(Object, Map)}
     *
     * @param source 复制的源对象（只能是bean对象）
     * @param target 复制的目标对象{@link Map}
     * @author :loulan
     */
    public static void copy(Object source, Map<String, Object> target) {
        beanToMap(source, target);
    }

    /**
     * 将对象属性复制为一个map集合对象，功能等同于{@link BeanTool#copy(Object)}
     *
     * @param source 复制的源对象（只能是bean对象）
     * @return Map集合对象
     * @author :loulan
     */
    public static Map<String, Object> copy(Object source) {
        return beanToMap(source);
    }

    /**
     * 将源对象属性复制到目标类的对象里
     *
     * @param <T>   泛型
     * @param source 复制的源对象（可以是bean对象，也可以是Map集合）
     * @param clzz   目标对象的类（必须是可以实例化的bean的类对象）
     * @return class类文件对应的对象
     * @author :loulan
     */
    public static <T> T copy(Object source, Class<T> clzz) {
        try {
            T t = clzz.newInstance();
            copy(source, t);
            return t;
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * 将Map集合数据复制到指定类的对象里，{@link BeanTool#copy(Object, Class)}也有map到bean的属性复制功能,<br>
     * 功能等同于{@link BeanTool#mapToBean(Map, Class)}
     * <p>
     * map到对象的转化问题比较多，比如日期等类型的转换就会出现问题，所以这里标注过期。
     * 可以使用owner-json里面的【io.gitee.loulan_yxq.owner.json.bean.JsonBeanTool】
     * 工具类进行map到对象的转化。
     *
     * @param <T>   泛型
     * @param source 复制的源对象
     * @param clzz   目标对象的类（必须是可以实例化的bean的类对象）
     * @return class类文件对应的对象
     * @author :loulan
     */
    public static <T> T copy(Map<String, ? extends Object> source, Class<T> clzz) {
        return mapToBean(source, clzz);
    }

    /**
     * 将一个对象(Map对象)的List集合转化为另一个对象(bean)的List集合<br>
     * 还有一个{@link BeanTool#fastCopy(List, Class)}方法也可以实现这个共能，它们的区别是fastCopy更快但是集合对象顺序会乱，copy的满一些但顺序不会乱。
     *
     * @param <T>   泛型
     * @param source list的源对象(可以是bean对象，也可以是Map对象)
     * @param clzz   目标对象的类对象（只能是bean对象的类对象）
     * @return 目标对象的list集合
     * @author :loulan
     */
    public static <T> List<T> copy(List<? extends Object> source, Class<T> clzz) {
        List<T> list = CollectionTool.list();
        collectionConvert(source, list, clzz, false);
        return list;
    }

    /**
     * 将一个对象(Map对象)的Set集合转化为另一个对象(bean)的Set集合<br>
     *
     * @param <T>   泛型
     * @param source set的源对象(可以是bean对象，也可以是Map对象)
     * @param clzz   目标对象的类对象（只能是bean对象的类对象）
     * @return 目标对象的set集合
     * @author :loulan
     */
    public static <T> Set<T> copy(Set<? extends Object> source, Class<T> clzz) {
        Set<T> set = CollectionTool.set();
        collectionConvert(source, set, clzz, true);
        return set;
    }

    /**
     * 将bean对象的List集合转化为Map对象的List集合
     *
     * @param source 源对象的list集合（只能是bean对象的）
     * @return Map对象的list集合
     * @author :loulan
     */
    public static List<Map<String, Object>> copy(List<? extends Object> source) {
        List<Map<String, Object>> list = CollectionTool.list();
        collectionConvert(source, list, false);
        return list;
    }

    /**
     * 将bean对象的Set集合转化为Map对象的Set集合
     *
     * @param source 源对象的set集合（只能是bean对象的）
     * @return map对象的set集合
     * @author :loulan
     */
    public static Set<Map<String, Object>> copy(Set<? extends Object> source) {
        Set<Map<String, Object>> set = CollectionTool.set();
        collectionConvert(source, set, true);
        return set;
    }

    /**
     * bean对象的List集合转化为Map对象的List集合
     *
     * @param source 源对象的list集合（只能是bean对象的）
     * @return {@link Map}对象的list集合
     * @author :loulan
     */
    public static List<Map<String, Object>> fastCopy(List<? extends Object> source) {
        List<Map<String, Object>> list = CollectionTool.list();
        collectionConvert(source, list, true);
        return list;
    }

    /**
     * 将一个对象(Map对象)的List集合转化为另一个对象(bean)的List集合(速度比{@link BeanTool#copy(List, Class)}的快)
     *
     * @param <T>   泛型
     * @param source list的源对象(可以是bean对象，也可以是Map对象)
     * @param clzz   目标对象的类对象(只能bean对象)
     * @return 目标对象的list集合
     * @author :loulan
     */
    public static <T> List<T> fastCopy(List<? extends Object> source, Class<T> clzz) {
        List<T> list = CollectionTool.list();
        collectionConvert(source, list, clzz, true);
        return list;
    }

    /**
     * 将对象的的属性复制到map集合里面
     *
     * @param sourceObj 要进行属性复制的对象（只能是bean对象）
     * @param targetMap 目标map集合
     * @author :loulan
     */
    public static void beanToMap(Object sourceObj, Map<String, Object> targetMap) {
        try {
            BeanPropertiesTool.beanToBean(sourceObj, targetMap);
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * 将对象的的属性复制到map集合里面
     *
     * @param sourceObj 要进行属性复制的对象（只能是bean对象）
     * @return map对象
     * @author :loulan
     */
    public static Map<String, Object> beanToMap(Object sourceObj) {
        try {
            Map<String, Object> target = new HashMap<>();
            beanToMap(sourceObj, target);
            return target;
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * 将map集合数据复制到对象属性里面
     * <p>
     * map到对象的转化问题比较多，比如日期等类型的转换就会出现问题，所以这里标注过期。
     * 可以使用owner-json里面的【io.gitee.loulan_yxq.owner.json.bean.JsonBeanTool】
     * 工具类进行map到对象的转化。
     *
     * @param sourceMap 源map对象数据
     * @param targetObj 要整合数据的对象（只能是bean对象）
     * @author :loulan
     */
    @Deprecated
    public static void mapToBean(Map<String, ? extends Object> sourceMap, Object targetObj) {
        try {
            BeanPropertiesTool.mapToBean(sourceMap, targetObj);
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * 将map集合数据复制到对象属性里面
     * <p>
     * map到对象的转化问题比较多，比如日期等类型的转换就会出现问题，所以这里标注过期。
     * 可以使用owner-json里面的【io.gitee.loulan_yxq.owner.json.bean.JsonBeanTool】
     * 工具类进行map到对象的转化。
     *
     * @param <T>   泛型
     * @param sourceMap 复制的源对象
     * @param clzz      目标对象的类（只能是bean对象的类对象）
     * @return class类文件对应的对象
     * @author :loulan
     */
    @Deprecated
    public static <T> T mapToBean(Map<String, ? extends Object> sourceMap, Class<T> clzz) {
        try {
            T t = clzz.newInstance();
            mapToBean(sourceMap, t);
            return t;
        } catch (Exception ex) {
            throw new CopyPropertieException(ex);
        }
    }

    /**
     * 集合对象的转换（一个集合对象转化为另一个对象的集合）
     *
     * @param <T>   泛型
     * @param datas  源对象集合
     * @param target 目标集合
     * @param clzz   目标对象的类对象
     * @param isFast 是否快速转换（使用的是java8的Stream的并行流）
     * @author :loulan
     */
    private static <T> void collectionConvert(Collection<? extends Object> datas, Collection<T> target, Class<T> clzz, boolean isFast) {
        // 判断集合是否为空，如果为空的话，直接返回一个空集合就可以了
        if (CollectionTool.isEmpty(datas)) {
            return;
        }

        // 看看是否需要快速转化，所谓的快速转化就是stream是否并行，并行会乱了顺序
        Stream<?> datasStream = isFast ? datas.parallelStream() : datas.stream();

        // 遍历转换
        target.addAll(datasStream.map(data -> copy(data, clzz)).collect(CollectorTool.toList()));
    }

    /**
     * 将Object的集合转换为Map集合的集合
     *
     * @param datas  源对象集合
     * @param target Map<String,Object> 的list集合
     * @param isFast 是否快速转换（使用的是java8的Stream的并行流）
     * @author :loulan
     */
    private static void collectionConvert(Collection<? extends Object> datas, Collection<Map<String, Object>> target, boolean isFast) {
        // 判断集合是否为空，如果为空的话，直接返回一个空集合就可以了
        if (CollectionTool.isEmpty(datas)) {
            return;
        }

        // 看看是否需要快速转化，所谓的快速转化就是stream是否并行，并行会乱了顺序
        Stream<?> datasStream = isFast ? datas.parallelStream() : datas.stream();

        // 遍历转换
        target.addAll(datasStream.map(data -> copy(data)).collect(CollectorTool.toList()));
    }
}
