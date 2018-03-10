
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.items.site.month.ReportingMonthDAO;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@FixMethodOrder(MethodSorters.JVM)
public class DaoTest {

    /**
     *
     * https://docs.microsoft.com/ru-ru/sql/relational-databases/blob/enable-and-configure-filestream
     * http://kooboo-cms.ru/articles/detail/enable-filestream-on-exist-database-by-interface-sql-server-management-studio/
     *
     CREATE TABLE TEST_FILESTREAM (
     id int not null,
     fileGUID uniqueidentifier default newid() unique rowguidcol not null,
     fileDATA varbinary(max) filestream
     )
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     GO
     INSERT INTO  TEST_FILESTREAM(id,fileGUID, fileDATA)
     VALUES (1
     ,NEWID()
     ,(SELECT * FROM OPENROWSET(BULK N'C:\Users\xxx\Desktop\collection Bid_O.txt', SINGLE_BLOB) AS text1)
     );
     GO

     * @throws ClassNotFoundException
     */
    @Test
    @DisplayName("TEST SQL FILE STREAM")
//    @Disabled
    public void shouldGetFilestream_WhenConnectToSQLWebTest() throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=web;";

        String sqlQuery = "SELECT  " +
                "[id]" +
                " ,[fileGUID]" +
                " ,[fileDATA]" +
                "  FROM [web].[dbo].[TEST_FILESTREAM]";
        try (
                Connection connection = DriverManager.getConnection(url, "User", "123456");
                PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InputStream is = rs.getBinaryStream(3);
                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(
                        new InputStreamReader(is,
                                Charset.forName(StandardCharsets.UTF_8.name()))
                )
                ) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
//                    System.out.println(textBuilder.toString());
                    String s  = textBuilder.toString().replaceAll("Q","1234567890");
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (SQLException ex) {
            System.out.println(ex);
//            Logger.getLogger(SqlConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
