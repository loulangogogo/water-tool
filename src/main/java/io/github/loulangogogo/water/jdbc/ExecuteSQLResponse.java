package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*********************************************************
 ** sql执行响应对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
abstract class ExecuteSQLResponse {

    // 数据库链接对象
    protected Connection connection;

    /**
     * 关闭连接
     *
     * @author :loulan
     */
    public ExecuteSQLResponse close() {
        AssertTool.notNull(connection, "数据源连接不能为空。");
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
            return this;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * 进行事务的提交
     *
     * @author :loulan
     */
    public ExecuteSQLResponse commit() {
        try {
            connection.commit();
            return this;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * 进行事务的回滚
     *
     * @author :loulan
     */
    public ExecuteSQLResponse rollback() {
        try {
            connection.rollback();
            return this;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLRuntimeException(ex);
        }
    }
}
