package io.github.loulangogogo.water.collection;

import io.github.loulangogogo.water.stream.CollectorTool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/*********************************************************
 ** Collection集合类工具,继承了CollectionTool工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class CollTool extends CollectionTool {

    /**
     * 移除重复内容
     *
     * @param <T>   泛型
     * @param <R>   泛型
     * @param tList 要进行重复数据移除的集合对象
     * @param keyFn 要进行去重的属性关键字函数表达式
     * @return 移除重复数据之后的集合对象
     * @author :loulan
     */
    public static <T, R> List<T> removeRepeat(List<T> tList, Function<? super T, R> keyFn) {

        if (tList == null || tList.isEmpty()) {
            return CollTool.list();
        }

        Set<R> seen = new HashSet<>();

        return tList.stream()
                .filter(t -> seen.add(keyFn.apply(t)))
                .collect(Collectors.toList());
    }

    /**
     * 移除重复内容
     *
     * @param <T>    泛型
     * @param tList  要进行重复数据移除的集合对象
     * @param keyFn  要进行去重的属性关键字函数表达式
     * @param isFast 是否快速移除，采用parallelStream的方式
     * @return 移除重复数据之后的集合对象
     * @author :loulan
     */
    @Deprecated
    public static <T> List<T> removeRepeat(List<T> tList, Function<? super T, ?> keyFn, boolean isFast) {
        if (isEmpty(tList)) {
            return CollTool.list();
        }

        return (isFast ? tList.parallelStream() : tList.stream()).collect(CollectorTool.collectingAndThen(
                CollectorTool.toMap(keyFn, o -> o, (o1, o2) -> o1, LinkedHashMap::new),
                o -> new ArrayList<T>(o.values())
        ));
    }


    /**
     * 留下重复内容
     *
     * @param <T>   泛型
     * @param <R>   泛型
     * @param tList 要进行重复数据留取的集合对象
     * @param keyFn 要进行去重的属性关键字函数表达式
     * @return 留取重复数据之后的集合对象
     * @author :loulan
     */
    public static <T, R> List<T> keepRepeat(List<T> tList, Function<? super T, R> keyFn) {
        if (tList == null || tList.size() <= 1) {
            return CollTool.list();
        }

        Set<R> seen = new HashSet<>();
        return tList.stream()
                .filter(t -> !seen.add(keyFn.apply(t)))
                .collect(Collectors.toList());
    }

    /**
     * 留下重复内容
     *
     * @param <T>    泛型
     * @param <R>    泛型
     * @param tList  要进行重复数据留取的集合对象
     * @param keyFn  要进行去重的属性关键字函数表达式
     * @param isFast 是否快速移除，采用parallelStream的方式
     * @return 留取重复数据之后的集合对象
     * @author :loulan
     */
    @Deprecated
    public static <T, R> List<T> keepRepeat(List<T> tList, Function<? super T, R> keyFn, boolean isFast) {
        if (isEmpty(tList) || tList.size() <= 1) {
            return CollTool.list();
        }
        // 由于parallelStream是多线程操作，所以需要使用线程安全的集合来保证线程安全
        Map<R, Boolean> seen = new ConcurrentHashMap<>();
        return (isFast ? tList.parallelStream() : tList.stream())
                .filter(o -> (seen.putIfAbsent(keyFn.apply(o), Boolean.TRUE) != null))
                .collect(CollectorTool.toList());
    }

    /**
     * 将数组对象转换为List集合
     *
     * @param <T> 泛型
     * @param t   数组对象
     * @return list集合
     * @author :loulan
     */
    public static <T> List<T> of(T... t) {
        if (ArrayTool.isEmpty(t)) {
            return new ArrayList<>();
        }
        return ArrayTool.asList(t);
    }
}
