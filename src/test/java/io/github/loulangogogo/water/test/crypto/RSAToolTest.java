package io.github.loulangogogo.water.test.crypto;

import io.github.loulangogogo.water.crypto.Base64Tool;
import io.github.loulangogogo.water.crypto.RSATool;
import io.github.loulangogogo.water.enums.SignAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * 专门测试{@link RSATool}类的RSA加密工具方法。
 * <p>
 * 覆盖场景：密钥对生成、公钥/私钥还原、加密解密、签名验签、边界情况及异常处理。
 */
public class RSAToolTest {

    private KeyPair keyPair;
    private KeyPair keyPair2;

    /**
     * 在每个测试前初始化两组不同的密钥对，供后续测试复用。
     */
    @Before
    public void setUp() throws NoSuchAlgorithmException {
        keyPair = RSATool.getKeyPair("test_seed_for_rsa_tool_test");
        keyPair2 = RSATool.getKeyPair("another_seed_different_key");
    }

    // ==================== 密钥对生成 (getKeyPair) ====================

    /**
     * 测试{@link RSATool#getKeyPair(String)}方法，验证生成的密钥对不为null。
     */
    @Test
    public void testGetKeyPair_notNull() throws NoSuchAlgorithmException {
        KeyPair kp = RSATool.getKeyPair("salt_value");
        assertNotNull(kp);
        assertNotNull(kp.getPublic());
        assertNotNull(kp.getPrivate());
    }

    /**
     * 测试{@link RSATool#getKeyPair(String)}方法，验证相同seed生成的密钥对功能正常（可用于加解密）。
     * 注意：SecureRandom在较新JVM中不保证相同seed产生相同密钥，因此只验证功能性。
     */
    @Test
    public void testGetKeyPair_sameSeedBothFunctional() throws Exception {
        KeyPair kp1 = RSATool.getKeyPair("same_seed");
        KeyPair kp2 = RSATool.getKeyPair("same_seed");
        // 两把密钥都能正常工作
        String data = "functional test";
        String encrypted = RSATool.encrypt(data, kp1.getPublic());
        String decrypted = RSATool.decrypt(encrypted, kp1.getPrivate());
        assertEquals(data, decrypted);

        String encrypted2 = RSATool.encrypt(data, kp2.getPublic());
        String decrypted2 = RSATool.decrypt(encrypted2, kp2.getPrivate());
        assertEquals(data, decrypted2);
    }

    /**
     * 测试{@link RSATool#getKeyPair(String)}方法，验证不同seed生成不同的密钥对。
     */
    @Test
    public void testGetKeyPair_differentSeedDifferentKey() {
        assertFalse(java.util.Arrays.equals(
                keyPair.getPublic().getEncoded(),
                keyPair2.getPublic().getEncoded()));
    }

    // ==================== 公钥还原 (getPublicKey) ====================

