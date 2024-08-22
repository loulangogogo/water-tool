package io.github.loulangogogo.water.tool;

import io.github.loulangogogo.water.bean.BeanTool;
import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.interfaces.TreeNode;
import io.github.loulangogogo.water.stream.CollectorTool;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/*********************************************************
 ** 树工具
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class TreeTool {

    /**
     * Description :将List集合转化为List树集合(单线程组合树),有指定的父级值
     *
     * @param objectList 数据
     * @param idName     id属性的字段名称，有的是uuid
     * @param pidName    pid属性的字段名称
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @return 树的Map集合
     * @author :loulan
     */
    public static List<Map<String, Object>> toTree(List<? extends Object> objectList, String idName, String pidName, Object pidValue) {
        AssertTool.notEmpty(idName, "树id属性名称不能为空。");
        AssertTool.notEmpty(pidName, "树pid属性名称不能为空。");
        AssertTool.notNull(pidValue, "树最高级父级的值不能为空。");
        if (CollTool.isNotEmpty(objectList)) {
            List<Map<String, Object>> list = BeanTool.copy(objectList);
            return generateTree(list, idName, pidName, pidValue, false);
        } else {
            return CollTool.list();
        }
    }

    /**
     * Description :将List集合转化为List树集合(单线程组合树),有指定的父级值
     *
     * @param objectList 数据
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @return 树的Map集合
     * @author :loulan
     */
    public static <T extends TreeNode<T>> List<T> toTree(List<T> objectList, Object pidValue) {
        AssertTool.notNull(pidValue, "树最高级父级的值不能为空。");
        if (CollTool.isNotEmpty(objectList)) {
            return generateTree(objectList, pidValue, false);
        } else {
            return CollTool.list();
        }
    }

    /**
     * Description :将List集合转化为List树集合(多线程组合树),有指定的父级值
     *
     * @param objectList 数据
     * @param idName     id属性的字段名称，有的是uuid
     * @param pidName    pid属性的字段名称
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @return 树的Map集合
     * @author :loulan
     */
    public static List<Map<String, Object>> parallelToTree(List<? extends Object> objectList, String idName, String pidName, Object pidValue) {
        AssertTool.notEmpty(idName, "树id属性名称不能为空。");
        AssertTool.notEmpty(pidName, "树pid属性名称不能为空。");
        AssertTool.notNull(pidValue, "树最高级父级的值不能为空。");
        if (CollTool.isNotEmpty(objectList)) {
            List<Map<String, Object>> list = BeanTool.copy(objectList);
            return generateTree(list, idName, pidName, pidValue, true);
        } else {
            return CollTool.list();
        }
    }

    /**
     * Description :将List集合转化为List树集合(多线程组合树),有指定的父级值
     *
     * @param objectList 数据
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @return 树的Map集合
     * @author :loulan
     */
    public static <T extends TreeNode<T>> List<T> parallelToTree(List<T> objectList, Object pidValue) {
        AssertTool.notNull(pidValue, "树最高级父级的值不能为空。");
        if (CollTool.isNotEmpty(objectList)) {
            return generateTree(objectList, pidValue, true);
        } else {
            return CollTool.list();
        }
    }

    /**
     * Description :将List集合转化为List树集合
     *
     * @param objectList 数据
     * @param idName     id属性的字段名称，有的是uuid
     * @param pidName    pid属性的字段名称
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @param isParallel 是否并行执行
     * @return 树的Map集合
     * @author :loulan
     */
    private static List<Map<String, Object>> generateTree(List<Map<String, Object>> objectList, String idName, String pidName, Object pidValue, boolean isParallel) {
        Stream<Map<String, Object>> arrStream = isParallel ? objectList.parallelStream() : objectList.stream();

        List<Map<String, Object>> tree = arrStream.filter(map -> pidValue.equals(map.get(pidName)))
                .peek(map -> {
                    List<Map<String, Object>> childrenTree = generateTree(objectList, idName, pidName, map.get(idName), isParallel);
                    // 如果有子数据就添加到集合map里面，没有就继续循环
                    if (CollTool.isNotEmpty(childrenTree)) {
                        map.put("children", childrenTree);
                    }
                }).collect(CollectorTool.toList());
        return tree;
    }

    /**
     * Description :将List集合转化为List树集合
     *
     * @param objectList 数据
     * @param pidValue   顶级父类的pid的值（一般为-1）
     * @param isParallel 是否并行执行
     * @return 树的Map集合
     * @author :loulan
     */
    private static <T extends TreeNode<T>> List<T> generateTree(List<T> objectList, Object pidValue, boolean isParallel) {
        Stream<T> arrStream = isParallel ? objectList.parallelStream() : objectList.stream();

        return arrStream.filter(o -> pidValue.equals(o.getPid()))
                .peek(o -> {
                    List<T> children = generateTree(objectList, o.getId(), isParallel);
                    if (CollTool.isNotEmpty(children)) {
                        o.setChildren(children);
                    }
                }).collect(CollectorTool.toList());
    }
}
