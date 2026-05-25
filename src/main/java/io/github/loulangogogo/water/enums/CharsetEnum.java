package io.github.loulangogogo.water.enums;

import io.github.loulangogogo.water.tool.CharsetTool;

import java.nio.charset.Charset;

/*********************************************************
 ** 编码对照枚举类型
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public enum CharsetEnum {

    /**
     * UTF-8 编码（无连字符）
     *
     * @author :loulan
     */
    UTF8("UTF8", CharsetTool.CHARSET_UTF_8),
    
    /**
     * UTF-8 编码（标准格式）
     *
     * @author :loulan
     */
    UTF_8("UTF-8", CharsetTool.CHARSET_UTF_8),
    
    /**
     * GBK 编码
     *
     * @author :loulan
     */
    GBK("GBK", CharsetTool.CHARSET_GBK);

    private String code;
    private Charset charset;

    CharsetEnum(String code, Charset charset) {
        this.code = code;
        this.charset = charset;
    }

    /**
     * 获取编码字符串
     *
     * @return 编码字符串
     * @author :loulan
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码字符串
     *
     * @param code 编码字符串
     * @author :loulan
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取字符集对象
     *
     * @return 字符集对象
     * @author :loulan
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 设置字符集对象
     *
     * @param charset 字符集对象
     * @author :loulan
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
