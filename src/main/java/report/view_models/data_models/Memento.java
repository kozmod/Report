
package report.view_models.data_models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.TableClone;


public  class Memento<S > {
        
    private  ObservableList<S> tableMemento;

    public Memento(ObservableList<? extends TableClone>  tableItems) {
        this.tableMemento  = getNewObs( tableItems);   
              
    }
        
    public ObservableList<S> getSavedState() {return  this.getNewObs((ObservableList<? extends TableClone>) tableMemento); }
            
    private ObservableList<S> getNewObs(ObservableList<? extends TableClone> items){
        ObservableList<S> newObsList = FXCollections.observableArrayList();
        items.forEach((TableClone obsItem) -> newObsList.add((S) obsItem.getClone()));
        
    return newObsList;
    }
}  
