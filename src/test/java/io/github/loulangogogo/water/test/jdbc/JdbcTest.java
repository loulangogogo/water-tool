package io.github.loulangogogo.water.test.jdbc;

import org.junit.Test;

import java.sql.*;

/*********************************************************
 ** jdbc功能的测试
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class JdbcTest {

    @Test
    public void test() throws SQLException, InstantiationException, IllegalAccessException {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://192.168.2.84:5432/dev-hltq-dragon?allowMultiQueries=true",
                "hltq_dev",
                "056f7c2b54ef4ccfb494c652fca8319d");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from dg_user");
        ResultSet resultSet = preparedStatement.executeQuery();
    }

}
