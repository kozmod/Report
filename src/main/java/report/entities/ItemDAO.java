
package report.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.sql.SQLconnector;
import report.usage_strings.ServiceStrings;
import report.models_view.data_utils.DiffList;

import report.models_view.nodes.TableWrapper;


public interface ItemDAO<E,T extends TableWrapper> extends TableDataBaseName{
    ObservableList<E> getList();
    void delete(Collection<E> entry);
    void insert(Collection<E>  entry);

    default void  dellAndInsert(T table) {
        Collection<E>  memento= table.getMemento().getSavedState(),
                current = table.getItems();

        DiffList<E> diffList = new DiffList<>(memento,current);
        if(diffList.exElements() != null
                || diffList.exElements().size() > 0) delete( diffList.intersection());
        if(diffList.newElements()  != null
                || diffList.newElements().size()  > 0) insert( diffList.newElements());
      
    }
    
    /**
     * Get <b>Unique</b> value of column.
     * @return ObservableList
     */
    default ObservableList<Object> getDistinctOfColumn(String column, Object ... fistNodes){
         
        ObservableList<Object> obsList = FXCollections.observableArrayList(fistNodes);
        
        try ( Connection connection = SQLconnector.getInstance();
              PreparedStatement pstmt = connection.prepareStatement("execute getListDIST ?,?")){
                pstmt.setString(1, column);
                pstmt.setString(2, this.getTableString());
            
                pstmt.execute();
            
            try ( ResultSet resSet = pstmt.getResultSet()){
                while(resSet.next()){
                    if(!(resSet.getObject(column) == null)
                            && !(resSet.getObject(column).equals(ServiceStrings.Line)))
                        obsList.add(resSet.getObject(column));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obsList;
    }
    



}
