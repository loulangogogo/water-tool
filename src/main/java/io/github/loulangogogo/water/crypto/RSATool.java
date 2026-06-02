package io.github.loulangogogo.water.crypto;

import io.github.loulangogogo.water.enums.SignAlgorithm;
import io.github.loulangogogo.water.tool.AssertTool;
import io.github.loulangogogo.water.tool.CharsetTool;
import io.github.loulangogogo.water.tool.StrTool;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/*********************************************************
 ** RSA的加密工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class RSATool {

    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    // 128 对应 1024，256对应2048
    private static final int KEYSIZE = 2048;

    // 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
    private static final String KEY_ALGORITHM = "RSA";

    // RSA最大加密明文大小（Cipher限制加密字节大小为246，所以这里设置最大加密明文大小，之后进行分段加密） 前提KEYSZIE=2048
    private static final int MAX_ENCRYPT_BLOCK = 245;
    // RSA最大解密密文大小（Cipher限制解密的密文字节长度不能超过256，所以这里设置最大解密密文大小，之后进行分段解密） 前提KEYSZIE=2048
    private static final int MAX_DECRYPT_BLOCK = 256;

    // 签名和验证签名使用的算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 从签名算法字符串中提取"with"后面的算法名称
     * <p>
     * 例如：从"SHA256withRSA"中提取"RSA"，从"MD5withRSA"中提取"RSA"
     * </p>
     *
     * @param algorithm 完整的签名算法字符串，必须包含"with"关键字
     * @return "with"后面的算法名称
     * @throws IllegalArgumentException 当algorithm为空、不包含"with"或后缀算法不存在时抛出异常
     */
    private static String getAlgorithmAfterWith(String algorithm) {
        AssertTool.notNull(algorithm, "algorithm 不能为空");

        int indexOfWith = StrTool.lastIndexOfIgnoreCase(algorithm, "with");
        AssertTool.isTrue(indexOfWith > 0, "algorithm 必须包含with");
        String algorithmAfterWith = StrTool.substring(algorithm, indexOfWith + "with".length());
        AssertTool.notBlank(algorithmAfterWith, "algorithm 后缀算法不存在");
        return algorithmAfterWith;
    }


    /**
     * 获取钥匙对（公钥和私钥的钥匙对）
     *
     * @param saltValue 盐值
     * @return 钥匙对
     * @throws NoSuchAlgorithmException 未找到算法异常
     * @author :loulan
     */
    public static KeyPair getKeyPair(String saltValue) throws NoSuchAlgorithmException {
        return getKeyPair(saltValue, SignAlgorithm.SHA256withRSA);
    }

    /**
     * 根据指定的签名算法生成密钥对（公钥和私钥）
     * <p>
     * 从签名算法中提取加密算法名称（如从"SHA256withRSA"提取"RSA"），
     * 结合盐值初始化安全随机数生成器，生成指定长度的密钥对。
     * </p>
     *
     * @param saltValue 盐值字符串，用于增强密钥生成的随机性
     * @param algorithm 签名算法枚举，必须包含"with"关键字（如SHA256withRSA、MD5withRSA等）
     * @return 生成的密钥对对象，包含公钥和私钥
     * @throws NoSuchAlgorithmException 当指定的算法不可用时抛出异常
     */
    public static KeyPair getKeyPair(String saltValue, SignAlgorithm algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(getAlgorithmAfterWith(algorithm.getValue()));
        SecureRandom secureRandom = new SecureRandom(saltValue.getBytes());
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        return keyPair;
    }


    /**
     * 将密钥字符串转换为密钥对象
     *
     * @param privateKey 密钥字符串
     * @return 密钥对象
     * @throws NoSuchAlgorithmException 未找到指定的算法异常
     * @throws InvalidKeySpecException  无效的密钥key
     * @author :loulan
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        return getPrivateKey(Base64Tool.toDecode(privateKey));
    }

    /**
     * 将密钥字节数组转换为密钥对象
     *
     * @param privateKey 密钥字节数组
     * @return 密钥对象
     * @throws NoSuchAlgorithmException 未找到指定的算法异常
     * @throws InvalidKeySpecException  无效的密钥key
     * @author :loulan
     */
    public static PrivateKey getPrivateKey(byte[] privateKey) {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return kf.generatePrivate(spec);
    }

    /**
     * 将密钥字节数组转换为公钥对象
     *
     * @param publicKey 公钥字节数组
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 未找到指定的算法异常
     * @throws InvalidKeySpecException  无效的公钥key
     * @author :loulan
     */
    public static PublicKey getPublicKey(byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return kf.generatePublic(spec);
    }

    /**
     * 将密钥字节数组转换为公钥对象
     *
     * @param publicKey 公钥字节数组
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 未找到指定的算法异常
     * @throws InvalidKeySpecException  无效的公钥key
     * @author :loulan
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getPublicKey(Base64Tool.toDecode(publicKey));
    }

    /**
     * 对指定的数据进行加密（公钥加密，私钥解密，因为数据比较重要，不能被人随便看到，所以解密要用私钥）
     *
     * @param data      要进行加密的数据
     * @param publicKey 公钥
     * @return 加密后的数据
     * @throws NoSuchPaddingException    未找到指定填充方式异常
     * @throws NoSuchAlgorithmException  未找到指定算法异常
     * @throws InvalidKeyException       密钥无效异常
     * @throws IllegalBlockSizeException 块大小非法异常
     * @throws BadPaddingException       填充错误异常
     * @throws IOException               IO异常
     * @author :loulan
     */
    public static String encrypt(String data, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes(CharsetTool.CHARSET_UTF_8).length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(CharsetTool.CHARSET_UTF_8), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(CharsetTool.CHARSET_UTF_8), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        return Base64Tool.toEncode(encryptedData);
    }

    /**
     * 解密数据（公钥加密，私钥解密，因为数据比较重要，不能被人随便看到，所以解密要用私钥）
     *
     * @param data       要进行解密的数据
     * @param privateKey 私钥
     * @return 解密后的数据
     * @throws NoSuchPaddingException    未找到指定填充方式异常
     * @throws NoSuchAlgorithmException  未找到指定算法异常
     * @throws InvalidKeyException       密钥无效异常
     * @throws IllegalBlockSizeException 块大小非法异常
     * @throws BadPaddingException       填充错误异常
     * @throws IOException               IO异常
     * @author :loulan
     */
    public static String decrypt(String data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64Tool.toDecode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, CharsetTool.CHARSET_UTF_8);
    }

    /**
     * 进行签名 (因为签名比验证签名重要，所以签名采用私钥)
     *
     * @param data       要进行签名的数据
     * @param privateKey 私钥
     * @return 签名
     * @throws NoSuchAlgorithmException     未找到指定算法异常
     * @throws InvalidKeyException          密钥无效异常
     * @throws UnsupportedEncodingException 不支持的编码异常
     * @throws SignatureException           签名异常
     * @author :loulan
     */
    public static String sign(String data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(CharsetTool.UTF_8));
        byte[] sign = signature.sign();
        return Base64Tool.toEncode(sign);
    }

    /**
     * 进行签名验证(因为签名比验证签名重要，所以签名采用私钥)
     *
     * @param data       原始的数据
     * @param encodeSign 被签名的数据
     * @param publicKey  公钥
     * @return 签名是否正确
     * @throws NoSuchAlgorithmException     未找到指定算法异常
     * @throws InvalidKeyException          密钥无效异常
     * @throws UnsupportedEncodingException 不支持的编码异常
     * @throws SignatureException           签名异常
     * @author :loulan
     */
    public static boolean verifySign(String data, String encodeSign, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        Signature signature2 = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature2.initVerify(publicKey);
        signature2.update(data.getBytes(CharsetTool.UTF_8));
        return signature2.verify(Base64Tool.toDecode(encodeSign));
    }
}
