package io.github.loulangogogo.water.test.io;

import io.github.loulangogogo.water.io.FileTool;
import io.github.loulangogogo.water.io.IoTool;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * 测试{@link FileTool}和{@link IoTool}类的功能。
 */
public class IoToolTest {

    // FileTool tests
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * 测试{@link FileTool#getFileName}方法，验证获取文件名。
     */
    @Test
    public void testGetFileName() throws IOException {
        File file = tempFolder.newFile("test.txt");
        assertEquals("test.txt", FileTool.getFileName(file));
    }

    /**
     * 测试{@link FileTool#getFileName}方法，验证null参数返回空字符串。
     */
    @Test
    public void testGetFileName_null() {
        assertEquals("", FileTool.getFileName(null));
    }

    /**
     * 测试{@link FileTool#getFileName}方法，验证获取目录名。
     */
    @Test
    public void testGetFileName_directory() throws IOException {
        File dir = tempFolder.newFolder("mydir");
        assertEquals("mydir", FileTool.getFileName(dir));
    }

    /**
     * 测试{@link FileTool#getFileNamePrefix}方法，验证获取File对象文件名前缀。
     */
    @Test
    public void testGetFileNamePrefix_file() throws IOException {
        File file = tempFolder.newFile("report.pdf");
        assertEquals("report", FileTool.getFileNamePrefix(file));
    }

    /**
     * 测试{@link FileTool#getFileNamePrefix}方法，验证获取字符串文件名前缀。
     */
    @Test
    public void testGetFileNamePrefix_string() {
        assertEquals("report", FileTool.getFileNamePrefix((String) "report.pdf"));
    }

    /**
     * 测试{@link FileTool#getFileNamePrefix}方法，验证无扩展名文件名返回原名。
     */
    @Test
    public void testGetFileNamePrefix_noExtension() {
        assertEquals("README", FileTool.getFileNamePrefix((String) "README"));
    }

    /**
     * 测试{@link FileTool#getFileNamePrefix}方法，验证空字符串返回空。
     */
    @Test
    public void testGetFileNamePrefix_empty() {
        assertEquals("", FileTool.getFileNamePrefix((String) ""));
    }

    /**
     * 测试{@link FileTool#getFileNamePrefix}方法，验证null参数返回空。
     */
    @Test
    public void testGetFileNamePrefix_null() {
        assertEquals("", FileTool.getFileNamePrefix((String) null));
    }

    /**
     * 测试{@link FileTool#getFileNameSuffix}方法，验证获取File对象文件扩展名。
     */
    @Test
    public void testGetFileNameSuffix_file() throws IOException {
        File file = tempFolder.newFile("image.png");
        assertEquals("png", FileTool.getFileNameSuffix(file));
    }

    /**
     * 测试{@link FileTool#getFileNameSuffix}方法，验证获取字符串文件扩展名。
     */
    @Test
    public void testGetFileNameSuffix_string() {
        assertEquals("png", FileTool.getFileNameSuffix((String) "image.png"));
    }

    /**
     * 测试{@link FileTool#getFileNameSuffix}方法，验证无扩展名返回空。
     */
    @Test
    public void testGetFileNameSuffix_noExtension() {
        assertEquals("", FileTool.getFileNameSuffix((String) "README"));
    }

    /**
     * 测试{@link FileTool#getFileNameSuffix}方法，验证空字符串返回空。
     */
    @Test
    public void testGetFileNameSuffix_empty() {
        assertEquals("", FileTool.getFileNameSuffix((String) ""));
    }

    /**
     * 测试{@link FileTool#getFileNameSuffix}方法，验证null参数返回空。
     */
    @Test
    public void testGetFileNameSuffix_null() {
        assertEquals("", FileTool.getFileNameSuffix((String) null));
    }

    /**
     * 测试{@link FileTool#file}方法，验证路径字符串转File对象。
     */
    @Test
    public void testFile() {
        File file = FileTool.file("/tmp/test");
        assertNotNull(file);
        assertEquals("/tmp/test", file.getPath());
    }

    /**
     * 测试{@link FileTool#getFileMimeType}方法，验证通过File对象获取MIME类型。
     */
    @Test
    public void testGetFileMimeType_file() throws IOException {
        File file = tempFolder.newFile("test.txt");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write("Hello World".getBytes());
        }
        String mimeType = FileTool.getFileMimeType(file);
        assertNotNull(mimeType);
    }

