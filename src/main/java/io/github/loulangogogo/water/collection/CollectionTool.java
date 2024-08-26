package io.github.loulangogogo.water.collection;

import io.github.loulangogogo.water.tool.ObjectTool;

import java.util.*;

/*********************************************************
 ** Collection集合类工具,请尽量使用CollTool
 ** @author 楼兰
 ** @since 8
 *********************************************************/
@Deprecated
public class CollectionTool {

    /**
     * 判断集合是否为空（为null或者没有数据）
     *
     * @param datas 集合对象
     * @return 是否为空
     * @author :loulan
     */
    public static boolean isEmpty(Collection<? extends Object> datas) {
        if (ObjectTool.isNull(datas) || datas.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断集合是否不为空（里面有数据）
     *
     * @param datas 集合对象
     * @return 是否不为空
     * @author :loulan
     */
    public static boolean isNotEmpty(Collection<? extends Object> datas) {
        return !isEmpty(datas);
    }

    /**
     * 获取一个默认的List集合
     *
     * @param <T> 泛型
     * @return 返回一个默认的list集合
     * @author :loulan
     */
    public static <T> List<T> list() {
        return arrayList();
    }

    /**
     * 获取一个默认的set集合
     *
     * @param <T> 泛型
     * @return 返回一个默认的set集合
     * @author :loulan
     */
    public static <T> Set<T> set() {
        return hashSet();
    }

    /**
     * 返回一个arrayList集合
     *
     * @param <T> 泛型
     * @return 返回一个arrayList集合
     * @author :loulan
     */
    public static <T> List<T> arrayList() {
        return new ArrayList<T>();
    }

    /**
     * 返回一个LinkedList的集合
     *
     * @param <T> 泛型
     * @return 返回一个LinkedList的集合
     * @author :loulan
     */
    public static <T> List<T> linkedList() {
        return new LinkedList<T>();
    }

    /**
     * 返回一个HashSet集合
     *
     * @param <T> 泛型
     * @return 返回一个HashSet集合
     * @author :loulan
     */
    public static <T> Set<T> hashSet() {
        return new HashSet<T>();
    }

    /**
     * 返回一个LinkedHashSet集合
     *
     * @param <T> 泛型
     * @return 返回一个LinkedHashSet集合
     * @author :loulan
     */
    public static <T> Set<T> linkedHashSet() {
        return new LinkedHashSet<T>();
    }
}
