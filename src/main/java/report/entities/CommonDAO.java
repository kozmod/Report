package report.entities;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.sql.SQLconnector;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.usage_strings.ServiceStrings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface CommonDAO<C,T extends AbstractTableWrapper> extends TableDataBaseName{
    C getData();
    void delete(C entry);
    void insert(C  entry);

    default void  dellAndInsert(T object) {
        this.delete((C)object.getMemento().getSavedState());
        this.insert((C)object.getItems());
    }

    /**
     * Get <b>Unique</b> value of column.
     * @return ObservableList
     */
    //Procedure
    default <X> ObservableList<X> getDistinctOfColumn(String column, X ... fistNodes){

        Set<X> disSet = new TreeSet<>();
        for (int i = 0; i< fistNodes.length; i++){
            disSet.add(fistNodes[i]);
        }

        try (Connection connection = SQLconnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("execute getListDIST ?,?")){
            pstmt.setString(1, column);
            pstmt.setString(2, this.sqlTableName());

            pstmt.execute();

            try ( ResultSet resSet = pstmt.getResultSet()){
                while(resSet.next()){
                    if(!(resSet.getObject(column) == null)
                            && !(resSet.getObject(column).equals(ServiceStrings.Line)))
                        disSet.add((X) resSet.getObject(column));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  FXCollections.observableArrayList(disSet);
    }

}
