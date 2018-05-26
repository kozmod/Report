
package report.entities.items.estimate;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.LogController;
import report.layout.controllers.estimate.EstimateController;
import report.models.sql.SqlConnector;
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
import report.layout.controllers.estimate.EstimateController.Est;
//import report.models.Formula_test;
import report.entities.items.cb.AddEstTIV;


public class EstimateDAO implements CommonNamedDAO<Collection<EstimateTVI>> {

    private Est enumEst;
    private String tableName = SQL.Tables.ESTIMATE;

    public EstimateDAO() {
    }

    public EstimateDAO(Est enumEst) {
        this.enumEst = enumEst;
    }

    /**
     * Get String of a Mirror (SQL.Tables).
     *
     * @return List of AbstractEstimateTVI
     */
    @Override
    public String getSqlTableName() {
        return this.tableName;
    }

    /**
     * Get List of TableWrapper Items (EstimateTVI) from SQL
     *
     * @return List of AbstractEstimateTVI
     */
    @Override
    public ObservableList<EstimateTVI> getData() {
        return this.getData(
                enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER),
                enumEst.getTableType(),
                EstimateController.Est.Common.getCountAgentTVI()
        );
    }

    public ObservableList<EstimateTVI> getData(String siteNumber, int tableType,CountAgentTVI contractor) {
        ObservableList<EstimateTVI> listEstAll = FXCollections.observableArrayList();
        String sql = "SELECT "
                + "E.[id]"
                + ",E.[SiteNumber]"
                + ",E.[TypeHome] "
                + ",E.[JM_name] "
                + ",E.[JobsOrMaterials] "
                + ",E.[BindedJob]"
                + ",E.[Value]"
                + ",E.[Unit]"
                + ",E.[Price_one]"
                + ",E.[Price_sum]"
                + ",E.[BuildingPart]"
                + ",E.[TableType]"
                + ",E.[id_count]"
                + ",E.[DateCreate]"
                + ",E.[dell]"
                + ",(CASE  "
                + "WHEN EXISTS "
                + "(SELECT *  From dbo.[KS] KS "
                + "WHERE KS.[SiteNumber] = E.[SiteNumber] "
//                + "AND KS .[Contractor] = E.[Contractor] "
                + "AND KS .[id_count] = E.[id_count] "
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
                + "AND E.[id_count] = ? "
                + "AND E.[TableType] = ? ";
//                                            + "AND E.[dell] = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(sql)) {

            prst.setString(1, siteNumber);
            prst.setInt(2, contractor.getIdCountConst());
            prst.setInt(3, tableType);

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                EstimateTVI item = new EstimateTVI(
                        rs.getLong(SQL.Common.ID),
                        rs.getTimestamp(SQL.Common.DATE_CREATE),
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
//                        rs.getObject(SQL.Common.CONTRACTOR).toString(),
                        contractor.toString(),
                        rs.getObject(SQL.Estimate.JM_NAME).toString(),
                        rs.getObject(SQL.Estimate.JOB_MATERIAL).toString(),
                        rs.getObject(SQL.Estimate.BINDED_JOB).toString(),
                        rs.getDouble(SQL.Estimate.VALUE),
                        rs.getObject(SQL.Estimate.UNIT).toString(),
                        rs.getDouble(SQL.Estimate.PRICE_ONE),
                        rs.getDouble(SQL.Estimate.PRICE_SUM),
                        rs.getObject(SQL.Estimate.BUILDING_PART).toString(),
                        rs.getInt(SQL.Estimate.TABLE_TYPE),
                        rs.getBoolean(SQL.Estimate.EXIST)
                );
                //??
                item.setDel(rs.getInt(SQL.Common.DEL));
                listEstAll.add(item);
            }


            SQLWarning warning = prst.getWarnings();
            while (warning != null) {
                System.out.println(warning.getMessage());

//                LogController.getlogTextArea().appendText(warning.getMessage() + " \n");
                warning = warning.getNextWarning();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listEstAll;
    }


    /**
     * Get <b>BASE</b>List of TableWrapper Items (EstimateTVI) from SQL
     *
     * @return List of AbstractEstimateTVI
     */
    public ObservableList<AddEstTIV> getBaseList(String BildingPart) {
        ObservableList<AddEstTIV> list = FXCollections.observableArrayList();

        String psmtmtString = "SELECT"
                + " * "
                + "FROM  [Estimate] E  "
                + "WHERE E.[SiteNumber]  = '0' "
                + "AND   E.[TableType]   = 0 "
                + "And   E.[TypeHome]    = ? "
                + "And   E.[BuildingPart] = ? ";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {

            pstmt.setString(1, Est.Base.getSiteSecondValue(SQL.Common.TYPE_HOME));
            pstmt.setString(2, BildingPart);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
//                            ceckResult = true;
                list.add(new AddEstTIV(
                        0,
                        false,
                        new Timestamp(System.currentTimeMillis()),
                        Est.Base.getSiteSecondValue(SQL.Common.SITE_NUMBER),//rs.getObject("SiteNumber").formatNumber(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
                        Est.Base.getSiteSecondValue(SQL.Common.CONTRACTOR),//rs.getObject("Contractor").formatNumber(),
                        rs.getObject(SQL.Estimate.JM_NAME).toString(),
                        rs.getObject(SQL.Estimate.JOB_MATERIAL).toString(),
                        rs.getObject(SQL.Estimate.BINDED_JOB).toString(),
                        rs.getDouble(SQL.Estimate.VALUE),
                        rs.getObject(SQL.Estimate.UNIT).toString(),
                        rs.getDouble(SQL.Estimate.PRICE_ONE),
                        rs.getDouble(SQL.Estimate.PRICE_SUM),
                        rs.getObject(SQL.Estimate.BUILDING_PART).toString()
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
     * @return List of AbstractEstimateTVI
     */
    public ObservableList<EstimateTVI> getOneBuildingPartList(Est enumEst, String title) {

        ObservableList<EstimateTVI> listEstAll = FXCollections.observableArrayList();


        String ResultSetString = "SELECT "
                + " * "
                + ",(CASE  "
                + "WHEN EXISTS "
                + "(SELECT *  From dbo.[KS] KS "
                + "WHERE KS.[SiteNumber] = E.[SiteNumber] "
//                + "AND KS .[Contractor] = E.[Contractor] "
                + "AND KS .[id_count_const] = E.[id_count_const] "
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
//                + "AND   E.[Contractor] = ? "
                + "AND   E.[id_count_const] = ? "
                + "AND E.[BuildingPart] = ? "
                + "AND E.[TableType] = ? "
                + "AND E.[dell] = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(ResultSetString)) {

            CountAgentTVI contractor = EstimateController.Est.Common.getCountAgentTVI();
            prst.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER));
            prst.setInt(2, contractor.getIdCountConst());
            prst.setString(3, title);
            prst.setInt(4, enumEst.getTableType());

            ResultSet rs = prst.executeQuery();

            while (rs.next()) {

                EstimateTVI item = new EstimateTVI(
                        rs.getLong(SQL.Common.ID),
                        rs.getTimestamp(SQL.Common.DATE_CREATE),
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
//                        rs.getObject(SQL.Common.CONTRACTOR).toString(),
                        rs.getObject("id_count_const").toString(),
                        rs.getObject(SQL.Estimate.JM_NAME).toString(),
                        rs.getObject(SQL.Estimate.JOB_MATERIAL).toString(),
                        rs.getObject(SQL.Estimate.BINDED_JOB).toString(),
                        rs.getDouble(SQL.Estimate.VALUE),
                        rs.getObject(SQL.Estimate.UNIT).toString(),
                        rs.getDouble(SQL.Estimate.PRICE_ONE),
                        rs.getDouble(SQL.Estimate.PRICE_SUM),
                        rs.getObject(SQL.Estimate.BUILDING_PART).toString(),
                        rs.getInt(SQL.Estimate.TABLE_TYPE),
                        rs.getBoolean(SQL.Estimate.EXIST)
                );
                //??
                item.setDel(rs.getInt(SQL.Common.DEL));
                listEstAll.add(item);
            }


            SQLWarning warning = prst.getWarnings();
            while (warning != null) {
                System.out.println(warning.getMessage());

//                LogController.getlogTextArea().appendText(warning.getMessage() + " \n");
                warning = warning.getNextWarning();
            }


        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listEstAll;
    }

    /**
     * Delete EstimateTVI  Entities from SQL
     *
     * @param items (Collection of AbstractEstimateTVI)
     */
    @Override
    public void delete(Collection<EstimateTVI> items) {

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("update [dbo].[Estimate] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (AbstractEstimateTVI obsAbstractEstimateTVI : items) {
                pstmt.setLong(1, obsAbstractEstimateTVI.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
            items.forEach(item -> {
                LogController.appendLogViewText("deleted EST item: " + ((AbstractEstimateTVI) item).getJM_name()
                        + " [JM/ " + ((AbstractEstimateTVI) item).getJobOrMat() + "]"
                        + " [BP/ " + ((AbstractEstimateTVI) item).getBindJob() + "]"
                        + " [S#/ " + ((AbstractEstimateTVI) item).getSiteNumber() + "]"
                        + " [C/ " + ((AbstractEstimateTVI) item).getContractor() + "]");
            });
            LogController.appendLogViewText(items.size() + " deleted");

        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Override
//    public void delete(Collection<AbstractEstimateTVI> items) {
//                
//        try(Connection connection   = SqlConnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute dellRowEst ?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (AbstractEstimateTVI obsItem : items) {
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
//                LogController.appendLogViewText("deleted item: "+ ((AbstractEstimateTVI)item).getJM_name()
//                                                         +" [JM/ "+((AbstractEstimateTVI)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((AbstractEstimateTVI)item).getBindJob()     + "]"
//                                                         +" [S#/ " + ((AbstractEstimateTVI)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((AbstractEstimateTVI)item).getContractor()   + "]");
//                });
//            LogController.appendLogViewText(items.size() + " deleted");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    /**
     * Delete EstimateTVI  Entities from SQL
     *
     * @param items (Collection of AbstractEstimateTVI)
     */
    @Override
    public void insert(Collection<EstimateTVI> items) {
        String sql = "insert into [dbo].[Estimate] "
                + "("
                + "[SiteNumber]"
                + ",[TypeHome] "
                + ",[id_count_const] "
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
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,0)";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (AbstractEstimateTVI obsAbstractEstimateTVI : items) {
                CountAgentTVI contractor = EstimateController.Est.Common.getCountAgentTVI();
                pstmt.setString(1, obsAbstractEstimateTVI.getSiteNumber());
                pstmt.setString(2, obsAbstractEstimateTVI.getTypeHome());
                pstmt.setInt(3, contractor.getIdCountConst());
                pstmt.setString(4, obsAbstractEstimateTVI.getJM_name());
                pstmt.setString(5, obsAbstractEstimateTVI.getJobOrMat());
                pstmt.setString(6, obsAbstractEstimateTVI.getBindJob());
                pstmt.setString(7, obsAbstractEstimateTVI.getUnit());
                pstmt.setDouble(8, obsAbstractEstimateTVI.getQuantity());
                pstmt.setDouble(9, obsAbstractEstimateTVI.getPriceOne());
                pstmt.setString(10, obsAbstractEstimateTVI.getBuildingPart());
                pstmt.setInt(11, ((EstimateTVI) obsAbstractEstimateTVI).getTableType());


                int affectedRows = pstmt.executeUpdate();

                try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                    if (generategKeys.next())
                        obsAbstractEstimateTVI.setId(generategKeys.getLong(1));
                }
            }
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
            items.forEach(item -> {
                LogController.appendLogViewText("inserted item: " + ((AbstractEstimateTVI) item).getJM_name()
                        + " [JM/ " + ((AbstractEstimateTVI) item).getJobOrMat() + "]"
                        + " [BP/ " + ((AbstractEstimateTVI) item).getBindJob() + "]"
                        + " [S#/ " + ((AbstractEstimateTVI) item).getSiteNumber() + "]"
                        + " [C/ " + ((AbstractEstimateTVI) item).getContractor() + "]");
            });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertEstNewTables(Est enumEst) {

        String pstString =
                "DECLARE "
                        + " @coefficient as float"
                        + ",@SiteNumber  as varchar(100)"
//                        + ",@Contractor  as varchar(100)"
                        + ",@id_count_const  as int "
                        + ",@TypeHome    as varchar(100)"
                        + ",@TableType   as int"
                        + ",@SiteNumberFrom as varchar(100) "
//                        + ",@ContractorFrom as varchar(100) "
                        + ",@id_count_const_FROM as int "
                        + ",@TableTypeFrom  as int "
                        + "SET @SiteNumber  = ? "       //1
                        + "SET @id_count_const  = ? "   //2
                        + "SET @TypeHome    = ? "       //3
                        + "SET @TableType   = ? "       //4
//                        + "SET @coefficient = (SELECT top 1 K FROM dbo.[Site_new] WHERE [SiteNumber] = @SiteNumber AND [Contractor] = @Contractor AND [dell] = 0 ) "
                        + "SET @coefficient = (CASE WHEN @TableType = 1 THEN 1 "
                        + " WHEN @TableType = 2 THEN (SELECT top 1 K FROM dbo.[Site_new] WHERE [SiteNumber] = @SiteNumber AND [id_Count] = @id_count_const AND [dell] = 0) END) "
                        + "SET @SiteNumberFrom = (CASE WHEN @TableType = 1 THEN '0' WHEN @TableType = 2 then @SiteNumber END) "
//                        + "SET @ContractorFrom = (CASE WHEN @TableType = 1 THEN '0' WHEN @TableType = 2 then @Contractor END) "
                        + "SET @id_count_const_FROM = (CASE WHEN @TableType = 1 THEN -1 WHEN @TableType = 2 then @id_count_const END) "
                        + "SET @TableTypeFrom  = (CASE WHEN @TableType = 1 THEN  0  WHEN @TableType = 2 then 1           END) "
                        + "INSERT into dbo.[Estimate] ("
                        + " [SiteNumber]"
                        + ",[TypeHome]"
//                        + ",[Contractor]"
                        + ",[id_count_const]"
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
//                        + ",@Contractor "
                        + ",@id_count_const "
                        + ",E.[JM_name]"
                        + ",E.[JobsOrMaterials]"
                        + ",E.[BindedJob]"
                        + ",E.[Unit]"
                        + ",E.[Value]"
                        + ",E.[Price_one]*isNULL(@coefficient, 1) "
                        + ",E.[BuildingPart]"
                        + ",@TableType "
                        + ",E.[NumberSession]"
                        + ",E.[dell]"
                        + ",E.[DateCreate] "
                        + "FROM  [Estimate] E "
                        + "WHERE E.[SiteNumber] = @SiteNumberFrom "
//                        + "And   E.[Contractor] = @ContractorFrom "
                        + "And   E.[id_count_const] = @id_count_const_FROM "
                        + "And   E.[TableType]  = @TableTypeFrom "
                        + "And   E.[TypeHome]   = @TypeHome "
                        + "And   E.[dell] = 0 ";


        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(pstString);) {

            CountAgentTVI contractor = EstimateController.Est.Common.getCountAgentTVI();
            pstmt.setString(1, enumEst.getSiteSecondValue(SQL.Estimate.SITE_NUMBER));
            pstmt.setInt(2, contractor.getIdCountConst());
            pstmt.setString(3, enumEst.getSiteSecondValue(SQL.Estimate.TYPE_HOME));
            pstmt.setInt(4, enumEst.getTableType());


            pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    @Override
//    public void insert(Collection<AbstractEstimateTVI> items) {
//        try(Connection connection = SqlConnector.getInstance();
//            PreparedStatement pstmt  = connection.prepareStatement("execute addRowEst ?,?,?,?,?,?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (AbstractEstimateTVI obsItem : items) {
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
//                LogController.appendLogViewText("inserted item: "+ ((AbstractEstimateTVI)item).getJM_name()
//                                                         +" [JM/ "+((AbstractEstimateTVI)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((AbstractEstimateTVI)item).getBindJob()     + "]"
//                                                         +" [S#/ " + ((AbstractEstimateTVI)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((AbstractEstimateTVI)item).getContractor()   + "]");
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
     *
     * @param memento (TableWrapper)
     */
    @Override
    public void dellAndInsert(Memento<Collection<EstimateTVI>> memento) {
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
//        table.updateTableFromSQL(this.getOneBuildingPartList(Est.Base, table.getTitle()));
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
////        table.updateTableFromSQL(this.getOneBuildingPartList(Est.Base, table.getTitle()));
//        Est.Base.updateList_DL(this);
//       }


}
