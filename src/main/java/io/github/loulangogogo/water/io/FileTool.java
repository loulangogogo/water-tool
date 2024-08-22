package io.github.loulangogogo.water.io;

import io.github.loulangogogo.water.enums.FileMagic;
import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*********************************************************
 ** 文件工具类
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class FileTool {

    /**
     * 获取文件的魔数。
     * 【判断文件的类型，即使用户修改了文件后缀，也能正确读取】
     *
     * @param file 文件
     * @return 文件魔数枚举
     * @throws IOException io异常
     * @author :loulan
     */
    public static FileMagic getFileMagic(File file) throws IOException {
        return FileMagic.valueOf(file);
    }

    /**
     * 获取流数据文件的魔数。
     * 【判断文件的类型，即使用户修改了文件后缀，也能正确读取】
     *
     * @param ins 流数据
     * @return 文件魔数枚举
     * @throws IOException io异常
     * @author :loulan
     */
    public static FileMagic getFileMagic(InputStream ins) throws IOException {
        return FileMagic.valueOf(ins);
    }

    /**
     * 获取文件名
     *
     * @param file 文件对象
     * @return 文件名(如果file是个文件夹 ， 那么返回的就是文件夹的名称)
     * @author :loulan
     */
    public static String getFileName(File file) {
        if (ObjectTool.isNull(file)) {
            return "";
        }
        return file.getName();
    }

    /**
     * 获取文件名的主名（没有后缀）
     *
     * @param file 文件对象
     * @return 文件主名（不包含后缀）
     * @author :loulan
     */
    public static String getFileNamePrefix(File file) {
        String fileName = getFileName(file);
        return getFileNamePrefix(fileName);
    }

    /**
     * 获取文件名的主名（没有后缀）
     *
     * @param fileName 文件名称
     * @return 文件主名（不包含后缀）
     * @author :loulan
     */
    public static String getFileNamePrefix(String fileName) {
        if (StrTool.isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    /**
     * 获取文件名的后缀（不带点）
     *
     * @param file 文件对象
     * @return 文件名的后缀（不带点）
     * @author :loulan
     */
    public static String getFileNameSuffix(File file) {
        String fileName = getFileName(file);
        return getFileNameSuffix(fileName);
    }

    /**
     * 获取文件名的后缀（不带点）
     *
     * @param fileName 文件名
     * @return 文件名的后缀（不带点）
     * @author :loulan
     */
    public static String getFileNameSuffix(String fileName) {
        if (StrTool.isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 根据路径获取生成一个File文件对象
     *
     * @param path 文件路径
     * @return File 对象
     * @author :loulan
     */
    public static File file(String path) {
        return new File(path);
    }

    /**
     * 获取输入流文件内容类型
     *
     * @param inputStream 输入流
     * @return 类型，返回值如： image/jpeg
     * @author :loulan
     */
    public static String getFileMimeType(InputStream inputStream) {
        try {
            String detect = new Tika().detect(inputStream);
            return detect;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 获取文件的内容类型
     *
     * @param file 文件对象
     * @return 类型，返回值如： image/jpeg
     * @author :loulan
     */
    public static String getFileMimeType(File file) {
        try {
            String detect = new Tika().detect(file);
            return detect;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
