package io.github.loulangogogo.water.jdbc;

import java.sql.Connection;

/*********************************************************
 ** sql修改（更新，插入，删除）执行响应类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
class ExecuteSQLResponseFix extends ExecuteSQLResponse {

    // sql执行修该结果数据
    private int fixCount;

    private ExecuteSQLResponseFix(Connection conn, int fixCount) {
        this.fixCount = fixCount;
        this.connection = conn;
    }

    /**
     * 实例化修改响应对象
     *
     * @param conn     数据源连接对象
     * @param fixCount 修改数据条数
     * @return {@link ExecuteSQLResponseFix}修改响应对象
     * @author :loulan
     */
    public static ExecuteSQLResponseFix instance(Connection conn, int fixCount) {
        return new ExecuteSQLResponseFix(conn, fixCount);
    }

    /**
     * 获取修改数据条数
     *
     * @return 修改条数
     * @author :loulan
     */
    public int toInt() {
        return fixCount;
    }

    /**
     * 判断修改执行是否成功
     *
     * @return 是否成功
     * @author :loulan
     */
    public boolean toBoolean() {
        return fixCount > 0;
    }
}
