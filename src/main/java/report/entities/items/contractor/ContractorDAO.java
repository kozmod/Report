
package report.entities.items.contractor;


import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.osr.OSR_DAO;
import report.layout.controllers.LogController;
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


public class ContractorDAO implements CommonNamedDAO<Collection<ContractorTIV>> {

    @Override
    public String getSqlTableName() {
        return SQL.Tables.CONTRACTORS;
    }

    /**
     * Get List of Contractor Items from SQL (Contractor)
     *
     * @return ObservableList of ContractorTIV
     */
    @Override
    public ObservableList<ContractorTIV> getData() {
        ObservableList<ContractorTIV> listAllContractors
                = FXCollections.observableArrayList(ContractorTIV.extractor());

        String sqlQuery = "SELECT "
                + " * "
                + "FROM dbo.[Contractors] "
                + "WHERE [dell] = 0";

        try (Connection connection = SqlConnector.getInstance();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sqlQuery);) {
            while (rs.next()) {
                listAllContractors.add(new ContractorTIV(
                                rs.getLong(SQL.Common.ID),
                                rs.getString(SQL.Site.CONTRACTOR),
                                rs.getString(SQL.Contractors.DIRECTOR),
                                rs.getString(SQL.Contractors.ADRESS),
                                rs.getString(SQL.Contractors.COMMENTS)

                        )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContractorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAllContractors;
    }

    public ContractorTIV getOne(String contractorName) {

        ContractorTIV contractor = null;

        String sqlQuery = "SELECT "
                + " * "
                + ",[Contractor] "
                + "FROM dbo.[Contractors] "
                + "WHERE [dell] = 0 "
                + "AND [Contractor] = ? ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setString(1, contractorName);

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    contractor = new ContractorTIV(
                            rs.getLong("id"),
                            rs.getString("Contractor"),
                            rs.getString("Director"),
                            rs.getString("Adress"),
                            rs.getString("Comments")
                    );


                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContractorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contractor;
    }

    /**
     * Delete ContractorTIV Entities from SQL (Contractors)
     *
     * @param items (Collection of ContractorTIV)
     */
    @Override
    public void delete(Collection<ContractorTIV> items) {
        String sql = "update [dbo].[Contractors] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            for (ContractorTIV obsItem : items) {
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
            Logger.getLogger(ContractorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Insert ContractorTIV Entities to SQL(Contractors)
     *
     * @param items (Collection of ContractorTIV)
     */
    @Override
    public void insert(Collection<ContractorTIV> items) {
        String sql = "INSERT into [dbo].[Contractors] "
                + "( "
                + " [Contractor]"
                + ",[Director]"
                + ",[Adress]"
                + ",[Comments]"
                + " ) "
                + "VALUES(?,?,?,?)";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            for (ContractorTIV obsItem : items) {
                pstmt.setString(1, obsItem.getContractor());
                pstmt.setString(2, obsItem.getDirector());
                pstmt.setString(3, obsItem.getAdress());
                pstmt.setString(4, obsItem.getComments());


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
            Logger.getLogger(OSR_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void dellAndInsert(TableWrapper table) {
//        Collection tableMemento = table.getMemento().getSavedState(),
//                   current = table.getObservableItems();
//        
//        
////        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
////           table.getCRUD().getDelete().forEach(i -> System.out.println("Delete - " + ((TableClone)i).getId()));
////           table.getCRUD().getCreate().forEach(i -> System.out.println("INS    - " + ((TableClone)i).getId()));
////           table.getCRUD().getUpdate().forEach(i -> System.out.println("UPDATE - " + ((TableClone)i).getId()));
////           table.getCRUD().clearAll();
////            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,,>");
//        DataUtils.DiffList<ContractorTIV> diffList = new DataUtils.DiffList(tableMemento,current);
//        if(diffList.exElements() != null 
//           || diffList.exElements().size() > 0) delete(diffList.exElements());
//        if(diffList.newElements()  != null 
//           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
//
//        
//    }

    public void insert1(Collection<ContractorTIV> items) {
        String sql = "INSERT into [dbo].[Contractors] "
                + "( "
                + " [Contractor]"
                + ",[Director]"
                + ",[Adress]"
                + ",[Comments]"
                + " ) "
                + "VALUES(?,?,?,?)";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            for (ContractorTIV obsItem : items) {
                pstmt.setString(1, obsItem.getContractor());
                pstmt.setString(2, obsItem.getDirector());
                pstmt.setString(3, obsItem.getAdress());
                pstmt.setString(4, obsItem.getComments());


                int affectedRows = pstmt.executeUpdate();

                try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                    if (generategKeys.next())
                        obsItem.setId(generategKeys.getLong(1));
                }
            }

            //SQL commit
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OSR_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
