package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*********************************************************
 ** sql修改执行对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLFix extends ExecuteSQL {

    /**
     * 有参构造方法
     *
     * @param _connection        数据库链接对象
     * @param _preparedStatement sql预编译对象
     * @return {@link ExecuteSQL}可执行对象
     * @author :loulan
     */
    public ExecuteSQLFix(Connection _connection, PreparedStatement _preparedStatement) {
        AssertTool.notNull(_connection, "数据库链接对象不能为空");
        AssertTool.notNull(_preparedStatement, "sql预编译对象不能为空");
        this.connection = _connection;
        this.preparedStatement = _preparedStatement;
    }

    /**
     * 执行sql语句
     *
     * @return {@link ExecuteSQLResponseFix}修改结果响应对象
     * @author :loulan
     */
    @Override
    public ExecuteSQLResponseFix execute() {
        try {
            connection.setAutoCommit(isAutoCommit);
            return ExecuteSQLResponseFix.instance(connection, preparedStatement.executeUpdate());
        } catch (SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }
}
