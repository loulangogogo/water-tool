package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.collection.ArrayTool;
import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.map.MapTool;
import io.github.loulangogogo.water.stream.CollectorTool;
import io.github.loulangogogo.water.tool.AssertTool;
import io.github.loulangogogo.water.tool.ObjectTool;
import io.github.loulangogogo.water.tool.ReflectTool;
import io.github.loulangogogo.water.tool.StrTool;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;

/*********************************************************
 ** sql查询执行响应类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLResponseQuery extends ExecuteSQLResponse {

    // sql查询数据结果对象
    private ResultSet resultSet;

    private ExecuteSQLResponseQuery(Connection conn, ResultSet resultSet) {
        this.resultSet = resultSet;
        this.connection = conn;
    }

    /**
     * 实例化对象
     *
     * @param conn      数据库链接对象
     * @param resultSet 查询数据结果
     * @return {@link ExecuteSQLResponseQuery}sql查询执行响应对象
     * @author :loulan
     */
    public static ExecuteSQLResponseQuery instance(Connection conn, ResultSet resultSet) {
        AssertTool.notNull(conn, "数据库连接对象不能为空。");
        AssertTool.notNull(resultSet, "查询数据结果不能为空。");
        return new ExecuteSQLResponseQuery(conn, resultSet);
    }


    /**
     * 结果集的处理器，从结果集中获取数据(返回{@link List < Map <String,Object>>})
     *
     * @param isCamel 是否转换为驼峰是的属性名称
     * @return 集合数据
     * @author :loulan
     */
    public List<Map<String, Object>> toMap(boolean isCamel) {
        try {
            // 查询结果解析
            List<Map<String, Object>> list = CollTool.list();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Map<String, Object> map = MapTool.map();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(isCamel ? StrTool.underlineToCamel(metaData.getColumnLabel(i)) : metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                list.add(map);
            }

            return list;
        } catch (SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * 结果集的处理器，从结果集中获取数据(返回指定类型的对象)
     *
     * @param clzz 数据对象的类型
     * @return 集合数据
     * @author :loulan
     */
    public <T> List<T> toObj(Class<T> clzz) {
        try {
            // 查询结果解析
            List<T> list = CollTool.list();

            // 获取表字段名（驼峰式的），以及对应的字段
            Map<String, Field> tFieldMap = ArrayTool.asList(ReflectTool.getFields(clzz)).stream()
                    .collect(CollectorTool.toMap(
                            o -> o.getName(),
                            o -> o,
                            // 如果遇到字段名重复的以第一个为准。因为按照反射获取的字段属性顺序，子类自己的排在前面，父类的属性排在后面
                            (o1, o2) -> o1)
                    );
            // 循环解析每一条结果数据
            while (resultSet.next()) {
                // 获取当前行的元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // 循环每一列数据将数据分析获取出来
                T t = clzz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Field field = tFieldMap.get(StrTool.underlineToCamel(metaData.getColumnLabel(i)));
                    if (ObjectTool.isNull(field)) {
                        // 如果对象属性中不存在这一列数据，那么也就不取用这一列的数据了
                        continue;
                    }

                    // 判断是否是基本数据类型 (基本数据类型和一些特殊包装数据类型需要特别处理)
                    Class<?> fieldType = field.getType();
                    if (!fieldType.isPrimitive()) {
                        // 包装数据类型值判断获取
                        Object value = null;
                        if (String.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getString(i);
                        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getBigDecimal(i);
                        } else if (Blob.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getBlob(i);
                        } else if (byte[].class.isAssignableFrom(fieldType)) {
                            value = resultSet.getBytes(i);
                        } else if (fieldType.isArray()) {
                            value = resultSet.getArray(i);
                        } else if (Object.class.getName().equals(fieldType.getName())) {
                            // Object不能使用isAssignableFrom，因为Object是所有包装类的基类，所以采用类的全限定名称进行判断
                            value = resultSet.getObject(i);
                        } else {
                            // 如果不是以上的包装类型，那么就直接通过这个方法来进行数据的获取
                            value = resultSet.getObject(i, fieldType);
                        }
                        ReflectTool.setFieldValue(t, field, value);
                    } else {
                        // 基本数据类型数据值判断获取
                        Object value = null;
                        if (int.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getInt(i);
                        } else if (double.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getDouble(i);
                        } else if (float.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getFloat(i);
                        } else if (long.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getLong(i);
                        } else if (byte.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getByte(i);
                        } else if (boolean.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getBoolean(i);
                        } else if (short.class.isAssignableFrom(fieldType)) {
                            value = resultSet.getShort(i);
                        } else if (char.class.isAssignableFrom(fieldType)) {
                            String tempValue = resultSet.getString(i);
                            value = StrTool.isNotEmpty(tempValue) ? tempValue.charAt(0) : ' ';
                        }
                        ReflectTool.setFieldValue(t, field, value);
                    }
                }
                list.add(t);
            }

            return list;
        } catch (SQLException ex) {
            throw new SQLRuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new SQLRuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new SQLRuntimeException(ex);
        }
    }
}
