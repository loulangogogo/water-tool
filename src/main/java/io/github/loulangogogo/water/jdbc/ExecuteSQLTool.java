package io.github.loulangogogo.water.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/*********************************************************
 ** 执行sql的工具
 * TODO 没有必要
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ExecuteSQLTool {

    public static List<Map<String,Object>> select(Connection conn, String sql) {
        ExecuteSQLRequestQuery executeSQLRequestQuery = ExecuteSQLRequestQuery.builder();
        executeSQLRequestQuery.sql(sql);
        ExecuteSQLQuery executeSQLQuery = executeSQLRequestQuery.build(conn);
        ExecuteSQLResponseQuery executeSQLResponseQuery = executeSQLQuery.execute();
        return executeSQLResponseQuery.toMap(true);
    }
}
