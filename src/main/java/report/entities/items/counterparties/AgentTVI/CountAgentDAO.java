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

        String sqlQuery = "SELECT *  FROM [Test].[dbo].[vCOMPANY_IDEAL_COR]";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            if(pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    while(rs.next()) {
                        list.add(
                                new CountAgentTVI(
                                        rs.getInt("ID_COUNT"),
                                        rs.getString("Name_Count"),
                                        rs.getInt("ID_FORM"),
                                        rs.getString("Name_Form"),
                                        rs.getInt("ID_TYPE"),
                                        rs.getString("Name_Type")
                                )
                        );
                    }
                }
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
