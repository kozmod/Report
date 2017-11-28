package report.layoutControllers.allPropeties;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.contractor.TableViewItemContractorDAO;
import report.entities.items.osr.TableViewItemOSRDAO;
import report.entities.items.osr.TableItemOSR;
import report.entities.items.variable.VariablePropertiesCommonDAOTableView;
import report.entities.items.variable.TableItemVariable;
import report.models.coefficient.Quantity;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.TableCellFactory;
import report.models_view.nodes.nodes_factories.TableFactory;

class AllPropertiesControllerTF implements TableFactory {

    private AllPropertiesControllerTF() {
    }

    /**
     * Create TableWrapper "OSR"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static TableWrapper decorOSR(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table,new TableViewItemOSRDAO());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<TableItemOSR,Double> valueAllColumn
                = tableWrapper.addColumn("Значение (общее)","expenses");
        TableColumn valueColumn    = tableWrapper.addColumn("Значение (за дом)","expensesPerHouse");

        valueAllColumn.setEditable(true);
//        valueColumn.setCellFactory(p -> TableCellFactory.getDecimalCell());
//        TableFactory.setTextFieldCell_NumberStringConverter(valueAllColumn);
        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueAllColumn
        );

        textColumn.setCellFactory(param -> TableCellFactory.getTestIdOSR());

//        ContextMenu cm = ContextMenuFactory.getOSR(tableWrapper);
//        tableWrapper.setContextMenu(cm);

        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemOSR, Double> t) -> {
            TableItemOSR editingItem = (TableItemOSR) t.getTableView().getItems().get(t.getTablePosition().getRow());

            editingItem.setExpenses(t.getNewValue());
            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.value());

            t.getTableView().refresh();
            //Diseble Save & Cancel Context menu Item
        });

        return tableWrapper;
    }


    /**
     * Create TableWrapper "Variable"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static TableWrapper decorVariable(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table,new VariablePropertiesCommonDAOTableView());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<TableItemVariable,Double> valueAllColumn
                = tableWrapper.addColumn("Значение",   "value");
//
        valueAllColumn.setEditable(true);
//        TableFactory.setTextFieldCell_NumbertringConverter_threeZeroes(valueAllColumn);
        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter("###,##0.000"),
                valueAllColumn

        );
        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemVariable, Double> t) -> {
            t.getRowValue().setValue(t.getNewValue());
        });


        return tableWrapper;
    }


    /**
     * Create TableWrapper "Contractor"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static TableWrapper decorContractor(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table,new TableViewItemContractorDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn idColumn     = tableWrapper.addColumn("ID", "id");
        TableColumn contractorColumn = tableWrapper.addColumn("Подрядчик","contractor");


        idColumn.setMaxWidth(50);
        idColumn.setMinWidth(35);

        return tableWrapper;
    }

}
