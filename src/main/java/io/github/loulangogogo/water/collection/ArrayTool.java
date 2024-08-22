package io.github.loulangogogo.water.collection;

import io.github.loulangogogo.water.stream.CollectorTool;
import io.github.loulangogogo.water.tool.ObjectTool;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*********************************************************
 ** 数组工具类
 ** @author 楼兰
 ** @since JDK-1.8
 *********************************************************/
public class ArrayTool {

    /**
     * 将数组对象转换为List集合
     *
     * @param t 数组对象
     * @return list集合
     * @author :loulan
     */
    public static <T> List<T> asList(T... t) {
        if (isEmpty(t)) {
            return null;
        }

        return stream(t).collect(CollectorTool.toList());
    }

    /**
     * 获取指定数据的数组对象
     *
     * @param obj 指定的数据
     * @return 数组对象
     * @author :loulan
     */
    public static <T> T[] of(T... obj) {
        return obj;
    }

    /**
     * 判断数组是否为空
     *
     * @param array 要进行判断的数组
     * @return 数组是否为空
     * @author :loulan
     */
    public static <T> boolean isEmpty(T[] array) {
        if (ObjectTool.isNull(array) || array.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 要进行判断的数组
     * @return 数组是否不为空
     * @author :loulan
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 获取流对象
     *
     * @param array 目标数组
     * @return 转换完成的流对象
     * @author :loulan
     */
    public static <T> Stream<T> stream(T[] array) {
        if (ObjectTool.isNull(array)) {
            return null;
        }
        return Arrays.stream(array);
    }

    /**
     * 获取并行流对象
     *
     * @param array 目标数组
     * @return 转换完成的并行流对象
     * @author :loulan
     */
    public static <T> Stream<T> parallelStream(T[] array) {
        if (ObjectTool.isNull(array)) {
            return null;
        }
        return stream(array).parallel();
    }

    /**
     * 数组添加元素，合并生成新数组(并不是真的在原来的数组上添加元素，而是生成一个新的数组来包含所有元素)
     *
     * @param arr     要添加元素的素组
     * @param element 要添加的元素
     * @return 添加之后生成的新数组
     * @author :loulan
     */
    public static <T> T[] add(T[] arr, T element) {
        if (isEmpty(arr)) {
            return of(element);
        }
        return ArrayUtils.add(arr, element);
    }

    /**
     * 数组添加元素，合并生成新数组(并不是真的在原来的数组上添加元素，而是生成一个新的数组来包含所有元素)
     *
     * @param arr      要添加元素的素组
     * @param elements 要添加的元素
     * @return 添加之后生成的新数组
     * @author :loulan
     */
    public static <T> T[] addAll(T[] arr, T... elements) {
        if (isEmpty(arr)) {
            return elements;
        }
        return ArrayUtils.addAll(arr, elements);
    }
}
