
package report.entities.items.expenses;

import report.entities.TableViewItemDAO;
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
import report.models.sql.SQLconnector;
import report.models_view.nodes.node_wrappers.TableWrapper;


public class TableViewItemExpensesDAO implements TableViewItemDAO<TableItemExpenses, TableWrapper> {

    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public String getTableString() {return SQL.Tables.SITE_EXPENSES;}
    /**
     * Get List of expenses Items from SQL (SiteExpenses)
    * @return  ObservableList of TableItemExpenses
    */
    @Override
    public ObservableList<TableItemExpenses> getList() {
        ObservableList<TableItemExpenses> list =  FXCollections.observableArrayList(TableItemExpenses.extractor());
        
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
                    list.add(new TableItemExpenses(
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
               Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  list;
    }

    
   /**
     * Delete TableItemExpenses Entities from SQL (SiteExpenses)
    * @param items (Collection of TableExpenses) 
    */
    @Override
    public void delete(Collection<TableItemExpenses> items) {
        String sql = "update [dbo].[SiteExpenses] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemExpenses obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           items.forEach(item -> {
//                LogController.appendLogViewText("deleted item: "+ ((TableItemOSR)item).getJM_name()
//                                                         +" [JM/ "+((TableItemOSR)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItemOSR)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    * Insert TableItemExpenses Entities to SQL (SiteExpenses)
    * @param items (Collection of TableItemExpenses) 
    */
    @Override
    public void insert(Collection<TableItemExpenses> items) {
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
            
                for (TableItemExpenses obsItem : items) {
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
////                LogController.appendLogViewText("inserted item: "+ ((TableItem)item).getJM_name()
////                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
//                });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    
}
