package io.github.loulangogogo.water.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.loulangogogo.water.exception.JsonException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*********************************************************
 ** json工具类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class JsonTool extends JsonToolObjectMapper {
    /**
     * 将对象转化为json字符串
     *
     * @param obj 要进行json转化的对象
     * @return 转化完成的对象
     * @author :loulan
     */
    public static String toJson(Object obj) {
        return toJsonString(obj);
    }

    /**
     * 将对象转化为json字节数组
     *
     * @param obj 要进行json转化的对象
     * @return 转化完成的字节数组
     * @author :loulan
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (Exception ex) {
            throw new JsonException("obj转json异常", ex);
        }
    }

    /**
     * 将对象转化为json字符串
     *
     * @param obj 要进行json转化的对象
     * @return 转化完成的对象
     * @author :loulan
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new JsonException("obj转json异常", ex);
        }
    }

    /**
     * 解析json为指定的对象
     *
     * @param <T>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parseObj(String json, Class<T> clzz) {
        try {
            T t = objectMapper.readValue(json, clzz);
            return t;
        } catch (Exception ex) {
            throw new JsonException("json转obj异常", ex);
        }
    }

    /**
     * 解析json为指定的对象
     *
     * @param <T>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parseObj(byte[] json, Class<T> clzz) {
        try {
            T t = objectMapper.readValue(json, clzz);
            return t;
        } catch (Exception ex) {
            throw new JsonException("json转obj异常", ex);
        }
    }

    /**
     * 解析json为指定类型的List集合
     *
     * @param <T>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 指定类型的List集合
     * @author :loulan
     */
    public static <T> List<T> parseList(String json, Class<T> clzz) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clzz);
        return parse(json, collectionType);
    }

    /**
     * 解析json为指定类型的List集合
     *
     * @param <T>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 指定类型的List集合
     * @author :loulan
     */
    public static <T> List<T> parseList(byte[] json, Class<T> clzz) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clzz);
        return parse(json, collectionType);
    }

    /**
     * 解析json为Map对象
     *
     * @param json 要进行解析的json数据
     * @return 解析后的Map对象
     * @author :loulan
     */
    public static Map<String, Object> parseMap(String json) {
        return parseMap(json, Object.class);
    }

    /**
     * 解析json为Map对象
     *
     * @param json 要进行解析的json数据
     * @return 解析后的Map对象
     * @author :loulan
     */
    public static Map<String, Object> parseMap(byte[] json) {
        return parseMap(json, Object.class);
    }

    /**
     * 解析json为指定类型的Map对象
     *
     * @param <V>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 解析后指定类型的Map对象
     * @author :loulan
     */
    public static <V> Map<String, V> parseMap(String json, Class<V> clzz) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, clzz);
        return parse(json, mapType);
    }

    /**
     * 解析json为指定类型的Map对象
     *
     * @param <V>  泛型
     * @param json 要进行解析的json数据
     * @param clzz 指定类型的class对象
     * @return 解析后指定类型的Map对象
     * @author :loulan
     */
    public static <V> Map<String, V> parseMap(byte[] json, Class<V> clzz) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, clzz);
        return parse(json, mapType);
    }

    /**
     * 解析json为{@link JsonMap}对象
     *
     * @param json 要进行解析的json数据
     * @return 解析后JsonMap对象
     * @author :loulan
     */
    public static JsonMap parseJsonMap(String json) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(JsonMap.class, String.class, Object.class);
        return parse(json, mapType);
    }

    /**
     * 解析json为{@link JsonMap}对象
     *
     * @param json 要进行解析的json数据
     * @return 解析后JsonMap对象
     * @author :loulan
     */
    public static JsonMap parseJsonMap(byte[] json) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(JsonMap.class, String.class, Object.class);
        return parse(json, mapType);
    }

    /**
     * 解析json为{@link JsonMap}对象得list集合
     *
     * @param json 要进行解析的json数据
     * @return 解析后JsonMap对象的list集合
     * @author :loulan
     */
    public static List<JsonMap> parseListJsonMap(String json) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(JsonMap.class, String.class, Object.class);
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, mapType);
        return parse(json, collectionType);
    }

    /**
     * 解析json为{@link JsonMap}对象得list集合
     *
     * @param json 要进行解析的json数据
     * @return 解析后JsonMap对象的list集合
     * @author :loulan
     */
    public static List<JsonMap> parseListJsonMap(byte[] json) {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(JsonMap.class, String.class, Object.class);
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, mapType);
        return parse(json, collectionType);
    }

    /**
     * 将json解析为指定的类型
     *
     * @param <T>          泛型
     * @param json         要进行解析的json数据
     * @param valueTypeRef 类型指引
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parse(String json, TypeReference<T> valueTypeRef) {
        return parse(json, objectMapper.constructType(valueTypeRef));
    }

    /**
     * 将json解析为指定的类型
     *
     * @param <T>          泛型
     * @param json         要进行解析的json数据
     * @param valueTypeRef 类型指引
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parse(byte[] json, TypeReference<T> valueTypeRef) {
        return parse(json, objectMapper.constructType(valueTypeRef));
    }

    /**
     * 将json解析为指定的类型（内部使用）
     *
     * @param <T>      泛型
     * @param json     要进行解析的json数据
     * @param javaType 要解析成的类型对象
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parse(String json, JavaType javaType) {
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception ex) {
            throw new JsonException("json解析异常", ex);
        }
    }

    /**
     * 将json解析为指定的类型（内部使用）
     *
     * @param <T>      泛型
     * @param json     要进行解析的json数据
     * @param javaType 要解析成的类型对象
     * @return 解析后的指定类型
     * @author :loulan
     */
    public static <T> T parse(byte[] json, JavaType javaType) {
        try {
            return objectMapper.readValue(json, javaType);
        } catch (Exception ex) {
            throw new JsonException("json解析异常", ex);
        }
    }

    /**
     * 获取类型工厂{@link TypeFactory}，通过类型工厂可以更好的生成JavaType
     *
     * @return 类型工厂
     * @author :loulan
     */
    public static TypeFactory getTypeFactory() {
        return objectMapper.getTypeFactory();
    }
}
