
package report.entities.items.KS;

import report.entities.TableViewItemDAO;
import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.layoutControllers.LogController;
import report.layoutControllers.estimate.EstimateController.Est;
import report.models.sql.SQLconnector;
import report.entities.items.TableItem;
import report.models_view.nodes.node_wrappers.TableWrapperEST;


public class TableViewItemKSDAO implements TableViewItemDAO<TableItemKS, TableWrapperEST> {
    
    private Est enumEst;
    
    public Est getEnumEst(){return enumEst;}

    public TableViewItemKSDAO() {}
    public TableViewItemKSDAO(Est enumEst){this.enumEst = enumEst;}
    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public String getTableString() {return SQL.Tables.KS;}
    
    
    
    /**
    * Get List of TableWrapper Items (TableItemKS) from SQL
    * @return  List of TableItemKS
    */
    @Override
    public ObservableList<TableItemKS> getList(){
        
        ObservableList<TableItemKS> list = FXCollections.observableArrayList();
        
        String psmtmtString = "SELECT  "
                       + " [id]"
                       + ",[KS_Number]"
                       + ",[KS_Date]"
                       + ",[SiteNumber]"
                       + ",[TypeHome]"
                       + ",[Contractor]"
                       + ",[JM_name]"
                       + ",[JobsOrMaterials]"
                       + ",[BindedJob]"
                       + ",[Value]"
                       + ",[Unit]"
                       + ",[Price_one]"
                       + ",[Price_sum]"
                       + ",[BuildingPart]"
                       + ",[DateCreate]"
                       + ",[dell]"
                + "From dbo.[KS]"
                + "Where [SiteNumber] = ? "
                + "And [Contractor] = ? ";
//                + "And [dell] = 0 ";
        
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            
                pstmt.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                pstmt.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
                pstmt.execute();
            
            ResultSet rs = pstmt.getResultSet();
            
                while(rs.next()){
                    
                    TableItemKS item = new TableItemKS(
                                    rs.getLong     (SQL.Common.ID),
                                    rs.getTimestamp(SQL.Common.DATE_CREATE),
                                    rs.getInt      (SQL.KS.NUMBER),
                                    rs.getInt      (SQL.KS.DATE),
                                    rs.getObject   (SQL.Common.SITE_NUMBER).toString(),
                                    rs.getObject   (SQL.Common.TYPE_HOME).toString(),
                                    rs.getObject   (SQL.Common.CONTRACTOR).toString(),
                                    rs.getObject   (SQL.Estimate.JM_NAME).toString(),
                                    rs.getObject   (SQL.Estimate.JOB_MATERIAL).toString(),
                                    rs.getObject   (SQL.Estimate.BINDED_JOB).toString(),
                                    rs.getDouble    (SQL.Estimate.VALUE),
                                    rs.getObject   (SQL.Estimate.UNIT).toString(),
                                    rs.getDouble    (SQL.Estimate.PRICE_ONE),
                                    rs.getDouble    (SQL.Estimate.PRICE_SUM),
                                    rs.getObject   (SQL.Estimate.BUILDING_PART).toString()
                            );
                            item.setDel(rs.getInt(SQL.Common.DEL));
                    list.add(item);     
                }
   
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    /**
    * Get List of TableWrapper Items (TableItemKS) from SQL
    * <b>(only ONE List use KS number)</b>
     * 
     * @param enumEst  (enumeration)
     * @param ksNumber (integer)
     * @return List of TableItemKS
    */
    
    public List<TableItemKS> getOneKSList(Est enumEst, int ksNumber){
        
        List<TableItemKS> list = FXCollections.observableArrayList();
        
        String psmtmtString = "SELECT  "
                       + " * "
                + "FROM  dbo.[KS]"
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [KS_Number] = ? "
                + "AND   [dell] = 0 ";
        
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            
                pstmt.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER).toString());
                pstmt.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR).toString());
                pstmt.setInt   (3, ksNumber);
                pstmt.execute();
            
            ResultSet rs = pstmt.getResultSet();
            
                while(rs.next()){
                    
                    TableItemKS item = new TableItemKS(
                                    rs.getLong     (SQL.Common.ID),
                                    rs.getTimestamp(SQL.Common.CONTRACTOR),
                                    rs.getInt      (SQL.KS.NUMBER),
                                    rs.getInt      (SQL.KS.DATE),
                                    rs.getObject   (SQL.Common.SITE_NUMBER).toString(),
                                    rs.getObject   (SQL.Common.TYPE_HOME).toString(),
                                    rs.getObject   (SQL.Common.CONTRACTOR).toString(),
                                    rs.getObject   (SQL.Estimate.JM_NAME).toString(),
                                    rs.getObject   (SQL.Estimate.JOB_MATERIAL).toString(),
                                    rs.getObject   (SQL.Estimate.BINDED_JOB).toString(),
                                    rs.getDouble    (SQL.Estimate.VALUE),
                                    rs.getObject   (SQL.Estimate.UNIT).toString(),
                                    rs.getDouble    (SQL.Estimate.PRICE_ONE),
                                    rs.getDouble    (SQL.Estimate.PRICE_SUM),
                                    rs.getObject   (SQL.Estimate.BUILDING_PART).toString()
                            );
                            item.setDel(rs.getInt(SQL.Common.DEL));
                    list.add(item);     
                }
   
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
    * Delete TableItemKS  Entities from SQL
    * @param items (Collection of TableItemKS) 
    */
    @Override
    public void delete(Collection<TableItemKS> items) {
        String sql = "update [dbo].[KS] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
                for (TableItemKS obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           connection.commit();
           items.forEach(item -> {
                LogController.appendLogViewText("deleted KS item: "
                                                         + item.getJM_name() 
                                                         +" [KS# "+item.getKSNumber()   + "]"
                                                         +" [JM/ "+item.getJobOrMat()   + "]"
                                                         +" [BP/ "+item.getBindedJob()  + "]"
                                                         +" [S#/ "+item.getSiteNumber() + "]"
                                                         +" [C/ " +item.getContractor() + "]");
                });
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Override
//    public void delete(Collection<TableItemKS> items) {
//        try(Connection connection   = SQLconnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute dellRowKS ?,?,?,?,?,?");) {
//            connection.setAutoCommit(false);
//                for (TableItemKS obsItem : items) {
////                    
//                    pstmt.setInt      (1, obsItem.getKSNumber());
//                    pstmt.setString   (2, obsItem.getSiteNumber());
//                    pstmt.setString   (3, obsItem.getContractor());
//                    pstmt.setString   (4, obsItem.getBildingPart());
//                    pstmt.setString   (5, obsItem.getJM_name());
//                    pstmt.setString   (6, obsItem.getBindedJob());
//                    
//                    pstmt.addBatch();
//                }
//           pstmt.executeBatch();
//           connection.commit();
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    /**
    * Delete All Entities in One KS from SQL
     * @param ksNumber  (String)
    */
    public static void deleteKS(String ksNumber){
        
        String psmtmtString ="DELETE FROM dbo.[KS]  WHERE [KS_Number]= ? and [SiteNumber] = ? and [Contractor] = ?";
       
        try(Connection connection = SQLconnector.getInstance(); 
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, ksNumber);
            pstmt.setString(2, Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
            pstmt.setString(3, Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } 
    }

    @Override
    public void insert(Collection<TableItemKS> items) {
        String sql = "INSERT into [dbo].[KS] "
                                    + "( " 
                                    + " [KS_Number]" 
                                    + ",[KS_Date]" 
                                    + ",[SiteNumber]" 
                                    + ",[TypeHome]" 
                                    + ",[Contractor]" 
                                    + ",[JM_name]" 
                                    + ",[JobsOrMaterials]" 
                                    + ",[BindedJob]" 
                                    + ",[Unit]" 
                                    + ",[Value]" 
                                    + ",[Price_one]" 
                                    + ",[BuildingPart]"  
                                    + " ) " 
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql,
                                                                  Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemKS obsItem : items) {
                    pstmt.setInt      (1,  obsItem.getKSNumber());
                    pstmt.setInt      (2,  obsItem.getKSDate());
                    pstmt.setString   (3,  obsItem.getSiteNumber());
                    pstmt.setString   (4,  obsItem.getTypeHome());
                    pstmt.setString   (5,  obsItem.getContractor());
                    pstmt.setString   (6,  obsItem.getJM_name());
                    pstmt.setString   (7,  obsItem.getJobOrMat());
                    pstmt.setString   (8,  obsItem.getBindedJob());
                    pstmt.setString   (9,  obsItem.getUnit());
                    pstmt.setDouble    (10, obsItem.getValue());
                    pstmt.setDouble    (11, obsItem.getPrice_one());
                    pstmt.setString   (12, obsItem.getBildingPart());
                    
                    int affectedRows = pstmt.executeUpdate();
                    
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys();){
                        if(generategKeys.next())
                            obsItem.setId(generategKeys.getLong(1));
                    }   
                }; 
           //SQL commit
           connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
//    public void insert(Collection<TableItemKS> items) {
//        try(Connection connection   = SQLconnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute addRowKS ?,?,?,?,?,?,?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (TableItemKS obsItem : items) {
//                    pstmt.setInt      (1,  obsItem.getKSNumber());
//                    pstmt.setInt      (2,  obsItem.getKSDate());
//                    pstmt.setString   (3,  obsItem.getSiteNumber());
//                    pstmt.setString   (4,  obsItem.getContractor());
//                    pstmt.setString   (5,  obsItem.getBildingPart());
//                    pstmt.setString   (6,  obsItem.getJM_name());
//                    pstmt.setString   (7,  obsItem.getBindedJob());
//                    pstmt.setString   (8,  obsItem.getJobOrMat());
//                    pstmt.setFloat    (9,  obsItem.getValue());
//                    pstmt.setString   (10,  obsItem.getUnit());
//                    pstmt.setFloat    (11, obsItem.getPrice_one());
//                    pstmt.setString   (12, obsItem.getTypeHome());
//                    
//                    pstmt.addBatch();
//                }
//           pstmt.executeBatch(); 
//           //SQL commit
//           connection.commit();
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    /**
     * Insert NEW KS into List of KS (KS Tab).
     * 
     * @param ks_Number
     * @param ks_Date
     * @param listKS (List<TableItem> listKS)
     */
    public void insertNewKS(int ks_Number,int ks_Date, List<TableItem> listKS){
                     
        String pstString ="INSERT into dbo.[KS] ("
                                            + " [KS_Number]"
                                            + ",[KS_Date]"
                                            + ",[SiteNumber]"
                                            + ",[TypeHome]"
                                            + ",[Contractor]"
                                            + ",[JM_name]"
                                            + ",[JobsOrMaterials]"
                                            + ",[BindedJob]"
                                            + ",[Unit]"
                                            + ",[Value]"
                                            + ",[Price_one]"
                                            + ",[BuildingPart]"
                                            + ",[NumberSession]"
                                            + ",[dell]"
                                            + ",[DateCreate]"
                                            + ")"
                                        + " select "
                                            + "?"
                                            + ",?"
                                            + ",E.[SiteNumber]"
                                            + ",E.[TypeHome]"
                                            + ",E.[Contractor]"
                                            + ",E.[JM_name]"
                                            + ",E.[JobsOrMaterials]"
                                            + ",E.[BindedJob]"
                                            + ",E.[Unit]"
                                            + ",E.[Value]"
                                            + ",E.[Price_one]"
                                            + ",E.[BuildingPart]"
                                            + ",E.[NumberSession]"
                                            + ",E.[dell]"
                                            + ",E.[DateCreate] "
                                        + "FROM  [Estimate] E "
                                        + "WHERE E.[SiteNumber] = ? "
                                        + "And   E.[Contractor] = ? "
                                        + "And   E.[JM_name]    = ? "
                                        + "And   E.[BindedJob]  = ? "
                                        + "And   E.[TableType]  = 2 "
                                        + "And   E.[dell]       = 0 ";
        
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(pstString);) {
                for (TableItem item : listKS) {
            
                    pstmt.setInt   (1, ks_Number);
                    pstmt.setInt   (2, (int)(Math.round(ks_Date*100)/100));
                    pstmt.setString(3, item.getSiteNumber());
                    pstmt.setString(4, item.getContractor());
                    pstmt.setString(5, item.getJM_name());    //JM_Name
                    pstmt.setString(6, item.getBindedJob()); //bindedJob
                
                    pstmt.addBatch();
                }
           pstmt.executeBatch();
           
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
//    public void dellAndInsert(Collection<TableItemKS> dellItem, Collection<TableItemKS> newItem){
    @Override
    public void dellAndInsert(TableWrapperEST table){
        TableViewItemDAO.super.dellAndInsert(table);
        //??????????
//        table.updateTableFromSQL(this.getOneKSList(Est.KS, ksNumber ));
        Est.KS.updateList_DL(this);
        
       }  
    
}
