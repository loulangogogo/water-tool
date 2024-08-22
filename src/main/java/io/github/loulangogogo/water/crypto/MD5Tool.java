package io.github.loulangogogo.water.crypto;

import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/*********************************************************
 ** md5加密工具类。
 ** md5hex与md5都是常用的哈希函数，它们的差异在于生成哈希结果的方式不同。
 ** md5生成的哈希结果是一个128位的二进制数，而md5hex生成的哈希结果是一个32位的Hex字符串。
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class MD5Tool {


    /**
     * 计算MD5摘要，并以32个字符的十六进制字符串形式返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要作为十六进制字符串
     * @author :loulan
     */
    public static String md5Hex(String data) {
        if (StrTool.isEmpty(data)) {
            return null;
        }
        return DigestUtils.md5Hex(data);
    }

    /**
     * 计算MD5摘要，并以32个字符的十六进制字符串形式返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要作为十六进制字符串
     * @author :loulan
     */
    public static String md5Hex(byte[] data) {
        if (ObjectTool.isNull(data)) {
            return null;
        }
        return DigestUtils.md5Hex(data);
    }

    /**
     * 计算MD5摘要，并以32个字符的十六进制字符串形式返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要作为十六进制字符串
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static String md5Hex(InputStream data) throws IOException {
        if (ObjectTool.isNull(data)) {
            return null;
        }
        return DigestUtils.md5Hex(data);
    }

    /**
     * 计算MD5摘要，并以128位的二进制数返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要
     * @author :loulan
     */
    public static byte[] md5(String data) {
        if (StrTool.isEmpty(data)) {
            return null;
        }
        return DigestUtils.md5(data);
    }

    /**
     * 计算MD5摘要，并以128位的二进制数返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要
     * @author :loulan
     */
    public static byte[] md5(byte[] data) {
        if (ObjectTool.isNull(data)) {
            return null;
        }
        return DigestUtils.md5(data);
    }

    /**
     * 计算MD5摘要，并以128位的二进制数返回值
     *
     * @param data 要进行加密的数据
     * @return MD5摘要
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static byte[] md5(InputStream data) throws IOException {
        if (ObjectTool.isNull(data)) {
            return null;
        }
        return DigestUtils.md5(data);
    }
}
