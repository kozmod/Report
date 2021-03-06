/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.entities.items.period;

import report.entities.abstraction.dao.CommonDao;
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
import report.layout.controllers.LogController;
import report.layout.controllers.estimate.EstimateController_old.Est;
import report.models.sql.SqlConnector;

public class PeriodDao implements CommonDao<Collection<PeriodTIV>> {

    @Override
    public ObservableList<PeriodTIV> getData() {
        ObservableList<PeriodTIV> list = FXCollections.observableArrayList(PeriodTIV.extractor());

        String sqlQuery = "SELECT "       //[id] [SiteNumber] [Contractor][Text][DateFrom][DateTo]
                + " * "
                + "from dbo.[SiteJobPeriod] "
                + "WHERE [SiteNumber] = ? "
                + "AND   [Contractor] = ? "
                + "AND   [dell] = 0";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery);) {
            //set false SQL Autocommit
            pstmt.setString(1, Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
            pstmt.setString(2, Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
            pstmt.execute();

            try (ResultSet rs = pstmt.getResultSet()) {
                while (rs.next())
                    list.add(new PeriodTIV(
                                    rs.getLong(SQL.Common.ID),
                                    rs.getString(SQL.Common.SITE_NUMBER),
                                    rs.getString(SQL.Common.CONTRACTOR),
                                    rs.getString(SQL.Common.TEXT),
                                    rs.getInt(SQL.Period.DATE_FROM),
                                    rs.getInt(SQL.Period.DATE_TO)
                            )
                    );


            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void delete(Collection<PeriodTIV> items) {
        String sql = "update [dbo].[SiteJobPeriod] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (PeriodTIV obsItem : items) {
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
            Logger.getLogger(PeriodDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(Collection<PeriodTIV> items) {
        String sql = "INSERT into [dbo].[SiteJobPeriod] "
                + "( "
                + " [SiteNumber]"
                + ",[Contractor]"
                + ",[Text]"
                + ",[DateFrom]"
                + ",[DateTo]"
                + " ) "
                + "VALUES(?,?,?,?,?)";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            for (PeriodTIV obsItem : items) {
                pstmt.setString(1, obsItem.getSiteNumber());
                pstmt.setString(2, obsItem.getContractor());
                pstmt.setString(3, obsItem.getText());
                pstmt.setInt(4, obsItem.getDateFrom());
                pstmt.setInt(5, obsItem.getDateTo());

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
            Logger.getLogger(PeriodDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
