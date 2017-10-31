
package report.models_view.nodes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import report.entities.ItemDAO;
import report.entities.items.TableItem;


public  class TableWrapperEST<E extends TableItem> extends TableWrapper<E> {

    private DoubleProperty sum = new SimpleDoubleProperty();

    /*!******************************************************************************************************************
    *                                                                                                      Getter/Setter
    ********************************************************************************************************************/

    public DoubleProperty getSumProperty() {return sum;}



    /*!******************************************************************************************************************
    *                                                                                                       CONSTRUCTORS
    ********************************************************************************************************************/

    public TableWrapperEST(TableView<E> table,ItemDAO<E,TableWrapper> dao) {
        super("TEST EST TITLE",table,dao);
    }
    public TableWrapperEST(String title, TableView<E> table, ItemDAO<E,TableWrapper> dao) {
        super(title,table,dao);
    }

    /*!******************************************************************************************************************
    *                                                                                                             METHODS
    ********************************************************************************************************************/
    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     * 3. computeSum() - saveEst sum of all "Price_sum" elements.
     * <br>
     * @param items - Observable List of table item (inherited TableItems)
     */
    @Override
    public void setTableData(ObservableList items){
        super.setTableData(items);
        computeSum();
        super.tableView().getItems().addListener((ListChangeListener.Change<? extends TableItem> c) -> {
            if(c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                this.computeSum();
                System.out.println("report.report.models_view.nodes.TableWrapperEST.setTableData() == sum -> " + sum.getValue());
            }
        });


    }


    /**
     * Get sum of all "Price_sum" elements.
     */
    public void computeSum(){
        Double summ = new Double(0);
        for (TableItem  obsItem : super.tableView().getItems())
            summ = summ + obsItem.getPrice_sum();
        sum.setValue(summ);

    }


}