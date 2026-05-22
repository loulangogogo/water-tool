package io.github.loulangogogo.water.test.io;

import io.github.loulangogogo.water.io.ZipTool;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;

/**
 * 测试{@link ZipTool}类的功能。
 */
public class ZipToolTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * 测试zip方法，验证压缩单个文件的场景。
     */
    @Test
    public void testZip_singleFile() throws IOException {
        File sourceFile = tempFolder.newFile("source.txt");
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write("Hello Zip!".getBytes());
        }

        File zipFile = tempFolder.newFile("output.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipTool.zip(fos, fis, "source.txt");
        }

        assertTrue(zipFile.exists());
        assertTrue(zipFile.length() > 0);
    }

    /**
     * 测试zip方法，验证压缩多个文件的场景。
     */
    @Test
    public void testZip_files() throws IOException {
        File file1 = tempFolder.newFile("file1.txt");
        try (FileOutputStream fos = new FileOutputStream(file1)) {
            fos.write("content1".getBytes());
        }
        File file2 = tempFolder.newFile("file2.txt");
        try (FileOutputStream fos = new FileOutputStream(file2)) {
            fos.write("content2".getBytes());
        }

        File zipFile = tempFolder.newFile("output.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            ZipTool.zip(fos, file1, file2);
        }

        assertTrue(zipFile.exists());
        assertTrue(zipFile.length() > 0);
    }

    /**
     * 测试zip方法，验证压缩整个目录的场景。
     */
    @Test
    public void testZip_directory() throws IOException {
        File dir = tempFolder.newFolder("mydir");
        File file1 = new File(dir, "a.txt");
        try (FileOutputStream fos = new FileOutputStream(file1)) {
            fos.write("file a".getBytes());
        }
        File file2 = new File(dir, "b.txt");
        try (FileOutputStream fos = new FileOutputStream(file2)) {
            fos.write("file b".getBytes());
        }

        File zipFile = tempFolder.newFile("output.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            ZipTool.zip(fos, dir);
        }

        assertTrue(zipFile.exists());
    }

    /**
     * 测试zip方法，验证传入空文件数组的场景。
     */
    @Test
    public void testZip_emptyArray() throws IOException {
        File zipFile = tempFolder.newFile("empty.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            ZipTool.zip(fos);
        }
        // Should complete without error
    }

    /**
     * 测试zip方法，验证使用过滤器筛选文件的场景。
     */
    @Test
    public void testZip_withFilter() throws IOException {
        File file1 = tempFolder.newFile("include.txt");
        try (FileOutputStream fos = new FileOutputStream(file1)) {
            fos.write("include me".getBytes());
        }
        File file2 = tempFolder.newFile("exclude.tmp");
        try (FileOutputStream fos = new FileOutputStream(file2)) {
            fos.write("exclude me".getBytes());
        }

        File zipFile = tempFolder.newFile("filtered.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            ZipTool.zip(fos, f -> f.getName().endsWith(".txt"), file1, file2);
        }

        assertTrue(zipFile.exists());
    }

    /**
     * 测试unzip方法，验证解压到指定目录的场景。
     */
    @Test
    public void testUnzip_toDirectory() throws IOException {
        // Create a zip first
        File sourceFile = tempFolder.newFile("test_unzip.txt");
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write("test content for unzip".getBytes());
        }

        File zipFile = tempFolder.newFile("test.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipTool.zip(fos, fis, "test_unzip.txt");
        }

        File outDir = tempFolder.newFolder("unzipped");
        ZipTool.unzip(zipFile, outDir);

        File unzippedFile = new File(outDir, "test_unzip.txt");
        assertTrue(unzippedFile.exists());
    }

    /**
     * 测试unzip方法，验证解压到zip文件所在目录的场景。
     */
    @Test
    public void testUnzip_sameDirectory() throws IOException {
        File sourceFile = tempFolder.newFile("test_same.txt");
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write("content".getBytes());
        }

        File zipFile = tempFolder.newFile("test_same.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipTool.zip(fos, fis, "test_same.txt");
        }

        File result = ZipTool.unzip(zipFile);
        assertNotNull(result);
        assertTrue(result.exists());
    }

    /**
     * 测试unzip方法，验证通过文件路径字符串解压的场景。
     */
    @Test
    public void testUnzip_byPath() throws IOException {
        File sourceFile = tempFolder.newFile("test_path.txt");
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write("path test".getBytes());
        }

        File zipFile = tempFolder.newFile("test_path.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipTool.zip(fos, fis, "test_path.txt");
        }

        File result = ZipTool.unzip(zipFile.getAbsolutePath());
        assertNotNull(result);
    }

    /**
     * 测试unzip方法，验证指定字符集解压的场景。
     */
    @Test
    public void testUnzip_withCharset() throws IOException {
        File sourceFile = tempFolder.newFile("test_charset.txt");
        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write("中文内容".getBytes(StandardCharsets.UTF_8));
        }

        File zipFile = tempFolder.newFile("test_charset.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipTool.zip(fos, fis, "test_charset.txt");
        }

        File result = ZipTool.unzip(zipFile, StandardCharsets.UTF_8);
        assertNotNull(result);
    }
}
