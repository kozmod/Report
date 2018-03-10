
package report.entities.abstraction;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.beck.sql.SqlConnector;
import report.usage_strings.ServiceStrings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface CommonNamedDAO<E> extends CommonDAO<E> {
    String getSqlTableName();
    /**
     * Get <b>Unique</b> distinct value of column.
     * @return ObservableList
     */
    default <X> ObservableList<X> getDistinct(String column, X ... fistNodes){
        Set<X> disSet = new TreeSet<>(
                Arrays.asList(fistNodes)
        );
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("execute getListDIST ?,?")
        ){
            pstmt.setString(1, column);
            pstmt.setString(2, this.getSqlTableName());
            pstmt.execute();
            try ( ResultSet resSet = pstmt.getResultSet()){
                while(resSet.next()){
                    if(!(resSet.getObject(column) == null)
                            && !(resSet.getObject(column).equals(ServiceStrings.Line)))
                        disSet.add((X) resSet.getObject(column));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  FXCollections.observableArrayList(disSet);
    }

}
