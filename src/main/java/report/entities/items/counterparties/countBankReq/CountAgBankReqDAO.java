package report.entities.items.counterparties.countBankReq;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.TableViewItemDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountAgBankReqDAO implements TableViewItemDAO<CountAgBankReq>{

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/


    @Override
    public ObservableList<CountAgBankReq> getData() {
        ObservableList<CountAgBankReq> list =  FXCollections.observableArrayList(CountAgBankReq.extractor());

        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][Type][Value]
                + " * "
                + "from dbo.[SiteExpenses] "
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [dell] = 0";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);) {

        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }

    @Override
    public void delete(Collection<CountAgBankReq> entry) {

    }

    @Override
    public void insert(Collection<CountAgBankReq> entry) {

    }

    @Override
    public String sqlTableName() {
        return SQL.Tables.COUNT_REQ_BANK;
    }
}
