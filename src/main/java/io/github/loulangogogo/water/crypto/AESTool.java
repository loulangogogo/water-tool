package io.github.loulangogogo.water.crypto;

import io.github.loulangogogo.water.exception.DecryptException;
import io.github.loulangogogo.water.exception.EncryptException;

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
        ECB,
        CBC,
        PCBC,
        CTR,
        CFB,
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
        NoPadding,
        PKCS5Padding,
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
        return new String(decrypt(key, Base64Tool.toDecode(data), MODE.ECB.name(), PADDING.PKCS5Padding.name()));
    }
}
