
package report.entities.items.contractor;

import report.entities.ItemDAO;
import report.entities.items.osr.ItemOSRDAO;
import report.entities.items.variable.ItemPropertiesFAO;
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
import report.models.sql.SQLconnector;
import report.view_models.nodes.Table;


public class ItemContractorDAO implements ItemDAO<TableItemContractor,  Table<TableItemContractor>> {

    @Override
    public String getTableString() {
        return SQL.Tables.CONTRACTORS;
    }
    /**
     * Get List of Contractor Items from SQL (Contractor)
    * @return  ObservableList of TableItemContractor
    */
    @Override
    public ObservableList<TableItemContractor> getList() {
        ObservableList<TableItemContractor> listAllContractors 
                =  FXCollections.observableArrayList(TableItemContractor.extractor());
        
        String sqlQuery = "SELECT "
                       + " * "
                       + "from dbo.[Contractors] "
                       + "WHERE [dell] = 0";
        
          try(Connection connection = SQLconnector.getInstance();
                Statement st = connection.createStatement();) {
               
               ResultSet rs = st.executeQuery(sqlQuery);

               while(rs.next()){
                   listAllContractors.add(new TableItemContractor(
                                                rs.getLong  (SQL.Common.ID),
                                                rs.getString(SQL.Site.CONTRACTOR),
                                                rs.getString(SQL.Contractors.DIRECTOR),
                                                rs.getString(SQL.Contractors.ADRESS),
                                                rs.getString(SQL.Contractors.COMMENTS)

                                                )       
                   );
               }
           } catch (SQLException ex) {
               Logger.getLogger(ItemPropertiesFAO.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  listAllContractors;
    }

/**
     * Delete TableItemContractor Entities from SQL (Contractors)
    * @param items (Collection of TableItemContractor) 
    */
    @Override
    public void delete(Collection<TableItemContractor> items) {
        String sql = "update [dbo].[Contractors] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemContractor obsItem : items) {
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
            Logger.getLogger(ItemPropertiesFAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Insert TableItemContractor Entities to SQL(Contractors)
    * @param items (Collection of TableItemContractor) 
    */
    @Override
    public void insert(Collection<TableItemContractor> items) {
        String sql = "INSERT into [dbo].[Contractors] "
                    + "( " 
                    + " [Contractor]" 
                    + ",[Director]" 
                    + ",[Adress]" 
                    + ",[Comments]" 
                    + " ) " 
                    + "VALUES(?,?,?,?)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql,
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            
                for (TableItemContractor obsItem : items) {
                    pstmt.setString (1,  obsItem.getContractor());
                    pstmt.setString (2,  obsItem.getDirector());
                    pstmt.setString (3,  obsItem.getAdress());
                    pstmt.setString (4,  obsItem.getComments());

     
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
            Logger.getLogger(ItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void dellAndInsert(Table table) {
//        Collection memento = table.getMemento().getSavedState(),
//                   current = table.getItems();
//        
//        
////        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
////           table.getCRUD().getDelete().forEach(i -> System.out.println("Delete - " + ((TableClone)i).getId()));
////           table.getCRUD().getCreate().forEach(i -> System.out.println("INS    - " + ((TableClone)i).getId()));
////           table.getCRUD().getUpdate().forEach(i -> System.out.println("UPDATE - " + ((TableClone)i).getId()));
////           table.getCRUD().clearAll();
////            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,,>");
//        DataUtils.DiffList<TableItemContractor> diffList = new DataUtils.DiffList(memento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.exElements());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
//
//        
//    }


    
}
