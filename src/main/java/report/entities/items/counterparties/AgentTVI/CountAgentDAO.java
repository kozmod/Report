package report.entities.items.counterparties.AgentTVI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import report.entities.CommonDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountAgentDAO implements CommonDAO<Collection<CountAgentTVI>> {


    @Override
    public String sqlTableName() {
        return SQL.Tables.COUNTERPARTIES;
    }

    @Override
    public ObservableList<CountAgentTVI> getData() {
        ObservableList<CountAgentTVI> list =  FXCollections.observableArrayList(CountAgentTVI.extractor());

        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][Type][Value]
                + " * "
                + "from dbo.[SiteExpenses] "
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [dell] = 0";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);) {
            pstmt.execute();

            try(ResultSet rs = pstmt.getResultSet()){
                //TODO:




            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }

    @Override
    public void delete(Collection<CountAgentTVI> entry) {

    }

    @Override
    public void insert(Collection<CountAgentTVI> entry) {

    }
}
