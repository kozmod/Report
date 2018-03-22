package report.models.sql;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BinaryStreamQuery {

    public void insert(List<File> files) {

        try (Connection connection = SqlConnector.getInstance();
             Statement stm = connection.createStatement()) {
            for (File file : files) {
                String sqlQuery = "INSERT INTO TBL_Documents_Templates(Template_Name,Template_Format,Template_Desc, fileDATA, fileGUID)\n" +
                        " VALUES ( '"+file.getName() +"'"
                        +", '"+ FilenameUtils.getExtension(file.getAbsolutePath())+"'"
                        +",'"+file.getName()+"'"
                        +" ,(SELECT * FROM OPENROWSET(BULK N'" + file.getAbsolutePath()+"'"
                        +", SINGLE_BLOB) AS text1) " +
                        " ,NEWID());";
                stm.addBatch(sqlQuery);
            }
            stm.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
