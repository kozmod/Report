package report.entities.items.variable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.CommonDAO;
import report.entities.TableViewItemDAO;
import report.entities.items.TableDItem;
import report.models.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesDAO implements CommonDAO<TableItemVariable_new> {


    @Override
    public TableItemVariable_new getData() {
        TableItemVariable_new item = null;
        String sqlQuery = "SELECT "
                + " * "
                + "from dbo.[TBL_COMMAND_PROPERTY] "
                + "WHERE  [dell] = 0 ";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.execute();

            try(ResultSet rs = pstmt.getResultSet()){
                while(rs.next())
                    item = new TableItemVariable_new(
                            rs.getLong("id"),
                            rs.getDouble(TableItemVariable_new.SQL.INCOME_TAX),
                            rs.getDouble(TableItemVariable_new.SQL.SALE_EXP)
                    );
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TableItemVariable.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(item == null)
            item = new TableItemVariable_new(0,0,0);
        return  item;
    }

    @Override
    public void delete(TableItemVariable_new entry) {

    }

    @Override
    public void insert(TableItemVariable_new entry) {

    }

    @Override
    public String sqlTableName() {
        return null;
    }
}
