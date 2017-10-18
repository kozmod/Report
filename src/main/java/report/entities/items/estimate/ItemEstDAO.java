
package report.entities.items.estimate;

import report.layoutControllers.LogController;
import report.entities.ItemDAO;
import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.layoutControllers.estimate.EstimateController.Est;
//import report.models.Formula_test;
import report.models.sql.SQLconnector;
import report.entities.items.TableItem;
import report.entities.items.cb.TableItemCB;
import report.models_view.nodes.TableWrapperEST;


public class ItemEstDAO implements ItemDAO<TableItemEst, TableWrapperEST> {

    private Est enumEst;
    
    public Est setEnumEst(){return enumEst;}
    
    public ItemEstDAO() { }
    public ItemEstDAO(Est enumEst) {this.enumEst = enumEst; }
    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public String getTableString() {return SQL.Tables.ESTIMATE;}
    
    /**
    * Get List of TableWrapper Items (TableItemEst) from SQL
    * @return  List of TableItem
    */
    @Override
    public ObservableList<TableItemEst> getList() {
        
        ObservableList<TableItemEst> listEstAll =  FXCollections.observableArrayList();
        
        String sql = "SELECT "
                                + "E.[id]"
                                + ",E.[SiteNumber]"
                                + ",E.[TypeHome] "
                                + ",E.[Contractor] "
                                + ",E.[JM_name] "
                                + ",E.[JobsOrMaterials] "
                                + ",E.[BindedJob]"
                                + ",E.[Value]"
                                + ",E.[Unit]"
                                + ",E.[Price_one]"
                                + ",E.[Price_sum]"
                                + ",E.[BuildingPart]"
                                + ",E.[TableType]"
                                + ",E.[DateCreate]"
                                + ",E.[dell]"
                                  + ",(CASE  "
                                        + "WHEN EXISTS "
                                            + "(SELECT *  From dbo.[KS] KS "
                                                + "WHERE KS.[SiteNumber] = E.[SiteNumber] "
                                                + "AND KS .[Contractor] = E.[Contractor] "
                                                + "AND KS .[TypeHome] = E.[TypeHome] "
                                                + "AND KS .[JM_name] = E.[JM_name] "
                                                + "AND KS .[JobsOrMaterials] = E.[JobsOrMaterials] "
                                                + "AND KS .[BindedJob] = E.[BindedJob] "
                                                + "AND KS .[Unit] = E.[Unit] "
                                                + "AND KS .[BuildingPart] = E.[BuildingPart] "
                                            + ")"
                                            + "THEN CAST(1 AS bit) "
                                            + "ELSE CAST(0 AS bit) "
                                            + "END "
                                            + ") AS 'Exist' "
                                            + "FROM   dbo.[Estimate] E "
                                            + "WHERE E.[SiteNumber] = ? "
                                            + "AND E.[Contractor] = ? "
                                            + "AND E.[TableType] = ? ";
//                                            + "AND E.[dell] = 0 ";
                
        try(Connection connection = SQLconnector.getInstance();
                PreparedStatement prst = connection.prepareStatement(sql)) {
                prst.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                prst.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
                prst.setInt   (3, enumEst.getTaleType());
                
                ResultSet rs = prst.executeQuery();
                            
                    while(rs.next()){
                        TableItemEst item =  new TableItemEst(
                                    rs.getLong      (SQL.Common.ID),
                                    rs.getTimestamp (SQL.Common.DATE_CREATE),
                                    rs.getObject    (SQL.Common.SITE_NUMBER).toString(),
                                    rs.getObject    (SQL.Common.TYPE_HOME).toString(),
                                    rs.getObject    (SQL.Common.CONTRACTOR).toString(),
                                    rs.getObject    (SQL.Estimate.JM_NAME).toString(),
                                    rs.getObject    (SQL.Estimate.JOB_MATERIAL).toString(),
                                    rs.getObject    (SQL.Estimate.BINDED_JOB).toString(),
                                    rs.getDouble(SQL.Estimate.VALUE),
                                    rs.getObject    (SQL.Estimate.UNIT).toString(),
                                    rs.getDouble     (SQL.Estimate.PRICE_ONE),
                                    rs.getDouble     (SQL.Estimate.PRICE_SUM),
                                    rs.getObject    (SQL.Estimate.BUILDING_PART).toString(),
                                    rs.getInt       (SQL.Estimate.TABLE_TYPE),
                                    rs.getBoolean   (SQL.Estimate.EXIST)
                                    );
                        //??
                            item.setDel(rs.getInt(SQL.Common.DEL));
                        listEstAll.add(item);
                        }
                       
            
                SQLWarning warning = prst.getWarnings();
                while (warning != null){
                System.out.println(warning.getMessage());
                
//                LogController.getlogTextArea().appendText(warning.getMessage() + " \n");
                warning = warning.getNextWarning();
            }
                
                
                
                    
            } catch (SQLException ex) {
               Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      
       return listEstAll ;
    }
     
    /**
    * Get <b>BASE</b>List of TableWrapper Items (TableItemEst) from SQL
    * @return  List of TableItem
    */
    public ObservableList<TableItemCB> getBaseList(String BildingPart) {
              ObservableList<TableItemCB> list =  FXCollections.observableArrayList();
      
            String psmtmtString ="SELECT"
                                        + " * "
                                        + "FROM  [Estimate] E  "
                                        + "WHERE E.[SiteNumber]  = '0' "
                                        + "AND   E.[TableType]   = 0 "
                                        + "And   E.[TypeHome]    = ? "
                                        + "And   E.[BuildingPart] = ? ";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString); ) {
           
            pstmt.setString(1, Est.Base.getSiteSecondValue(SQL.Common.TYPE_HOME));
            pstmt.setString(2, BildingPart);
           
            ResultSet rs = pstmt.executeQuery();
                   while(rs.next()){
//                            ceckResult = true;
                            list.add(new TableItemCB(
                                    0,
                                    false, 
                                    new Timestamp(System.currentTimeMillis()),
                                    Est.Base.getSiteSecondValue(SQL.Common.SITE_NUMBER),//rs.getObject("SiteNumber").formatNumber(),
                                    rs.getObject (SQL.Common.TYPE_HOME).toString(),
                                    Est.Base.getSiteSecondValue(SQL.Common.CONTRACTOR),//rs.getObject("Contractor").formatNumber(),
                                    rs.getObject (SQL.Estimate.JM_NAME).toString(),
                                    rs.getObject (SQL.Estimate.JOB_MATERIAL).toString(),
                                    rs.getObject (SQL.Estimate.BINDED_JOB).toString(),
                                    rs.getDouble  (SQL.Estimate.VALUE),
                                    rs.getObject (SQL.Estimate.UNIT).toString(),
                                    rs.getDouble  (SQL.Estimate.PRICE_ONE),
                                    rs.getDouble  (SQL.Estimate.PRICE_SUM),
                                    rs.getObject (SQL.Estimate.BUILDING_PART).toString()
                            ));
                  
                        }
                   
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    /**
    * Get List of TableWrapper Items (TableItemEst) from SQL
    * <b>(only ONE List use table title)</b>
     * 
     * @param enumEst (enumeration)
     * @param title   (String)
     * @return List of TableItem
    */
    public List<TableItemEst> getOneBildingPartList(Est enumEst, String  title) {
        
        List<TableItemEst> listEstAll =  FXCollections.observableArrayList();
        
        
        String ResultSetString = "SELECT "
                                  + " * "
                                  + ",(CASE  "
                                        + "WHEN EXISTS "
                                            + "(SELECT *  From dbo.[KS] KS "
                                                + "WHERE KS.[SiteNumber] = E.[SiteNumber] "
                                                + "AND KS .[Contractor] = E.[Contractor] "
                                                + "AND KS .[TypeHome] = E.[TypeHome] "
                                                + "AND KS .[JM_name] = E.[JM_name] "
                                                + "AND KS .[JobsOrMaterials] = E.[JobsOrMaterials] "
                                                + "AND KS .[BindedJob] = E.[BindedJob] "
                                                + "AND KS .[Unit] = E.[Unit] "
                                                + "AND KS .[BuildingPart] = E.[BuildingPart] "
                                            + ")"
                                            + "THEN CAST(1 AS bit) "
                                            + "ELSE CAST(0 AS bit) "
                                            + "END "
                                            + ") AS 'Exist' "
                                            + "FROM   dbo.[Estimate] E "
                                            + "WHERE E.[SiteNumber] = ? "
                                            + "AND   E.[Contractor] = ? "
                                                            + "AND E.[BuildingPart] = ? "
                                            + "AND E.[TableType] = ? "
                                            + "AND E.[dell] = 0 ";
                
        try(Connection connection = SQLconnector.getInstance();
                PreparedStatement prst = connection.prepareStatement(ResultSetString)) {
                prst.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                prst.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
                prst.setString(3, title);
                prst.setInt   (4, enumEst.getTaleType());
                
                ResultSet rs = prst.executeQuery();
                            
                        while(rs.next()){
             
                        TableItemEst item =  new TableItemEst(
                                 rs.getLong      (SQL.Common.ID),
                                    rs.getTimestamp (SQL.Common.DATE_CREATE),
                                    rs.getObject    (SQL.Common.SITE_NUMBER).toString(),
                                    rs.getObject    (SQL.Common.TYPE_HOME).toString(),
                                    rs.getObject    (SQL.Common.CONTRACTOR).toString(),
                                    rs.getObject    (SQL.Estimate.JM_NAME).toString(),
                                    rs.getObject    (SQL.Estimate.JOB_MATERIAL).toString(),
                                    rs.getObject    (SQL.Estimate.BINDED_JOB).toString(),
                                    rs.getDouble     (SQL.Estimate.VALUE),
                                    rs.getObject    (SQL.Estimate.UNIT).toString(),
                                    rs.getDouble     (SQL.Estimate.PRICE_ONE),
                                    rs.getDouble     (SQL.Estimate.PRICE_SUM),
                                    rs.getObject    (SQL.Estimate.BUILDING_PART).toString(),
                                    rs.getInt       (SQL.Estimate.TABLE_TYPE),
                                    rs.getBoolean   (SQL.Estimate.EXIST)
                                    );
                        //??
                            item.setDel(rs.getInt(SQL.Common.DEL));
                        listEstAll.add(item);
                        }
                       
            
                SQLWarning warning = prst.getWarnings();
                while (warning != null){
                System.out.println(warning.getMessage());
                
//                LogController.getlogTextArea().appendText(warning.getMessage() + " \n");
                warning = warning.getNextWarning();
            }
                
                
                
                    
            } catch (SQLException ex) {
               Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      
       return listEstAll ;
    }

    /**
     * Delete TableItemEst  Entities from SQL
    * @param items (Collection of TableItem) 
    */
    @Override
    public void delete(Collection<TableItemEst> items) {
                
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement("update [dbo].[Estimate] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItem obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           items.forEach(item -> {
                LogController.appendLogViewText("deleted EST item: "+ ((TableItem)item).getJM_name()
                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Override
//    public void delete(Collection<TableItem> items) {
//                
//        try(Connection connection   = SQLconnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute dellRowEst ?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (TableItem obsItem : items) {
//                    pstmt.setString   (1, obsItem.getSiteNumber());
//                    pstmt.setString   (2, obsItem.getContractor());
//                    pstmt.setString   (3, obsItem.getBildingPart());
//                    pstmt.setString   (4, obsItem.getJM_name());
//                    pstmt.setString   (5, obsItem.getBindedJob());
//                    pstmt.setInt      (6, ((TableItemEst)obsItem).getTableType());
//                    
//                    pstmt.addBatch();  
//                }
//           pstmt.executeBatch();
//           //SQL commit
//           connection.commit();
//           //add info to LogTextArea / LogController
//           items.forEach(item -> {
//                LogController.appendLogViewText("deleted item: "+ ((TableItem)item).getJM_name()
//                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
//                });
//            LogController.appendLogViewText(items.size() + " deleted");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
    /**
     * Delete TableItemEst  Entities from SQL
    * @param items (Collection of TableItem) 
    */
    @Override
    public void insert(Collection<TableItemEst> items) {
        String sql = "insert into [dbo].[Estimate] "
                                + "("
                                + "[SiteNumber]"
                                + ",[TypeHome] "
                                + ",[Contractor] "
                                + ",[JM_name] "
                                + ",[JobsOrMaterials] "
                                + ",[BindedJob]"
                                + ",[Unit]"
                                + ",[Value]"
                                + ",[Price_one]"
                                + ",[BuildingPart]"
                                + ",[TableType]"
                                + ",[dell]"
                                + ")"
                                +"VALUES(?,?,?,?,?,?,?,?,?,?,?,0)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql, 
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItem obsItem : items) {
                    pstmt.setString   (1,  obsItem.getSiteNumber());
                    pstmt.setString   (2,  obsItem.getTypeHome());
                    pstmt.setString   (3,  obsItem.getContractor());
                    pstmt.setString   (4,  obsItem.getJM_name());
                    pstmt.setString   (5,  obsItem.getJobOrMat());
                    pstmt.setString   (6,  obsItem.getBindedJob());
                    pstmt.setString   (7,  obsItem.getUnit());
                    pstmt.setDouble    (8,  obsItem.getValue());
                    pstmt.setDouble    (9,  obsItem.getPrice_one());
                    pstmt.setString   (10,  obsItem.getBildingPart());
                    pstmt.setInt      (11,  ((TableItemEst)obsItem).getTableType());

                    
                    int affectedRows = pstmt.executeUpdate();
                    
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys();){
                        if(generategKeys.next())
                            obsItem.setId(generategKeys.getLong(1));
                    }   
                }
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           items.forEach(item -> {
                LogController.appendLogViewText("inserted item: "+ ((TableItem)item).getJM_name()
                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void insertEstNewTables(Est enumEst){

        String pstString =
                "DECLARE "
                    +" @coefficient as float"
                    +",@SiteNumber  as varchar(100)"
                    +",@Contractor  as varchar(100)"
                    +",@TypeHome    as varchar(100)"
                    +",@TableType   as int" 
                    +",@SiteNumberFrom as varchar(100) "
                    +",@ContractorFrom as varchar(100) "
                    +",@TableTypeFrom  as int "
                + "SET @SiteNumber  = ? "       //1                                                                                    
                + "SET @Contractor  = ? "       //2                                                                               
                + "SET @TypeHome    = ? "       //3                                                                           
                + "SET @TableType   = ? "       //4                                                                       
                    + "SET @coefficient = (SELECT top 1 K FROM dbo.[Site] WHERE [SiteNumber] = @SiteNumber AND [Contractor] = @Contractor AND [dell] = 0 ) "
                    + "SET @SiteNumberFrom = (CASE WHEN @TableType = 1 THEN '0' WHEN @TableType = 2 then @SiteNumber END) "
                    + "SET @ContractorFrom = (CASE WHEN @TableType = 1 THEN '0' WHEN @TableType = 2 then @Contractor END) "
                    + "SET @TableTypeFrom  = (CASE WHEN @TableType = 1 THEN  0  WHEN @TableType = 2 then 1           END) "
                        + "INSERT into dbo.[Estimate] ("
                                            + " [SiteNumber]"
                                            + ",[TypeHome]"
                                            + ",[Contractor]"
                                            + ",[JM_name]"
                                            + ",[JobsOrMaterials]"
                                            + ",[BindedJob]"
                                            + ",[Unit]"
                                            + ",[Value]"
                                            + ",[Price_one]"
                                            + ",[BuildingPart]"
                                            + ",[TableType]"
                                            + ",[NumberSession]"
                                            + ",[dell]"
                                            + ",[DateCreate]"
                                            + ")"
                                    + " SELECT "
                                            + " @SiteNumber"                               
                                            + ",E.[TypeHome]"
                                            + ",@Contractor "                             
                                            + ",E.[JM_name]"
                                            + ",E.[JobsOrMaterials]"
                                            + ",E.[BindedJob]"
                                            + ",E.[Unit]"
                                            + ",E.[Value]"
                                            + ",E.[Price_one]*isNULL(@coefficient, 1) "
//                                            + ",E.[Price_one]*(CASE "
//								+ "WHEN @coefficient is NULL  THEN 1 "
//								+ "ELSE @coefficient "
//								+ "END ) "
                                            + ",E.[BuildingPart]"
                                            + ",@TableType "                             
                                            + ",E.[NumberSession]"
                                            + ",E.[dell]"
                                            + ",E.[DateCreate] "
                                        + "FROM  [Estimate] E "
                                        + "WHERE E.[SiteNumber] = @SiteNumberFrom "           
                                            + "And   E.[Contractor] = @ContractorFrom "            
                                            + "And   E.[TableType]  = @TableTypeFrom "           
                                            + "And   E.[TypeHome]   = @TypeHome "           
                                            + "And   E.[dell] = 0 ";
        
        
        try (Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(pstString);){
             //1: coefficient / 5: from - SiteNumber / 6: from - Contractor / 7: from - TableType
//            if(enumEst == Est.Base){
//                pstmt.setFloat  (1,  1);
//                pstmt.setString (5, "0");
//                pstmt.setString (6, "0"); 
//                pstmt.setInt    (7,  0); 
//            } else
//            if(enumEst == Est.Changed){
//                pstmt.setDouble  (1, Formula.getValue());
////                pstmt.setFloat  (1, coefficient.getValue());
//                pstmt.setString (5, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
//                pstmt.setString (6, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
//                pstmt.setInt    (7, 1); 
//            }
//             //2: SiteNumber / 3: Contractor / 4: TableType / 8: TypeHome
//                pstmt.setString (2, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
//                pstmt.setString (3, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
//                pstmt.setInt    (4, enumEst.getTaleType());                                                       
//                pstmt.setString (8, enumEst.getSiteSecondValue(SQL.Common.TYPE_HOME));
            
                
                pstmt.setString (1, enumEst.getSiteSecondValue(SQL.Estimate.SITE_NUMBER));
                pstmt.setString (2, enumEst.getSiteSecondValue(SQL.Estimate.CONTRACTOR));
                pstmt.setString (3, enumEst.getSiteSecondValue(SQL.Estimate.TYPE_HOME));
                pstmt.setInt    (4, enumEst.getTaleType());

           

                                                                    
                


            
            pstmt.execute();
//            System.out.println("OEF INSERRT " + coefficient.getValue());
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    @Override
//    public void insert(Collection<TableItem> items) {
//        try(Connection connection = SQLconnector.getInstance();
//            PreparedStatement pstmt  = connection.prepareStatement("execute addRowEst ?,?,?,?,?,?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (TableItem obsItem : items) {
//                    pstmt.setString   (1,  obsItem.getSiteNumber());
//                    pstmt.setString   (2,  obsItem.getContractor());
//                    pstmt.setString   (3,  obsItem.getBildingPart());
//                    pstmt.setString   (4,  obsItem.getJM_name());
//                    pstmt.setString   (5,  obsItem.getBindedJob());
//                    pstmt.setInt      (6,  ((TableItemEst)obsItem).getTableType());
//                    pstmt.setString   (7,  obsItem.getJobOrMat());
//                    pstmt.setFloat    (8,  obsItem.getValue());
//                    pstmt.setString   (9,  obsItem.getUnit());
//                    pstmt.setFloat    (10, obsItem.getPrice_one());
//                    pstmt.setString   (11, obsItem.getTypeHome());
//                    
//                    pstmt.addBatch();  
//                }
//           pstmt.executeBatch();
//           //SQL commit
//           connection.commit();
//           //add info to LogTextArea / LogController
//           items.forEach(item -> {
//                LogController.appendLogViewText("inserted item: "+ ((TableItem)item).getJM_name()
//                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
//                });
//            LogController.appendLogViewText(items.size() + " inserted");
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    /**
     * Delete TableItemEst  Entities from SQL and then insert new Entities.
     * Use TableWrapper to saveEst  <b>memento</b> and <b>current</b> (Current List of elements).
     * <br>Then update TableWrapper Items ( to saveEst new Element's ID of SQL)
     * <br>Then update "ALL elements List"
    * @param table (TableWrapper)
    */
    @Override
    public void dellAndInsert(TableWrapperEST table){
//        Collection memento = table.getMemento().getSavedState(),
//                   current = table.getItems();     
//        
//        DataUtils.DiffList diffList = new DataUtils.DiffList(memento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.exElements());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
        ItemDAO.super.dellAndInsert(table);
       //?????????????
//        table.updateTableFromSQL(this.getOneBildingPartList(Est.Base, table.getTitle()));
        Est.Base.updateList_DL(this);
       }
    
       
    
}
