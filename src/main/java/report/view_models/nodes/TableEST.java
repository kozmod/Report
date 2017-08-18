
package report.view_models.nodes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import report.entities.items.TableItem;




public  class TableEST<S> extends Table<TableItem>{
    
   private DoubleProperty sum = new SimpleDoubleProperty();
     
//Getter ===============================================================================================
         
   public DoubleProperty getSumProperty() {return sum;}


         
//Setter ===============================================================================================     

//Constructor ==========================================================================================
    public TableEST() {     
        super();
    }
    
    public TableEST(String title) { super(title);}

    public TableEST(String title, ObservableList items) {super(title,items); }
    
//Methots ==============================================================================================
    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     * 3. computeSum() - get sum of all "Price_sum" elements.
     * <br>
     * @param items - Observable List of table item (inherited TableItems)
     * @see computeSum()
     */
    @Override
    public void setTableData(ObservableList items){
        super.setTableData(items);
        computeSum(); 
        this.getItems().addListener((ListChangeListener.Change<? extends TableItem> c) -> {
                if(c.next() && 
                        (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                        this.computeSum();  
                        System.out.println("report.report.view_models.nodes.TableEST.setTableData() == sum -> " + sum.getValue());
                }
        }); 

        
    }
   
    
    /** 
     * Get sum of all "Price_sum" elements.
     */
    public void computeSum(){
        Double summ = new Double(0); 
        for (TableItem  obsItem : this.getItems())
            summ = summ + obsItem.getPrice_sum();
        sum.setValue(summ);
         
    }      
     

}
