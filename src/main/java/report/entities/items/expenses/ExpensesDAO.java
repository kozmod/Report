
package report.entities.items.expenses;

import report.entities.abstraction.CommonDAO;
import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.layoutControllers.LogController;
import report.layoutControllers.estimate.EstimateController.Est;
import report.models.beck.sql.SQLconnector;


public class ExpensesDAO implements CommonDAO<Collection<ExpensesTVI>> {

    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of Item
    */
    @Override
    public String sqlTableName() {return SQL.Tables.SITE_EXPENSES;}
    /**
     * Get List of expenses Items from SQL (SiteExpenses)
    * @return  ObservableList of ExpensesTVI
    */
    @Override
    public ObservableList<ExpensesTVI> getData() {
        ObservableList<ExpensesTVI> list =  FXCollections.observableArrayList(ExpensesTVI.extractor());
        
        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][Type][Value]
                       + " * "
                       + "from dbo.[SiteExpenses] "
                       + "WHERE [SiteNumber] = ? "
                       + "AND   [Contractor] = ? "
                       + "AND   [dell] = 0";
        
          try(Connection connection = SQLconnector.getInstance();
                PreparedStatement pstmt = connection.prepareStatement(sqlQuery);) {
            //set false SQL Autocommit
                pstmt.setString(1, Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                pstmt.setString(2, Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
                pstmt.execute();
                
                try(ResultSet rs = pstmt.getResultSet();){
                   while(rs.next())
                    list.add(new ExpensesTVI(
                                                rs.getLong  (SQL.Common.ID),
                                                rs.getString(SQL.Common.SITE_NUMBER),
                                                rs.getString(SQL.Common.CONTRACTOR),
                                                rs.getString(SQL.Common.TEXT),
                                                rs.getByte  (SQL.Common.TYPE),
                                                rs.getDouble(SQL.Common.VALUE)
                                                )       
                            );
                    

               
               }
           } catch (SQLException ex) {
               Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  list;
    }

    
   /**
     * Delete ExpensesTVI Entities from SQL (SiteExpenses)
    * @param items (Collection of TableExpenses) 
    */
    @Override
    public void delete(Collection<ExpensesTVI> items) {
        String sql = "update [dbo].[SiteExpenses] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (ExpensesTVI obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           items.forEach(item -> {
//                LogController.appendLogViewText("deleted item: "+ ((OSR_TIV)item).getJM_name()
//                                                         +" [JM/ "+((OSR_TIV)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((OSR_TIV)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    * Insert ExpensesTVI Entities to SQL (SiteExpenses)
    * @param items (Collection of ExpensesTVI)
    */
    @Override
    public void insert(Collection<ExpensesTVI> items) {
            String sql = "INSERT into [dbo].[SiteExpenses] "
                    + "( " 
                    + " [SiteNumber]"
                    + ",[Contractor]"
                    + ",[Text]"
                    + ",[Type]"
                    + ",[Value]"
                    + " ) " 
                    + "VALUES(?,?,?,?,?)";
//                    + "VALUES('10а','УЮТСТРОЙ','ssss',0,11111)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql,
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            
                for (ExpensesTVI obsItem : items) {
                    pstmt.setString   (1, obsItem.getSiteNumber());
                    pstmt.setString   (2, obsItem.getContractor());
                    pstmt.setString   (3, obsItem.getText());
                    pstmt.setInt      (4, obsItem.getType());
                    pstmt.setDouble   (5, obsItem.getValue());                                                       

                    int affectedRows = pstmt.executeUpdate();
                    
                    System.out.println(affectedRows);
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys();){
                        if(generategKeys.next())
                            obsItem.setId(generategKeys.getLong(1));
                    }    
                }
     
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
//           items.forEach(item -> {
////                LogController.appendLogViewText("inserted item: "+ ((Item)item).getJM_name()
////                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((Item)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
//                });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    
}
