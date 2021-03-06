package report.entities.items.site.month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.expenses.ExpensesDao;
import report.models.sql.SqlConnector;
import report.models.converters.dateStringConverters.LocalDayStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CashFlowDAO {
    public ObservableList<ReportingMonth> getData() {
        return getData(
                new LocalDayStringConverter().fromString("2012-01-01"),
                new LocalDayStringConverter().fromString("2018-06-01")
        );

    }

    public ObservableList<ReportingMonth> getData(LocalDate dateFrom, LocalDate dateTo) {
        ObservableList<ReportingMonth> list = FXCollections.observableArrayList();

        //TODO add date parameters
        String sqlQuery = "exec [dbo].[sp_CASH_FLOW] @DateTO = '2012-01-01',@DateFrom = '2018-06-01'";
//        String sqlQuery = "exec dbo.MONTH_GRAPHIK @DateTO = '2012-01-01',@DateFrom = '2018-06-01'";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            if (pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    while (rs.next()) {
                        list.add(
                                new ReportingMonth(
                                        rs.getDate("month")
                                        , rs.getString("count_finish")
                                        , rs.getString("count_pay")
                                        , rs.getString("Price_sum")
                                        , rs.getString("S_CRED")
                                        , rs.getString("Oper_profit")
                                        , rs.getString("Add_Cost")
                                        , rs.getString("all_Taxes")
                                        , rs.getString("SUm_OSR")

                                )
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpensesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
