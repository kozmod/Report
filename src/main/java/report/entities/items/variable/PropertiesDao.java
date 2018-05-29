package report.entities.items.variable;

import report.entities.abstraction.CommonDao;
import report.models.sql.SqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesDao implements CommonDao<VariableTIV_new> {

    @Override
    public VariableTIV_new getData() {
        VariableTIV_new item = null;
        String sqlQuery = "SELECT "
                + " * "
                + "from dbo.[TBL_COMMAND_PROPERTY] "
                + "WHERE  [dell] = 0 ";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.execute();

            try (ResultSet rs = pstmt.getResultSet()) {
                while (rs.next())
                    item = new VariableTIV_new(
                            rs.getLong("id"),
                            rs.getDouble(VariableTIV_new.SQL.INCOME_TAX),
                            rs.getDouble(VariableTIV_new.SQL.SALE_EXP)
                    );
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(VariableTIV.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (item == null)
            item = new VariableTIV_new(0, 0, 0);
        return item;
    }

    @Override
    public void delete(VariableTIV_new entry) {
        throw new UnsupportedOperationException("не реализовано");

    }

    @Override
    public void insert(VariableTIV_new entry) {
        throw new UnsupportedOperationException("не реализовано");
    }

}
