
package report.models.view.wrappers.table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import report.entities.abstraction.dao.CommonDao;
import report.entities.items.AbstractEstimateTVI;


public class PriceSumTableWrapper<E extends AbstractEstimateTVI> extends TableWrapper<E> implements SummableColumnTable<DoubleProperty> {

    private DoubleProperty priceSum = new SimpleDoubleProperty();

    public PriceSumTableWrapper(TableView<E> table, CommonDao<ObservableList<E>> dao) {
        super("TEST EST TITLE", table, dao);
    }

    public PriceSumTableWrapper(String title, TableView<E> table, CommonDao<ObservableList<E>> dao) {
        super(title, table, dao);
    }

    @Override
    public DoubleProperty getProperty() {
        return priceSum;
    }


    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveMemento() - save table items to TableMemento_old.
     * <br>
     * 3. computeProperty() - saveEst priceSum of all "Price_sum" elements.
     * <br>
     *
     * @param items - Observable List of table item (inherited TableItems)
     */
    @Override
    public void setTableData(ObservableList items) {
        super.setTableData(items);
        computeProperty();
        super.tableView().getItems().addListener((ListChangeListener.Change<? extends AbstractEstimateTVI> c) -> {
            if (c.next() && (c.wasUpdated() || c.wasAdded() || c.wasRemoved())) {
                this.computeProperty();
            }
        });


    }

    /**
     * Get priceSum of all "Price_sum" elements.
     */
    @Override
    public DoubleProperty computeProperty() {
        Double sum = new Double(0);
        for (AbstractEstimateTVI obsAbstractEstimateTVI : super.tableView().getItems()){
            sum = sum + obsAbstractEstimateTVI.getPriceSum();
        }
        priceSum.setValue(sum);
        return  priceSum;
    }



}
