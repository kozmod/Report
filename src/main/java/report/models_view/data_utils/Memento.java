
package report.models_view.data_utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.TableClone;


public  class Memento<S extends TableClone> {
        
    private  ObservableList<S> tableMemento;

    public Memento(ObservableList<S>  tableItems) {
        this.tableMemento  = getNewObs( tableItems);   
              
    }
        
    public ObservableList<S> getSavedState() {return  this.getNewObs( tableMemento); }
            
    private ObservableList<S> getNewObs(ObservableList<S> items){
        ObservableList<S> newObsList = FXCollections.observableArrayList();
        items.forEach((S obsItem) -> {
            newObsList.add(
                    (S)obsItem.getClone()
            );
        });
    return newObsList;
    }
}  
