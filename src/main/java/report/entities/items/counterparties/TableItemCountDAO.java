package report.entities.items.counterparties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.TableViewItemDAO;
import report.entities.items.expenses.TableItemExpenses;
import report.entities.items.expenses.TableViewItemExpensesDAO;
import report.layoutControllers.estimate.EstimateController;
import report.models.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableItemCountDAO implements TableViewItemDAO<TableItemCount> {


    @Override
    public String sqlTableName() {
        return SQL.Tables.COUNTERPARTIES;
    }

    @Override
    public ObservableList<TableItemCount> getData() {
        ObservableList<TableItemCount> list =  FXCollections.observableArrayList(TableItemCount.extractor());

        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][Type][Value]
                + " * "
                + "from dbo.[SiteExpenses] "
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [dell] = 0";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);) {
            //set false SQL Autocommit
            pstmt.setString(1, EstimateController.Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
            pstmt.setString(2, EstimateController.Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
            pstmt.execute();

            try(ResultSet rs = pstmt.getResultSet();){




            }
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }

    @Override
    public void delete(Collection<TableItemCount> entry) {

    }

    @Override
    public void insert(Collection<TableItemCount> entry) {

    }
}
