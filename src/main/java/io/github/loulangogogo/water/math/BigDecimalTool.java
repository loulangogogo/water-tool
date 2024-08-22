package io.github.loulangogogo.water.math;

import java.math.BigDecimal;

/*********************************************************
 ** {@link BigDecimal}类的工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class BigDecimalTool {

    /**
     * 进行{@link BigDecimal}实例化
     *
     * @param data 数据
     * @return BigDecimal对象
     * @author :loulan
     */
    public static BigDecimal valueOf(float data) {
        return new BigDecimal(data);
    }

    /**
     * 进行{@link BigDecimal}实例化
     *
     * @param data 数据
     * @return BigDecimal对象
     * @author :loulan
     */
    public static BigDecimal valueOf(String data) {
        return new BigDecimal(data);
    }

    /**
     * 进行{@link BigDecimal}实例化
     *
     * @param data 数据
     * @return BigDecimal对象
     * @author :loulan
     */
    public static BigDecimal valueOf(double data) {
        return new BigDecimal(data);
    }

    /**
     * 进行{@link BigDecimal}实例化
     *
     * @param data 数据
     * @return BigDecimal对象
     * @author :loulan
     */
    public static BigDecimal valueOf(int data) {
        return new BigDecimal(data);
    }
}
