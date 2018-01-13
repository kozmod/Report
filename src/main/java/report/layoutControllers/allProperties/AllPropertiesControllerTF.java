package report.layoutControllers.allProperties;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import report.entities.items.TableDItem;
import report.entities.items.contractor.ContractorDAO;
import report.entities.items.counterparties.CountAgentTVI;
import report.entities.items.counterparties.CountAgentDAO;
import report.entities.items.variable.PropertiesDAO;
import report.entities.items.variable.VariableTIV_new;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.node_wrappers.ReverseTableWrapper;
import report.models_view.nodes.node_wrappers.TableWrapper;
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
//    static TableWrapper decorOSR(TableView table){
//        TableWrapper tableWrapper = new TableWrapper(table,new OSR_DAO());
//
////        tableWrapper.setEditable(true);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
//        TableColumn<OSR_TIV,Double> valueAllColumn
//                = tableWrapper.addColumn("Значение (общее)","expenses");
//        TableColumn valueColumn    = tableWrapper.addColumn("Значение (за дом)","expensesPerHouse");
//
//        valueAllColumn.setEditable(true);
////        valueColumn.setCellFactory(p -> TableCellFactory.getDecimalCell());
////        TableFactory.setTextFieldCell_NumberStringConverter(valueAllColumn);
//        TableFactory.setTextFieldTableCell(
//                new DoubleStringConverter(),
//                valueAllColumn
//        );
//
//        textColumn.setCellFactory(param -> TableCellFactory.getTestIdOSR());
//
////        ContextMenu cm = ContextMenuFactory.getOSR(tableWrapper);
////        tableWrapper.setContextMenu(cm);
//
//        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<OSR_TIV, Double> t) -> {
//            OSR_TIV editingItem = (OSR_TIV) t.getTableView().getItems().get(t.getTablePosition().getRow());
//
//            editingItem.setExpenses(t.getNewValue());
//            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.value());
//
//            t.getTableView().refresh();
//            //Diseble Save & Cancel Context menu Item
//        });
//
//        return tableWrapper;
//    }


    /**
     * Create TableWrapper "Variable"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static ReverseTableWrapper<VariableTIV_new> decorVariable(TableView table){
        ReverseTableWrapper<VariableTIV_new> tableWrapper = new ReverseTableWrapper<>("Общие параметры",table,new PropertiesDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TableDItem,String> titleCol = new TableColumn<>("Параметр");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().firstValueProperty());

        TableColumn<TableDItem,Double> valueCol = new TableColumn<>("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter("###,##0.000")));
        tableWrapper.tableView().getColumns().addAll(titleCol,valueCol);



//        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
//        TableColumn<VariableTIV,Double> valueAllColumn
//                = tableWrapper.addColumn("Значение",   "value");
////
//        valueAllColumn.setEditable(true);
////        TableFactory.setTextFieldCell_NumbertringConverter_threeZeroes(valueAllColumn);
//        TableFactory.setTextFieldTableCell(
//                new DoubleStringConverter("###,##0.000"),
//                valueAllColumn
//
//        );
//        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<VariableTIV, Double> t) -> {
//            t.getRowValue().setValue(t.getNewValue());
//        });


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
        TableWrapper tableWrapper = new TableWrapper(table,new ContractorDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn idColumn     = tableWrapper.addColumn("ID", "id");
        TableColumn contractorColumn = tableWrapper.addColumn("Подрядчик","contractor");

        idColumn.setMaxWidth(50);
        idColumn.setMinWidth(35);

        return tableWrapper;
    }
    /**
     * Create TableWrapper "Counterparties"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static TableWrapper<CountAgentTVI> decorCountAgent(TableView<CountAgentTVI> table){
        //TODO: delete Mock -> CountAgentTVI
//        CountAgentDAO dao =  Mockito.mock(CountAgentDAO.class);
//        ObservableList<CountAgentTVI> list = FXCollections.observableArrayList(CountAgentTVI.extractor());
//        list.addAll(new CountAgentTVI(1,"GREM", "OOO"," ИП"),
//                    new CountAgentTVI(2,"УЮТ", "OфO"," Подрядчик"),
//                    new CountAgentTVI(3,"САРАЙ", "ААO","Клиент")
//        );
//        Mockito.when(dao.getData()).thenReturn(list);

        TableWrapper<CountAgentTVI> tableWrapper = new TableWrapper(table,new CountAgentDAO());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CountAgentTVI,String> formColumn
                = tableWrapper.addColumn("Организационно прововая Форма", cellData -> cellData.getValue().formProperty());
        TableColumn<CountAgentTVI,String> typerColumn
                = tableWrapper.addColumn("Тип",cellData -> cellData.getValue().typeProperty());
        TableColumn<CountAgentTVI,String> nameColumn
                = tableWrapper.addColumn("Наименование",cellData -> cellData.getValue().nameProperty());

        return tableWrapper;
    }
}
