package report.entities.items.counterparties.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.DaoUtil;
import report.entities.items.expenses.ExpensesDAO;
import report.models.sql.SqlConnector;
import report.models.view.LinkedNamePair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CounterAgentDaoUtil implements DaoUtil<String,Integer> {
    private static String matchSql =  "SELECT " +
            "   [Name] " +
            "  ,[Name_cor] " +
            "  ,[ID_IDEAL_CORR] " +
            "  FROM [Test].[dbo].[vTABLE_CORRENSPONDENCE] " +
            "  WHERE Name = ? " +
            "  and Name_Cor != 'NULL'";
    private static String notMatchSql =
            "SELECT " +
            "  FRK_A.[ID_IDEAL_CORR] as  [ID_IDEAL_CORR_FROM_FRK_ACCOUNT_FRK]" +
            " ,FRK_A.[Name_Cor]" +
            " , C.[ID_IDEAL_CORR] as [ID_IDEAL_CORR_FROM_vTABLE_CORRENSPONDENCE]" +
            " ,[NumberSession]" +
            " ,[dell]" +
            " ,[DateCreate]" +
            "  FROM [Test].[dbo].[FRK_ACCOUNT_FRK] FRK_A" +
            "  LEFT JOIN [Test].[dbo].[vTABLE_CORRENSPONDENCE] C" +
            "  ON FRK_A.[ID_IDEAL_CORR] = C.[ID_IDEAL_CORR]" +
            "  WHERE C.[ID_IDEAL_CORR] is Null" +
            "  Order by FRK_A.[ID_IDEAL_CORR]";

    public  static  ObservableList<LinkedNamePair> getMatchLinkedNames(String name){
        ObservableList<LinkedNamePair> list = FXCollections.observableArrayList();

        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(matchSql)) {
            pstmt.setString(1,name);
            if(pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    while(rs.next()) {
                        list.add(new LinkedNamePair(
                                        rs.getInt("ID_IDEAL_CORR"),
                                        rs.getString("Name_cor")
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
    public  static  ObservableList<LinkedNamePair> getNotMatchLinkedNames(){
        ObservableList<LinkedNamePair> list = FXCollections.observableArrayList();

        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(notMatchSql)) {
            if(pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    while(rs.next()) {
                        list.add(new LinkedNamePair(
                                        rs.getInt("ID_IDEAL_CORR_FROM_FRK_ACCOUNT_FRK"),
                                        rs.getString("Name_cor")
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
    public static Map<String,Integer> getForm(){
        String sql ="SELECT DISTINCT [id],[Name] FROM [dbo].[DIC_Count_Form] WHERE dell = 0";
        return new CounterAgentDaoUtil().getPairValue("Name","id",sql);
    }
    public static Map<String,Integer> getType(){
        String sql ="SELECT DISTINCT [id],[Name] FROM [dbo].[DIC_Count_Type] WHERE dell = 0";
        return new CounterAgentDaoUtil().getPairValue("Name","id",sql);
    }
}
