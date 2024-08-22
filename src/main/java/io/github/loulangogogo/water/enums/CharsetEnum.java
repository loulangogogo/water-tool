package io.github.loulangogogo.water.enums;

import io.github.loulangogogo.water.tool.CharsetTool;

import java.nio.charset.Charset;

/*********************************************************
 ** 编码对照枚举类型
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public enum CharsetEnum {

    UTF8("UTF8", CharsetTool.CHARSET_UTF_8),
    UTF_8("UTF-8", CharsetTool.CHARSET_UTF_8),
    GBK("GBK", CharsetTool.CHARSET_GBK);

    private String code;
    private Charset charset;

    CharsetEnum(String code, Charset charset) {
        this.code = code;
        this.charset = charset;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
