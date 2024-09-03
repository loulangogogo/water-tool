package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.SQLException;

/*********************************************************
 ** sql执行修改请求对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLRequestFix extends ExecuteSQLRequest {

    private ExecuteSQLRequestFix() {
    }

    /**
     * 构建一个sql执行修改请求对象
     *
     * @return {@link ExecuteSQLRequestFix}修改请求对象
     * @author :loulan
     */
    public static ExecuteSQLRequestFix builder() {
        return new ExecuteSQLRequestFix();
    }

    /**
     * 构建 sql 可执行对象
     *
     * @param _conn 数据库链接对象
     * @return {@link ExecuteSQL}sql可执行对象
     * @author :loulan
     */
    @Override
    public ExecuteSQLFix build(Connection _conn) {
        AssertTool.notNull(_conn, "数据库链接不能为空");
        try {
            return new ExecuteSQLFix(_conn, getPreparedStatement(_conn));
        }catch (SQLException ex){
            throw  new SQLRuntimeException(ex);
        }
    }
}