    /**
     * 测试{@link FileTool#getFileMimeType}方法，验证通过InputStream获取MIME类型。
     */
    @Test
    public void testGetFileMimeType_inputStream() throws IOException {
        String content = "%PDF-1.4 test content";
        InputStream is = new ByteArrayInputStream(content.getBytes());
        String mimeType = FileTool.getFileMimeType(is);
        assertNotNull(mimeType);
    }

    // IoTool tests
    /**
     * 测试{@link IoTool#close}方法，验证安全关闭单个Closeable。
     */
    @Test
    public void testClose() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IoTool.close(baos);
        // Should not throw
    }

    /**
     * 测试{@link IoTool#close}方法，验证安全关闭多个Closeable。
     */
    @Test
    public void testCloseMultiple() throws IOException {
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        IoTool.close(baos1, baos2);
        // Should not throw
    }

    /**
     * 测试{@link IoTool#close}方法，验证关闭null不抛出异常。
     */
    @Test
    public void testClose_null() {
        IoTool.close((Closeable) null);
        // Should not throw
    }

    /**
     * 测试{@link IoTool#close}方法，验证关闭多个null不抛出异常。
     */
    @Test
    public void testClose_nulls() {
        IoTool.close(null, null, null);
        // Should not throw
    }

    /**
     * 测试{@link IoTool#copy}方法，验证InputStream到OutputStream的数据拷贝。
     */
    @Test
    public void testCopy_stream() throws IOException {
        String data = "Hello, World!";
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IoTool.copy(bais, baos);
        assertEquals(data, baos.toString());
    }

    /**
     * 测试{@link IoTool#copy}方法，验证ByteArrayOutputStream转InputStream。
     */
    @Test
    public void testCopy_ByteArrayOutputStream() throws IOException {
        ByteArrayOutputStream source = new ByteArrayOutputStream();
        source.write("test data".getBytes());
        InputStream target = IoTool.copy(source);
        byte[] buffer = new byte[100];
        int len = target.read(buffer);
        assertEquals("test data", new String(buffer, 0, len));
    }

    /**
     * 测试{@link IoTool#peekFirstNBytes}方法，验证预读InputStream前N字节。
     */
    @Test
    public void testPeekFirstNBytes() throws IOException {
        String data = "Hello, World! This is a test.";
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        bais.mark(5);
        byte[] peeked = IoTool.peekFirstNBytes(bais, 5);
        assertEquals("Hello", new String(peeked, 0, 5));
    }

    /**
     * 测试{@link IoTool#peekFirstNBytes}方法，验证支持mark的InputStream预读。
     */
    @Test
    public void testPeekFirstNBytes_supportsMark() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream("test".getBytes());
        // ByteArrayInputStream supports mark, so this should work
        byte[] peeked = IoTool.peekFirstNBytes(bais, 4);
        assertEquals("test", new String(peeked));
    }

    /**
     * 测试{@link IoTool#peekFirstNBytes}方法，验证limit为0时抛出异常。
     */
    @Test(expected = Exception.class)
    public void testPeekFirstNBytes_zeroLimit() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream("test".getBytes());
        // limit <= 0 should throw
        IoTool.peekFirstNBytes(bais, 0);
    }

    /**
     * 测试{@link IoTool#peekFirstNBytes}方法，验证负数limit抛出异常。
     */
    @Test(expected = Exception.class)
    public void testPeekFirstNBytes_negativeLimit() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream("test".getBytes());
        IoTool.peekFirstNBytes(bais, -1);
    }

    /**
     * 测试{@link IoTool#peekFirstNBytes}方法，验证不支持mark的InputStream抛出异常。
     */
    @Test(expected = Exception.class)
    public void testPeekFirstNBytes_noMark() throws IOException {
        // InputStream that doesn't support mark
        InputStream is = new InputStream() {
            private int pos = 0;
            private byte[] data = "test".getBytes();
            @Override
            public int read() {
                return pos < data.length ? data[pos++] : -1;
            }
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        IoTool.peekFirstNBytes(is, 4);
    }
}
