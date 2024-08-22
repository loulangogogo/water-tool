package io.github.loulangogogo.water.crypto;

import java.util.Base64;

/*********************************************************
 ** base64的编码和解码工具类
 ** @author loulan
 ** @since 8
 *********************************************************/
public class Base64Tool {
    /**
     * 将字节数组编码字符串
     *
     * @param data 字节数组数据
     * @return 字符串
     * @author :loulan
     */
    public static String toEncode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 将字符串解码字节数组
     *
     * @param data 字符串数据
     * @return 字节数组
     * @author :loulan
     */
    public static byte[] toDecode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
