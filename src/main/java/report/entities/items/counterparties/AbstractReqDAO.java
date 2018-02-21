package report.entities.items.counterparties;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.contractor.ContractorDAO;
import report.entities.items.contractor.ContractorTIV;
import report.entities.items.osr.OSR_DAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.layoutControllers.LogController;
import report.models.beck.sql.SQLconnector;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public  abstract class AbstractReqDAO implements CommonNamedDAO<List<ObjectPSI>> {
    public  abstract List<ObjectPSI> getByID(int id);

    @Override
    public void delete(List<ObjectPSI> items) {
        String sql = "UPDATE "+ this.sqlTableName() +" SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);) {
            connection.setAutoCommit(false);
            if(items != null && !items.isEmpty())
                pstmt.setLong   (1,
                        items.stream()
                                .findFirst()
                                .get()
                                .getId()
                );
            pstmt.executeBatch();
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
//            items.forEach(item -> {
////                LogController.appendLogViewText("deleted item: "+ ((OSR_TIV)item).getJM_name()
////                                                         +" [JM/ "+((OSR_TIV)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((OSR_TIV)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
//            });
        } catch (SQLException ex) {
            Logger.getLogger(AbstractReqDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(List<ObjectPSI> items) {
        int i = 0;
        StringBuilder sqlStringBuilder =  new StringBuilder("INSERT INTO " )
                .append(this.sqlTableName())
                .append("(");
        for (ObjectPSI item : items) {
            if(i == 0){
                sqlStringBuilder.append(item.getSqlName());
            }else{
                sqlStringBuilder.append(",").append(item.getSqlName());
            }
            i++;
        }
        sqlStringBuilder.append(") VALUES(");
        for (; i >=0 ; i--) {
            if (i == 0) {
                sqlStringBuilder.append("?,");
            } else {
                sqlStringBuilder.append("? )");
            }
        }
            try(Connection connection = SQLconnector.getInstance();
                PreparedStatement pstmt  = connection.prepareStatement(sqlStringBuilder.toString(),
                        Statement.RETURN_GENERATED_KEYS);) {
                //set false SQL Autocommit
                connection.setAutoCommit(false);
                i = 1;
                for (ObjectPSI item : items) {
                    pstmt.setObject(i,  item.getValue());
                    i++;
                }
                    int affectedRows = pstmt.executeUpdate();
                    try(ResultSet generategKeys = pstmt.getGeneratedKeys()){
                        if(generategKeys.next())
                            for (ObjectPSI item : items) {
                                item.setId(generategKeys.getLong(1));
                            }
                    }
                //SQL commit
                connection.commit();
                //add info to LogTextArea / LogController
//           items.forEach(item -> {
////                LogController.appendLogViewText("inserted item: "+ ((Item)item).getJM_name()
////                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
////                                                         +" [BP/ "+((Item)item).getBindedJob()     + "]"
////                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
////                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
//                });
                LogController.appendLogViewText(items.size() + " inserted");
            } catch (SQLException ex) {
                Logger.getLogger(AbstractReqDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
