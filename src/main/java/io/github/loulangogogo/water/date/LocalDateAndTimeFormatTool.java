package io.github.loulangogogo.water.date;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/*********************************************************
 ** 本地日期和时间格式化工具
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class LocalDateAndTimeFormatTool {

    /**
     * 将本地时间转换为指定的格式
     *
     * @param temporalAccessor 本地日期或者时间
     * @param pattern 转换格式
     * @return 格式化后的日期
     * @author :loulan
     */
    public static String format(final TemporalAccessor temporalAccessor, final String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporalAccessor);
    }

    /**
     * 将毫秒值转换为指定的格式
     *
     * @param millis 毫秒值
     * @param pattern 转换格式
     * @return 格式化后的日期
     * @author :loulan
     */
    public static String format(final long millis, final String pattern) {
        return format(LocalDateAndTimeTool.toLocalDateTime(millis), pattern);
    }
}
