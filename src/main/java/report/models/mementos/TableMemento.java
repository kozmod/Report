
package report.models.mementos;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import report.entities.items.Clone;

import java.util.*;

public  class TableMemento<E extends Clone> implements Memento<Collection<E>> {

    Set<E> deleteSet = Collections.newSetFromMap(new IdentityHashMap<>());
    Set<E> insertSet = Collections.newSetFromMap(new IdentityHashMap<>());
    private  ObservableList<E> tableMemento;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public TableMemento(ObservableList<E>  tableItems) {
        this.tableMemento  = getNewObs( tableItems);
        addListener(tableItems);

    }
    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public ObservableList<E> getSavedState() {
        return  this.getNewObs( tableMemento ); }
    @Override
    public  Collection<E> toInsert(){
       return insertSet;
    }
    @Override
    public Collection<E> toDelete(){
       return deleteSet;
    }
    @Override
    public void clearChanges(){
        deleteSet.clear();
        insertSet.clear();
    }
    /***************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/
    private ObservableList<E> getNewObs(ObservableList<E> items){
        ObservableList<E> newObsList = FXCollections.observableArrayList();
        items.forEach((E obsItem) -> newObsList.add(obsItem.getClone()));

        return newObsList;
    }
    private void addListener(ObservableList<E> items){
        items.addListener((ListChangeListener<E>) observable -> {
            if(observable.next()) {
                if (observable.wasAdded()) {
                    insertSet.addAll(observable.getAddedSubList());
                    System.out.println("\32[31m + Added \33[0m");
                } else if (observable.wasUpdated()) {
                    E item = items.get(observable.getFrom());
                    if(!insertSet.contains(item)) {
                        deleteSet.add(item);
                        insertSet.add(item);
                        System.out.println("\32[31m + up \33[0m");
                    }
                } else if (observable.wasRemoved()) {
                    List<? extends E> itemList = observable.getRemoved();
                    insertSet.removeAll(itemList);
                    deleteSet.addAll(itemList);
                    System.out.println("\32[31m + rem \33[0m");
                }
            }
        });
    }

}

//public  class TableMemento<S extends Clone> {
//
//    private  ObservableList<S> tableMemento;
//
//    public TableMemento(ObservableList<S>  tableItems) {
//        this.tableMemento  = getNewObs( tableItems);
//
//    }
//
//    public ObservableList<S> getSavedState() {return  this.getNewObs( tableMemento); }
//
//    private ObservableList<S> getNewObs(ObservableList<S> items){
//        ObservableList<S> newObsList = FXCollections.observableArrayList();
//        items.forEach((S obsItem) -> {
//            newObsList.add(
//                    (S)obsItem.getClone()
//            );
//        });
//    return newObsList;
//    }
//}
