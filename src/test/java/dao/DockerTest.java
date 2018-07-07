package dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DockerTest {

    @Test
    void dockMSSQLConnectTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String url = "jdbc:sqlserver://172.31.93.73:1433;databaseName=Test;";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Test;";
//            connection = DriverManager.getConnection(url, "User", "123456");
        Connection connection = DriverManager.getConnection(url, "sa", "SaUser123456");

    }
}
