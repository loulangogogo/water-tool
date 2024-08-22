package io.github.loulangogogo.water.tool;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.collection.CollectionTool;
import io.github.loulangogogo.water.enums.MaskingDataTypeEnum;
import io.github.loulangogogo.water.map.MapTool;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*********************************************************
 ** 字符串工具类
 ** 
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class StrTool {

    /**
     * 判断字符串是否匹配正则表达式
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return 是否匹配
     * @author :loulan
     */
    public static boolean isMatch(String str, String regex) {
        if (ObjectTool.isNull(str) || ObjectTool.isNull(regex)) {
            // 如果有一个是null直接返回false
            return false;
        }

        return str.matches(regex);
    }

    /**
     * 根据分隔符将字符串分割为多个指定类型对象的集合
     *
     * @param str       要进行分割的字符串
     * @param separator 分隔符
     * @param function  分割类型提供的方法
     * @return 分割出来的对象集合
     * @author :loulan
     */
    public static <T> List<T> splitToList(final String str, final String separator, Function<String, T> function) {
        if (StrTool.isEmpty(str)) {
            return CollTool.list();
        }
        String[] strs = str.split(separator);
        List<T> list = ArrayTool.stream(strs).map(function).collect(Collectors.toList());
        return list;
    }

    /**
     * 根据分隔符将字符串分割为多个字符串集合
     *
     * @param str       要进行分割的字符串
     * @param separator 分隔符
     * @return 分割出来的字符串集合
     * @author :loulan
     */
    public static List<String> splitToList(final String str, final String separator) {
        if (StrTool.isEmpty(str)) {
            return CollTool.list();
        }
        String[] strs = str.split(separator);
        List<String> list = ArrayTool.stream(strs).collect(Collectors.toList());
        return list;
    }

    /**
     * 字符串截取，截取所有open和close之间的字符串数组
     *
     * @param str   要被截取的字符串
     * @param open  开始字符串
     * @param close 结束字符串
     * @return 截取的字符串数组
     * @author :loulan
     */
    public static String[] substringsBetween(final String str, final String open, final String close) {
        return StringUtils.substringsBetween(str, open, close);
    }

    /**
     * 字符串截取，截取open和close之间的字符串
     *
     * @param str   要被截取的字符串
     * @param open  开始字符串
     * @param close 结束字符串
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringBetween(final String str, final String open, final String close) {
        return StringUtils.substringBetween(str, open, close);
    }

    /**
     * 字符串截取，截取tag连续出现两次之间的字符串
     *
     * @param str 要被截取的字符串
     * @param tag 截取的字符串标志
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringBetween(final String str, final String tag) {
        return StringUtils.substringBetween(str, tag);
    }

    /**
     * 字符串截取，获取最后一次出现分隔符之前的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符的
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringBeforeLast(final String str, final String separator) {
        return StringUtils.substringBeforeLast(str, separator);
    }

    /**
     * 字符串截取，获取第一次出现分隔符之前的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringBefore(final String str, final String separator) {
        return StringUtils.substringBefore(str, separator);
    }

    /**
     * 字符串截取，获取第一次出现分隔符之前的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符的编码
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringBefore(final String str, final int separator) {
        return StringUtils.substringBefore(str, separator);
    }

    /**
     * 字符串截取，获取最后一次出现分隔符之后的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringAfterLast(final String str, final String separator) {
        return StringUtils.substringAfterLast(str, separator);
    }

    /**
     * 字符串截取，获取最后一次出现分隔符之后的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符的编码
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringAfterLast(final String str, final int separator) {
        return StringUtils.substringAfterLast(str, separator);
    }

    /**
     * 字符串截取，获取第一次出现分隔符之后的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringAfter(final String str, final String separator) {
        return StringUtils.substringAfter(str, separator);
    }

    /**
     * 字符串截取，获取第一次出现分隔符之后的子字符串。
     *
     * @param str       要被截取的字符串
     * @param separator 分隔符的编码
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substringAfter(final String str, final int separator) {
        return StringUtils.substringAfter(str, separator);
    }

    /**
     * 字符串截取
     *
     * @param str   要被截取的字符串
     * @param start 从第几个字符开始
     * @param end   从第几个字符结束
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substring(final String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    /**
     * 字符串截取，截取从start开始之后的字符串
     *
     * @param str   要被截取的字符串
     * @param start 从第几个字符开始
     * @return 截取的字符串
     * @author :loulan
     */
    public static String substring(final String str, int start) {
        return StringUtils.substring(str, start);
    }

    /**
     * 字符串数据脱敏,需要根据指定的数据类型进行脱敏
     *
     * @param data 要进行脱敏的数据
     * @param type 数据类型（枚举值）
     * @return 脱敏之后的数据
     * @author :loulan
     */
    public static String dataMasking(String data, MaskingDataTypeEnum type) {
        if (isEmpty(data)) return data;

        if (type == MaskingDataTypeEnum.PHONE && data.length() > 7) {
            return data.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        } else if (type == MaskingDataTypeEnum.NAME && data.length() > 1) {
            if (data.length() >= 3)
                return data.replaceAll("(.{1}).+(.{1})", "$1*$2");
            else
                return data.replaceAll("(.{1}).*", "$1*");

        } else if (type == MaskingDataTypeEnum.EMAIL) {
            // 如果没有@说明这个邮箱有问题，直接返回就可以了，如果有，那么就进行分解了。
            if (data.contains("@")) {
                return data.replaceAll("^(.{0,3}).*@(.*)$", "$1***@$2");
            }
        } else if (type == MaskingDataTypeEnum.ID_CARD && data.length() > 15) {
            return data.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
        } else if (type == MaskingDataTypeEnum.BANK_CARD && data.length() > 10) {
            return data.replaceAll("(\\w{4})\\w*(\\w{3})", "$1******$2");
        }

        return data;
    }

    /**
     * 判断字符串是否不为空白，具体说明请参看方法{@link StrTool#isBlank(CharSequence)}
     *
     * @param str 要进行判断文本
     * @return 判断字符串是否不为空白的
     * @author :loulan
     */
    public static boolean isNotBlank(CharSequence str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 判断字符串数据石佛空白。
     * 什么是空白呢，就是里面没有任何有效的字符。
     * 空格、全角空格、制表符、换行符，等不可见字符，都算是空白
     * 它和 {@link StrTool#isEmpty(CharSequence)}的区别就是，如果有空格，empty就不算空，但是blank算空
     * StrTool.isBlank(null)      = true
     * StrTool.isBlank("")        = true
     * StrTool.isBlank(" ")       = true
     * StrTool.isBlank("bob")     = false
     * StrTool.isBlank("  bob  ") = false
     * StrTool.isBlank("  \r  ") = false
     * StrTool.isBlank("  \t  ") = false
     * StrTool.isBlank("  \f  ") = false
     * StrTool.isBlank("  \r  ") = false
     *
     * @param str 要进行判断文本
     * @return 判断字符串是否为空白的
     * @author :loulan
     */
    public static boolean isBlank(CharSequence str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 格式化文本，采用{key}进行占位，使用map的value值替换占位
     *
     * @param str 要进行格式化的文本
     * @param map 参数值对的map集合
     * @return 格式化后的文本
     * @author :loulan
     */
    public static String format(CharSequence str, Map<?, ?> map) {
        return format(str, map, true);
    }

    /**
     * 格式化文本，采用{key}进行占位，使用map的value值替换占位
     *
     * @param str        要进行格式化的文本
     * @param map        参数值对的map集合
     * @param ignoreNull 是否忽略{@code null}值。true 忽略，则map对应的值为null的时候不被替换；false 那么null对应的值则被替换为”“
     * @return 格式化后的文本
     * @author :loulan
     */
    public static String format(CharSequence str, Map<?, ?> map, boolean ignoreNull) {
        if (ObjectTool.isNull(str)) {
            // 如果字符串为null的话直接返回null
            return null;
        }
        String replaceStr = str.toString();
        if (MapTool.isEmpty(map)) {
            return replaceStr;
        }

        Object value;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            value = entry.getValue();
            if (ObjectTool.isNull(value) && ignoreNull) {
                continue;
            }
            replaceStr = replaceStr.replace("{" + entry.getKey() + "}", ObjectTool.isNull(value) ? "" : valueOf(value));
        }

        return replaceStr;
    }

    /**
     * 格式化文本，使用{}进行占位，后面参数按顺序进入占位
     *
     * @param str  要进行格式化的文本
     * @param objs 占位参数
     * @return 被格式化后的文本
     * @author :loulan
     */
    public static String format(CharSequence str, Object... objs) {
        if (ObjectTool.isNull(str)) {
            // 如果字符串为null的话直接返回null
            return null;
        }
        String replaceStr = str.toString();
        if (ArrayTool.isEmpty(objs)) {
            // 如果参数为空的话，直接返回字符串
            return replaceStr;
        }

        String[] strs = replaceStr.split("\\{\\}", -1);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            stringBuilder.append(strs[i]);
            // 因为是按照占位符分割的，所以占位符个数要比分割出来的数组长度少一个，超过占位符长度的串后面不再拼接
            if (i < (strs.length - 1)) {
                if (i < objs.length) {
                    // 长度不超过参数长度，则正常拼接
                    stringBuilder.append(valueOf(objs[i]));
                } else {
                    // 超过的恢复原来的占位符
                    stringBuilder.append("{}");
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将数据对象转换为字符串（和toString方法有关系）
     *
     * @param obj 要进行转换的对象
     * @return 转换之后的String对象
     * @author :loulan
     */
    public static String valueOf(Object obj) {
        return String.valueOf(obj);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 要哦继续宁判断的字符串
     * @return 字符串是否不为空
     * @author :loulan
     */
    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要进行判断的字符串对象
     * @return 字符串是否为空
     * @author :loulan
     */
    public static boolean isEmpty(final CharSequence str) {
        StringUtils.isEmpty(str);
        return StringUtils.isEmpty(str);
    }

    /**
     * 从字符串中提取符合正则表达式的串集合。
     *
     * @param str   字符串对象
     * @param regex 正则表达式
     * @return 符合正则表达式的集合
     * @author :loulan
     */
    public static List<String> regexStrs(String str, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<String> list = CollectionTool.list();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 从字符串中提取符合正则表达式捕获组的串集合<br>
     * 捕获组从第1组开始，第0组表示的是整个正则表达式
     *
     * @param str    字符串对象
     * @param regex  正则表达式
     * @param groups 想要提取的捕获组数组，
     * @return 符合正则表达式捕获组（每个组也是list集合）的集合
     * @author :loulan
     */
    public static List<List<String>> regexStrs(String str, String regex, Integer[] groups) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<List<String>> list = CollectionTool.list();
        while (matcher.find()) {
            List<String> tmpList = CollectionTool.list();
            // 如果获取组为空，那么获取整个正则表达示的
            if (ArrayTool.isNotEmpty(groups)) {
                for (int group : groups) {
                    tmpList.add(matcher.group(group));
                }
            } else {
                tmpList.add(matcher.group());
            }
            list.add(tmpList);
        }
        return list;
    }

    /**
     * 从字符串中提取符合正则表达式捕获组的串集合<br>
     * 捕获组从第1组开始，第0组表示的时整个正则表达式
     *
     * @param str   字符串对象
     * @param regex 正则表达式
     * @param group 想要提取的捕获组，
     * @return 符合正则表达式捕获的集合
     * @author :loulan
     */
    public static List<String> regexStrs(String str, String regex, Integer group) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<String> list = CollectionTool.list();
        while (matcher.find()) {
            list.add(matcher.group(group));
        }
        return list;
    }

    /**
     * 从字符串中提取符合正则表达式的串。(相当于将提取出来的集合进行拼接)
     *
     * @param str   字符串对象
     * @param regex 正则表达式
     * @return 符合正则表达式的串拼接
     * @author :loulan
     */
    public static String regexStr(String str, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        StringBuilder strBuilder = new StringBuilder();
        while (matcher.find()) {
            strBuilder.append(matcher.group());
        }
        return strBuilder.toString();
    }

    /**
     * 去掉指定字符之后的字符串
     *
     * @param str 字符串
     * @param cs  要去掉的字符
     * @return 去掉指定字符之后的字符串
     * @author :loulan
     */
    public static String trim(String str, String cs) {
        String[] split = str.split(cs);
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            strBuilder.append(split[i]);
        }
        return strBuilder.toString();
    }

    /**
     * 将驼峰式的字符串转化为下划线的（转换完后默认是小写的）
     *
     * @param str 字符串
     * @return 转换后的下划线字符串，默认小写
     * @author :loulan
     */
    public static String camelToUnderline(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder strBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                strBuilder.append("_");
            }
            strBuilder.append(Character.toLowerCase(c));
        }

        return strBuilder.toString();
    }

    /**
     * 将下划线字符串转换为驼峰式的字符串
     *
     * @param str 下划线字符串
     * @return 转换后的驼峰字符串
     * @author :loulan
     */
    public static String underlineToCamel(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                strBuilder.append(Character.toUpperCase(str.charAt(++i)));
            } else {
                strBuilder.append(Character.toLowerCase(c));
            }
        }
        return strBuilder.toString();
    }

    /**
     * 比较两个字符串是否相等，规则如下
     * <ul>
     *     <li>str1和str2都为{@code null}</li>
     *     <li>忽略大小写使用{@link String#equalsIgnoreCase(String)}判断相等</li>
     *     <li>不忽略大小写使用{@link String#contentEquals(CharSequence)}判断相等</li>
     * </ul>
     *
     * @param str1       要比较的字符串1
     * @param str2       要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 两个字符串是否相同
     * @author :loulan
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (ObjectTool.isNull(str1)) {
            // 如果str1为null的话，那么就看str2是否也是null了
            return str2 == null;
        }

        if (ObjectTool.isNull(str2)) {
            // 如果str1不是null,但是str2是null，也就表明了两个对象不相等
            return false;
        }

        return ignoreCase ? str1.toString().equalsIgnoreCase(str2.toString()) : str1.toString().contentEquals(str2);
    }

    /**
     * 比较两个字符串是否相等(默认不忽略大小写)
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 两个字符串是否相同
     * @author :loulan
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }
}
