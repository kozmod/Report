
package report.entities.items.contractor;

import report.entities.TableViewItemDAO;
import report.entities.items.variable.VariablePropertiesCommonDAOTableView;
import report.layoutControllers.LogController;
import report.entities.items.osr.TableViewItemOSRDAO;
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
import report.models.sql.SQLconnector;
import report.models_view.nodes.node_wrappers.TableWrapper;


public class TableViewItemContractorDAO implements TableViewItemDAO<TableItemContractor, TableWrapper> {

    @Override
    public String sqlTableName() {
        return SQL.Tables.CONTRACTORS;
    }
    /**
     * Get List of Contractor Items from SQL (Contractor)
    * @return  ObservableList of TableItemContractor
    */
    @Override
    public ObservableList<TableItemContractor> getData() {
        ObservableList<TableItemContractor> listAllContractors 
                =  FXCollections.observableArrayList(TableItemContractor.extractor());
        
        String sqlQuery = "SELECT "
                       + " * "
                       + "FROM dbo.[Contractors] "
                       + "WHERE [dell] = 0";
        
          try(Connection connection = SQLconnector.getInstance();
              Statement st = connection.createStatement();
              ResultSet rs = st.executeQuery(sqlQuery);) {
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
               Logger.getLogger(VariablePropertiesCommonDAOTableView.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  listAllContractors;
    }

    public TableItemContractor getOne(String contractorName) {

        TableItemContractor contractor = null;

        String sqlQuery = "SELECT "
                       + " * "
                       + ",[Contractor] "
                       + "FROM dbo.[Contractors] "
                       + "WHERE [dell] = 0 "
                       + "AND [Contractor] = ? ";

          try(Connection connection = SQLconnector.getInstance();
              PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
              pstmt.setString(1,contractorName);

              try(ResultSet rs = pstmt.executeQuery();) {
                  while (rs.next()) {
                      contractor = new TableItemContractor(
                              rs.getLong("id"),
                              rs.getString("Contractor"),
                              rs.getString("Director"),
                              rs.getString("Adress"),
                              rs.getString("Comments")
                      );


                  }
              }
           } catch (SQLException ex) {
               Logger.getLogger(VariablePropertiesCommonDAOTableView.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  contractor;
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
                    pstmt.setLong   (1, obsItem.getID());
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
            Logger.getLogger(VariablePropertiesCommonDAOTableView.class.getName()).log(Level.SEVERE, null, ex);
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
                            obsItem.setID(generategKeys.getLong(1));
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
            Logger.getLogger(TableViewItemOSRDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void dellAndInsert(TableWrapper table) {
//        Collection tableMemento = table.getMemento().getSavedState(),
//                   current = table.getItems();
//        
//        
////        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
////           table.getCRUD().getDelete().forEach(i -> System.out.println("Delete - " + ((TableClone)i).getID()));
////           table.getCRUD().getCreate().forEach(i -> System.out.println("INS    - " + ((TableClone)i).getID()));
////           table.getCRUD().getUpdate().forEach(i -> System.out.println("UPDATE - " + ((TableClone)i).getID()));
////           table.getCRUD().clearAll();
////            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,,>");
//        DataUtils.DiffList<TableItemContractor> diffList = new DataUtils.DiffList(tableMemento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.exElements());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
//
//        
//    }


    
}
