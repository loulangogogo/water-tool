package io.github.loulangogogo.water.jdbc;

import io.github.loulangogogo.water.exception.SQLRuntimeException;
import io.github.loulangogogo.water.tool.AssertTool;

import java.sql.Connection;
import java.sql.SQLException;

/*********************************************************
 ** sql查询请求对象
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLRequestQuery extends ExecuteSQLRequest {

    private ExecuteSQLRequestQuery() {

    }

    /**
     * 构建一个查询请求对象
     *
     * @return {@link ExecuteSQLRequestQuery}查询请求对象
     * @author :loulan
     */
    public static ExecuteSQLRequestQuery builder() {
        return new ExecuteSQLRequestQuery();
    }

    /**
     * 构建 sql 可执行对象
     *
     * @param _conn 数据库链接对象
     * @return {@link ExecuteSQL}sql可执行对象
     * @author :loulan
     */
    @Override
    public ExecuteSQLQuery build(Connection _conn) {
        AssertTool.notNull(_conn, "数据库链接不能为空");
        try {
            return new ExecuteSQLQuery(_conn, getPreparedStatement(_conn));
        }catch (SQLException ex){
            throw  new SQLRuntimeException(ex);
        }
    }
}
