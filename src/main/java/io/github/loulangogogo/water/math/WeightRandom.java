package io.github.loulangogogo.water.math;

import io.github.loulangogogo.water.map.MapTool;

import java.util.Map;

/*********************************************************
 ** 随机生成获取对象的权重对象,相同的T对象只能put进去一个
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class WeightRandom<T> {

    private Map<T, Number> data;

    public Map<T, Number> getData() {
        return data;
    }

    /**
     * 构造函数
     *
     * @author :loulan
     */
    public WeightRandom() {
        this.data = MapTool.map();
    }


    /**
     * 添加权重数据
     *
     * @param t      权重对应对象
     * @param weight 权重
     * @return 当前对象
     * @author :loulan
     */
    public WeightRandom<T> put(T t, int weight) {
        data.put(t, weight);
        return this;
    }

    /**
     * 添加权重数据
     *
     * @param t      权重对应对象
     * @param weight 权重
     * @return 当前对象
     * @author :loulan
     */
    public WeightRandom<T> put(T t, double weight) {
        data.put(t, weight);
        return this;
    }

    /**
     * 添加权重数据
     *
     * @param t      权重对应对象
     * @param weight 权重
     * @return 当前对象
     * @author :loulan
     */
    public WeightRandom<T> put(T t, float weight) {
        data.put(t, weight);
        return this;
    }

    /**
     * 对权重数据进行求和
     *
     * @return 权重的和数据
     * @author :loulan
     */
    public double sum() {
        // 如果集合是空的，那么直接返回0
        if (MapTool.isEmpty(data)) {
            return 0;
        }
        // 对所有进行求和
        double sumData = data.values().stream().mapToDouble(o -> o.doubleValue()).sum();
        return sumData;
    }


}
