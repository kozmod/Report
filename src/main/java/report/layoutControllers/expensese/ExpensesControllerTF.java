package report.layoutControllers.expensese;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import report.entities.items.expenses.ItemExpensesDAO;
import report.entities.items.expenses.TableItemExpenses;
import report.entities.items.period.ItemPeriodDAO;
import report.entities.items.period.TableItemPeriod;
import report.entities.items.site.TableItemPreview;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.TableCellFactory;

public class ExpensesControllerTF {

    private ExpensesControllerTF() {
    }

    /**
     * Create TableWrapper "ChangeView"(expensesesLayoutController).
     * <br>
     * Show SQL table Items where dell = 1
     * <br>
     *
     * @return TableView
     */
    public static TableView getChangeView(){
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn columnV = new TableColumn("K");
        columnV.setCellValueFactory(new PropertyValueFactory("value"));


        TableColumn columnPO = new TableColumn("Onew");
        columnPO.setCellValueFactory(new PropertyValueFactory("price_one"));

        TableColumn columnPS = new TableColumn("Sum");
        columnPS.setCellValueFactory(new PropertyValueFactory("price_sum"));

        TableColumn columnDate = new TableColumn("Date");
        columnDate.setCellValueFactory(new PropertyValueFactory("dateCreate"));
        columnDate.setMinWidth(80);

        columnV .setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());
        columnPO.setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());
        columnPS.setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());

        table.getColumns().addAll(columnV, columnPO, columnPS, columnDate );

        return table;

    }

    /**
     * Create TableWrapper(ExpensesAddLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public  static TableWrapper<TableItemPreview> decorProperty_Site(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableWrapper.getTableView().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                // Get the tableWrapper header
                Pane header = (Pane) tableWrapper.getTableView().lookup("TableHeaderRow");
                if(header!=null && header.isVisible()) {
                    header.setMaxHeight(0);
                    header.setMinHeight(0);
                    header.setPrefHeight(0);
                    header.setVisible(false);
                    header.setManaged(false);
                }
            }
        });

        TableColumn titleColumn  = tableWrapper.addColumn("Параметр", "firstValue");
        TableColumn valueColumn  = tableWrapper.addColumn("Значение", "secondValue");


        valueColumn.setCellFactory(param ->  TableCellFactory.getEditSiteCell());


        return tableWrapper;
    }

    /**
     * Create TableWrapper "expenses"(expensesesLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapper<TableItemExpenses> decorProperty_Expenses(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO( new ItemExpensesDAO());

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn textColumn   = tableWrapper.addColumn("Наименование", "text");
        TableColumn typeColumn   = tableWrapper.addColumn("Тип",          "type");
        TableColumn valueColumn  = tableWrapper.addColumn("Значение",     "value");

        typeColumn.setCellFactory(param ->  TableCellFactory.getExpenseesCell());

        return tableWrapper;
    }

    /**
     * Create TableWrapper "Job & Period"(expensesesLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapper<TableItemPeriod> decorProperty_JobPeriod(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO( new ItemPeriodDAO());

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn dateFromColumn = tableWrapper.addColumn("Датаначала",     "dateFrom");
        TableColumn dateToColumn   = tableWrapper.addColumn("Дата Окончания", "dateTo");
        TableColumn textColumns    = tableWrapper.addColumn("Коментарии",     "text");

        dateFromColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());
        dateToColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());


        return tableWrapper;
    }

}
