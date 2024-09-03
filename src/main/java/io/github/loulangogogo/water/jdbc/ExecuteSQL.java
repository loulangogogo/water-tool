package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*********************************************************
 ** 克执行sql
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
abstract class ExecuteSQL {

    // sql预编译对象
    protected PreparedStatement preparedStatement;

    // 数据库链接对象
    protected Connection connection;

    // 是否自动提交（就是不开启事务）。默认是不开启事务
    protected boolean isAutoCommit = true;


    /**
     * 执行sql
     *
     * @return {@link ExecuteSQLResponse}sql执行响应对象
     * @author :loulan
     */
    public abstract ExecuteSQLResponse execute();

    /**
     * 开启事务
     *
     * @return {@link ExecuteSQL}可执行对象
     * @author :loulan
     */
    public ExecuteSQL openTransaction() {
        this.isAutoCommit = false;
        return this;
    }

    /**
     * 关闭事务
     *
     * @return {@link ExecuteSQL}可执行对象
     * @author :loulan
     */
    public ExecuteSQL closeTransaction() {
        this.isAutoCommit = true;
        return this;
    }
}
