package io.github.loulangogogo.water.io;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

/*********************************************************
 ** 资源工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ResourceTool {

    /**
     * 读取classpath下的资源为输入流
     *
     * @param resource 资源在classpath下的路径
     * @return 资源的输入流, 如果资源不存在返回null
     * @author :loulan
     */
    public static InputStream getInputStream(String resource) throws IOException {
        URL url = getResource(resource);
        return url.openStream();
    }

    /**
     * 读取classpath下的资源为{@link File}文件
     *
     * @param resource 资源在classpath下的路径
     * @return File文件对象, 如果资源不存在就返回null
     * @author :loulan
     */
    public static File getFile(String resource) throws IOException, URISyntaxException {
        URL url = getResource(resource);
        return new File(url.toURI());
    }

    /**
     * 获取资源的{@link URL}
     *
     * @param resource 资源在classpath下的路径
     * @return 资源的URL, 如果资源不存在返回null
     * @author :loulan
     */
    public static URL getResource(String resource) throws IOException {
        return IOUtils.resourceToURL(resource);
    }

    /**
     * 获取资源的{@link URL}
     *
     * @param resource    资源在classpath下的路径
     * @param classLoader 类加载器
     * @return 资源的URL, 如果资源不存在返回null
     * @author :loulan
     */
    public static URL getResource(String resource, ClassLoader classLoader) throws IOException {
        return IOUtils.resourceToURL(resource, classLoader);
    }

    /**
     * 读取资源的内容
     *
     * @param resource 资源在classpath下的路径
     * @param charset  编码方式
     * @return 资源的内容，如果资源不存在则返回null
     * @author :loulan
     */
    public static String readStr(String resource, Charset charset) throws IOException {
        return IOUtils.resourceToString(resource, charset);
    }

    /**
     * 读取资源的内容
     *
     * @param resource    资源在classpath下的路径
     * @param charset     编码方式
     * @param classLoader 类加载器
     * @return 资源的内容，如果资源不存在则返回null
     * @author :loulan
     */
    public static String readStr(String resource, Charset charset, ClassLoader classLoader) throws IOException {

        return IOUtils.resourceToString(resource, charset, classLoader);

    }

    /**
     * 获取资源的字节数组数据
     *
     * @param resource 资源在classpath下的路径
     * @return 资源的字节数组数据, 如果资源不存在返回null
     * @author :loulan
     */
    public static byte[] readBytes(String resource) throws IOException {
        return IOUtils.resourceToByteArray(resource);
    }

    /**
     * 获取资源的字节数组数据
     *
     * @param resource    资源在classpath下的路径
     * @param classLoader 类加载器
     * @return 资源的字节数组数据, 如果资源不存在返回null
     * @author :loulan
     */
    public static byte[] readBytes(String resource, ClassLoader classLoader) throws IOException {
        return IOUtils.resourceToByteArray(resource, classLoader);
    }
}
