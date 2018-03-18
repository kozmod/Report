
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.abstraction.CommonDAO;
import report.entities.items.site.month.ReportingMonthDAO;
import report.models.beck.sql.SqlConnector;
import report.usage_strings.ServiceStrings;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://docs.microsoft.com/ru-ru/sql/relational-databases/blob/enable-and-configure-filestream
 * http://kooboo-cms.ru/articles/detail/enable-filestream-on-exist-database-by-interface-sql-server-management-studio/
 * <p>
 * CREATE TABLE TEST_FILESTREAM (
 * id int not null,
 * fileGUID uniqueidentifier default newid() unique rowguidcol not null,
 * fileDATA varbinary(max) filestream
 * )
 * ////////////////////////////////////////////////////////////////////////////////////////////////////////////
 * GO
 * INSERT INTO  TEST_FILESTREAM(id,fileGUID, fileDATA)
 * VALUES (1
 * ,NEWID()
 * ,(SELECT * FROM OPENROWSET(BULK N'C:\Users\xxx\Desktop\collection Bid_O.txt', SINGLE_BLOB) AS text1)
 * );
 * GO
 * ////////////////////////////////////////////////////////////////////////////////////////////////////////////
 */
@SuppressWarnings("Duplicates")
@FixMethodOrder(MethodSorters.JVM)
public class BinarySqlFileTest {
    @Test
    void shouldInsertDocToSQl() throws SQLException {
        String sqlQuery = "INSERT INTO TBL_Documents_Templates(Tanplate_Name,Template_Format,Tanplate_Desc, fileDATA, fileGUID)\n" +
                " VALUES ( ?, ?, ?" +
                " ,(SELECT * FROM OPENROWSET(BULK N'D:\\IdeaProjects\\Report\\lib\\docs_templates\\список документов.doc', SINGLE_BLOB) AS text1)\n" +
                " ,NEWID());";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            pstmt.setString(1, "список документов");
            pstmt.setString(2, "doc");
            pstmt.setString(3, "test");
            pstmt.execute();
        }
    }

    @Test
    void shouldInsertDocxToSQl() throws SQLException {
        String sqlQuery = "INSERT INTO TBL_Documents_Templates(Tanplate_Name,Template_Format,Tanplate_Desc, fileDATA, fileGUID)\n" +
                " VALUES ( ?, ?, ?" +
                " ,(SELECT * FROM OPENROWSET(BULK N'D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон.docx', SINGLE_BLOB) AS text1)\n" +
                " ,NEWID());";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            pstmt.setString(1, "шаблон");
            pstmt.setString(2, "docx");
            pstmt.setString(3, "test_docx");
            pstmt.execute();
        }
    }

    @Test
    void shouldGetDocumentDocFromSQL() throws SQLException, IOException {
        String sqlQuery = "SELECT  *  FROM [dbo].[TBL_Documents_Templates] WHERE id = ? ";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                InputStream fis = rs.getBinaryStream("fileDATA");
                HWPFDocument doc = new HWPFDocument(fis);

                WordExtractor we = new WordExtractor(doc);

                StringBuilder sb = new StringBuilder();
                String[] paragraphs = we.getParagraphText();
                for (String para : paragraphs) {
                    sb.append(para);
                }
                fis.close();
                System.out.println(sb.toString());

                OutputStream out = new FileOutputStream("sample2.doc");
                out.write(sb.toString().getBytes());
//                doc.write(out);
                out.flush();
                out.close();
            }
        }
    }

    @Test
    void shouldGetDocumentDocxFromSQL() throws SQLException, IOException {
        String sqlQuery = "SELECT  *  FROM [dbo].[TBL_Documents_Templates] WHERE id = ? ";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)
        ) {
            pstmt.setInt(1, 2);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InputStream fis = rs.getBinaryStream("fileDATA");
                XWPFDocument document = new XWPFDocument(fis);

                List<XWPFParagraph> paragraphs = document.getParagraphs();
                paragraphs.stream().forEach(para -> {
                    para.getRuns().stream().forEach(run -> {
                        System.out.println(run.getText(0));
                    });
                });
//                paragraphs.stream().forEach(
//                        p -> System.out.println(p.getText())
//                );
                document.write(new FileOutputStream("Я чччч и здлрогоWWWWWWWW.docx"));

            }
        }
    }

    @Test
    @DisplayName("ТЕСТ с заменой из Notepad++")
    void shouldGetDocumentDocxFromFile() throws SQLException, IOException {
        InputStream fis = new FileInputStream("D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон.docx");
        XWPFDocument document = new XWPFDocument(fis);

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.stream().forEach(para -> {
            para.getRuns().stream().forEach(run -> {
                String text = run.getText(0);
                if (text != null && text.contains("%Contractor_Name%")) {
                    text = text.replace("%Contractor_Name%", "MAX_MAX");
                    run.setText(text, 0);
                }
            });
        });
        document.write(new FileOutputStream("Я чччч и здлрогоWWWWWWWW.docx"));


    }
}

