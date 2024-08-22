package io.github.loulangogogo.water.io;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.exception.IORuntimeException;
import io.github.loulangogogo.water.tool.CharsetTool;
import io.github.loulangogogo.water.tool.ObjectTool;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/*********************************************************
 ** zip工具类。<br>
 ** 如果要进行zip压缩添加密码，可以使用zip4j工具包(https://github.com/srikanth-lingala/zip4j)。<br>
 ** 如果要进行7z、tar、gz等等方式的加密操作，可以使用apache的compress工具包(https://commons.apache.org/proper/commons-compress/examples.html)。
 ** 
 ** @author 楼兰
 ** @since JDK-1.8
 *********************************************************/
public class ZipTool {

    // 路径标志，压缩目录的的时候防止目录出重复字符，所以添加这个路径标志
    private static String PATH_MARK = "loulan_zip_mark-";

    // 默认编码，使用平台相关编码
    private static final Charset DEFAULT_CHARSET = CharsetTool.defaultCharset();

    /**
     * 按照名称和输出流，将输入流压缩输出（对输出流只能压缩输出一次）
     * 如果你有多个输入流想要压缩到同一个输出流，这个不行，可以使用{@link ZipTool#zip(ZipOutputStream, InputStream, String)}
     *
     * @param outputStream 输出流
     * @param inputStream  要压缩的输入流
     * @param fileName     输入流文件名称(或这文件路径)
     * @throws IOException io流异常
     * @author :loulan
     */
    public static void zip(OutputStream outputStream, InputStream inputStream, String fileName) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zip(zipOutputStream, inputStream, fileName);
        // 关闭压缩流而不关闭输出的底层流，因为流是外部的，由外部来处理
        zipOutputStream.finish();
    }

    /**
     * 按照名称和输出流，将输入流压缩输出（可对同一个压缩输出连续压缩）
     *
     * @param zipOutputStream 压缩输出流
     * @param inputStream     要压缩的输入流
     * @param fileName        输入流文件名称(或这文件路径)
     * @throws IOException io流异常
     * @author :loulan
     */
    public static void zip(ZipOutputStream zipOutputStream, InputStream inputStream, String fileName) throws IOException {
        // 开始一个新的压缩条目
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        // 流数据复制
        IoTool.copy(inputStream, zipOutputStream);
        // 关闭压缩流的当前条目
        zipOutputStream.closeEntry();
        // 对压缩流进行刷新
        zipOutputStream.flush();
    }

    /**
     * 对指定的文件(或文件夹)进行压缩
     *
     * @param outputStream 压缩文件的输出流
     * @param files        可以是文件，也可以是文件夹
     * @throws IOException io流异常
     * @author :loulan
     */
    public static void zip(OutputStream outputStream, File... files) throws IOException {
        zip(outputStream, filterFile -> filterFile.exists(), files);
    }

    /**
     * 对指定的文件(或文件夹)进行压缩
     *
     * @param outputStream 压缩文件的输出流
     * @param files        可以是文件，也可以是文件夹
     * @param filter       过滤器；过滤的是文件，用来判断文件是否压缩，如果传入的是文件夹，那么过滤的是文件夹中的每一个文件
     * @throws IOException io流异常
     * @author :loulan
     */
    public static void zip(OutputStream outputStream, Predicate<File> filter, File... files) throws IOException {
        // 如果文件是null，那么后面也就不需要执行了
        if (ArrayTool.isEmpty(files)) {
            return;
        }

        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        for (File file : files) {
            if (file.isDirectory()) {
                // loutlan-  添加这个东西只是为了防止重复字符串的出现，因为后面要根据当前文件夹路径获取子文件路径
                zipDirectory(zipOutputStream, file, PATH_MARK + file.getParent(), filter);
            } else {
                // 如果是文件的话直接输出
                if (filter.test(file)) {
                    FileInputStream fis = new FileInputStream(file);
                    zip(zipOutputStream, fis, FileTool.getFileName(file));
                }
            }
        }

        zipOutputStream.finish();
    }

    /**
     * 解压缩
     *
     * @param zipFile 压缩文件
     * @param outFile 输出文件对象
     * @throws IORuntimeException io流异常
     * @author :loulan
     */
    public static void unzip(ZipFile zipFile, File outFile) throws IORuntimeException {
        // 获取所有的文件枚举
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        // 循环所有枚举
        while (entries.hasMoreElements()) {
            // 获取枚举条目
            Optional<ZipEntry> zipEntryOptional = Optional.ofNullable(entries.nextElement());
            zipEntryOptional.ifPresent(zipEntry -> {
                // 判断当前条目是否是文件，如果是文件夹直接过，文件的话就进去执行
                if (!zipEntry.isDirectory()) {
                    try {
                        // 获取当前条目文件的输入流
                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        // 获取输出文件的File对象
                        File newOutFile = FileTool.file(outFile.getPath() + "/" + zipEntry.getName());
                        // 判断文件对象的目录是否存在，不存在需要创建
                        if (!newOutFile.getParentFile().exists()) {
                            newOutFile.getParentFile().mkdirs();
                        }
                        // 创建文件输出流
                        FileOutputStream fos = new FileOutputStream(newOutFile);
                        IoTool.copy(inputStream, fos);
                        IoTool.close(fos, inputStream);
                    } catch (IOException e) {
                        throw new IORuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * 解压缩。默认编码UTF-8
     *
     * @param zipFile 压缩文件的File对象
     * @param outFile 输出文件File对象
     * @throws IOException io异常
     * @author :loulan
     */
    public static void unzip(File zipFile, File outFile) throws IOException {
        unzip(zipFile, outFile, DEFAULT_CHARSET);
    }

    /**
     * 解压缩
     *
     * @param zipFile 压缩文件的File对象
     * @param outFile 输出文件File对象
     * @param charset 编码设置
     * @throws IOException io异常
     * @author :loulan
     */
    public static void unzip(File zipFile, File outFile, Charset charset) throws IOException {
        unzip(new ZipFile(zipFile, charset), outFile);
    }

    /**
     * 解压缩。解到压缩文件相同的目录（默认编码UTF-8）
     *
     * @param zipFile 压缩文件的File对象
     * @return 解压缩之后的文件对象
     * @throws IOException io异常
     * @author :loulan
     */
    public static File unzip(File zipFile) throws IOException {
        return unzip(zipFile, DEFAULT_CHARSET);
    }

    /**
     * 解压缩。解到压缩文件相同的目录
     *
     * @param zipFile 压缩文件的File对象
     * @param charset 编码设置
     * @return 解压缩之后的文件对象
     * @throws IOException io异常
     * @author :loulan
     */
    public static File unzip(File zipFile, Charset charset) throws IOException {
        File outFile = FileTool.file(zipFile.getParent() + "/" + FileTool.getFileNamePrefix(zipFile));
        unzip(zipFile, outFile, charset);
        return outFile;
    }

    /**
     * 解压缩。解到压缩文件相同的目录
     *
     * @param zipFilePath 压缩文件的路径
     * @param charset     编码设置
     * @return 解压缩之后的文件对象
     * @throws IOException io异常
     * @author :loulan
     */
    public static File unzip(String zipFilePath, Charset charset) throws IOException {
        return unzip(new File(zipFilePath), charset);
    }

    /**
     * 解压缩。解到压缩文件相同的目录.(默认编码UTF-8)
     *
     * @param zipFilePath 压缩文件的路径
     * @return 解压缩之后的文件对象
     * @throws IOException io异常
     * @author :loulan
     */
    public static File unzip(String zipFilePath) throws IOException {
        return unzip(zipFilePath, DEFAULT_CHARSET);
    }

    /**
     * 压缩文件夹
     *
     * @param zipOutputStream 压缩输出流
     * @param dirFile         文件夹的file对象
     * @param currentDirPath  当前文件夹路径，为什么设置这个呢？因为压缩需要设置ZipEntry,所以为了获取目录下的文件的相对文件夹的路径，所以需要提供当前文件夹的路径
     * @param filter          过滤器，过滤的是文件，而不是文件夹
     * @author :loulan
     */
    private static void zipDirectory(ZipOutputStream zipOutputStream, File dirFile, String currentDirPath, Predicate<File> filter) throws IOException {
        // 如果文件是null，那么后面也就不需要执行了
        if (ObjectTool.isNull(dirFile)) {
            return;
        }

        // 判断file是文件还是文件夹
        if (dirFile.isDirectory()) {
            // 如果是文件夹
            File[] files = dirFile.listFiles();
            for (File tempFile : files) {
                zipDirectory(zipOutputStream, tempFile, currentDirPath, filter);
            }
        } else {
            // 如果是文件,然后进行过滤器进行过滤判断
            if (filter.test(dirFile)) {
                FileInputStream fis = new FileInputStream(dirFile);
                // PATH_MARK 是路径标志，因为后面要进行路径替换，担心同样的字符串后面也有，所以这里设置标志，这样就可以避免重复字符串了。
                String path = PATH_MARK + dirFile.getPath();
                // 替换掉当前文件夹路径部分，剩下的就是文件相对于文件夹的相对路径了
                String entryPath = path.replace(currentDirPath, "");
                zip(zipOutputStream, fis, entryPath);
                IoTool.close(fis);
            }
        }
    }
}
