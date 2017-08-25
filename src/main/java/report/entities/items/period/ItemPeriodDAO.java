/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.entities.items.period;

import report.entities.ItemDAO;
import report.usege_strings.SQL;
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
import report.controllers.LogLayoutController;
import report.controllers.showEstLayoutController.Est;
import report.models.sql.SQLconnector;
import report.view_models.nodes.TableWrapper;

public class ItemPeriodDAO implements ItemDAO<TableItemPeriod, TableWrapper> {

    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public String getTableString() {return SQL.Tables.SITE_JOB_PERIOD;}
    
    @Override
    public ObservableList<TableItemPeriod> getList() {
        ObservableList<TableItemPeriod> list =  FXCollections.observableArrayList(TableItemPeriod.extractor());
        
        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][DateFrom][DateTo]
                       + " * "
                       + "from dbo.[SiteJobPeriod] "
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
                    list.add(new TableItemPeriod(
                                                rs.getLong  (SQL.Common.ID),
                                                rs.getString(SQL.Common.SITE_NUMBER),
                                                rs.getString(SQL.Common.CONTRACTOR),
                                                rs.getString(SQL.Common.TEXT),
                                                rs.getInt   (SQL.Period.DATE_FROM),
                                                rs.getInt   (SQL.Period.DATE_TO)
                                                )       
                            );
                    

               
               }
           } catch (SQLException ex) {
               Logger.getLogger(ItemPeriodDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  list;
    }

    @Override
    public void delete(Collection<TableItemPeriod> items) {
       String sql = "update [dbo].[SiteJobPeriod] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemPeriod obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogLayoutController
           items.forEach(item -> {
//                LogLayoutController.appendLogViewText("deleted item: "+ ((TableItemOSR)item).getJM_name() 
//                                                         +" [JM/ "+((TableItemOSR)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItemOSR)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogLayoutController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemPeriodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(Collection<TableItemPeriod> items) {
         String sql = "INSERT into [dbo].[SiteJobPeriod] "
                    + "( " 
                    + " [SiteNumber]"
                    + ",[Contractor]"
                    + ",[Text]"
                    + ",[DateFrom]"
                    + ",[DateTo]"
                    + " ) " 
                    + "VALUES(?,?,?,?,?)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql,
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            
                for (TableItemPeriod obsItem : items) {
                    pstmt.setString   (1, obsItem.getSiteNumber());
                    pstmt.setString   (2, obsItem.getContractor());
                    pstmt.setString   (3, obsItem.getText());
                    pstmt.setInt      (4, obsItem.getDateFrom());
                    pstmt.setInt      (5, obsItem.getDateTo());                                                       

                    int affectedRows = pstmt.executeUpdate();
                    
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys();){
                        if(generategKeys.next())
                            obsItem.setId(generategKeys.getLong(1));
                    }    
                }
           
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogLayoutController
//           items.forEach(item -> {
////                LogLayoutController.appendLogViewText("inserted item: "+ ((TableItem)item).getJM_name() 
////                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
//                });
            LogLayoutController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(ItemPeriodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
}
