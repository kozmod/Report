package report.entities.commonItems;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import report.entities.abstraction.CommonDAO;
import report.entities.items.expenses.ExpensesDAO;
import report.models.beck.sql.SQLconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class CommonItemDAO<F,S>  implements CommonDAO<Collection<CommonDItem<F,S>>> {

    private final String sqlTable;
    private final String sqlFirstValueColumn;
    private final String sqlSecondValueColumn;

    /***************************************************************************
     *                                                                         *
     * CONSTRUCTOR                                                             *
     *                                                                         *
     **************************************************************************/
    public CommonItemDAO(String sqlTable, String sqlFirstValueColumn, String sqlSecondValueColumn) {
        this.sqlTable = sqlTable;
        this.sqlFirstValueColumn = sqlFirstValueColumn;
        this.sqlSecondValueColumn = sqlSecondValueColumn;
    }
    /***************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     //**************************************************************************/
    private String getSqlString(){
        return new StringBuilder("SELECT DISTINCT\n")
                .append("id").append(",")
                .append(sqlFirstValueColumn).append(",")
                .append(sqlSecondValueColumn)
                .append(" FROM ").append(sqlTable)
                .append(" WHERE dell = 0")
                .toString();
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     //**************************************************************************/
    public ObservableList<Pair<F,S>> getPairList(){
        ObservableList<Pair<F,S>> list =  FXCollections.observableArrayList();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(this.getSqlString())) {
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    while (rs.next()){
                        list.add(
                                new Pair<>(
                                        (F) rs.getObject(sqlFirstValueColumn),
                                        (S) rs.getObject(sqlSecondValueColumn)
                                )
                        );
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     //**************************************************************************/
    @Override
    public <E> E getData() {
        ObservableList<CommonDItem<F,S>> list =  FXCollections.observableArrayList(CommonDItem.extractor());
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(this.getSqlString())) {
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    while (rs.next()){
                        list.add(
                                new CommonDItem<>(
                                        rs.getLong("id"),
                                        this.sqlTable,
                                        (F) rs.getObject(sqlFirstValueColumn),
                                        (S) rs.getObject(sqlSecondValueColumn)
                                )
                        );
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (E) list;
    }



    @Override
    public void delete(Collection<CommonDItem<F, S>> entry) {

    }

    @Override
    public void insert(Collection<CommonDItem<F, S>> entry) {

    }

    @Override
    public String sqlTableName() {
        return this.sqlTable;
    }
}
