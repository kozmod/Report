package report.entities.items.counterparties;

import report.entities.abstraction.dao.CommonNamedDao;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.sql.SqlConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractReqDao implements CommonNamedDao<List<ObjectPSI>> {
    public abstract List<ObjectPSI> getByID(int id);

    @Override
    public void delete(List<ObjectPSI> items) {
        String sql = "UPDATE " + this.getSqlTableName() + " SET dell = 1 WHERE [id_Count] = ? AND [dell] = 0;";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            if (items != null && !items.isEmpty())
                pstmt.setLong(1,
                        items.stream()
                                .findFirst()
                                .get()
                                .getId()
                );
            pstmt.executeUpdate();
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
//            items.forEach(item -> {
////                LogController.appendLogViewText("deleted item: "+ ((OSR_TIV)item).getJM_name()
////                                                         +" [JM/ "+((OSR_TIV)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((OSR_TIV)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((AbstractEstimateTVI)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((AbstractEstimateTVI)item).getContractor()   + "]");
//            });
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AbstractReqDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(List<ObjectPSI> items) {
        String string;
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(string = ReqDaoUtils.buildSqlString("id_Count", items),
                     Statement.RETURN_GENERATED_KEYS)) {
            //set false SQL Autocommit
            System.out.println(string);
            connection.setAutoCommit(false);
            pstmt.setObject(1, items.get(0).getId());
            int i = 2;
            for (ObjectPSI item : items) {
                Object value = item.getValue();
                if (value.getClass().equals(LocalDate.class)) {
                    pstmt.setObject(i, ((LocalDate) value).toEpochDay());
                } else {
                    pstmt.setObject(i, item.getValue());
                }
                i++;
            }
            int affectedRows = pstmt.executeUpdate();
//                    try(ResultSet generategKeys = pstmt.getGeneratedKeys()){
//                        if(generategKeys.next())
//                            for (ObjectPSI item : items) {
//                                item.setId(generategKeys.getLong(1));
//                            }
//                    }
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
//                LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AbstractReqDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
