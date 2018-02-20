package report.entities.abstraction;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.mementos.Memento;
import report.models.beck.sql.SQLconnector;
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

public interface CommonDAO<C>  {
    <E extends C> E getData();
    void delete(C entry);
    void insert(C  entry);
    /**
     * Delete & Insert data to SQL in direct sequence
     * @return ObservableList
     */
    default void  dellAndInsert(Memento<C> memento) {
        this.delete(memento.toDelete());
        this.insert(memento.toInsert());
    }

    /**
     * Get <b>Unique</b> distinct value of column use SQL Table Name & SQL Table Column Name.
     * @return ObservableList
     */
//    default <X> ObservableList<X> getDistinct(String table,String column){
//        Set<X> disSet = new TreeSet<>( );
//        try (Connection connection = SQLconnector.getInstance();
//             PreparedStatement pstmt = connection.prepareStatement("execute getListDIST ?,?")
//        ){
//            pstmt.setString(1, column);
//            pstmt.setString(2, table);
//            pstmt.execute();
//            try ( ResultSet resSet = pstmt.getResultSet()){
//                while(resSet.next()){
//                    if(!(resSet.getObject(column) == null)
//                            && !(resSet.getObject(column).equals(ServiceStrings.Line)))
//                        disSet.add((X) resSet.getObject(column));
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CommonDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return  FXCollections.observableArrayList(disSet);
//    }

}
