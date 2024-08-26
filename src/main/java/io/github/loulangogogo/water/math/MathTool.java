package io.github.loulangogogo.water.math;

import io.github.loulangogogo.water.tool.AssertTool;

import java.util.Map;

/*********************************************************
 ** 数学工具
 **
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class MathTool {

    /**
     * 生成一个[0,1)之间的随机数<br>
     * 注意不包含1
     *
     * @return 生成一个[0, 1)之间的随机数
     * @author :loulan
     */
    public static double random() {
        return Math.random();
    }

    /**
     * 生成一个[0,range)的随机数<br>
     * 注意不包含range
     *
     * @param range 指定的随机范围
     * @return 生成的随机双精度浮点数
     * @author :loulan
     */
    public static double random(int range) {
        AssertTool.isTrue(range >= 0, "范围大小不能小于0");
        return random() * range;
    }

    /**
     * 生成[startRange,endRange)范围内的随机数<br>
     * 注意不包含endRange
     *
     * @param startRange 开始范围
     * @param endRange   结束范围
     * @return 生成指定范围内的随机双精度浮点数
     * @author :loulan
     */
    public static double random(int startRange, int endRange) {
        AssertTool.isTrue(startRange >= 0, "开始范围大小不能小于0");
        AssertTool.isTrue(endRange >= 0, "结束范围大小不能小于0");
        AssertTool.isTrue(endRange >= startRange, "开始范围不能大于结束范围");
        return random(endRange - startRange) + startRange;
    }

    /**
     * 生成一个[0,range)的随机数<br>
     * 注意不包含range
     *
     * @param range 指定的随机范围
     * @return 生成的随机双精度浮点数
     * @author :loulan
     */
    public static double random(double range) {
        AssertTool.isTrue(range >= 0, "范围大小不能小于0");
        return random() * range;
    }

    /**
     * 生成[startRange,endRange)范围内的随机数<br>
     * 注意不包含endRange
     *
     * @param startRange 开始范围
     * @param endRange   结束范围
     * @return 生成指定范围内的随机双精度浮点数
     * @author :loulan
     */
    public static double random(double startRange, double endRange) {
        AssertTool.isTrue(startRange >= 0, "开始范围大小不能小于0");
        AssertTool.isTrue(endRange >= 0, "结束范围大小不能小于0");
        AssertTool.isTrue(endRange >= startRange, "开始范围不能大于结束范围");
        return random(endRange - startRange) + startRange;
    }

    /**
     * 生成一个[0,range)的随机正整数<br>
     * 注意不包含range
     *
     * @param range 指定的随机范围
     * @return 生成的随机正整数
     * @author :loulan
     */
    public static int randomInt(int range) {
        AssertTool.isTrue(range >= 0, "范围大小不能小于0");
        return (int) Math.floor(random() * range);
    }

    /**
     * 生成[startRange,endRange)范围内的随机正整数<br>
     * 注意不包含endRange
     *
     * @param startRange 开始范围
     * @param endRange   结束范围
     * @return 生成指定范围内的随机正整数
     * @author :loulan
     */
    public static int randomInt(int startRange, int endRange) {
        AssertTool.isTrue(startRange >= 0, "开始范围大小不能小于0");
        AssertTool.isTrue(endRange >= 0, "结束范围大小不能小于0");
        AssertTool.isTrue(endRange >= startRange, "开始范围不能大于结束范围");
        return (int) Math.floor(random(endRange - startRange) + startRange);
    }

    /**
     * 根据权重获取对应的数据<br>
     * 把所有的权重求和，然后根据总权重生成范围内的随机数，判断随机数据是哪个权重范围下的。<br>
     * 比如权重为 A=10，B=20，C=30，D=40<br>
     * 那么随机数[0-10)属于A，[10,30)属于B，[30,60)属于C,[60,100)属于D，<br>
     * 所以随机数 random 小于 10 属于A<br>
     * random 小于 (10+20) 属于B<br>
     * random 小于 (10+20+30) 属于C<br>
     * random 小于 (10+20+30+40) 属于D<br>
     *
     * @param <K>          泛型
     * @param weightRandom 权重对象数据
     * @return 根据数据的权重随机获取数据
     * @author :loulan
     */
    public static <K> K randomWeight(WeightRandom<K> weightRandom) {
        AssertTool.notNull(weightRandom, "参数不能为空！");
        double range = weightRandom.sum();
        double random = random(range);

        double initValue = 0;
        for (Map.Entry<K, Number> entry : weightRandom.getData().entrySet()) {
            initValue += entry.getValue().doubleValue();
            if (random < initValue) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 根据权重百分概率获取对应概率数据，概率权重总和不能超过100%。如果概率权重总和小于100，那么剩余概率返回null<br>
     * 把所有的权重求和，然后根据总权重生成范围内的随机数，判断随机数据是哪个权重范围下的。<br>
     * 比如权重为 A=10，B=20，C=30，D=30<br>
     * 那么随机数[0-10)属于A，[10,30)属于B，[30,60)属于C,[60,90)属于D，[90,100)属于null<br>
     * 所以随机数 random 小于 10 属于A<br>
     * random 小于 (10+20) 属于B<br>
     * random 小于 (10+20+30) 属于C<br>
     * random 小于 (10+20+30+30) 属于D<br>
     * 其余的返回null
     *
     * @param <K>          泛型
     * @param weightRandom 权重对象数据
     * @return 根据数据的权重概率随机获取数据
     * @author :loulan
     */
    public static <K> K randomPercent(WeightRandom<K> weightRandom) {
        AssertTool.notNull(weightRandom, "参数不能为空！");
        double range = weightRandom.sum();
        AssertTool.isTrue(range <= 100, "权重百分总和不能大于100");
        double random = random(100);
        double initValue = 0;
        for (Map.Entry<K, Number> entry : weightRandom.getData().entrySet()) {
            initValue += entry.getValue().doubleValue();
            if (random < initValue) {
                return entry.getKey();
            }
        }
        return null;
    }
}
