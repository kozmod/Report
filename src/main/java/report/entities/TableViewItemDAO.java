//
//package report.entities;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Set;
//import java.util.TreeSet;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.ObservableSet;
//import report.models.mementos.Memento;
//import report.models.sql.SQLconnector;
//import report.models_view.nodes.table_wrappers.AbstractTableWrapper;
//import report.usage_strings.ServiceStrings;
//import report.models.DiffList;
//
//import report.models_view.nodes.table_wrappers.TableWrapper;
//
//
//public interface TableViewItemDAO<E> extends CommonDAO<Collection<E>> {
//    @Override
//    ObservableList<E> getData();
//    @Override
//    void delete(Collection<E> entry);
//    @Override
//    void insert(Collection<E>  entry);
//
////    @Override
////    default void  dellAndInsert(Memento<Collection<E>> memento) {
//////        Collection<E>  memento =  table.getMemento().getSavedState(),
//////                current = table.getItems();
////        Collection<E> deleteCollection = memento.toDelete();
////        Collection<E> insertCollection = memento.toInsert();
////        if(deleteCollection != null | !deleteCollection.isEmpty()){
////            System.out.println("\33[34m " + deleteCollection.toString());
////            this.delete(deleteCollection);
////        }
////        if(insertCollection != null | !insertCollection.isEmpty()){
////            System.out.println("\33[34m " + insertCollection.toString());
////            this.insert( insertCollection);
////        }
//
////        DiffList<E> diffList = new DiffList<>(memento,current);
////        if(diffList.exElements() != null
////                || diffList.exElements().size() > 0) this.delete( diffList.intersection());
////        if(diffList.newElements()  != null
////                || diffList.newElements().size()  > 0) this.insert( diffList.newElements());
//
////    }
//
//
//
//
////    @Override
////    default void  dellAndInsert(AbstractTableWrapper<Collection<E>> table) {
//////        Collection<E>  memento =  table.getMemento().getSavedState(),
//////                current = table.getItems();
////
////        Collection<E> deleteCollection = table.getMemento().toDelete();
////        Collection<E> insertCollection = table.getMemento().toInsert();
////        if(deleteCollection != null| !deleteCollection.isEmpty()){
////            System.out.println("\33[34m " + deleteCollection.toString());
////            this.delete(deleteCollection);
////        }
////        if(insertCollection != null| !insertCollection.isEmpty()){
////            System.out.println("\33[34m " + insertCollection.toString());
////            this.insert( insertCollection);
////        }
////
//////        DiffList<E> diffList = new DiffList<>(memento,current);
//////        if(diffList.exElements() != null
//////                || diffList.exElements().size() > 0) this.delete( diffList.intersection());
//////        if(diffList.newElements()  != null
//////                || diffList.newElements().size()  > 0) this.insert( diffList.newElements());
////
////    }
//
//
//
//}
