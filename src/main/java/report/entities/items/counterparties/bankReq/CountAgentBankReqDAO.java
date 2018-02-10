package report.entities.items.counterparties.bankReq;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.CommonDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.beck.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountAgentBankReqDAO implements CommonDAO<Collection<CountAgentBankReq>>{

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/


    @Override
    public ObservableList<CountAgentBankReq> getData() {
        ObservableList<CountAgentBankReq> list =  FXCollections.observableArrayList(CountAgentBankReq.extractor());

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
    public void delete(Collection<CountAgentBankReq> entry) {

    }

    @Override
    public void insert(Collection<CountAgentBankReq> entry) {

    }

    @Override
    public String sqlTableName() {
        return SQL.Tables.COUNT_REQ_BANK;
    }
}
