package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.collection.CollTool;
import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.map.MapTool;
import io.github.loulangogogo.water.tool.AssertTool;
import io.github.loulangogogo.water.tool.StrTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*********************************************************
 ** sql执行请求对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
abstract class ExecuteSQLRequest {

    // sql，可以是携带占位符号的sql，#{xxx} 参数占位,${xxx} sql注入
    protected String sql;

    // 参数，占位符对应的参数。参数的数量必须大于等于占位符的数量
    protected Map<String, Object> paramMap = new ConcurrentHashMap<>();


    /**
     * 设置sql语句
     *
     * @param _sql 执行的sql
     * @return {@link ExecuteSQLRequest}执行请求对象
     * @author :loulan
     */
    public ExecuteSQLRequest sql(String _sql) {
        AssertTool.notEmpty(_sql, "sql不能为空");
        this.sql = sql;
        return this;
    }

    /**
     * 设置占位符参数
     *
     * @param key   占位参数
     * @param value 参数值
     * @return {@link ExecuteSQLRequest}执行请求对象
     * @author :loulan
     */
    public ExecuteSQLRequest param(String key, Object value) {
        AssertTool.notEmpty(key, "占位参数不能为空");
        AssertTool.notNull(value, "参数值不能为空");
        this.paramMap.put(key, value);
        return this;
    }

    /**
     * 构建 sql 可执行对象
     *
     * @param _conn 数据库链接对象
     * @return {@link ExecuteSQL}sql可执行对象
     * @author :loulan
     */
    public abstract ExecuteSQL build(Connection _conn);

    /**
     * 获取sql预编译对象
     *
     * @param conn 数据库链接对象
     * @return {@link PreparedStatement}sql预编译对象
     * @author :loulan
     */
    protected PreparedStatement getPreparedStatement(Connection conn) throws SQLException {
        AssertTool.notNull(conn, "数据源连接不能为空。");
        AssertTool.isFalse(conn.isClosed(), "当前数据源连接已经关闭。");
        AssertTool.notEmpty(sql, "sql语句不能为空。");

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        // 判断是否需要进行参数的替换组合
        if (MapTool.isEmpty(paramMap)) {
            return preparedStatement;
        }

        String dealSQL = this.sql;

        // 要注入的sql（先进行这个的替换，因为这个是直接字符串的替换）
        List<String> sqlNames = StrTool.regexStrs(dealSQL, "\\$\\{(\\s*\\S+\\s*)\\}", 1);
        // sql的注入直接进行拼接替换
        if (CollTool.isNotEmpty(sqlNames)) {
            // 进行遍历替换
            for (String sqlName : sqlNames) {
                Object o = paramMap.get(sqlName.trim());
                // 判断数据必须存在，否则抛出异常，而且这个数据必须是String类型的
                AssertTool.notNull(o, sqlName + "参数的数据不存在。");
                // 替换条所有的注入sql
                dealSQL = dealSQL.replaceAll("\\$\\{\\s*" + sqlName.trim() + "\\s*\\}", String.valueOf(o));
            }
        }

        // 要占位的参数（这个后替换，因为这个是替换为 ? 交给预编译处理）
        List<String> paramNames = StrTool.regexStrs(dealSQL, "\\#\\{(\\s*\\S+\\s*)\\}", 1);
        List<Object> params = CollTool.list();
        if (CollTool.isNotEmpty(paramNames)) {
            // 将所有的参数占位符替换为？占位符
            dealSQL = dealSQL.replaceAll("\\#\\{\\s*\\S+\\s*\\}", "?");
            for (String paramName : paramNames) {
                Object o = paramMap.get(paramName.trim());
                // 判断数据必须存在，否则抛出异常
                AssertTool.notNull(o, paramName + "参数的数据不存在。");
                params.add(o);
            }
        }

        // 捕获所有的占位符?
        List<String> marks = StrTool.regexStrs(dealSQL, "(\\?)", 1);
        if (CollTool.isNotEmpty(marks)) {
            // 产看确定参数和占位符数量的对比
            AssertTool.notEmpty(params, "参数数量少于占位符数量。");
            AssertTool.isTrue(params.size() >= marks.size(), "参数数量少于占位符数量。");

            // 进行占位符替换数据
            for (int i = 1; i <= marks.size(); i++) {
                preparedStatement.setObject(i, params.get(i-1));
            }
        }

        return preparedStatement;
    }
}
