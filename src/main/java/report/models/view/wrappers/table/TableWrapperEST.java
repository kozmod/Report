
package report.models.view.wrappers.table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import report.entities.abstraction.CommonDao;
import report.entities.items.AbstractEstimateTVI;


public class TableWrapperEST<E extends AbstractEstimateTVI> extends TableWrapper<E> {

    private DoubleProperty sum = new SimpleDoubleProperty();

    /*!******************************************************************************************************************
     *                                                                                                      Getter/Setter
     ********************************************************************************************************************/

    public DoubleProperty getSumProperty() {
        return sum;
    }



    /*!******************************************************************************************************************
     *                                                                                                       CONSTRUCTORS
     ********************************************************************************************************************/

    public TableWrapperEST(TableView<E> table, CommonDao<ObservableList<E>> dao) {
        super("TEST EST TITLE", table, dao);
    }

    public TableWrapperEST(String title, TableView<E> table, CommonDao<ObservableList<E>> dao) {
        super(title, table, dao);
    }

    /*!******************************************************************************************************************
     *                                                                                                             METHODS
     ********************************************************************************************************************/

    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveMemento() - save table items to TableMemento.
     * <br>
     * 3. computeSum() - saveEst sum of all "Price_sum" elements.
     * <br>
     *
     * @param items - Observable List of table item (inherited TableItems)
     */
    @Override
    public void setTableData(ObservableList items) {
        super.setTableData(items);
        computeSum();
        super.tableView().getItems().addListener((ListChangeListener.Change<? extends AbstractEstimateTVI> c) -> {
            if (c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())) {
                this.computeSum();
                System.out.println("report.report.models.view.wrappers.table_wrappers.TableWrapperEST.setTableData() == sum -> " + sum.getValue());
            }
        });


    }


    /**
     * Get sum of all "Price_sum" elements.
     */
    public void computeSum() {
        Double summ = new Double(0);
        for (AbstractEstimateTVI obsAbstractEstimateTVI : super.tableView().getItems())
            summ = summ + obsAbstractEstimateTVI.getPriceSum();
        sum.setValue(summ);

    }


}