    /**
     * 测试{@link RSATool#getPublicKey(String)}方法，验证从Base64字符串还原公钥。
     */
    @Test
    public void testGetPublicKey_fromString() throws Exception {
        String pubKeyStr = Base64Tool.toEncode(keyPair.getPublic().getEncoded());
        PublicKey restored = RSATool.getPublicKey(pubKeyStr);
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPublic().getEncoded(), restored.getEncoded());
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[])}方法，验证从字节数组还原公钥。
     */
    @Test
    public void testGetPublicKey_fromBytes() throws Exception {
        PublicKey restored = RSATool.getPublicKey(keyPair.getPublic().getEncoded());
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPublic().getEncoded(), restored.getEncoded());
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[])}方法，验证还原后的公钥算法名称为RSA。
     */
    @Test
    public void testGetPublicKey_algorithm() throws Exception {
        PublicKey restored = RSATool.getPublicKey(keyPair.getPublic().getEncoded());
        assertEquals("RSA", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[], SignAlgorithm)}方法，验证从字节数组配合签名算法还原公钥。
     */
    @Test
    public void testGetPublicKey_withSignAlgorithm() throws Exception {
        PublicKey restored = RSATool.getPublicKey(keyPair.getPublic().getEncoded(), SignAlgorithm.SHA256withRSA);
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPublic().getEncoded(), restored.getEncoded());
        assertEquals("RSA", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[], SignAlgorithm)}方法，验证不同RSA签名算法均可还原公钥。
     */
    @Test
    public void testGetPublicKey_withVariousRSAAlgorithms() throws Exception {
        SignAlgorithm[] rsaAlgorithms = {
            SignAlgorithm.MD5withRSA,
            SignAlgorithm.SHA1withRSA,
            SignAlgorithm.SHA256withRSA,
            SignAlgorithm.SHA384withRSA,
            SignAlgorithm.SHA512withRSA
        };
        for (SignAlgorithm algo : rsaAlgorithms) {
            PublicKey restored = RSATool.getPublicKey(keyPair.getPublic().getEncoded(), algo);
            assertNotNull("Failed with algorithm: " + algo, restored);
            assertArrayEquals("Failed with algorithm: " + algo,
                keyPair.getPublic().getEncoded(), restored.getEncoded());
        }
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[], SignAlgorithm)}方法，验证还原后的公钥可用于加解密。
     */
    @Test
    public void testGetPublicKey_withSignAlgorithm_thenEncryptDecrypt() throws Exception {
        PublicKey restored = RSATool.getPublicKey(keyPair.getPublic().getEncoded(), SignAlgorithm.SHA256withRSA);
        String data = "test data for encryption";
        String encrypted = RSATool.encrypt(data, restored);
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[], SignAlgorithm)}方法，验证ECDSA算法可还原公钥。
     * 使用手动生成的EC密钥对进行测试。
     */
    @Test
    public void testGetPublicKey_withECDSA() throws Exception {
        // 手动生成EC密钥对（使用256位）
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        KeyPair ecKeyPair = kpg.generateKeyPair();

        PublicKey restored = RSATool.getPublicKey(ecKeyPair.getPublic().getEncoded(), SignAlgorithm.SHA256withECDSA);
        assertNotNull(restored);
        assertArrayEquals(ecKeyPair.getPublic().getEncoded(), restored.getEncoded());
        assertEquals("EC", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPublicKey(byte[], SignAlgorithm)}方法，验证DSA算法可还原公钥。
     * 使用手动生成的DSA密钥对进行测试。
     */
    @Test
    public void testGetPublicKey_withDSA() throws Exception {
        // 手动生成DSA密钥对（使用1024位）
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair dsaKeyPair = kpg.generateKeyPair();

        PublicKey restored = RSATool.getPublicKey(dsaKeyPair.getPublic().getEncoded(), SignAlgorithm.SHA1withDSA);
        assertNotNull(restored);
        assertArrayEquals(dsaKeyPair.getPublic().getEncoded(), restored.getEncoded());
        assertEquals("DSA", restored.getAlgorithm());
    }

    // ==================== 私钥还原 (getPrivateKey) ====================

    /**
     * 测试{@link RSATool#getPrivateKey(String)}方法，验证从Base64字符串还原私钥。
     */
    @Test
    public void testGetPrivateKey_fromString() throws Exception {
        String priKeyStr = Base64Tool.toEncode(keyPair.getPrivate().getEncoded());
        PrivateKey restored = RSATool.getPrivateKey(priKeyStr);
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPrivate().getEncoded(), restored.getEncoded());
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[])}方法，验证从字节数组还原私钥。
     */
    @Test
    public void testGetPrivateKey_fromBytes() throws Exception {
        PrivateKey restored = RSATool.getPrivateKey(keyPair.getPrivate().getEncoded());
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPrivate().getEncoded(), restored.getEncoded());
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[])}方法，验证还原后的私钥算法名称为RSA。
     */
    @Test
    public void testGetPrivateKey_algorithm() throws Exception {
        PrivateKey restored = RSATool.getPrivateKey(keyPair.getPrivate().getEncoded());
        assertEquals("RSA", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[], SignAlgorithm)}方法，验证从字节数组配合签名算法还原私钥。
     */
    @Test
    public void testGetPrivateKey_withSignAlgorithm() throws Exception {
        PrivateKey restored = RSATool.getPrivateKey(keyPair.getPrivate().getEncoded(), SignAlgorithm.SHA256withRSA);
        assertNotNull(restored);
        assertArrayEquals(keyPair.getPrivate().getEncoded(), restored.getEncoded());
        assertEquals("RSA", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[], SignAlgorithm)}方法，验证不同RSA签名算法均可还原私钥。
     */
    @Test
    public void testGetPrivateKey_withVariousRSAAlgorithms() throws Exception {
        SignAlgorithm[] rsaAlgorithms = {
            SignAlgorithm.MD5withRSA,
            SignAlgorithm.SHA1withRSA,
            SignAlgorithm.SHA256withRSA,
            SignAlgorithm.SHA384withRSA,
            SignAlgorithm.SHA512withRSA
        };
        for (SignAlgorithm algo : rsaAlgorithms) {
            PrivateKey restored = RSATool.getPrivateKey(keyPair.getPrivate().getEncoded(), algo);
            assertNotNull("Failed with algorithm: " + algo, restored);
            assertArrayEquals("Failed with algorithm: " + algo,
                keyPair.getPrivate().getEncoded(), restored.getEncoded());
        }
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[], SignAlgorithm)}方法，验证还原后的私钥可用于加解密。
     */
    @Test
    public void testGetPrivateKey_withSignAlgorithm_thenEncryptDecrypt() throws Exception {
        PrivateKey restored = RSATool.getPrivateKey(keyPair.getPrivate().getEncoded(), SignAlgorithm.SHA256withRSA);
        String data = "test data for encryption";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, restored);
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[], SignAlgorithm)}方法，验证ECDSA算法可还原私钥。
     * 使用手动生成的EC密钥对进行测试。
     */
    @Test
    public void testGetPrivateKey_withECDSA() throws Exception {
        // 手动生成EC密钥对（使用256位）
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        KeyPair ecKeyPair = kpg.generateKeyPair();

        PrivateKey restored = RSATool.getPrivateKey(ecKeyPair.getPrivate().getEncoded(), SignAlgorithm.SHA256withECDSA);
        assertNotNull(restored);
        assertArrayEquals(ecKeyPair.getPrivate().getEncoded(), restored.getEncoded());
        assertEquals("EC", restored.getAlgorithm());
    }

    /**
     * 测试{@link RSATool#getPrivateKey(byte[], SignAlgorithm)}方法，验证DSA算法可还原私钥。
     * 使用手动生成的DSA密钥对进行测试。
     */
    @Test
    public void testGetPrivateKey_withDSA() throws Exception {
        // 手动生成DSA密钥对（使用1024位）
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair dsaKeyPair = kpg.generateKeyPair();

        PrivateKey restored = RSATool.getPrivateKey(dsaKeyPair.getPrivate().getEncoded(), SignAlgorithm.SHA1withDSA);
        assertNotNull(restored);
        assertArrayEquals(dsaKeyPair.getPrivate().getEncoded(), restored.getEncoded());
        assertEquals("DSA", restored.getAlgorithm());
    }

    // ==================== 加密解密 (encrypt/decrypt) ====================

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证短英文字符串加解密的正确性。
     */
    @Test
    public void testEncryptDecrypt_shortText() throws Exception {
        String data = "Hello RSA!";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());

        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证中文字符串加解密的正确性。
     */
    @Test
    public void testEncryptDecrypt_chineseText() throws Exception {
        String data = "中文RSA加密解密测试";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证超长文本（超过单段RSA加密上限245字节）的分段加解密正确性。
     */
    @Test
    public void testEncryptDecrypt_longText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("LongTextRSA ");
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证空字符串的加解密。
     */
    @Test
    public void testEncryptDecrypt_emptyString() throws Exception {
        String data = "";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}方法，验证加密后密文与明文不同。
     */
    @Test
    public void testEncrypt_ciphertextDiffersFromPlaintext() throws Exception {
        String data = "secret data";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        assertNotEquals(data, encrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}方法，验证相同明文多次加密后都能正确解密。
     * 注意：默认RSA填充在较新JVM中可能引入随机性，密文不一定相同。
     */
    @Test
    public void testEncrypt_samePlaintextBothDecryptable() throws Exception {
        String data = "multiple encrypt test";
        String encrypted1 = RSATool.encrypt(data, keyPair.getPublic());
        String encrypted2 = RSATool.encrypt(data, keyPair.getPublic());
        // 两次加密结果都能正确解密
        assertEquals(data, RSATool.decrypt(encrypted1, keyPair.getPrivate()));
        assertEquals(data, RSATool.decrypt(encrypted2, keyPair.getPrivate()));
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证恰好为单段上限长度（245字节）的数据加解密。
     */
    @Test
    public void testEncryptDecrypt_exactlyMaxBlock() throws Exception {
        // 245字节的ASCII字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 245; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}和{@link RSATool#decrypt(String, PrivateKey)}方法，
     * 验证超过单段上限（245字节）但不足两段的数据加解密。
     */
    @Test
    public void testEncryptDecrypt_slightlyOverMaxBlock() throws Exception {
        // 246字节，刚好需要两段
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 246; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#decrypt(String, PrivateKey)}方法，验证用错误私钥解密会抛出异常。
     */
    @Test(expected = Exception.class)
    public void testDecrypt_wrongKey() throws Exception {
        String data = "wrong key test";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        // 用另一把私钥解密，应抛出异常
        RSATool.decrypt(encrypted, keyPair2.getPrivate());
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey, SignAlgorithm)}方法，
     * 验证使用指定算法枚举加密后可正确解密。
     */
    @Test
    public void testEncrypt_withAlgorithm() throws Exception {
        String data = "algorithm encrypt test";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());

        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey, SignAlgorithm)}方法，
     * 验证不同RSA算法枚举均可正常加解密。
     */
    @Test
    public void testEncrypt_withVariousAlgorithms() throws Exception {
        String data = "various algorithms test";
        SignAlgorithm[] rsaAlgorithms = {
            SignAlgorithm.MD5withRSA,
            SignAlgorithm.SHA1withRSA,
            SignAlgorithm.SHA256withRSA,
            SignAlgorithm.SHA384withRSA,
            SignAlgorithm.SHA512withRSA
        };
        for (SignAlgorithm algo : rsaAlgorithms) {
            String encrypted = RSATool.encrypt(data, keyPair.getPublic(), algo);
            String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
            assertEquals("Failed with algorithm: " + algo, data, decrypted);
        }
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey, SignAlgorithm)}方法，
     * 验证中文字符串加解密。
     */
    @Test
    public void testEncrypt_withAlgorithm_chinese() throws Exception {
        String data = "中文算法加密测试";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey, SignAlgorithm)}方法，
     * 验证长文本分段加解密。
     */
    @Test
    public void testEncrypt_withAlgorithm_longText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("AlgorithmLongText ");
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#decrypt(String, PrivateKey, SignAlgorithm)}方法，
     * 验证使用指定算法枚举解密。
     */
    @Test
    public void testDecrypt_withAlgorithm() throws Exception {
        String data = "algorithm decrypt test";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#decrypt(String, PrivateKey, SignAlgorithm)}方法，
     * 验证不同RSA算法枚举均可正常解密。
     */
    @Test
    public void testDecrypt_withVariousAlgorithms() throws Exception {
        String data = "various algorithms decrypt test";
        SignAlgorithm[] rsaAlgorithms = {
            SignAlgorithm.MD5withRSA,
            SignAlgorithm.SHA1withRSA,
            SignAlgorithm.SHA256withRSA,
            SignAlgorithm.SHA384withRSA,
            SignAlgorithm.SHA512withRSA
        };
        for (SignAlgorithm algo : rsaAlgorithms) {
            String encrypted = RSATool.encrypt(data, keyPair.getPublic());
            String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate(), algo);
            assertEquals("Failed with algorithm: " + algo, data, decrypted);
        }
    }

    /**
     * 测试{@link RSATool#decrypt(String, PrivateKey, SignAlgorithm)}方法，
     * 验证中文字符串解密。
     */
    @Test
    public void testDecrypt_withAlgorithm_chinese() throws Exception {
        String data = "中文算法解密测试";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        assertEquals(data, decrypted);
    }

    /**
     * 测试{@link RSATool#decrypt(String, PrivateKey, SignAlgorithm)}方法，
     * 验证长文本分段解密。
     */
    @Test
    public void testDecrypt_withAlgorithm_longText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("AlgorithmDecryptText ");
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        assertEquals(data, decrypted);
    }

    // ==================== 签名验签 (sign/verifySign) ====================

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}和{@link RSATool#verifySign(String, String, PublicKey)}方法，
     * 验证正常签名和验签流程。
     */
    @Test
    public void testSignVerify_normal() throws Exception {
        String data = "data to sign";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        assertNotNull(signature);
        assertFalse(signature.isEmpty());

        boolean valid = RSATool.verifySign(data, signature, keyPair.getPublic());
        assertTrue(valid);
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}方法，验证相同数据多次签名结果一致（确定性签名）。
     */
    @Test
    public void testSign_deterministic() throws Exception {
        String data = "deterministic sign";
        String sig1 = RSATool.sign(data, keyPair.getPrivate());
        String sig2 = RSATool.sign(data, keyPair.getPrivate());
        assertEquals(sig1, sig2);
    }

    /**
     * 测试{@link RSATool#verifySign(String, String, PublicKey)}方法，验证数据被篡改后验签失败。
     */
    @Test
    public void testVerifySign_tamperedData() throws Exception {
        String data = "original data";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        boolean valid = RSATool.verifySign("tampered data", signature, keyPair.getPublic());
        assertFalse(valid);
    }

    /**
     * 测试{@link RSATool#verifySign(String, String, PublicKey)}方法，验证签名被篡改后验签失败。
     */
    @Test
    public void testVerifySign_tamperedSignature() throws Exception {
        String data = "original data";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        // 篡改签名（替换部分字符）
        char[] chars = signature.toCharArray();
        chars[5] = (chars[5] == 'A') ? 'B' : 'A';
        String tamperedSig = new String(chars);
        boolean valid = RSATool.verifySign(data, tamperedSig, keyPair.getPublic());
        assertFalse(valid);
    }

    /**
     * 测试{@link RSATool#verifySign(String, String, PublicKey)}方法，验证用错误公钥验签失败。
     */
    @Test
    public void testVerifySign_wrongPublicKey() throws Exception {
        String data = "data to sign";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        boolean valid = RSATool.verifySign(data, signature, keyPair2.getPublic());
        assertFalse(valid);
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}和{@link RSATool#verifySign(String, String, PublicKey)}方法，
     * 验证中文数据的签名和验签。
     */
    @Test
    public void testSignVerify_chineseData() throws Exception {
        String data = "中文数据签名验签测试";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        assertTrue(RSATool.verifySign(data, signature, keyPair.getPublic()));
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}和{@link RSATool#verifySign(String, String, PublicKey)}方法，
     * 验证空字符串的签名和验签。
     */
    @Test
    public void testSignVerify_emptyString() throws Exception {
        String data = "";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        assertTrue(RSATool.verifySign(data, signature, keyPair.getPublic()));
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}和{@link RSATool#verifySign(String, String, PublicKey)}方法，
     * 验证长文本的签名和验签。
     */
    @Test
    public void testSignVerify_longText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append("SignVerifyLongText ");
        }
        String data = sb.toString();
        String signature = RSATool.sign(data, keyPair.getPrivate());
        assertTrue(RSATool.verifySign(data, signature, keyPair.getPublic()));
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey, SignAlgorithm)}和
     * {@link RSATool#verifySign(String, String, PublicKey, SignAlgorithm)}方法，
     * 验证使用指定算法枚举签名验签。
     */
    @Test
    public void testSignVerify_withAlgorithm() throws Exception {
        String data = "algorithm sign verify test";
        String signature = RSATool.sign(data, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        assertNotNull(signature);
        assertFalse(signature.isEmpty());

        boolean valid = RSATool.verifySign(data, signature, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        assertTrue(valid);
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey, SignAlgorithm)}和
     * {@link RSATool#verifySign(String, String, PublicKey, SignAlgorithm)}方法，
     * 验证不同RSA算法枚举均可正常签名验签。
     */
    @Test
    public void testSignVerify_withVariousAlgorithms() throws Exception {
        String data = "various algorithms sign verify";
        SignAlgorithm[] rsaAlgorithms = {
            SignAlgorithm.MD5withRSA,
            SignAlgorithm.SHA1withRSA,
            SignAlgorithm.SHA256withRSA,
            SignAlgorithm.SHA384withRSA,
            SignAlgorithm.SHA512withRSA
        };
        for (SignAlgorithm algo : rsaAlgorithms) {
            String signature = RSATool.sign(data, keyPair.getPrivate(), algo);
            boolean valid = RSATool.verifySign(data, signature, keyPair.getPublic(), algo);
            assertTrue("Failed with algorithm: " + algo, valid);
        }
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey, SignAlgorithm)}和
     * {@link RSATool#verifySign(String, String, PublicKey, SignAlgorithm)}方法，
     * 验证中文字符串签名验签。
     */
    @Test
    public void testSignVerify_withAlgorithm_chinese() throws Exception {
        String data = "中文算法签名验签测试";
        String signature = RSATool.sign(data, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        boolean valid = RSATool.verifySign(data, signature, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        assertTrue(valid);
    }

    /**
     * 测试{@link RSATool#verifySign(String, String, PublicKey, SignAlgorithm)}方法，
     * 验证数据被篡改后验签失败。
     */
    @Test
    public void testVerifySign_withAlgorithm_tamperedData() throws Exception {
        String data = "original data";
        String signature = RSATool.sign(data, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        boolean valid = RSATool.verifySign("tampered data", signature, keyPair.getPublic(), SignAlgorithm.SHA256withRSA);
        assertFalse(valid);
    }

    /**
     * 测试{@link RSATool#verifySign(String, String, PublicKey, SignAlgorithm)}方法，
     * 验证签名算法不匹配时验签失败。
     */
    @Test
    public void testVerifySign_withAlgorithm_mismatch() throws Exception {
        String data = "algorithm mismatch test";
        String signature = RSATool.sign(data, keyPair.getPrivate(), SignAlgorithm.SHA256withRSA);
        // 用不同的算法验签应该失败
        boolean valid = RSATool.verifySign(data, signature, keyPair.getPublic(), SignAlgorithm.SHA512withRSA);
        assertFalse(valid);
    }

    // ==================== 跨密钥对验证 ====================

    /**
     * 测试验证：密钥对A加密的数据不能用密钥对B的私钥解密（不同密钥对互不兼容）。
     */
    @Test(expected = Exception.class)
    public void testCrossKeyPair_decryptFails() throws Exception {
        String data = "cross key pair test";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        RSATool.decrypt(encrypted, keyPair2.getPrivate());
    }

    /**
     * 测试验证：密钥对A签名的数据不能用密钥对B的公钥验签。
     */
    @Test
    public void testCrossKeyPair_verifySignFails() throws Exception {
        String data = "cross key pair sign test";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        assertFalse(RSATool.verifySign(data, signature, keyPair2.getPublic()));
    }

    // ==================== 密钥还原后再加解密 ====================

    /**
     * 测试公钥从Base64字符串还原后再用于加密，私钥从Base64字符串还原后再用于解密，
     * 验证密钥序列化/反序列化后功能不变。
     */
    @Test
    public void testKeyRestoration_thenEncryptDecrypt() throws Exception {
        String pubKeyStr = Base64Tool.toEncode(keyPair.getPublic().getEncoded());
        String priKeyStr = Base64Tool.toEncode(keyPair.getPrivate().getEncoded());

        PublicKey restoredPub = RSATool.getPublicKey(pubKeyStr);
        PrivateKey restoredPri = RSATool.getPrivateKey(priKeyStr);

        String data = "restored key encrypt decrypt";
        String encrypted = RSATool.encrypt(data, restoredPub);
        String decrypted = RSATool.decrypt(encrypted, restoredPri);
        assertEquals(data, decrypted);
    }

    /**
     * 测试私钥从Base64字符串还原后再用于签名，公钥从Base64字符串还原后再用于验签，
     * 验证密钥序列化/反序列化后签名功能不变。
     */
    @Test
    public void testKeyRestoration_thenSignVerify() throws Exception {
        String pubKeyStr = Base64Tool.toEncode(keyPair.getPublic().getEncoded());
        String priKeyStr = Base64Tool.toEncode(keyPair.getPrivate().getEncoded());

        PublicKey restoredPub = RSATool.getPublicKey(pubKeyStr);
        PrivateKey restoredPri = RSATool.getPrivateKey(priKeyStr);

        String data = "restored key sign verify";
        String signature = RSATool.sign(data, restoredPri);
        assertTrue(RSATool.verifySign(data, signature, restoredPub));
    }

    // ==================== 加密结果格式验证 ====================

    /**
     * 测试{@link RSATool#encrypt(String, PublicKey)}方法，验证加密结果为合法的Base64字符串。
     */
    @Test
    public void testEncrypt_resultIsBase64() throws Exception {
        String data = "base64 format test";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        // Base64解码不应抛异常
        byte[] decoded = Base64Tool.toDecode(encrypted);
        assertNotNull(decoded);
        assertTrue(decoded.length > 0);
    }

    /**
     * 测试{@link RSATool#sign(String, PrivateKey)}方法，验证签名结果为合法的Base64字符串。
     */
    @Test
    public void testSign_resultIsBase64() throws Exception {
        String data = "sign base64 format";
        String signature = RSATool.sign(data, keyPair.getPrivate());
        byte[] decoded = Base64Tool.toDecode(signature);
        assertNotNull(decoded);
        assertTrue(decoded.length > 0);
    }
}
