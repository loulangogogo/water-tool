package io.github.loulangogogo.water.tool;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.enums.CharsetEnum;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/*********************************************************
 ** 字符集工具类
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class CharsetTool {
    /**
     * ISO-8859-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * ISO-8859-1
     */
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    /**
     * UTF-8
     */
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    /**
     * GBK
     */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

    /**
     * CP1252
     */
    public static final Charset CHARSET_1252 = Charset.forName("CP1252");

    /**
     * 平台默认的编码方式
     *
     * @return 编码对象
     * @author :loulan
     */
    public static Charset defaultCharset() {
//        return CHARSET_GBK;
        return CHARSET_UTF_8;
    }

    /**
     * 将字符串的编码字符转换为对象的形式
     *
     * @param charset 字符串的额编码方式
     * @return 编码对象
     * @throws IllegalArgumentException 编码字符串不正确
     * @author :loulan
     */
    public static Charset toCharset(String charset) {
        // 将字符串的charset转换为对象的类型
        Optional<CharsetEnum> charsetEnumOpt = ArrayTool.stream(CharsetEnum.values())
                .filter(charsetEnum -> charset.equalsIgnoreCase(charsetEnum.getCode()))
                .findFirst();
        // 判断这个字符串是否存在于枚举类型中
        if (charsetEnumOpt.isPresent()) {
            return charsetEnumOpt.get().getCharset();
        } else {
            throw new IllegalArgumentException("charset or method is not illegal !");
        }
    }

}
