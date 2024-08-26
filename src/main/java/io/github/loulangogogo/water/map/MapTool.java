package io.github.loulangogogo.water.map;

import io.github.loulangogogo.water.stream.CollectorTool;
import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;

import java.util.HashMap;
import java.util.Map;

/*********************************************************
 ** Map集合工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class MapTool {

    /**
     * 判断Map集合是否为空
     *
     * @param map 集合对象
     * @return 是否为空
     * @author :loulan
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return ObjectTool.isNull(map) || map.isEmpty();
    }

    /**
     * 判断Map集合是否不为空
     *
     * @param map 集合对象
     * @return 是否不为空
     * @author :loulan
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Description :将map集合中的key的字符串中下滑先转换为驼峰式的
     *
     * @param <T> 泛型
     * @param map 需要进行转换的map集合
     * @return 转换完成的map集合
     * @author :loulan
     */
    public static <T> Map<String, T> underlineToCamel(Map<String, T> map) {
        return map.entrySet().stream().collect(CollectorTool.toMap(
                entry -> StrTool.underlineToCamel(entry.getKey()),
                entry -> entry.getValue()
        ));
    }

    /**
     * 创建一个默认的map对象{@link HashMap}
     *
     * @param <R> 泛型
     * @param <T> 泛型
     * @return 默认的map对象
     * @author :loulan
     */
    public static <R, T> Map<R, T> map() {
        return new HashMap<R, T>();
    }

    /**
     * 根据key，value生成一个map{@link HashMap}集合
     *
     * @param <K> 泛型
     * @param <V> 泛型
     * @param k   key
     * @param v   value
     * @return 包含一个值的map集合
     * @author :loulan
     */
    public static <K, V> Map<K, V> of(K k, V v) {
        Map<K, V> map = map();
        map.put(k, v);
        return map;
    }
}
