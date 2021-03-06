
package report.entities.items.osr;

import report.entities.abstraction.dao.CommonDao;
import report.layout.controllers.LogController;
import report.models.coefficient.Quantity;
import report.models.sql.SqlConnector;
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


public class OSR_Dao implements CommonDao<Collection<OSR_TIV>> {

    /**
     * Get Quantity of sites
     *
     * @return siteQuantity (integer)
     */
    public int getSiteQuantity() {
        int siteQuantity = 0;
        try (Connection connection = SqlConnector.getInstance();
             Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery("SELECT count(*) from dbo.[Site] WHERE [dell] = 0");

            while (rs.next()) {
                siteQuantity = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OSR_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return siteQuantity;
    }

    /**
     * Get List of OSR Items from SQL
     *
     * @return ObservableList of OSR_TIV
     */
    @Override
    public ObservableList getData() {
        ObservableList<OSR_TIV> listAllOSR = FXCollections.observableArrayList(OSR_TIV.extractor());

        String sqlQuery = "SELECT "
                + " * "
                + "from dbo.[SiteOSR] "
                + "WHERE  [dell] = 0 "
                + "ORDER BY [Text]";

        try (Connection connection = SqlConnector.getInstance();
             Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(sqlQuery);
//               int q =  850;
            int q = Quantity.value();
            while (rs.next()) {
                listAllOSR.add(new OSR_TIV(
                                rs.getLong(SQL.Common.ID),
                                rs.getString(SQL.Common.TEXT),
                                rs.getDouble(SQL.Common.VALUE),
//                                                rs.getDouble(SQL.Common.VALUE)/850
                                rs.getDouble(SQL.Common.VALUE) / q
                        )
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(OSR_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAllOSR;
    }


    /**
     * Delete OSR_TIV Entities from SQL
     *
     * @param items (Collection of OSR_TIV)
     */
    @Override
    public void delete(Collection<OSR_TIV> items) {
        String sql = "update [dbo].[SiteOSR] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (OSR_TIV obsItem : items) {
                pstmt.setLong(1, obsItem.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
            items.forEach(item -> {
//                LogController.appendLogViewText("deleted item: "+ ((OSR_TIV)item).getJM_name()
//                                                         +" [JM/ "+((OSR_TIV)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((OSR_TIV)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((AbstractEstimateTVI)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((AbstractEstimateTVI)item).getContractor()   + "]");
            });
            LogController.appendLogViewText(items.size() + " deleted");

        } catch (SQLException ex) {
            Logger.getLogger(OSR_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Insert OSR_TIV Entities to SQL
     *
     * @param items (Collection of OSR_TIV)
     */
    @Override
    public void insert(Collection<OSR_TIV> items) {
        String sql = "INSERT into [dbo].[SiteOSR] "
                + "( "
                + "[Text]"
                + ",[Value]"
                + " ) "
                + "VALUES(?,?)";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            for (OSR_TIV obsItem : items) {
                pstmt.setString(1, obsItem.getText());
                pstmt.setDouble(2, obsItem.getExpenses());

                int affectedRows = pstmt.executeUpdate();

                try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                    if (generategKeys.next())
                        obsItem.setId(generategKeys.getLong(1));
                }
            }

            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
//           items.forEach(item -> {
////                LogController.appendLogViewText("inserted item: "+ ((AbstractEstimateTVI)item).getJM_name()
////                                                         +" [JM/ "+((AbstractEstimateTVI)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((AbstractEstimateTVI)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((AbstractEstimateTVI)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((AbstractEstimateTVI)item).getContractor()   + "]");
//                });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(OSR_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//    @Override
//    public void dellAndInsert(TableWrapper table) {
//        Collection tableMemento = table.getMemento().getSavedState(),
//                   current = table.getObservableItems();
//        
////        DataUtils.DiffList diffList = new DataUtils.DiffList(tableMemento,current);
////        if(diffList.baseCollectionNotContain() != null
////           || diffList.baseCollectionNotContain().size() > 0) delete(diffList.baseCollectionNotContain());
////        if(diffList.changeCollectionNotContain()  != null
////           || diffList.changeCollectionNotContain().size()  > 0) insert(diffList.changeCollectionNotContain());
//        DataUtils.DiffList diffList = new DataUtils.DiffList(tableMemento,current);
//        if(diffList.baseCollectionNotContain() != null
//           || diffList.baseCollectionNotContain().size() > 0) delete(diffList.intersection());
//        if(diffList.changeCollectionNotContain()  != null
//           || diffList.changeCollectionNotContain().size()  > 0) insert(diffList.changeCollectionNotContain());
//        
//       //?????????????
////        table.updateTableFromSQL(getData());
//        
//    }

}
