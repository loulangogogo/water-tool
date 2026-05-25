package io.github.loulangogogo.water.crypto;

import io.github.loulangogogo.water.exception.DecryptException;
import io.github.loulangogogo.water.exception.EncryptException;
import io.github.loulangogogo.water.tool.CharsetTool;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*********************************************************
 ** AES加解密工具类（对称加密）
 ** @author loulan
 ** @since 8
 *********************************************************/
public class AESTool {

    private static final String ALGORITHM = "AES";

    private static final int KEYSIZE = 128;

    /*********************************************************
     ** 工作模式的枚举类型
     **
     **  2022/7/7 18:42
     ** @author loulan
     ** @since 8
     *********************************************************/
    public enum MODE {
        /**
         * 电子密码本模式（Electronic Codebook）
         * <p>最简单的操作模式，将明文分成固定长度的块，每块独立加密</p>
         */
        ECB,
        
        /**
         * 密码分组链接模式（Cipher Block Chaining）
         * <p>每个明文块先与前一个密文块进行异或后再加密，需要初始化向量IV</p>
         */
        CBC,
        
        /**
         * 密文分组链接模式（Propagating Cipher Block Chaining）
         * <p>CBC的改进版本，具有更好的错误传播特性</p>
         */
        PCBC,
        
        /**
         * 计数器模式（Counter）
         * <p>将计数器值加密后与明文异或，可并行处理</p>
         */
        CTR,
        
        /**
         * 密文反馈模式（Cipher Feedback）
         * <p>将前一个密文块加密后与当前明文块异或，适合流式数据</p>
         */
        CFB,
        
        /**
         * 输出反馈模式（Output Feedback）
         * <p>将加密算法的输出反馈到输入端，生成密钥流</p>
         */
        OFB
    }

    /*********************************************************
     ** 填充方式的枚举类型
     **
     **  2022/7/7 18:42
     ** @author loulan
     ** @since 8
     *********************************************************/
    public enum PADDING {
        /**
         * 无填充模式
         * <p>要求数据长度必须是块大小的整数倍，否则抛出异常</p>
         */
        NoPadding,
        
        /**
         * PKCS#5 填充模式
         * <p>最常用的填充方式，填充字节值为需要填充的字节数</p>
         */
        PKCS5Padding,
        
        /**
         * ISO/IEC 10126 填充模式
         * <p>最后一个字节填充填充字节数，其他字节随机填充</p>
         */
        ISO10126Padding
    }

    /**
     * 获取钥匙key
     *
     * @param saltValue 盐值，这样可以保证获取的钥匙一致
     * @return 钥匙key
     * @throws NoSuchAlgorithmException 未找到指定算法异常
     * @author :loulan
     */
    public static String getkey(String saltValue) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(saltValue.getBytes());
        keyGenerator.init(KEYSIZE, secureRandom);// 密钥长度
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Tool.toEncode(secretKey.getEncoded());
    }

    /**
     * 创建钥匙的key对象
     *
     * @param key 钥匙的数组形式
     * @return 钥匙的key对象
     * @author :loulan
     */
    private static Key buildKey(byte[] key) {
        return new SecretKeySpec(key, ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param key  密钥
     * @param data 需要加密的数据
     * @param mode 工作模式{@link MODE}
     * @param pad  填充方式{@link PADDING}
     * @return 加密后的数据
     * @author :loulan
     */
    public static byte[] encrypt(String key, byte[] data, String mode, String pad) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", ALGORITHM, mode, pad));
            cipher.init(Cipher.ENCRYPT_MODE, buildKey(Base64Tool.toDecode(key)));
            return cipher.doFinal(data);
        } catch (Exception ex) {
            throw new EncryptException(ex);
        }
    }

    /**
     * 加密数据,默认ECB,PKCS5Padding
     *
     * @param key  密钥
     * @param data 需要加密的数据
     * @return 解密后的数据
     * @author :loulan
     */
    public static byte[] encrypt(String key, byte[] data) {
        return encrypt(key, data, MODE.ECB.name(), PADDING.PKCS5Padding.name());
    }

    /**
     * 加密数据,默认ECB,PKCS5Padding
     *
     * @param key  密钥
     * @param data 需要加密的数据
     * @return 解密后的数据
     * @author :loulan
     */
    public static String encrypt(String key, String data) {
        return Base64Tool.toEncode(encrypt(key, data.getBytes(StandardCharsets.UTF_8), MODE.ECB.name(), PADDING.PKCS5Padding.name()));
    }

    /**
     * 解密数据
     *
     * @param key  密钥
     * @param data 需要解密的数据
     * @param mode 工作模式{@link MODE}
     * @param pad  填充方式{@link PADDING}
     * @return 解密后的数据
     * @author :loulan
     */
    public static byte[] decrypt(String key, byte[] data, String mode, String pad) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", ALGORITHM, mode, pad));
            cipher.init(Cipher.DECRYPT_MODE, buildKey(Base64Tool.toDecode(key)));
            return cipher.doFinal(data);
        } catch (Exception ex) {
            throw new DecryptException(ex);
        }
    }

    /**
     * 解密数据,默认ECB,PKCS5Padding
     *
     * @param key  密钥
     * @param data 需要解密的数据
     * @return 解密后的数据
     * @author :loulan
     */
    public static byte[] decrypt(String key, byte[] data) {
        return decrypt(key, data, MODE.ECB.name(), PADDING.PKCS5Padding.name());
    }

    /**
     * 解密数据,默认ECB,PKCS5Padding
     *
     * @param key  密钥
     * @param data 需要解密的数据
     * @return 解密后的数据
     * @author :loulan
     */
    public static String decrypt(String key, String data) {
        return new String(decrypt(key, Base64Tool.toDecode(data), MODE.ECB.name(), PADDING.PKCS5Padding.name()), CharsetTool.CHARSET_UTF_8);
    }
}
