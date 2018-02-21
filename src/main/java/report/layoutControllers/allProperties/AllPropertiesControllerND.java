package report.layoutControllers.allProperties;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import report.entities.abstraction.CommonDAO;
import report.entities.items.DItem;
import report.entities.items.contractor.ContractorDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentDAO;
import report.entities.items.counterparties.CounterpatiesDaoUtil;
import report.entities.items.counterparties.ReqBankDAO;
import report.entities.items.counterparties.ReqCommonDAO;
import report.entities.items.counterparties.ReqExBodyDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.entities.items.variable.PropertiesDAO;
import report.entities.items.variable.VariableTIV_new;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.wrappers.propertySheetWrappers.PropertySheetWrapper;
import report.models.view.wrappers.tableWrappers.ReverseTableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.nodesFactories.TableFactory;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;

class AllPropertiesControllerND implements TableFactory {

    private AllPropertiesControllerND() {
    }
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
        Map<String,Integer> formMap = CounterpatiesDaoUtil.getForm();
        Map<String,Integer> typeMap = CounterpatiesDaoUtil.getType();

        TableWrapper<CountAgentTVI> tableWrapper = new TableWrapper(table,dao);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        table.setRowFactory(new Callback<TableView<CountAgentTVI>, TableRow<CountAgentTVI>>() {
//            @Override
//            public TableRow<CountAgentTVI> call(TableView<CountAgentTVI> tableView) {
//                final TableRow<CountAgentTVI> row = new TableRow<CountAgentTVI>() {
//                    @Override
//                    protected void updateItem(CountAgentTVI newRow, boolean empty) {
//                        super.updateItem(newRow, empty);
//                        if(super.getTableView().isEditable()) {
//                            super.setDisable(true);
//                            if (newRow != null)
//                                if (newRow.getForm().equals("-")
//                                        || newRow.getType().equals("-")
//                                        || newRow.getName().equals("-")) {
//                                    super.setDisable(false);
//                                }
//                        }else if(!super.getTableView().isEditable()){
//                            super.setDisable(false);
//                        }
//                    }
//                };
//                return row;
//            }
//        });
        TableColumn<CountAgentTVI,String> formColumn
                = tableWrapper.addColumn("Организационно прововая Форма",
                cellData ->cellData.getValue().formProperty()
        );
        TableColumn<CountAgentTVI,String> typerColumn
                = tableWrapper.addColumn("Тип",
                cellData -> cellData.getValue().typeProperty());
        TableColumn<CountAgentTVI,String> nameColumn
                = tableWrapper.addColumn("Наименование",
                cellData -> cellData.getValue().nameProperty());
        formColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(formMap.keySet().toArray(new String[0]))
        );
        typerColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(typeMap.keySet().toArray(new String[0]))
        );
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        formColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) ->{
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setForm(e.getNewValue());
            editedItem.setIdForm(formMap.get(e.getNewValue()));
        });
        typerColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) ->{
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setType(e.getNewValue());
            editedItem.setIdType(typeMap.get(e.getNewValue()));
        });
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) -> {
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setName(e.getNewValue());
            editedItem.setIdName(-1);
            System.out.println("\033[35m "+ e.getNewValue() + " old " + editedItem.getName() + "\033[0m");
        });


        return tableWrapper;
    }

    static PropertySheetWrapper getCountPropertySheet(){
        PropertySheet countSheet = new PropertySheet();
        PropertySheetWrapper wrapper = new PropertySheetWrapper(
                countSheet,
                new ReqCommonDAO(),
                new ReqBankDAO(),
                new ReqExBodyDAO()
        );
        ValidationSupport support = new ValidationSupport();
        countSheet.setContextMenu(ContextMenuFactory.getCommonSU(wrapper));//TODO
        wrapper.setValidationSupport(support);
        //add Items
//        wrapper.setFromBase();
        countSheet.setMode(PropertySheet.Mode.CATEGORY);
        countSheet.setModeSwitcherVisible(true);
        countSheet.setSearchBoxVisible(true);
        countSheet.setPropertyEditorFactory( param -> {
            PropertyEditor<?> editor = null;
            if(param.getValue().getClass().equals(Integer.class)) {
                editor = Editors.createNumericEditor(param);
            }else  if(param.getValue().getClass().equals(BigInteger.class)){
                editor = Editors.createNumericEditor(param);

            }else if(param.getValue().getClass().equals(String.class)){
                editor = Editors.createTextEditor(param);


            }else if(param.getValue().getClass().equals(LocalDate.class)){
                editor = Editors.createDateEditor(param);
            }else{
                editor = Editors.createTextEditor(param);

            }
            //add castom Text Area Editor
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
            support.registerValidator(
                    (Control) editor.getEditor(),
                    ((ObjectPSI)param).getValidator()
            );
            return editor;
        });
        return wrapper;
    }

    /**
     *
     * @param gridPane
     * @param tableWrapper
     */
    static void decorLinkedNamesGP(GridPane gridPane,TableWrapper<CountAgentTVI> tableWrapper){
        //Nodes
        ListView<String> listView = new ListView<>();
        ListSelectionView<String> listSelectionView= new ListSelectionView<>();
        HBox hBox = new HBox();
        Button editButton = new Button("Редактировать");
        Button saveListButton = new Button("Сохранить");
        Button cencelListButton = new Button("Отмена");
        PopOver popOver = new PopOver();
        //Settings
        hBox.getChildren().addAll(saveListButton,cencelListButton);
        hBox.setSpacing(10d);
        listSelectionView.setSourceFooter(hBox);
        popOver.setContentNode(listSelectionView);
        editButton.setOnAction(e ->{
            popOver.show(editButton);
            editButton.setDisable(true);

        });
        popOver.setOnHidden(e->{
            editButton.setDisable(false);
        });
        Bindings.bindBidirectional(editButton.disableProperty(),listView.disableProperty());
        Bindings.bindBidirectional(editButton.disableProperty(),tableWrapper.tableView().disableProperty());
        //GridPane
        gridPane.add(editButton, 0,0);
        gridPane.add(listView, 0,1,1,4);
    }

}
