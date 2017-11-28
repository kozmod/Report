
package report.models.mementos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.TableClone;

public  class TableMemento<E extends TableClone<E>> implements Memento<ObservableList<E>> {

    private  ObservableList<E> tableMemento;

    public TableMemento(ObservableList<E>  tableItems) {
        this.tableMemento  = getNewObs( tableItems);

    }

    @Override
    public ObservableList<E> getSavedState() {return  this.getNewObs( tableMemento); }

    private ObservableList<E> getNewObs(ObservableList<E> items){
        ObservableList<E> newObsList = FXCollections.observableArrayList();
        items.forEach((E obsItem) -> newObsList.add(obsItem.getClone()));

        return newObsList;
    }
}

//public  class TableMemento<S extends TableClone> {
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
