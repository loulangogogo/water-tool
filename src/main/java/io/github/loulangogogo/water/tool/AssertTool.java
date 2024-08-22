package io.github.loulangogogo.water.tool;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.exception.AssertException;
import io.github.loulangogogo.water.map.MapTool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/*********************************************************
 ** 断言工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class AssertTool {

    /**
     * 给定字符串是否不匹配指定的正则表达式
     * @param       str 给定的字符串(不能为null)
     * @param       regex 正则表达式(不能为null)
     * @param       errorMsg 提示信息
     * @author     :loulan
     * */
    public static void notMatch(String str, String regex, String errorMsg) {
        notNull(str, errorMsg);
        notNull(regex, errorMsg);
        if (!str.matches(regex)) {
            // 属于正常

        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 给定字符串是否匹配指定的正则表达式
     * @param       str 给定的字符串(不能为null)
     * @param       regex 正则表达式(不能为null)
     * @param       errorMsg 提示信息
     * @author     :loulan
     * */
    public static void isMatch(String str,String regex,String errorMsg) {
        notNull(str, errorMsg);
        notNull(regex, errorMsg);
        if (str.matches(regex)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        } 
    }

    /**
     * 检查给定的日期是否在指定的开始日期和结束日期之间。
     * 如果给定的日期不在指定范围内，则抛出异常。
     *
     * @param value 要检查的日期
     * @param start 开始日期，标记范围的起始点
     * @param end 结束日期，标记范围的结束点
     * @param message 当检查失败时，抛出异常包含的错误信息
     * @author loulan
     * */
    public static void between(Date value, Date start, Date end, String message) {
        after(value, start, message); // 检查给定的日期是否在开始日期之后
        before(value, end, message); // 检查给定的日期是否在结束日期之前
    }


    /**
     * 检查给定的日期是否在指定的开始日期和结束日期之间。
     * 如果给定的日期不在指定范围内，则抛出异常。
     *
     * @param value 要检查的日期
     * @param start 开始日期，标记范围的起始点
     * @param end 结束日期，标记范围的结束点
     * @param message 当检查失败时，抛出异常包含的错误信息
     * @author loulan
     * */
    public static void between(LocalDateTime value, LocalDateTime start, LocalDateTime end, String message) {
        after(value, start, message);
        before(value, end, message);
    }

    /**
     * 检查给定的日期是否在指定的开始日期和结束日期之间。
     * 如果给定的日期不在指定范围内，则抛出异常。
     *
     * @param value 要检查的日期
     * @param start 开始日期，标记范围的起始点
     * @param end 结束日期，标记范围的结束点
     * @param message 当检查失败时，抛出异常包含的错误信息
     * @author loulan
     * */
    public static void between(LocalDate value, LocalDate start, LocalDate end, String message) {
        after(value, start, message);
        before(value, end, message);
    }

    /**
     * 检查给定的日期是否在指定的开始日期和结束日期之间。
     * 如果给定的日期不在指定范围内，则抛出异常。
     *
     * @param value 要检查的日期
     * @param start 开始日期，标记范围的起始点
     * @param end 结束日期，标记范围的结束点
     * @param message 当检查失败时，抛出异常包含的错误信息
     * @author loulan
     * */
    public static void between(LocalTime value, LocalTime start, LocalTime end, String message) {
        after(value, start, message);
        before(value, end, message);
    }

    /**
     * 判断时间的先后，如果a时间在b时间的后面，则正常，否则抛出异常（任何一个为null也将抛出异常）
     *
     * @param a        要比较的靠后的时间
     * @param b        要比较的考前的时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void after(Date a, Date b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);
        if (a.after(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a时间在b时间的后面，则正常，否则抛出异常（任何一个为null也将抛出异常）
     *
     * @param a        要比较的靠后的时间
     * @param b        要比较的考前的时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void after(LocalDateTime a, LocalDateTime b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);
        if (a.isAfter(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a时间在b时间的后面，则正常，否则抛出异常（任何一个为null也将抛出异常）
     *
     * @param a        要比较的靠后的时间
     * @param b        要比较的考前的时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void after(LocalDate a, LocalDate b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);
        if (a.isAfter(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a时间在b时间的后面，则正常，否则抛出异常（任何一个为null也将抛出异常）
     *
     * @param a        要比较的靠后的时间
     * @param b        要比较的考前的时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void after(LocalTime a, LocalTime b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);
        if (a.isAfter(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a的时间再b之前，则正常，否则抛出异常（任何一个数据为null也将抛出异常）
     *
     * @param a        要比较的先前时间
     * @param b        要比较的靠后时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void before(Date a, Date b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);
        if (a.before(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a的时间再b之前，则正常，否则抛出异常（任何一个数据为null也将抛出异常）
     *
     * @param a        要比较的先前时间
     * @param b        要比较的靠后时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void before(LocalDateTime a, LocalDateTime b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);

        if (a.isBefore(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a的时间再b之前，则正常，否则抛出异常（任何一个数据为null也将抛出异常）
     *
     * @param a        要比较的先前时间
     * @param b        要比较的靠后时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void before(LocalDate a, LocalDate b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);

        if (a.isBefore(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断时间的先后，如果a的时间再b之前，则正常，否则抛出异常（任何一个数据为null也将抛出异常）
     *
     * @param a        要比较的先前时间
     * @param b        要比较的靠后时间
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void before(LocalTime a, LocalTime b, String errorMsg) {
        notNull(a, errorMsg);
        notNull(b, errorMsg);

        if (a.isBefore(b)) {
            // 属于正常
        } else {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断字符串不为空白，具体说明请参考{@link StrTool#isBlank(CharSequence)}
     *
     * @param str      要进行判断的字符串
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void notBlank(CharSequence str, String errorMsg) {
        if (StrTool.isBlank(str)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断字符串为空白，具体说明请参考{@link StrTool#isBlank(CharSequence)}
     *
     * @param str      要进行判断的字符串
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void isBlank(CharSequence str, String errorMsg) {
        if (StrTool.isNotBlank(str)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断数组不为空
     *
     * @param arr      要进行判断的数组对象
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void notEmpty(Object[] arr, String errorMsg) {
        if (ArrayTool.isEmpty(arr)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断{@link Map}集合不为空
     *
     * @param map      要进行判断的集合
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void notEmpty(Map<?, ?> map, String errorMsg) {
        if (MapTool.isEmpty(map)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断{@link Collection}集合不为空
     *
     * @param coll     要进行判断的集合
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void notEmpty(Collection<?> coll, String errorMsg) {
        if (CollTool.isEmpty(coll)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断字符串不为空
     *
     * @param str      要进行判断的字符串
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void notEmpty(String str, String errorMsg) {
        if (StrTool.isEmpty(str)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断数组为空
     *
     * @param arr      要进行判断的数组对象
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void isEmpty(Object[] arr, String errorMsg) {
        if (ArrayTool.isNotEmpty(arr)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断{@link Map}集合为空
     *
     * @param map      要进行判断的集合
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void isEmpty(Map<?, ?> map, String errorMsg) {
        if (MapTool.isNotEmpty(map)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断{@link Collection}集合为空
     *
     * @param coll     要进行判断的集合
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void isEmpty(Collection<?> coll, String errorMsg) {
        if (CollTool.isNotEmpty(coll)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断字符串为空
     *
     * @param str      要进行判断的字符串
     * @param errorMsg 提示信息
     * @author :loulan
     */
    public static void isEmpty(String str, String errorMsg) {
        if (StrTool.isNotEmpty(str)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断表达式为false
     *
     * @param expression 要进行判断的表达式
     * @param errorMsg   提示信息
     * @author :loulan
     */
    public static void isFalse(boolean expression, String errorMsg) {
        if (expression) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断表达式为true.主要是因为名称错误了所以改了{@link AssertTool#isTrue(boolean, String)}
     *
     * @param expression 要进行判断的表达式
     * @param errorMsg   提示信息
     * @author :loulan
     */
    @Deprecated
    public static void isTure(boolean expression, String errorMsg) {
        if (!expression) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断表达式为true
     *
     * @param expression 要进行判断的表达式
     * @param errorMsg   提示信息
     * @author :loulan
     */
    public static void isTrue(boolean expression, String errorMsg) {
        if (!expression) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断对象数据不为null
     *
     * @param obj      要进行判断的对象数据
     * @param errorMsg 当判断不正确的时候抛出的信息
     * @author :loulan
     */
    public static void notNull(Object obj, String errorMsg) {
        if (ObjectTool.isNull(obj)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 判断数据为null
     *
     * @param obj      要进行判断的对象数据
     * @param errorMsg 当判断不正确的时候抛出的信息
     * @author :loulan
     */
    public static void isNull(Object obj, String errorMsg) {
        if (ObjectTool.isNotNull(obj)) {
            throwAssertException(errorMsg);
        }
    }

    /**
     * 抛出断言异常{@link AssertException}的方法，因为里面基本每个函数方法都会抛出异常，所以这里进行了同意的设置
     *
     * @param str 要抛出异常的信息
     * @author :loulan
     */
    private static void throwAssertException(String str) {
        throw new AssertException(str);
    }
}
