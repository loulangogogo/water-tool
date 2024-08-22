package io.github.loulangogogo.water.io;

import io.github.loulangogogo.water.exception.IORuntimeException;
import io.github.loulangogogo.water.exception.InvalidParameterException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.*;

/*********************************************************
 ** io工具类
 ** 
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class IoTool {

    /**
     * 关闭io流（不抛出异常）
     *
     * @param closeable 可关闭的io对象
     * @author :loulan
     */
    public static void close(Closeable closeable) {
        IOUtils.closeQuietly(closeable);
    }

    /**
     * 关闭io流（不抛出异常）
     *
     * @param closeables 可关闭的io对象数组
     * @author :loulan
     */
    public static void close(Closeable... closeables) {
        IOUtils.closeQuietly(closeables);
    }

    /**
     * 将输入流复制到输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @author :loulan
     */
    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        IOUtils.copy(inputStream, outputStream);
    }

    /**
     * 获取流的前N个字节。返回这些字节，但流不受影响。需要支持标记/重置的流，或{@link PushbackInputStream}。
     * 如果流的字节数大于0但小于N，则剩余的字节数将为零。
     * （流的某部分字节可以知道流对应的类型）
     *
     * @param stream 要进行操作的流
     * @param limit  截取N个字节的长度，必须大于0
     * @return 截取到的流的前N个字节数组
     * @throws IOException io异常
     * @author :loulan
     */
    public static byte[] peekFirstNBytes(InputStream stream, int limit) throws IOException {
        if (limit < 0) {
            throw new InvalidParameterException("截取限制不能小于0");
        }

        if (!stream.markSupported()) {
            throw new IOException("该流不支持标记/重置");
        }

        stream.mark(limit);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(limit);
        copy(new BoundedInputStream(stream, limit), bos);

        int readBytes = bos.size();
        if (readBytes == 0) {
            throw new IORuntimeException("无效的流");
        }

        if (readBytes < limit) {
            bos.write(new byte[limit - readBytes]);
        }
        byte[] peekedBytes = bos.toByteArray();
        if (stream instanceof PushbackInputStream) {
            PushbackInputStream pin = (PushbackInputStream) stream;
            pin.unread(peekedBytes, 0, readBytes);
        } else {
            stream.reset();
        }

        return peekedBytes;
    }
}
