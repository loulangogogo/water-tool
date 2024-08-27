package io.github.loulangogogo.water.json;

import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.StrTool;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/*********************************************************
 ** Json解析后的对象，继承自{@link LinkedHashMap}
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class JsonMap extends LinkedHashMap<String, Object> {

    /**
     * JsonMap的构造器方法
     *
     * @param map {@link Map}集合对象
     * @author :loulan
     */
    private JsonMap(Map<String, Object> map) {
        putAll(map);
    }

    /**
     * JsonMap的构造器方法
     *
     * @author :loulan
     */
    public JsonMap() {
    }

    /**
     * JsonMap的实例化方法
     *
     * @param map {@link Map}集合对象
     * @return JsonMap对象
     * @author :loulan
     */
    public static JsonMap instance(Map<String, Object> map) {
        return new JsonMap(map);
    }

    /**
     * JsonMap的实例化方法
     *
     * @return JsonMap对象
     * @author :loulan
     */
    public static JsonMap instance() {
        return new JsonMap();
    }

    /**
     * h获取数据并且数据为json字符串
     *
     * @param key 数据值对应的key
     * @return json数据值
     * @author :loulan
     */
    public String getJson(String key) {
        return getJsonString(key);
    }

    /**
     * h获取数据并且数据为json字节数组
     *
     * @param key 数据值对应的key
     * @return json数据值
     * @author :loulan
     */
    public byte[] getJsonBytes(String key) {
        Object obj = get(key);
        if (ObjectTool.isNull(obj)) {
            return null;
        } else {
            return JsonTool.toJsonBytes(obj);
        }
    }

    /**
     * h获取数据并且数据为json字符串
     *
     * @param key 数据值对应的key
     * @return json数据值
     * @author :loulan
     */
    public String getJsonString(String key) {
        Object obj = get(key);
        if (ObjectTool.isNull(obj)) {
            return null;
        } else {
            return JsonTool.toJsonString(obj);
        }
    }

    /**
     * 获取数据并且数据为字符串
     *
     * @param key 数据值对应的key
     * @return 字符串类型的数据值
     * @author :loulan
     */
    public String getString(String key) {
        Object obj = get(key);
        if (ObjectTool.isNull(obj)) {
            return null;
        } else {
            return String.valueOf(get(key));
        }
    }

    /**
     * 获取{@link Integer}类型的数据值
     *
     * @param key 数据值对应的key
     * @return Integer类型的数据值
     * @author :loulan
     */
    public Integer getInt(String key) {
        String value = getString(key);
        if (StrTool.isEmpty(value)) {
            return null;
        } else {
            return Integer.valueOf(value);
        }
    }

    /**
     * 获取{@link Long}类型的数据值
     *
     * @param key 数据值对应的key
     * @return Long类型的数据值
     * @author :loulan
     */
    public Long getLong(String key) {
        String value = getString(key);
        if (StrTool.isEmpty(value)) {
            return null;
        } else {
            return Long.valueOf(value);
        }
    }

    /**
     * 获取{@link Float}类型的数据值
     *
     * @param key 数据值对应的key
     * @return Float类型的数据值
     * @author :loulan
     */
    public Float getFloat(String key) {
        String value = getString(key);
        if (StrTool.isEmpty(value)) {
            return null;
        } else {
            return Float.valueOf(value);
        }
    }

    /**
     * 获取{@link Double}类型的数据值
     *
     * @param key 数据值对应的key
     * @return Double类型的数据值
     * @author :loulan
     */
    public Double getDouble(String key) {
        String value = getString(key);
        if (StrTool.isEmpty(value)) {
            return null;
        } else {
            return Double.valueOf(value);
        }
    }

    /**
     * 获取{@link BigDecimal}类型的数据值
     *
     * @param key 数据值对应的key
     * @return BigDecimal类型的数据值
     * @author :loulan
     */
    public BigDecimal getBigDecimal(String key) {
        String value = getString(key);
        if (StrTool.isEmpty(value)) {
            return null;
        } else {
            return new BigDecimal(value);
        }
    }
}
