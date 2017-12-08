
package report.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import report.models.sql.SQLconnector;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.usage_strings.ServiceStrings;
import report.models.DiffList;

import report.models_view.nodes.node_wrappers.TableWrapper;


public interface TableViewItemDAO<E> extends CommonDAO<Collection<E>> {
    @Override
    ObservableList<E> getData();
    @Override
    void delete(Collection<E> entry);
    @Override
    void insert(Collection<E>  entry);

    @Override
    default void  dellAndInsert(AbstractTableWrapper<Collection<E>> table) {
        Collection<E>  memento =  table.getMemento().getSavedState(),
                current = table.getItems();

        DiffList<E> diffList = new DiffList<>(memento,current);
        if(diffList.exElements() != null
                || diffList.exElements().size() > 0) delete( diffList.intersection());
        if(diffList.newElements()  != null
                || diffList.newElements().size()  > 0) insert( diffList.newElements());
      
    }
    


}
