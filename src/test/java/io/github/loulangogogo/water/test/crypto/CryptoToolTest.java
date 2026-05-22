package io.github.loulangogogo.water.test.crypto;

import io.github.loulangogogo.water.crypto.AESTool;
import io.github.loulangogogo.water.crypto.Base64Tool;
import io.github.loulangogogo.water.crypto.MD5Tool;
import io.github.loulangogogo.water.crypto.RSATool;
import io.github.loulangogogo.water.crypto.SHA2Tool;
import io.github.loulangogogo.water.crypto.SHA3Tool;
import io.github.loulangogogo.water.tool.CharsetTool;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

/**
 * 测试{@link Base64Tool}、{@link MD5Tool}、{@link SHA2Tool}、{@link SHA3Tool}、{@link AESTool}、{@link RSATool}类的功能。
 */
public class CryptoToolTest {

    // Base64Tool tests
    /**
     * 测试{@link Base64Tool#toEncode}和{@link Base64Tool#toDecode}方法，验证编解码往返一致性。
     */
    @Test
    public void testBase64_roundTrip() {
        String original = "Hello, World!";
        String encoded = Base64Tool.toEncode(original.getBytes());
        byte[] decoded = Base64Tool.toDecode(encoded);
        assertEquals(original, new String(decoded));
    }

    /**
     * 测试{@link Base64Tool#toEncode}方法，验证空字节数组编码。
     */
    @Test
    public void testBase64_emptyBytes() {
        String encoded = Base64Tool.toEncode(new byte[0]);
        assertEquals("", encoded);
    }

    /**
     * 测试{@link Base64Tool#toEncode}和{@link Base64Tool#toDecode}方法，验证中文字符编解码。
     */
    @Test
    public void testBase64_chineseChars() {
        String original = "楼兰";
        String encoded = Base64Tool.toEncode(original.getBytes(CharsetTool.CHARSET_UTF_8));
        byte[] decoded = Base64Tool.toDecode(encoded);
        assertEquals(original, new String(decoded, CharsetTool.CHARSET_UTF_8));
    }

