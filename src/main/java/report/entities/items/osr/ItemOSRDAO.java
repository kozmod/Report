
package report.entities.items.osr;

import report.layoutControllers.LogController;
import report.entities.ItemDAO;
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
//import report.models.Formula_test;
import report.models.sql.SQLconnector;
import report.models_view.nodes.TableWrapper;


public class ItemOSRDAO implements ItemDAO<TableItemOSR,TableWrapper> {

    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public String getTableString() {return SQL.Tables.SITE_OSR;}
    
    
    /**
    * Get Quantity of sites
     * @return siteQuantity (integer)
    */
    public int getSiteQuantity(){
        int siteQuantity = 0;
        try(Connection connection = SQLconnector.getInstance();
                Statement st = connection.createStatement();) {
               
               ResultSet rs = st.executeQuery("SELECT count(*) from dbo.[Site] WHERE [dell] = 0");

               while(rs.next()){siteQuantity = rs.getInt(1);}
               
           } catch (SQLException ex) {
               Logger.getLogger(ItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return siteQuantity;
    }
    
    /**
     * Get List of OSR Items from SQL
    * @return  ObservableList of TableItemOSR
    */
    @Override
    public ObservableList getList() {
        ObservableList<TableItemOSR> listAllOSR =  FXCollections.observableArrayList(TableItemOSR.extractor());
        
        String sqlQuery = "SELECT "
                       + " * "
                       + "from dbo.[SiteOSR] "
                       + "WHERE  [dell] = 0 "
                       + "ORDER BY [Text]";
        
          try(Connection connection = SQLconnector.getInstance();
                Statement st = connection.createStatement();) {
               
               ResultSet rs = st.executeQuery(sqlQuery);
               int q =  850;
//               int q =  Formula.getQuantityValue();
               while(rs.next()){
                   listAllOSR.add(new TableItemOSR(
                                                rs.getLong  (SQL.Common.ID),
                                                rs.getString(SQL.Common.TEXT),
                                                rs.getDouble(SQL.Common.VALUE),
//                                                rs.getDouble(SQL.Common.VALUE)/850
                                                rs.getDouble(SQL.Common.VALUE)/q
                                                )       
                   );
                   
               }
           } catch (SQLException ex) {
               Logger.getLogger(ItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  listAllOSR;
    }


    /**
     * Delete TableItemOSR Entities from SQL
    * @param items (Collection of TableItemOSR) 
    */
    @Override
    public void delete(Collection<TableItemOSR> items) {
        String sql = "update [dbo].[SiteOSR] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemOSR obsItem : items) {
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
            Logger.getLogger(ItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Insert TableItemOSR Entities to SQL
    * @param items (Collection of TableItemOSR) 
    */
    @Override
    public void insert(Collection<TableItemOSR> items) {
        String sql = "INSERT into [dbo].[SiteOSR] "
                    + "( " 
                    + "[Text]" 
                    + ",[Value]"  
                    + " ) " 
                    + "VALUES(?,?)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql,
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            
                for (TableItemOSR obsItem : items) {
                    pstmt.setString (1,  obsItem.getText());
                    pstmt.setDouble (2,  obsItem.getExpenses());
     
                    int affectedRows = pstmt.executeUpdate();
                    
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
            Logger.getLogger(ItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
//    @Override
//    public void dellAndInsert(TableWrapper table) {
//        Collection memento = table.getMemento().getSavedState(),
//                   current = table.getItems();
//        
////        DataUtils.DiffList diffList = new DataUtils.DiffList(memento,current);
////        if(diffList.exElements() != null 
////           || diffList.exElements().size() > 0) delete(diffList.exElements());
////        if(diffList.newElements()  != null 
////           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
//        DataUtils.DiffList diffList = new DataUtils.DiffList(memento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.intersection());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
//        
//       //?????????????
////        table.updateTableFromSQL(getList());
//        
//    }
    
}
