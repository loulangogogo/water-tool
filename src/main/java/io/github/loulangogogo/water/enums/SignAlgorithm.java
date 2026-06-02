package io.github.loulangogogo.water.enums;

/**
 * 签名算法枚举
 * <p>
 * 定义了常用的数字签名算法，包括RSA、DSA和ECDSA等主流算法组合。
 * 这些算法结合了不同的哈希函数（MD2、MD5、SHA1、SHA256、SHA384、SHA512）与签名机制。
 * </p>
 * @see <a href="https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Signature">Java Cryptography Architecture Standard Algorithm Name Documentation</a>
 * @author loulan
 */
public enum SignAlgorithm {
    /**
     * RSA签名算法（不使用哈希函数）
     */
    NONEwithRSA("NONEwithRSA"),

    /**
     * MD2与RSA加密的签名算法
     */
    MD2withRSA("MD2withRSA"),
    /**
     * MD5与RSA加密的签名算法
     */
    MD5withRSA("MD5withRSA"),

    /**
     * SHA-1与RSA的签名算法
     */
    SHA1withRSA("SHA1withRSA"),
    /**
     * SHA-256与RSA的签名算法
     */
    SHA256withRSA("SHA256withRSA"),
    /**
     * SHA-384与RSA的签名算法
     */
    SHA384withRSA("SHA384withRSA"),
    /**
     * SHA-512与RSA的签名算法
     */
    SHA512withRSA("SHA512withRSA"),

    /**
     * DSA签名算法（不使用哈希函数）
     */
    NONEwithDSA("NONEwithDSA"),
    /**
     * DSA与SHA-1的签名算法
     */
    SHA1withDSA("SHA1withDSA"),

    /**
     * ECDSA签名算法（不使用哈希函数）
     */
    NONEwithECDSA("NONEwithECDSA"),
    /**
     * ECDSA与SHA-1的签名算法
     */
    SHA1withECDSA("SHA1withECDSA"),
    /**
     * ECDSA与SHA-256的签名算法
     */
    SHA256withECDSA("SHA256withECDSA"),
    /**
     * ECDSA与SHA-384的签名算法
     */
    SHA384withECDSA("SHA384withECDSA"),
    /**
     * ECDSA与SHA-512的签名算法
     */
    SHA512withECDSA("SHA512withECDSA"),

    /**
     * SHA-256与RSA-PSS的签名算法（需要BC库支持）
     */
    SHA256withRSA_PSS("SHA256WithRSA/PSS"),
    /**
     * SHA-384与RSA-PSS的签名算法（需要BC库支持）
     */
    SHA384withRSA_PSS("SHA384WithRSA/PSS"),
    /**
     * SHA-512与RSA-PSS的签名算法（需要BC库支持）
     */
    SHA512withRSA_PSS("SHA512WithRSA/PSS");

    private final String value;

    /**
     * 构造方法
     *
     * @param value 算法字符串表示，区分大小写
     */
    SignAlgorithm(String value) {
        this.value = value;
    }

    /**
     * 获取算法字符串表示，区分大小写
     *
     * @return 算法字符串表示
     */
    public String getValue() {
        return this.value;
    }
}