    // MD5Tool tests
    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证字符串MD5哈希值。
     */
    @Test
    public void testMd5Hex_string() {
        String hex = MD5Tool.md5Hex("hello");
        assertNotNull(hex);
        assertEquals(32, hex.length());
        // MD5 of "hello" is well-known
        assertEquals("5d41402abc4b2a76b9719d911017c592", hex);
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证null输入返回null。
     */
    @Test
    public void testMd5Hex_null() {
        assertNull(MD5Tool.md5Hex((String) null));
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证空字符串MD5哈希值。
     */
    @Test
    public void testMd5Hex_empty() {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", MD5Tool.md5Hex(""));
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证字节数组MD5哈希值。
     */
    @Test
    public void testMd5Hex_bytes() {
        String hex = MD5Tool.md5Hex("hello".getBytes());
        assertEquals("5d41402abc4b2a76b9719d911017c592", hex);
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证null字节数组返回null。
     */
    @Test
    public void testMd5Hex_nullBytes() {
        assertNull(MD5Tool.md5Hex((byte[]) null));
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证InputStream的MD5哈希值。
     */
    @Test
    public void testMd5Hex_inputStream() throws IOException {
        InputStream is = new ByteArrayInputStream("hello".getBytes());
        String hex = MD5Tool.md5Hex(is);
        assertEquals("5d41402abc4b2a76b9719d911017c592", hex);
    }

    /**
     * 测试{@link MD5Tool#md5Hex}方法，验证null InputStream返回null。
     */
    @Test
    public void testMd5Hex_nullInputStream() throws IOException {
        assertNull(MD5Tool.md5Hex((InputStream) null));
    }

    /**
     * 测试{@link MD5Tool#md5}方法，验证返回16字节MD5哈希。
     */
    @Test
    public void testMd5_bytes() {
        byte[] md5 = MD5Tool.md5("hello");
        assertNotNull(md5);
        assertEquals(16, md5.length);
    }

    /**
     * 测试{@link MD5Tool#md5}方法，验证null字节数组返回null。
     */
    @Test
    public void testMd5_nullBytes() {
        assertNull(MD5Tool.md5((byte[]) null));
    }

    /**
     * 测试{@link MD5Tool#md5}方法，验证null字符串返回null。
     */
    @Test
    public void testMd5_nullString() {
        assertNull(MD5Tool.md5((String) null));
    }

    // SHA2Tool tests
    /**
     * 测试{@link SHA2Tool#sha256}方法，验证SHA-256哈希长度。
     */
    @Test
    public void testSha256() {
        byte[] hash = SHA2Tool.sha256("hello");
        assertNotNull(hash);
        assertEquals(32, hash.length);
    }

    /**
     * 测试{@link SHA2Tool#sha256Hex}方法，验证SHA-256十六进制哈希值。
     */
    @Test
    public void testSha256Hex() {
        String hex = SHA2Tool.sha256Hex("hello");
        assertNotNull(hex);
        assertEquals(64, hex.length());
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", hex);
    }

    /**
     * 测试{@link SHA2Tool#sha384}方法，验证SHA-384哈希长度。
     */
    @Test
    public void testSha384() {
        byte[] hash = SHA2Tool.sha384("hello");
        assertNotNull(hash);
        assertEquals(48, hash.length);
    }

    /**
     * 测试{@link SHA2Tool#sha384Hex}方法，验证SHA-384十六进制哈希长度。
     */
    @Test
    public void testSha384Hex() {
        String hex = SHA2Tool.sha384Hex("hello");
        assertNotNull(hex);
        assertEquals(96, hex.length());
    }

    /**
     * 测试{@link SHA2Tool#sha512}方法，验证SHA-512哈希长度。
     */
    @Test
    public void testSha512() {
        byte[] hash = SHA2Tool.sha512("hello");
        assertNotNull(hash);
        assertEquals(64, hash.length);
    }

    /**
     * 测试{@link SHA2Tool#sha512Hex}方法，验证SHA-512十六进制哈希长度。
     */
    @Test
    public void testSha512Hex() {
        String hex = SHA2Tool.sha512Hex("hello");
        assertNotNull(hex);
        assertEquals(128, hex.length());
    }

    /**
     * 测试{@link SHA2Tool#sha512_224}方法，验证SHA-512/224哈希长度。
     */
    @Test
    public void testSha512_224() {
        byte[] hash = SHA2Tool.sha512_224("hello");
        assertNotNull(hash);
        assertEquals(28, hash.length);
    }

    /**
     * 测试{@link SHA2Tool#sha512_224Hex}方法，验证SHA-512/224十六进制哈希长度。
     */
    @Test
    public void testSha512_224Hex() {
        String hex = SHA2Tool.sha512_224Hex("hello");
        assertNotNull(hex);
        assertEquals(56, hex.length());
    }

    /**
     * 测试{@link SHA2Tool#sha512_256}方法，验证SHA-512/256哈希长度。
     */
    @Test
    public void testSha512_256() {
        byte[] hash = SHA2Tool.sha512_256("hello");
        assertNotNull(hash);
        assertEquals(32, hash.length);
    }

    /**
     * 测试{@link SHA2Tool#sha512_256Hex}方法，验证SHA-512/256十六进制哈希长度。
     */
    @Test
    public void testSha512_256Hex() {
        String hex = SHA2Tool.sha512_256Hex("hello");
        assertNotNull(hex);
        assertEquals(64, hex.length());
    }

    // SHA3Tool tests
    /**
     * 测试{@link SHA3Tool#sha3_224}方法，验证SHA3-224哈希长度。
     */
    @Test
    public void testSha3_224() {
        byte[] hash = SHA3Tool.sha3_224("hello");
        assertNotNull(hash);
        assertEquals(28, hash.length);
    }

    /**
     * 测试{@link SHA3Tool#sha3_224Hex}方法，验证SHA3-224十六进制哈希长度。
     */
    @Test
    public void testSha3_224Hex() {
        String hex = SHA3Tool.sha3_224Hex("hello");
        assertNotNull(hex);
        assertEquals(56, hex.length());
    }

    /**
     * 测试{@link SHA3Tool#sha3_256}方法，验证SHA3-256哈希长度。
     */
    @Test
    public void testSha3_256() {
        byte[] hash = SHA3Tool.sha3_256("hello");
        assertNotNull(hash);
        assertEquals(32, hash.length);
    }

    /**
     * 测试{@link SHA3Tool#sha3_256Hex}方法，验证SHA3-256十六进制哈希长度。
     */
    @Test
    public void testSha3_256Hex() {
        String hex = SHA3Tool.sha3_256Hex("hello");
        assertNotNull(hex);
        assertEquals(64, hex.length());
    }

    /**
     * 测试{@link SHA3Tool#sha3_384}方法，验证SHA3-384哈希长度。
     */
    @Test
    public void testSha3_384() {
        byte[] hash = SHA3Tool.sha3_384("hello");
        assertNotNull(hash);
        assertEquals(48, hash.length);
    }

    /**
     * 测试{@link SHA3Tool#sha3_384Hex}方法，验证SHA3-384十六进制哈希长度。
     */
    @Test
    public void testSha3_384Hex() {
        String hex = SHA3Tool.sha3_384Hex("hello");
        assertNotNull(hex);
        assertEquals(96, hex.length());
    }

    /**
     * 测试{@link SHA3Tool#sha3_512}方法，验证SHA3-512哈希长度。
     */
    @Test
    public void testSha3_512() {
        byte[] hash = SHA3Tool.sha3_512("hello");
        assertNotNull(hash);
        assertEquals(64, hash.length);
    }

    /**
     * 测试{@link SHA3Tool#sha3_512Hex}方法，验证SHA3-512十六进制哈希长度。
     */
    @Test
    public void testSha3_512Hex() {
        String hex = SHA3Tool.sha3_512Hex("hello");
        assertNotNull(hex);
        assertEquals(128, hex.length());
    }

    // AESTool tests
    /**
     * 测试{@link AESTool#getkey}方法，验证通过salt生成AES密钥。
     */
    @Test
    public void testAesGetKey() throws NoSuchAlgorithmException {
        String key = AESTool.getkey("salt123");
        assertNotNull(key);
        assertTrue(key.length() > 0);
    }

    /**
     * 测试{@link AESTool#encrypt}和{@link AESTool#decrypt}方法，验证字节数组加解密。
     */
    @Test
    public void testAesEncryptDecrypt_bytes() throws NoSuchAlgorithmException {
        String key = AESTool.getkey("test_salt");
        String data = "Hello, AES!";
        byte[] encrypted = AESTool.encrypt(key, data.getBytes());
        byte[] decrypted = AESTool.decrypt(key, encrypted);
        assertEquals(data, new String(decrypted));
    }

    /**
     * 测试{@link AESTool#encrypt}和{@link AESTool#decrypt}方法，验证字符串（含中文）加解密。
     */
    @Test
    public void testAesEncryptDecrypt_string() throws NoSuchAlgorithmException {
        String key = AESTool.getkey("test_salt");
        String data = "Hello, AES! 中文测试";
        String encrypted = AESTool.encrypt(key, data);
        String decrypted = AESTool.decrypt(key, encrypted);
        assertEquals("AES string encrypt/decrypt should preserve data with UTF-8 charset",
                data, decrypted);
    }

    /**
     * 测试{@link AESTool#encrypt}和{@link AESTool#decrypt}方法，验证指定加密模式和填充方式。
     */
    @Test
    public void testAesEncryptDecrypt_withMode() throws NoSuchAlgorithmException {
        String key = AESTool.getkey("test_salt");
        byte[] data = "test data".getBytes();
        byte[] encrypted = AESTool.encrypt(key, data, "ECB", "PKCS5Padding");
        byte[] decrypted = AESTool.decrypt(key, encrypted, "ECB", "PKCS5Padding");
        assertEquals("test data", new String(decrypted));
    }

    /**
     * 测试{@link AESTool#decrypt}方法，验证错误密钥解密抛出异常。
     */
    @Test(expected = Exception.class)
    public void testAesWrongKey() throws NoSuchAlgorithmException {
        String key1 = AESTool.getkey("salt1");
        String key2 = AESTool.getkey("salt2");
        byte[] data = "secret".getBytes();
        byte[] encrypted = AESTool.encrypt(key1, data);
        AESTool.decrypt(key2, encrypted);
    }

    // RSATool tests
    /**
     * 测试{@link RSATool#getKeyPair}方法，验证生成RSA密钥对。
     */
    @Test
    public void testRsaGetKeyPair() throws NoSuchAlgorithmException {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        assertNotNull(keyPair.getPublic());
        assertNotNull(keyPair.getPrivate());
    }

    /**
     * 测试{@link RSATool#getPrivateKey}方法，验证从字符串还原私钥。
     */
    @Test
    public void testRsaGetPrivateKey_string() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String privateKeyStr = Base64Tool.toEncode(keyPair.getPrivate().getEncoded());
        PrivateKey privateKey = RSATool.getPrivateKey(privateKeyStr);
        assertNotNull(privateKey);
    }

    /**
     * 测试{@link RSATool#getPrivateKey}方法，验证从字节数组还原私钥。
     */
    @Test
    public void testRsaGetPrivateKey_bytes() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        PrivateKey privateKey = RSATool.getPrivateKey(privateKeyBytes);
        assertNotNull(privateKey);
    }

    /**
     * 测试{@link RSATool#getPublicKey}方法，验证从字符串还原公钥。
     */
    @Test
    public void testRsaGetPublicKey_string() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String publicKeyStr = Base64Tool.toEncode(keyPair.getPublic().getEncoded());
        PublicKey publicKey = RSATool.getPublicKey(publicKeyStr);
        assertNotNull(publicKey);
    }

    /**
     * 测试{@link RSATool#getPublicKey}方法，验证从字节数组还原公钥。
     */
    @Test
    public void testRsaGetPublicKey_bytes() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        PublicKey publicKey = RSATool.getPublicKey(publicKeyBytes);
        assertNotNull(publicKey);
    }

    /**
     * 测试{@link RSATool#encrypt}和{@link RSATool#decrypt}方法，验证RSA加解密。
     */
    @Test
    public void testRsaEncryptDecrypt() throws Exception {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String data = "Hello RSA!";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals("RSA encrypt/decrypt should preserve data", data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt}和{@link RSATool#decrypt}方法，验证中文字符加解密。
     */
    @Test
    public void testRsaEncryptDecrypt_chinese() throws Exception {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String data = "中文RSA测试";
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals("RSA should handle Chinese characters correctly", data, decrypted);
    }

    /**
     * 测试{@link RSATool#encrypt}和{@link RSATool#decrypt}方法，验证长文本分段加解密。
     */
    @Test
    public void testRsaEncryptDecrypt_longText() throws Exception {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("Hello RSA ");
        }
        String data = sb.toString();
        String encrypted = RSATool.encrypt(data, keyPair.getPublic());
        String decrypted = RSATool.decrypt(encrypted, keyPair.getPrivate());
        assertEquals("RSA should handle long text with segmented encryption", data, decrypted);
    }

    /**
     * 测试{@link RSATool#sign}和{@link RSATool#verifySign}方法，验证签名生成与验证。
     */
    @Test
    public void testRsaSignVerify() throws Exception {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String data = "data to sign";
        String sign = RSATool.sign(data, keyPair.getPrivate());
        boolean verified = RSATool.verifySign(data, sign, keyPair.getPublic());
        assertTrue("Signature should be valid", verified);
    }

    /**
     * 测试{@link RSATool#verifySign}方法，验证错误数据签名验证失败。
     */
    @Test
    public void testRsaSignVerify_wrongData() throws Exception {
        KeyPair keyPair = RSATool.getKeyPair("test_salt_12345");
        String data = "data to sign";
        String wrongData = "wrong data";
        String sign = RSATool.sign(data, keyPair.getPrivate());
        boolean verified = RSATool.verifySign(wrongData, sign, keyPair.getPublic());
        assertFalse("Signature should not be valid for wrong data", verified);
    }
}
