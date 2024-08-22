package io.github.loulangogogo.water.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/*********************************************************
 ** 安全哈希算法 (SHA) 是美国国家标准与技术研究院 (NIST) 作为美国联邦信息处理标准 (FIPS) 发布的一系列加密哈希函数。 SHA 加密用于多种方法，包括散列数据、证书文件和其他加密目的，包括比特币等加密货币。这些哈希算法有助于保护现代互联网基础设施的骨干。
 ** 您将遇到的最常见的 SHA 函数系列是 SHA-1 和 SHA-2：
 ** SHA-1 是一种 160 位散列函数，由 MD5 算法演化而来。最初，SHA-1 哈希由国家安全局 (NSA) 创建，作为其数字签名算法的一部分。然而，在 SHA-1 中发现了加密漏洞，并且该标准在 2010 年之后不再被批准用于大多数加密用途。由于 SHA-1 不再符合当今的网络安全标准，SHA-2 已成为最常用的 SHA 函数。
 ** SHA-2 是在发现针对 SHA-1 的具有成本效益的暴力攻击后不久开发的。它是两个相似的哈希函数系列，具有不同的块大小，称为 SHA-256 和 SHA-512。 SHA-256 和 SHA-512 之间的主要区别在于字长； SHA-256 使用 32 字节字，而 SHA-512 使用 64 字节字。每个标准还有修改版本，称为 SHA-224、SHA-384、SHA-512/224 和 SHA-512/256。当今最常用的 SHA 函数是 SHA-256，它可以在当前的计算机处理级别提供大量保护。 SHA-2 具有 Merkle–Damgård 结构和 Davies–Meyer 压缩函数。
 ** 除了这些更广为人知的选项之外，您可能还会遇到另外两个 SHA 变体系列，即 SHA-0 和 SHA-3：
 ** SHA-0 是我们现在所说的 160 位或 20 字节长散列函数的基本版本，它于 1993 年以 SHA 算法的名称发布。由于发现了一个重大缺陷，散列函数在发布后不久就停止使用，在进一步发展基础理论之后，SHA-1 得以实现。
 ** SHA-3是目前发展最快的SHA加密技术。它与其他 SHA 的不同之处在于使用了最近开发的哈希函数 Keccak。支持的长度与 SHA-2 相同，但仍存在显着差异。 SHA-3 的不同之处在于它的整体结构，因为它基于范围广泛的随机函数生成，通常支持所有随机排列，从而允许输入或吸收任何数量的数据，如它所称，并输出或输出压缩呈现的数据。这样做提供了一个有效伪随机化的输出，理论上更安全。
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class SHA3Tool {

    /**
     * 计算SHA3-224摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_224(final byte[] data) {
        return DigestUtils.sha3_224(data);
    }

    /**
     * 计算SHA3-224摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static byte[] sha3_224(final InputStream data) throws IOException {
        return DigestUtils.sha3_224(data);
    }

    /**
     * 计算SHA3-224摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_224(final String data) {
        return sha3_224(StringUtils.getBytesUtf8(data));
    }

    /**
     * 计算SHA3-224摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_224Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * 计算SHA3-224摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static String sha3_224Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * 计算SHA3-224摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_224Hex(final String data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * 计算SHA3-256摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_256(final byte[] data) {
        return DigestUtils.sha3_256(data);
    }

    /**
     * 计算SHA3-256摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static byte[] sha3_256(final InputStream data) throws IOException {
        return DigestUtils.sha3_256(data);
    }

    /**
     * 计算SHA3-256摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_256(final String data) {
        return sha3_256(StringUtils.getBytesUtf8(data));
    }

    /**
     * 计算SHA3-256摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_256Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * 计算SHA3-256摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static String sha3_256Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * 计算SHA3-256摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_256Hex(final String data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * 计算SHA3-384摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_384(final byte[] data) {
        return DigestUtils.sha3_384(data);
    }

    /**
     * 计算SHA3-384摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static byte[] sha3_384(final InputStream data) throws IOException {
        return DigestUtils.sha3_384(data);
    }

    /**
     * 计算SHA3-384摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_384(final String data) {
        return sha3_384(StringUtils.getBytesUtf8(data));
    }

    /**
     * 计算SHA3-384摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_384Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * 计算SHA3-384摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static String sha3_384Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * 计算SHA3-384摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_384Hex(final String data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * 计算SHA3-512摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_512(final byte[] data) {
        return DigestUtils.sha3_512(data);
    }

    /**
     * 计算SHA3-512摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static byte[] sha3_512(final InputStream data) throws IOException {
        return DigestUtils.sha3_512(data);
    }

    /**
     * 计算SHA3-512摘要
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static byte[] sha3_512(final String data) {
        return sha3_512(StringUtils.getBytesUtf8(data));
    }

    /**
     * 计算SHA3-512摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_512Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_512(data));
    }

    /**
     * 计算SHA3-512摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @throws IOException 输入流异常
     * @author :loulan
     */
    public static String sha3_512Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_512(data));
    }

    /**
     * 计算SHA3-512摘要,返回16进制的字符串
     *
     * @param data 要进行摘要的数据
     * @return 摘要数据
     * @author :loulan
     */
    public static String sha3_512Hex(final String data) {
        return Hex.encodeHexString(sha3_512(data));
    }

}
