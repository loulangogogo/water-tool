package io.github.loulangogogo.water.bean;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.exception.CopyPropertieException;
import io.github.loulangogogo.water.map.MapTool;
import io.github.loulangogogo.water.stream.CollectorTool;
import io.github.loulangogogo.water.tool.AssertTool;
import io.github.loulangogogo.water.tool.ObjectTool;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/*********************************************************
 ** 对象属性工具
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class BeanPropertiesTool {

    /**
     * 将对象属性复制到map集合中
     *
     * @param source 目标对象
     * @param target 目标map集合
     * @throws IntrospectionException 获取属性描述器失败异常
     * @author :loulan
     */
    public static void beanToMap(Object source, Map<String, Object> target) throws IntrospectionException {
        AssertTool.notNull(source, "源对象不能为空");
        AssertTool.notNull(target, "目标对象不能为空");

        Class<?> targetClass = target.getClass();
        // 获取所有属性描述器
        PropertyDescriptor[] sourcePropertyDescriptors = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
        if (ArrayTool.isEmpty(sourcePropertyDescriptors)) {
            // 如果没有属性就直接返回
            return;
        }
        HashMap<String, Object> data = ArrayTool.parallelStream(sourcePropertyDescriptors)
                .filter(sourcePd -> {
                    // 过滤掉其中的class属性
                    if ("class".equals(sourcePd.getName())) return false;
                    // 无法读取的数据过滤掉
                    if (ObjectTool.isNotNull(sourcePd.getReadMethod())) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .collect(CollectorTool.toMap(
                        o -> o.getName(),
                        o -> {
                            try {
                                // 读取其中的数据
                                return o.getReadMethod().invoke(source);
                            } catch (Exception ex) {
                                throw new CopyPropertieException(ex);
                            }
                        },
                        (o1, o2) -> o1,
                        HashMap::new
                ));
        target.putAll(data);
    }

    /**
     * 将map集合中的数据复制到对象中
     *
     * @param source 源对象map集合
     * @param target 目标对象
     * @throws IntrospectionException 获取属性描述器失败异常
     * @author :loulan
     */
    public static void mapToBean(Map<String, ? extends Object> source, Object target) throws IntrospectionException {
        AssertTool.notNull(source, "源对象不能为空");
        AssertTool.notNull(target, "目标对象不能为空");

        if (MapTool.isEmpty(source)) {
            // 如果源集合没有数据直接返回，没得复制
            return;
        }

        PropertyDescriptor[] targetPropertyDescriptors = Introspector.getBeanInfo(target.getClass()).getPropertyDescriptors();
        if (ArrayTool.isEmpty(targetPropertyDescriptors)) {
            // 如果目标对象没有属性直接返回就行了
            return;
        }
        ArrayTool.parallelStream(targetPropertyDescriptors)
                .filter(targetPd -> {
                    if (ObjectTool.isNotNull(targetPd.getWriteMethod())) {
                        return true;
                    } else {
                        return false;
                    }
                }).forEach(targetPd -> {
                    String fieldName = targetPd.getName();
                    try {
                        targetPd.getWriteMethod().invoke(target, source.get(fieldName));
                    } catch (Exception ex) {
                        throw new CopyPropertieException(ex);
                    }
                });
    }

    /**
     * 将对象属性复制到对象中
     *
     * @param source 源对象
     * @param target 目标对象
     * @throws IntrospectionException 获取属性描述器失败异常
     * @author :loulan
     */
    public static void beanToBean(Object source, Object target) throws IntrospectionException {
        AssertTool.notNull(source, "源对象不能为空");
        AssertTool.notNull(target, "目标对象不能为空");

        Class<?> targetClass = target.getClass();
        // 获取所有属性描述器
        PropertyDescriptor[] targetPropertyDescriptors = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
        PropertyDescriptor[] sourcePropertyDescriptors = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();

        if (ArrayTool.isEmpty(sourcePropertyDescriptors) || ArrayTool.isEmpty(targetPropertyDescriptors)) {
            // 如果没有属性就直接返回
            return;
        }

        // 将源对象的属性转化未map集合，方便后面提取
        Map<String, PropertyDescriptor> sourcePropertyDescriptorsMap = ArrayTool.parallelStream(sourcePropertyDescriptors)
                .filter(o -> ObjectTool.isNotNull(o.getReadMethod()))
                .collect(CollectorTool.toMap(
                        PropertyDescriptor::getName,
                        o -> o,
                        (o1, o2) -> o1,
                        HashMap::new
                ));

        ArrayTool.parallelStream(targetPropertyDescriptors)
                .filter(o -> {
                    // 如果目标属性没有写公共写方法那么就不复制
                    if (ObjectTool.isNotNull(o.getWriteMethod())) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .forEach(targetPd -> {
                    PropertyDescriptor sourcePd = sourcePropertyDescriptorsMap.get(targetPd.getName());
                    // 如果指定属性在源对象属性中不存在那么不复制
                    if (ObjectTool.isNull(sourcePd)) {
                        return;
                    }

                    // 比较对象类型是否相同
                    Class<?> sourcePdPropertyType = sourcePd.getPropertyType();
                    Class<?> targetPdPropertyType = targetPd.getPropertyType();
                    if (!sourcePdPropertyType.equals(targetPdPropertyType)) {
                        // 如果对应属性类型不同，那么不复制
                        return;
                    }

                    try {
                        // 进行属性值的复制
                        Object value = sourcePd.getReadMethod().invoke(source);
                        targetPd.getWriteMethod().invoke(target, value);
                    } catch (Exception ex) {
                        throw new CopyPropertieException(ex);
                    }

                });
    }

    /**
     * 属性复制
     *
     * @param source 源对象
     * @param target 目标对象
     * @throws IntrospectionException 获取属性描述器失败异常
     * @author :loulan
     */
    public static void copyProperties(Object source, Object target) throws IntrospectionException {
        AssertTool.notNull(source, "源对象不能为空");
        AssertTool.notNull(target, "目标对象不能为空");
        if (ObjectTool.isInstanceof(source, Map.class) && ObjectTool.isInstanceof(target, Map.class)) {
            throw new CopyPropertieException("暂不支持MapToMap属性复制");
        } else if (ObjectTool.isInstanceof(source, Map.class) && !ObjectTool.isInstanceof(target, Map.class)) {
            // map到对象的属性复制
            mapToBean((Map<String, ? extends Object>) source, target);
        } else if (!ObjectTool.isInstanceof(source, Map.class) && ObjectTool.isInstanceof(target, Map.class)) {
            // 对象到map的属性复制
            beanToMap(source, (Map<String, Object>) target);
        } else {
            // 对象到对象的属性复制
            beanToBean(source, target);
        }
    }

}
