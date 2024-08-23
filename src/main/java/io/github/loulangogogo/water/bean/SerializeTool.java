package io.github.loulangogogo.water.bean;

import org.apache.commons.lang3.SerializationUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/*********************************************************
 ** Description: 序列化工具,该工具主要来自apache的lang3包{@link SerializationUtils}
 ** 
 ** @author 楼兰
 ** @since 8
 *********************************************************/
public class SerializeTool {

    /**
     * Description :通过序列化方式进行克隆，该方法必须要实现{@link Serializable}接口
     *
     * @param obj 要进行克隆的对象
     * @return 克隆之后的对象
     * @author :loulan
     */
    public static <T extends Serializable> T clone(T obj) {
        return SerializationUtils.clone(obj);
    }

    /**
     * Description :将一个对象序列化为一个byte数组
     *
     * @param obj 要进行序列化的对象
     * @return 序列化之后的byte数组
     * @author :loulan
     */
    public static byte[] serialize(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    /**
     * Description :将一个对象序列化到一个输出流中
     *
     * @param obj          要进行序列化的对象
     * @param outputStream 输出流
     * @author :loulan
     */
    public static void serialize(Serializable obj, OutputStream outputStream) {
        SerializationUtils.serialize(obj, outputStream);
    }

    /**
     * Description :将byte数组进行反序列化为一个对象
     *
     * @param objectData 要进行反序列化的byte数组
     * @return 反序列化后的对象
     * @author :loulan
     */
    public static <T> T deserialize(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }

    /**
     * Description :将一个输入流反序列化为一个对象
     *
     * @param inputStream 要进行反序列化的输入流
     * @return 反序列化后的对象
     * @author :loulan
     */
    public static <T> T deserialize(InputStream inputStream) {
        return SerializationUtils.deserialize(inputStream);
    }
}
