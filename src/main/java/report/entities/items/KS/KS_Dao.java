
package report.entities.items.KS;


import report.entities.abstraction.CommonNamedDao;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.estimate.EstimateController;
import report.models.sql.SqlConnector;
import report.models.mementos.Memento;
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
import report.layout.controllers.LogController;
import report.layout.controllers.estimate.EstimateController.Est;


public class KS_Dao implements CommonNamedDao<Collection<KS_TIV>> {

    private Est enumEst;

    public Est getEnumEst() {
        return enumEst;
    }

    public KS_Dao() {
    }

    public KS_Dao(Est enumEst) {
        this.enumEst = enumEst;
    }

    /**
     * Get String of a Mirror (SQL.Tables).
     *
     * @return List of AbstractEstimateTVI
     */
    @Override
    public String getSqlTableName() {
        return SQL.Tables.KS;
    }


    /**
     * Get List of TableWrapper Items (KS_TIV) from SQL
     *
     * @return List of KS_TIV
     */
    @Override
    public ObservableList<KS_TIV> getData() {
        return this.getData(
                enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER),
                EstimateController.Est.Common.getCountAgentTVI()
        );
    }

    public ObservableList<KS_TIV> getData(String siteNumber, CountAgentTVI contractor) {
        ObservableList<KS_TIV> list = FXCollections.observableArrayList();

        String psmtmtString = "SELECT  "
                + " [id]"
                + ",[KS_Number]"
                + ",[KS_Date]"
                + ",[SiteNumber]"
                + ",[TypeHome]"
//                + ",[Contractor]"
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
                + "And [id_count] = ? ";
//                + "And [dell] = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {

            pstmt.setString(1, siteNumber);
            pstmt.setInt(2, contractor.getIdCountConst());
            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();

            while (rs.next()) {

                KS_TIV item = new KS_TIV(
                        rs.getLong(SQL.Common.ID),
                        rs.getTimestamp(SQL.Common.DATE_CREATE),
                        rs.getInt(SQL.KS.NUMBER),
                        rs.getInt(SQL.KS.DATE),
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
                        contractor.toString(),
                        rs.getObject(SQL.Estimate.JM_NAME).toString(),
                        rs.getObject(SQL.Estimate.JOB_MATERIAL).toString(),
                        rs.getObject(SQL.Estimate.BINDED_JOB).toString(),
                        rs.getDouble(SQL.Estimate.VALUE),
                        rs.getObject(SQL.Estimate.UNIT).toString(),
                        rs.getDouble(SQL.Estimate.PRICE_ONE),
                        rs.getDouble(SQL.Estimate.PRICE_SUM),
                        rs.getObject(SQL.Estimate.BUILDING_PART).toString()
                );
                item.setDel(rs.getInt(SQL.Common.DEL));
                list.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Get List of TableWrapper Items (KS_TIV) from SQL
     * <b>(only ONE List use KS number)</b>
     *
     * @param enumEst  (enumeration)
     * @param ksNumber (integer)
     * @return List of KS_TIV
     */

    public List<KS_TIV> getOneKSList(Est enumEst, int ksNumber) {

        List<KS_TIV> list = FXCollections.observableArrayList();

        String psmtmtString = "SELECT  "
                + " * "
                + "FROM  dbo.[KS]"
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [KS_Number] = ? "
                + "AND   [dell] = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {

            pstmt.setString(1, enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER).toString());
            pstmt.setString(2, enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR).toString());
            pstmt.setInt(3, ksNumber);
            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();

            while (rs.next()) {

                KS_TIV item = new KS_TIV(
                        rs.getLong(SQL.Common.ID),
                        rs.getTimestamp(SQL.Common.CONTRACTOR),
                        rs.getInt(SQL.KS.NUMBER),
                        rs.getInt(SQL.KS.DATE),
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
                        rs.getObject(SQL.Common.CONTRACTOR).toString(),
                        rs.getObject(SQL.Estimate.JM_NAME).toString(),
                        rs.getObject(SQL.Estimate.JOB_MATERIAL).toString(),
                        rs.getObject(SQL.Estimate.BINDED_JOB).toString(),
                        rs.getDouble(SQL.Estimate.VALUE),
                        rs.getObject(SQL.Estimate.UNIT).toString(),
                        rs.getDouble(SQL.Estimate.PRICE_ONE),
                        rs.getDouble(SQL.Estimate.PRICE_SUM),
                        rs.getObject(SQL.Estimate.BUILDING_PART).toString()
                );
                item.setDel(rs.getInt(SQL.Common.DEL));
                list.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Delete KS_TIV  Entities from SQL
     *
     * @param items (Collection of KS_TIV)
     */
    @Override
    public void delete(Collection<KS_TIV> items) {
        String sql = "update [dbo].[KS] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            for (KS_TIV obsItem : items) {
                pstmt.setLong(1, obsItem.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
            items.forEach(item -> {
                LogController.appendLogViewText("deleted KS item: "
                        + item.getJM_name()
                        + " [KS# " + item.getKSNumber() + "]"
                        + " [JM/ " + item.getJobOrMat() + "]"
                        + " [BP/ " + item.getBindJob() + "]"
                        + " [S#/ " + item.getSiteNumber() + "]"
                        + " [C/ " + item.getContractor() + "]");
            });
        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Override
//    public void delete(Collection<KS_TIV> items) {
//        try(Connection connection   = SqlConnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute dellRowKS ?,?,?,?,?,?");) {
//            connection.setAutoCommit(false);
//                for (KS_TIV obsItem : items) {
////                    
//                    pstmt.setInt      (1, obsItem.getKSNumber());
//                    pstmt.setString   (2, obsItem.getSiteNumber());
//                    pstmt.setString   (3, obsItem.getContractor());
//                    pstmt.setString   (4, obsItem.getBuildingPart());
//                    pstmt.setString   (5, obsItem.getJM_name());
//                    pstmt.setString   (6, obsItem.getBindJob());
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
     *
     * @param ksNumber (String)
     */
    public static void deleteKS(String ksNumber) {

        String psmtmtString = "DELETE FROM dbo.[KS]  WHERE [KS_Number]= ? and [SiteNumber] = ? and [Contractor] = ?";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, ksNumber);
            pstmt.setString(2, Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
            pstmt.setString(3, Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public void insert(Collection<KS_TIV> items) {
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
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (KS_TIV obsItem : items) {
                pstmt.setInt(1, obsItem.getKSNumber());
                pstmt.setInt(2, obsItem.getKSDate());
                pstmt.setString(3, obsItem.getSiteNumber());
                pstmt.setString(4, obsItem.getTypeHome());
                pstmt.setString(5, obsItem.getContractor());
                pstmt.setString(6, obsItem.getJM_name());
                pstmt.setString(7, obsItem.getJobOrMat());
                pstmt.setString(8, obsItem.getBindJob());
                pstmt.setString(9, obsItem.getUnit());
                pstmt.setDouble(10, obsItem.getQuantity());
                pstmt.setDouble(11, obsItem.getPriceOne());
                pstmt.setString(12, obsItem.getBuildingPart());

                int affectedRows = pstmt.executeUpdate();

                try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                    if (generategKeys.next())
                        obsItem.setId(generategKeys.getLong(1));
                }
            }
            ;
            //SQL commit
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
//    public void insert(Collection<KS_TIV> items) {
//        try(Connection connection   = SqlConnector.getInstance();
//            PreparedStatement pstmt = connection.prepareStatement("execute addRowKS ?,?,?,?,?,?,?,?,?,?,?,?");) {
//            //set false SQL Autocommit
//            connection.setAutoCommit(false);
//                for (KS_TIV obsItem : items) {
//                    pstmt.setInt      (1,  obsItem.getKSNumber());
//                    pstmt.setInt      (2,  obsItem.getKSDate());
//                    pstmt.setString   (3,  obsItem.getSiteNumber());
//                    pstmt.setString   (4,  obsItem.getContractor());
//                    pstmt.setString   (5,  obsItem.getBuildingPart());
//                    pstmt.setString   (6,  obsItem.getJM_name());
//                    pstmt.setString   (7,  obsItem.getBindJob());
//                    pstmt.setString   (8,  obsItem.getJobOrMat());
//                    pstmt.setFloat    (9,  obsItem.getQuantity());
//                    pstmt.setString   (10,  obsItem.getUnit());
//                    pstmt.setFloat    (11, obsItem.getPriceOne());
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
     * @param listKS    (List<AbstractEstimateTVI> listKS)
     */
    public void insertNewKS(int ks_Number, int ks_Date, List<AbstractEstimateTVI> listKS) {

        String pstString = "INSERT into dbo.[KS] ("
                + " [KS_Number]"
                + ",[KS_Date]"
                + ",[SiteNumber]"
                + ",[TypeHome]"
//                + ",[Contractor]"
                + ",[id_count]"
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
                + " )"
                + " select "
                + " ?"
                + ",?"
                + ",E.[SiteNumber]"
                + ",E.[TypeHome]"
//                + ",E.[Contractor]"
                + ",E.[id_count]"
                + ",E.[JM_name]"
                + ",E.[JobsOrMaterials]"
                + ",E.[BindedJob]"
                + ",E.[Unit]"
                + ",E.[Value]"
                + ",E.[Price_one]"
                + ",E.[BuildingPart]"
                + ",E.[NumberSession] "
                + ",E.[dell] "
                + ",E.[DateCreate] "
                + "FROM  [Estimate] E "
                + "WHERE E.[SiteNumber] = ? "
//                + "And   E.[Contractor] = ? "
                + "And   E.[id_count]   = ? "
                + "And   E.[JM_name]    = ? "
                + "And   E.[BindedJob]  = ? "
                + "And   E.[TableType]  = 2 "
                + "And   E.[dell]       = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(pstString);) {
            for (AbstractEstimateTVI abstractEstimateTVI : listKS) {

                CountAgentTVI contractor = EstimateController.Est.Common.getCountAgentTVI();
                pstmt.setInt(1, ks_Number);
                pstmt.setInt(2,  (Math.round(ks_Date * 100) / 100));
                pstmt.setString(3, abstractEstimateTVI.getSiteNumber());
                pstmt.setInt(4, contractor.getIdCountConst());
                pstmt.setString(5, abstractEstimateTVI.getJM_name());
                pstmt.setString(6, abstractEstimateTVI.getBindJob());

                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void dellAndInsert(Memento<Collection<KS_TIV>> memento) {
        CommonNamedDao.super.dellAndInsert(memento);
        //??????????
//        table.updateTableFromSQL(this.getOneKSList(Est.KS, ksNumber ));
        Est.KS.updateList_DL(this);

    }

}
