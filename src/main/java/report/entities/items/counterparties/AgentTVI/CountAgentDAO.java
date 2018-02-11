package report.entities.items.counterparties.AgentTVI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import report.entities.abstraction.CommonDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.beck.sql.SQLconnector;
import report.usage_strings.SQL;

import java.sql.*;
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

    //TODO: add ability to check sythe of "linked Names"
    @Override
    public void insert(Collection<CountAgentTVI> list) {
        final String sqlAddCount = "execute [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
        final String sqlAddFRK = "INSERT INTO [dbo].[FRK_ACCOUNT_COUNT_TYPE_FORM] ([ID_IDEAL_CORR],[ID_FORM],[ID_COUNT],[ID_TYPE]) VALUES(?,?,?,?) ";
        list.forEach( item -> {
            int newElementId = -1;
            try (Connection connection = SQLconnector.getInstance();
                 CallableStatement cstmt = connection.prepareCall(sqlAddCount)) {
                cstmt.setString(1, Integer.toString(newElementId));
                cstmt.setString(2, item.getName());
                cstmt.execute();
                cstmt.getResultSet();
                cstmt.getMoreResults();
                cstmt.getResultSet();
                    if(cstmt.getMoreResults()){
                        try (ResultSet rs = cstmt.getResultSet()) {
                            while (rs.next()) {
                                newElementId = rs.getInt("ID");
                                if(newElementId != -1){
                                    item.setIdName(newElementId);
                                    System.out.println(" new element ID "+ newElementId + " see -> CountAgentDAO ");
                                }
                            }
                        }
                    }
            } catch (SQLException ex) {
                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connection = SQLconnector.getInstance();
                 CallableStatement cstmt = connection.prepareCall(sqlAddFRK)) {
                cstmt.setString(1,"-1");
                cstmt.setInt(2,item.getIdForm());
                cstmt.setInt(3, item.getIdName());
                cstmt.setInt(4,item.getIdType());
                cstmt.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }
}
