
package report.entities.items.estimate;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.Item;
import report.layoutControllers.LogController;
import report.models.beck.sql.SqlConnector;
import report.models.mementos.Memento;
import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.layoutControllers.estimate.EstimateController.Est;
//import report.models.Formula_test;
import report.entities.items.cb.AddEstTIV;


public class EstimateDAO implements CommonNamedDAO<Collection<EstimateTVI>> {

    private Est enumEst;
    private String tableName = SQL.Tables.ESTIMATE;
    
    public EstimateDAO() { }
    public EstimateDAO(Est enumEst) {this.enumEst = enumEst; }
    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of Item
    */
    @Override
    public String getSqlTableName() {return this.tableName;}
    
    /**
    * Get List of TableWrapper Items (EstimateTVI) from SQL
    * @return  List of Item
    */
    @Override
    public ObservableList<EstimateTVI> getData() {
        ObservableList<EstimateTVI> listEstAll =  FXCollections.observableArrayList();
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

        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement prst = connection.prepareStatement(sql)) {
                prst.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                prst.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
                prst.setInt   (3, enumEst.getTaleType());
                
                ResultSet rs = prst.executeQuery();
                            
                    while(rs.next()){
                        EstimateTVI item =  new EstimateTVI(
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
               Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      
       return listEstAll ;
    }
     
    /**
    * Get <b>BASE</b>List of TableWrapper Items (EstimateTVI) from SQL
    * @return  List of Item
    */
    public ObservableList<AddEstTIV> getBaseList(String BildingPart) {
              ObservableList<AddEstTIV> list =  FXCollections.observableArrayList();
      
            String psmtmtString ="SELECT"
                                        + " * "
                                        + "FROM  [Estimate] E  "
                                        + "WHERE E.[SiteNumber]  = '0' "
                                        + "AND   E.[TableType]   = 0 "
                                        + "And   E.[TypeHome]    = ? "
                                        + "And   E.[BuildingPart] = ? ";
        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString); ) {
           
            pstmt.setString(1, Est.Base.getSiteSecondValue(SQL.Common.TYPE_HOME));
            pstmt.setString(2, BildingPart);
           
            ResultSet rs = pstmt.executeQuery();
                   while(rs.next()){
//                            ceckResult = true;
                            list.add(new AddEstTIV(
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
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    /**
    * Get List of TableWrapper Items (EstimateTVI) from SQL
    * <b>(only ONE List use table title)</b>
     * 
     * @param enumEst (enumeration)
     * @param title   (String)
     * @return List of Item
    */
    public ObservableList<EstimateTVI> getOneBildingPartList(Est enumEst, String  title) {
        
        ObservableList<EstimateTVI> listEstAll =  FXCollections.observableArrayList();
        
        
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
                
        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement prst = connection.prepareStatement(ResultSetString)) {
                prst.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
                prst.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
                prst.setString(3, title);
                prst.setInt   (4, enumEst.getTaleType());
                
                ResultSet rs = prst.executeQuery();
                            
                        while(rs.next()){
             
                        EstimateTVI item =  new EstimateTVI(
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
               Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      
       return listEstAll ;
    }

    /**
     * Delete EstimateTVI  Entities from SQL
    * @param items (Collection of Item)
    */
    @Override
    public void delete(Collection<EstimateTVI> items) {
                
        try(Connection connection   = SqlConnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement("update [dbo].[Estimate] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (Item obsItem : items) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           items.forEach(item -> {
                LogController.appendLogViewText("deleted EST item: "+ ((Item)item).getJM_name()
                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
                                                         +" [BP/ "+((Item)item).getBindJob()     + "]"
                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Override
//    public void delete(Collection<Item> items) {
//                
//        try(Connection connection   = SqlConnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute dellRowEst ?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (Item obsItem : items) {
//                    pstmt.setString   (1, obsItem.getSiteNumber());
//                    pstmt.setString   (2, obsItem.getContractor());
//                    pstmt.setString   (3, obsItem.getBuildingPart());
//                    pstmt.setString   (4, obsItem.getJM_name());
//                    pstmt.setString   (5, obsItem.getBindJob());
//                    pstmt.setInt      (6, ((EstimateTVI)obsItem).getTableType());
//                    
//                    pstmt.addBatch();  
//                }
//           pstmt.executeBatch();
//           //SQL commit
//           connection.commit();
//           //add info to LogTextArea / LogController
//           items.forEach(item -> {
//                LogController.appendLogViewText("deleted item: "+ ((Item)item).getJM_name()
//                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((Item)item).getBindJob()     + "]"
//                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
//                });
//            LogController.appendLogViewText(items.size() + " deleted");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
    /**
     * Delete EstimateTVI  Entities from SQL
    * @param items (Collection of Item)
    */
    @Override
    public void insert(Collection<EstimateTVI> items) {
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
        try(Connection connection = SqlConnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql, 
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (Item obsItem : items) {
                    pstmt.setString   (1,  obsItem.getSiteNumber());
                    pstmt.setString   (2,  obsItem.getTypeHome());
                    pstmt.setString   (3,  obsItem.getContractor());
                    pstmt.setString   (4,  obsItem.getJM_name());
                    pstmt.setString   (5,  obsItem.getJobOrMat());
                    pstmt.setString   (6,  obsItem.getBindJob());
                    pstmt.setString   (7,  obsItem.getUnit());
                    pstmt.setDouble    (8,  obsItem.getQuantity());
                    pstmt.setDouble    (9,  obsItem.getPriceOne());
                    pstmt.setString   (10,  obsItem.getBuildingPart());
                    pstmt.setInt      (11,  ((EstimateTVI)obsItem).getTableType());

                    
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
                LogController.appendLogViewText("inserted item: "+ ((Item)item).getJM_name()
                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
                                                         +" [BP/ "+((Item)item).getBindJob()     + "]"
                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
                });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        
        
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(pstString);){
             //1: coefficient / 5: from - SiteNumber / 6: from - Contractor / 7: from - TableType
//            if(enumEst == Est.Base){
//                pstmt.setFloat  (1,  1);
//                pstmt.setString (5, "0");
//                pstmt.setString (6, "0"); 
//                pstmt.setInt    (7,  0); 
//            } else
//            if(enumEst == Est.Changed){
//                pstmt.setDouble  (1, Formula.getQuantity());
////                pstmt.setFloat  (1, coefficient.getQuantity());
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
//            System.out.println("OEF INSERRT " + coefficient.getQuantity());
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    @Override
//    public void insert(Collection<Item> items) {
//        try(Connection connection = SqlConnector.getInstance();
//            PreparedStatement pstmt  = connection.prepareStatement("execute addRowEst ?,?,?,?,?,?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (Item obsItem : items) {
//                    pstmt.setString   (1,  obsItem.getSiteNumber());
//                    pstmt.setString   (2,  obsItem.getContractor());
//                    pstmt.setString   (3,  obsItem.getBuildingPart());
//                    pstmt.setString   (4,  obsItem.getJM_name());
//                    pstmt.setString   (5,  obsItem.getBindJob());
//                    pstmt.setInt      (6,  ((EstimateTVI)obsItem).getTableType());
//                    pstmt.setString   (7,  obsItem.getJobOrMat());
//                    pstmt.setFloat    (8,  obsItem.getQuantity());
//                    pstmt.setString   (9,  obsItem.getUnit());
//                    pstmt.setFloat    (10, obsItem.getPriceOne());
//                    pstmt.setString   (11, obsItem.getTypeHome());
//                    
//                    pstmt.addBatch();  
//                }
//           pstmt.executeBatch();
//           //SQL commit
//           connection.commit();
//           //add info to LogTextArea / LogController
//           items.forEach(item -> {
//                LogController.appendLogViewText("inserted item: "+ ((Item)item).getJM_name()
//                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((Item)item).getBindJob()     + "]"
//                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
//                });
//            LogController.appendLogViewText(items.size() + " inserted");
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    /**
     * Delete EstimateTVI  Entities from SQL and then insert new Entities.
     * Use TableWrapper to saveEst  <b>tableMemento</b> and <b>current</b> (Current List of elements).
     * <br>Then update TableWrapper Items ( to saveEst new Element's ID of SQL)
     * <br>Then update "ALL elements List"
    * @param memento (TableWrapper)
    */
    @Override
    public void dellAndInsert(Memento<Collection<EstimateTVI>> memento){
//        Collection tableMemento = table.getMemento().getSavedState(),
//                   current = table.getObservableItems();
//        
//        DataUtils.DiffList diffList = new DataUtils.DiffList(tableMemento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.exElements());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
        CommonNamedDAO.super.dellAndInsert(memento);
       //?????????????
//        table.updateTableFromSQL(this.getOneBildingPartList(Est.Base, table.getTitle()));
        Est.Base.updateList_DL(this);
       }
//       @Override
//    public void dellAndInsert(AbstractTableWrapper<Collection<EstimateTVI>> table){
////        Collection tableMemento = table.getMemento().getSavedState(),
////                   current = table.getObservableItems();
////
////        DataUtils.DiffList diffList = new DataUtils.DiffList(tableMemento,current);
////        if(diffList.exElements() != null
////           || diffList.exElements().size() > 0) delete(diffList.exElements());
////        if(diffList.newElements()  != null
////           || diffList.newElements().size()  > 0) insert(diffList.newElements());
//        TableViewItemDAO.super.dellAndInsert(table);
//       //?????????????
////        table.updateTableFromSQL(this.getOneBildingPartList(Est.Base, table.getTitle()));
//        Est.Base.updateList_DL(this);
//       }
    
       
    
}
