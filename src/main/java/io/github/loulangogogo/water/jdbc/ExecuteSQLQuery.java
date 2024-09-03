package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*********************************************************
 ** sql查询执行对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLQuery extends ExecuteSQL {

    /**
     * 有参构造方法
     *
     * @param _connection        数据库链接对象
     * @param _preparedStatement sql预编译对象
     * @return {@link ExecuteSQL}可执行对象
     * @author :loulan
     */
    public ExecuteSQLQuery(Connection _connection, PreparedStatement _preparedStatement) {
        AssertTool.notNull(_connection, "数据库链接对象不能为空");
        AssertTool.notNull(_preparedStatement, "sql预编译对象不能为空");
        this.connection = _connection;
        this.preparedStatement = _preparedStatement;
    }

    /**
     * 执行sql查询
     *
     * @return {@link ExecuteSQLResponseQuery}查询执行响应对象
     * @author :loulan
     */
    @Override
    public ExecuteSQLResponseQuery execute() {
        try {
            connection.setAutoCommit(isAutoCommit);
            return ExecuteSQLResponseQuery.instance(connection, preparedStatement.executeQuery());
        } catch (SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }
}
