
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */
    @Test
    @DisplayName("TEST SQL FILE STREAM")
//    @Disabled
    public void shouldGetFileStream_WhenConnectToSQLWebTest() throws ClassNotFoundException, IOException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=web;";

        String sqlQuery = "SELECT  " +
                "[id]" +
//                " ,[fileGUID]" +
                " ,[context]" +
                " ,[fileDATA]" +
                "  FROM [web].[dbo].[TEST_FILESTREAM_2] WHERE id = ? ";
        try (
                Connection connection = DriverManager.getConnection(url, "User", "123456");
                PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            pstmt.setInt(1,1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InputStream fis = rs.getBinaryStream(3);
//                File file = new File("D:\\IdeaProjects\\Report\\AAA.doc");
//                FileInputStream fis = new FileInputStream(file.getAbsolutePath());

//                XWPFDocument document = new XWPFDocument(fis);
                HWPFDocument doc = new HWPFDocument(fis);

                WordExtractor we = new WordExtractor(doc);

                String text = "";
                String[] paragraphs = we.getParagraphText();
                for (String para : paragraphs) {
                    text += para.toString();
                }
                fis.close();
                System.out.println(text);


////                Reader reader = new InputStreamReader( is, "ISO-8859-1" );
//
//                StringBuffer buffer = new StringBuffer();
//                InputStreamReader isr = new InputStreamReader(fis);
//                Reader in = new BufferedReader(isr);
//                int ch;
//                while ((ch = in.read()) > -1) {
//                    buffer.append((char)ch);
//                }
//                in.close();
//
//                OutputStream fos = new FileOutputStream("AAA.doc");
//                Writer out = new OutputStreamWriter(fos);
//                out.write(buffer.toString());
//                out.close();

//                IOUtils.copy(fis,fos);
//                fos.flush();

            }


        } catch (SQLException ex) {
            System.out.println(ex);
//            Logger.getLogger(SqlConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
