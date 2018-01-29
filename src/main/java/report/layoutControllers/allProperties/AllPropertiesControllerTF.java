package report.layoutControllers.allProperties;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.BlendMode;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;
import report.entities.CommonDAO;
import report.entities.items.DItem;
import report.entities.items.contractor.ContractorDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.entities.items.variable.PropertiesDAO;
import report.entities.items.variable.VariableTIV_new;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.propertySheet_wrappers.PropertySheetWrapper;
import report.models_view.nodes.table_wrappers.ReverseTableWrapper;
import report.models_view.nodes.table_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.TableFactory;
import report.usage_strings.SQL;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

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
//            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.quantity());
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

        TableColumn<DItem,String> titleCol = new TableColumn<>("Параметр");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().firstValueProperty());

        TableColumn<DItem,Double> valueCol = new TableColumn<>("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter("###,##0.000")));
        tableWrapper.tableView().getColumns().addAll(titleCol,valueCol);



//        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
//        TableColumn<VariableTIV,Double> valueAllColumn
//                = tableWrapper.addColumn("Значение",   "quantity");
////
//        valueAllColumn.setEditable(true);
////        TableFactory.setTextFieldCell_NumbertringConverter_threeZeroes(valueAllColumn);
//        TableFactory.setTextFieldTableCell(
//                new DoubleStringConverter("###,##0.000"),
//                valueAllColumn
//
//        );
//        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<VariableTIV, Double> t) -> {
//            t.getRowValue().setQuantity(t.getNewValue());
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
        CommonDAO dao = new CountAgentDAO();
        TableWrapper<CountAgentTVI> tableWrapper = new TableWrapper(table,dao);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CountAgentTVI,String> formColumn
                = tableWrapper.addColumn("Организационно прововая Форма", cellData -> cellData.getValue().formProperty());
        TableColumn<CountAgentTVI,String> typerColumn
                = tableWrapper.addColumn("Тип",cellData -> cellData.getValue().typeProperty());
        TableColumn<CountAgentTVI,String> nameColumn
                = tableWrapper.addColumn("Наименование",cellData -> cellData.getValue().nameProperty());
        formColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
        typerColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        return tableWrapper;
    }

    static PropertySheetWrapper getCountPropertySheet(){
        PropertySheet countSheet = new PropertySheet();
        PropertySheetWrapper wrapper = new PropertySheetWrapper(countSheet);
        //add Items
        wrapper.setFromBase();

        //countSheet.getItems().addAll(list);

        countSheet.setMode(PropertySheet.Mode.CATEGORY);
        countSheet.setModeSwitcherVisible(true);
        countSheet.setSearchBoxVisible(true);
        countSheet.setPropertyEditorFactory(param -> {
            PropertyEditor<?> editor = null;
            if(param.getValue().getClass().equals(BigInteger.class)){
                editor = Editors.createNumericEditor(param);
            }else if(param.getValue().getClass().equals(String.class)){
                editor = Editors.createTextEditor(param);
            }else if(param.getValue().getClass().equals(LocalDate.class)){
                editor = Editors.createDateEditor(param);
            }else{
                editor = Editors.createTextEditor(param);

            }
            if(param.getName().equals("Паспорт:Кем\nвыдан"))
            editor = new AbstractPropertyEditor<String, TextArea>(param, new TextArea()) {
                @Override
                public void setValue(String value) {
                    ((TextArea)this.getEditor()).setText(value);
                }

                @Override
                protected ObservableValue<String> getObservableValue() {
                    return (this.getEditor()).textProperty();
                }

                {
                    TextInputControl control = (TextInputControl)this.getEditor();
                    control.focusedProperty().addListener((o, oldValue, newValue) -> {
                        if (newValue) {
                            Platform.runLater(() -> {
                                control.selectAll();
                            });
                        }

                    });
                }
            };
            return editor;

//            switch(param.getName()){
//                case "ОГРН":
//                case "ИНН":
//                    editor = Editors.createNumericEditor(param);
//                    return editor;
//                case "Дата присвоения ОГРН":
//                    editor = Editors.createDateEditor(param);
//                    return editor;
//                case "Юридический Адрес":
//                case "Фактический Адрес":
//                case "Адрес (Post)":
//                    editor = Editors.createTextEditor(param);
//                    return editor;
//                default:
//                    return Editors.createTextEditor(param);
//            }
        });
        return wrapper;
    }
}
