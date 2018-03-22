package report.entities.items.counterparties.AgentTVI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.sql.SqlConnector;
import report.models.view.LinkedNamePair;
import report.usage_strings.SQL;

import java.sql.*;
import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CounterAgentDAO implements CommonNamedDAO<Collection<CountAgentTVI>> {


    @Override
    public String getSqlTableName() {
        return SQL.Tables.COUNTERPARTIES;
    }

    @Override
    public ObservableList<CountAgentTVI> getData() {
        ObservableList<CountAgentTVI> list =  FXCollections.observableArrayList(CountAgentTVI.extractor());

        String sqlQuery = "SELECT *  FROM [Test].[dbo].[vCOMPANY_IDEAL_COR]";

        try(Connection connection = SqlConnector.getInstance();
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
            ex.printStackTrace();
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }

    @Override
    //TODO:Ask Диму  about deleting
    public void delete(Collection<CountAgentTVI> entry) {
//        String sqlQuery = "EXECUTE [dbo].[DELETE_FRK_ACCOUNT_COUNT_TYPE_FORM] @ID_COUNT= ?, @ID_FORM= ?, @ID_TYPE= ?;";
        String sqlQuery = "UPDATE [dbo].[FRK_ACCOUNT_COUNT_TYPE_FORM] " +
                " set DELL = 1 " +
                " WHERE  " +
                " ID_COUNT = ? " +
                " AND ID_FORM = ? " +
                " AND ID_TYPE = ? ";
        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            for (CountAgentTVI countAgent : entry) {
                pstmt.setInt(1,countAgent.getIdName());
                pstmt.setInt(2,countAgent.getIdForm());
                pstmt.setInt(3,countAgent.getIdType());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insert(Collection<CountAgentTVI> list) {
        final String sqlAddCount = "EXECUTE [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
        final String sqlAddFRK = "INSERT INTO [dbo].[FRK_ACCOUNT_COUNT_TYPE_FORM] ([ID_IDEAL_CORR],[ID_FORM],[ID_COUNT],[ID_TYPE]) VALUES(?,?,?,?) ";
        list.forEach(item -> {
            int newElementId = -1;
            try (Connection connection = SqlConnector.getInstance();
                 CallableStatement cstmt = connection.prepareCall(sqlAddCount)) {
                cstmt.setString(1, Integer.toString(newElementId));
                cstmt.setString(2, item.getName());
                cstmt.execute();
                cstmt.getResultSet();
                cstmt.getMoreResults();
                cstmt.getResultSet();
                if (cstmt.getMoreResults()) {
                    try (ResultSet rs = cstmt.getResultSet()) {
                        while (rs.next()) {
                            newElementId = rs.getInt("ID");
                            if (newElementId != -1) {
                                item.setIdName(newElementId);
                                System.out.println(" new element ID " + newElementId + " see ->CounterAgentDAO ");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection connection = SqlConnector.getInstance();
                 CallableStatement cstmt = connection.prepareCall(sqlAddFRK)) {
                if(Objects.isNull(item.getLinkedNames()) || item.getLinkedNames().isEmpty()) {
                    cstmt.setString(1, "-1");
                    cstmt.setInt(2, item.getIdForm());
                    cstmt.setInt(3, item.getIdName());
                    cstmt.setInt(4, item.getIdType());
                    cstmt.execute();
                }else if(Objects.nonNull(item.getLinkedNames()) && !item.getLinkedNames().isEmpty()){
                    for (LinkedNamePair linkedName : item.getLinkedNames()) {
                        cstmt.setString(1, linkedName.getKey().toString());
                        cstmt.setInt(2, item.getIdForm());
                        cstmt.setInt(3, item.getIdName());
                        cstmt.setInt(4, item.getIdType());
                        cstmt.addBatch();
                    }
                    cstmt.executeBatch();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
 }
