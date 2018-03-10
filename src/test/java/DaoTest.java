
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


    @Test
    @DisplayName("Общие параметры")
//    @Disabled
    public void firstTest() {

//        final String sqlQuery = "execute [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
//
//            try (Connection connection = SqlConnector.getInstance();
//                 CallableStatement cstmt = connection.prepareCall(sqlQuery)) {
//                cstmt.setString(1, "-1");
//                cstmt.setString(2, "AAAAAdAAA");
////            execute  [dbo].[INSERT_DIC_COUNT_NAME] @Name = '-1', @New_Name= 'ТЕСТ_2'
//                cstmt.execute();
//                System.out.println(cstmt.getResultSet());
//                System.out.println(cstmt.getMoreResults());
//                System.out.println(cstmt.getResultSet());
//                if(cstmt.getMoreResults()){
//                    try (ResultSet rs = cstmt.getResultSet()) {
//                        while (rs.next()) {
//                            System.out.println("id of new  value " + rs.getInt("ID"));
//                        }
//                    }
//                }
//
//            } catch (SQLException ex) {
//                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }

//        final String sqlAddCount = "execute [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
//        final String sqlAddFRK = "INSERT INTO [dbo].[FRK_ACCOUNT_COUNT_TYPE_FORM] ([ID_IDEAL_CORR],[ID_FORM],[ID_COUNT],[ID_TYPE]) VALUES(?,?,?,?) ";
//            int newElementId = -1;
//            try (Connection connection = SqlConnector.getInstance();
//                 CallableStatement cstmt = connection.prepareCall(sqlAddCount)) {
//                cstmt.setString(1, Integer.toString(newElementId));
//                cstmt.setString(2, "adsadada");
//                cstmt.execute();
//                System.out.println(cstmt.getResultSet());
//                System.out.println(cstmt.getMoreResults());
//                System.out.println(cstmt.getResultSet());
//                if(cstmt.getMoreResults()){
//                    try (ResultSet rs = cstmt.getResultSet()) {
//                        System.out.println("AAAAAAAAAa");
//                        while (rs.next()) {
//                            System.out.println("DDDDDDDDDDDDDDDDDDDDD");
//                            newElementId = rs.getInt("ID");
//                            if(newElementId != -1){
//                                System.out.println(" ++++++++ "+ newElementId);
//                            }
//                        }
//                    }
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }

    }

    @Test
    @DisplayName("ReportingMonth")
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
