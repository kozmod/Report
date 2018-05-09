package report.layout.controllers.allProperties;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import org.controlsfx.validation.ValidationSupport;
import report.entities.abstraction.CommonDAO;
import report.entities.contract.ContractTIV;
import report.entities.items.DItem;
import report.entities.items.contractor.ContractorDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CounterAgentDAO;
import report.entities.items.counterparties.utils.CounterAgentDaoUtil;
import report.entities.items.counterparties.ReqBankDAO;
import report.entities.items.counterparties.ReqCommonDAO;
import report.entities.items.counterparties.ReqExBodyDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.entities.items.site.month.ReportingMonth;
import report.entities.items.site.month.ReportingMonthDAO;
import report.entities.items.site.name.SiteNameTIV;
import report.entities.items.variable.PropertiesDAO;
import report.entities.items.variable.VariableTIV_new;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.LinkedNamePair;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.wrappers.propertySheetWrappers.PropertySheetWrapper;
import report.models.view.wrappers.tableWrappers.ReverseTableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.nodesFactories.TableFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class AllPropertiesControllerUtils implements TableFactory {

    private AllPropertiesControllerUtils() {
    }

    /**
     * Create TableWrapper "Variable"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    static ReverseTableWrapper<VariableTIV_new> decorVariable(TableView table) {
        ReverseTableWrapper<VariableTIV_new> tableWrapper = new ReverseTableWrapper<>("Общие параметры", table, new PropertiesDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<DItem, String> titleCol = new TableColumn<>("Параметр");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().firstValueProperty());

        TableColumn<DItem, Double> valueCol = new TableColumn<>("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter("###,##0.000")));
        tableWrapper.tableView().getColumns().addAll(titleCol, valueCol);

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
    static TableWrapper decorContractor(TableView table) {
        TableWrapper tableWrapper = new TableWrapper(table, new ContractorDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn idColumn = tableWrapper.addColumn("ID", "id");
        TableColumn contractorColumn = tableWrapper.addColumn("Подрядчик", "contractor");

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
    static TableWrapper<CountAgentTVI> decorCountAgent(TableView<CountAgentTVI> table) {
        CommonDAO dao = new CounterAgentDAO();
        Map<String, Integer> formMap = CounterAgentDaoUtil.getForm();
        Map<String, Integer> typeMap = CounterAgentDaoUtil.getType();

        TableWrapper<CountAgentTVI> tableWrapper = new TableWrapper(table, dao);
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
        TableColumn<CountAgentTVI, String> formColumn
                = tableWrapper.addColumn("Организационно прововая Форма",
                cellData -> cellData.getValue().formProperty()
        );
        TableColumn<CountAgentTVI, String> typerColumn
                = tableWrapper.addColumn("Тип",
                cellData -> cellData.getValue().typeProperty());
        TableColumn<CountAgentTVI, String> nameColumn
                = tableWrapper.addColumn("Наименование",
                cellData -> cellData.getValue().nameProperty());
        formColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(formMap.keySet().toArray(new String[0]))
        );
        typerColumn.setCellFactory(
                ComboBoxTableCell.forTableColumn(typeMap.keySet().toArray(new String[0]))
        );
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        formColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) -> {
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setForm(e.getNewValue());
            editedItem.setIdForm(formMap.get(e.getNewValue()));
        });
        typerColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) -> {
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setType(e.getNewValue());
            editedItem.setIdType(typeMap.get(e.getNewValue()));
        });
        nameColumn.setOnEditCommit((TableColumn.CellEditEvent<CountAgentTVI, String> e) -> {
            CountAgentTVI editedItem = e.getRowValue();
            editedItem.setName(e.getNewValue());
            editedItem.setIdName(-1);
            System.out.println("\033[35m " + e.getNewValue() + " old " + editedItem.getName() + "\033[0m");
        });
        return tableWrapper;
    }

    /**
     */
    static PropertySheetWrapper getCountPropertySheet(TableWrapper<CountAgentTVI> tableWrapper) {
        PropertySheet countSheet = new PropertySheet();
        PropertySheetWrapper propertySheetWrapper = new PropertySheetWrapper(
                countSheet,
                new ReqCommonDAO(),
                new ReqBankDAO(),
                new ReqExBodyDAO()
        );

        countSheet.setContextMenu(ContextMenuFactory.getCommonSU(propertySheetWrapper));//TODO

        //add Items
//        propertySheetWrapper.setFromBase();
        countSheet.setMode(PropertySheet.Mode.CATEGORY);
        countSheet.setModeSwitcherVisible(true);
        countSheet.setSearchBoxVisible(true);
        countSheet.setPropertyEditorFactory(param -> {
            PropertyEditor<?> editor;
            if (param.getValue().getClass().equals(Integer.class)) {
                editor = Editors.createNumericEditor(param);
            } else if (param.getValue().getClass().equals(BigInteger.class)) {
                editor = Editors.createNumericEditor(param);
            } else if (param.getValue().getClass().equals(String.class)) {
                if (param.getName().equals("Паспорт:Кем\nвыдан")) {
                    editor = new AbstractPropertyEditor<String, TextArea>(param, new TextArea()) {
                        @Override
                        public void setValue(String value) {
                            ((TextArea) this.getEditor()).setText(value);
                        }

                        @Override
                        protected ObservableValue<String> getObservableValue() {
                            return (this.getEditor()).textProperty();
                        }

                        {
                            TextInputControl control = this.getEditor();
                            control.focusedProperty().addListener((o, oldValue, newValue) -> {
                                if (newValue) {
                                    Platform.runLater(() -> control.selectAll());
                                }
                            });
                        }
                    };
                } else {
                    editor = Editors.createTextEditor(param);
                }
                //TODO -> REFACTOR
                if (Objects.nonNull(editor.getEditor()) && editor.getEditor() instanceof TextField) {
                    setPadegPopOver((TextField) editor.getEditor());
                }
            } else if (param.getValue().getClass().equals(LocalDate.class)) {
                editor = Editors.createDateEditor(param);
            } else {
                editor = Editors.createTextEditor(param);

            }
            ValidationSupport validationSupport = new ValidationSupport();
            validationSupport.registerValidator(
                    (Control) editor.getEditor(),
                    ((ObjectPSI) param).getValidator()
            );
            propertySheetWrapper.setValidationSupport(validationSupport);
            return editor;
        });
        tableWrapper.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    CountAgentTVI item = tableWrapper.getSelectionModel().getSelectedItem();
                    if (item != null && item.getRequisites() == null) {
                        propertySheetWrapper.setFromBase(newValue.getIdName());
                        item.setRequisites(
                                FXCollections.observableArrayList(propertySheetWrapper.getObservableItems())
                        );
                    } else {
                        propertySheetWrapper.setItems(item.getRequisites());
                    }
                });
        return propertySheetWrapper;
    }

    static void decorLinkedNamesGP(GridPane gridPane, TableWrapper<CountAgentTVI> tableWrapper) {
        //Nodes
        ListView<LinkedNamePair> listView = new ListView<>();
        HBox hBox = new HBox();
        PopOver popOver = new PopOver();
        Button editButton = new Button("Редактировать");
        editButton.setDisable(true);
        Button saveListButton = new Button("Сохранить");
        saveListButton.setOnAction(event -> {
            CounterAgentDAO counterAgentDAO = new CounterAgentDAO();
            counterAgentDAO.delete(
                    FXCollections.observableArrayList(tableWrapper.getSelectionModel().getSelectedItem())
            );
            counterAgentDAO.insert(
                    FXCollections.observableArrayList(tableWrapper.getSelectionModel().getSelectedItem())
            );
            popOver.hide();
        });
        Button cancelListButton = new Button("Отмена");
        cancelListButton.setOnAction(event -> {
            CountAgentTVI countAgent = tableWrapper.getSelectionModel().getSelectedItem();
            popOver.hide();
            tableWrapper.undoChangeItems();
            listView.setItems(tableWrapper.getSelectionModel().getSelectedItem().getLinkedNames());
        });

        hBox.getChildren().addAll(saveListButton, cancelListButton);
        hBox.setSpacing(10d);
        editButton.setOnAction(e -> {
            ListSelectionView<LinkedNamePair> listSelectionView = new ListSelectionView<>();
            listSelectionView.setMinWidth(1000);
            listSelectionView.setPrefWidth(1000);
            listSelectionView.setMinHeight(600);

            listSelectionView.setSourceFooter(hBox);
            listSelectionView.setSourceItems(listView.getItems());
            listSelectionView.setTargetItems(
                    CounterAgentDaoUtil.getNotMatchLinkedNames()
            );

            popOver.setContentNode(listSelectionView);
            popOver.show(editButton);
            editButton.setDisable(true);

        });
        popOver.setOnHidden(e -> {
            editButton.setDisable(false);
        });
        Bindings.bindBidirectional(editButton.disableProperty(), listView.disableProperty());
        Bindings.bindBidirectional(editButton.disableProperty(), tableWrapper.tableView().disableProperty());
        //GridPane
        gridPane.add(editButton, 0, 0);
        gridPane.add(listView, 0, 1, 1, 4);

        //TODO: eject Listener to  AllPropertiesController
        tableWrapper.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    CountAgentTVI item = tableWrapper.getSelectionModel().getSelectedItem();
                    if (item.getLinkedNames() == null) {
                        ObservableList linkedName = CounterAgentDaoUtil.getMatchLinkedNames(newValue.getName());
//                                if(!linkedName.isEmpty()){
                        item.setLinkedNames(linkedName);
//                                }
                    }
                    listView.setItems(item.getLinkedNames());
                });
    }

    static TableWrapper<ReportingMonth> decorMonthTable(TableView<ReportingMonth> table) {
        TableWrapper<ReportingMonth> tableWrapper = new TableWrapper<>(table, null);
        tableWrapper.tableView().setItems(new ReportingMonthDAO().getData());

        TableColumn<ReportingMonth, Date> monthColumn = tableWrapper.addColumn("Month", column -> column.getValue().monthProperty());
        TableColumn<ReportingMonth, String> finishColumn = tableWrapper.addColumn("2", column -> column.getValue().countFinishProperty());
        TableColumn<ReportingMonth, String> payColumn = tableWrapper.addColumn("3", column -> column.getValue().countPartyProperty());
        TableColumn<ReportingMonth, String> priseSumColumn = tableWrapper.addColumn("4", column -> column.getValue().priceSumProperty());
        TableColumn<ReportingMonth, String> sCredColumn = tableWrapper.addColumn("5", column -> column.getValue().sCredProperty());
        TableColumn<ReportingMonth, String> operProfitColumn = tableWrapper.addColumn("6", column -> column.getValue().operProfitProperty());
        TableColumn<ReportingMonth, String> addCostColumn = tableWrapper.addColumn("7", column -> column.getValue().addCostProperty());
        TableColumn<ReportingMonth, String> allTaxesColumn = tableWrapper.addColumn("8", column -> column.getValue().allTaxesProperty());
        TableColumn<ReportingMonth, String> sumOsrColumn = tableWrapper.addColumn("9", column -> column.getValue().sumOsrProperty());

        return tableWrapper;
    }


    static TableWrapper<ContractTIV> decorContractTable(TableView<ContractTIV> table) {
        TableWrapper<ContractTIV> tableWrapper = new TableWrapper<>(table, null);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        try {
//            tableWrapper.tableView().setItems(
//                    FXCollections.observableArrayList(
//                            new ContractService().list()
//                    )
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        TableColumn<ContractTIV, String> contractName = tableWrapper.addColumn("ContractName", column -> column.getValue().contractNameProperty());

        return tableWrapper;
    }

    static TableWrapper<SiteNameTIV> decorSiteNameTable(TableView<SiteNameTIV> table) {
        TableWrapper<SiteNameTIV> tableWrapper = new TableWrapper<>(table, null);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<SiteNameTIV, String> siteName = tableWrapper.addColumn("siteName", column -> column.getValue().siteNameProperty());

        return tableWrapper;
    }

    private static void setPadegPopOver(final TextField sourceTextField) {
        PopOver padegPopOver = new PadegTextFieldPopOver(sourceTextField);
    }

    /**\
     * Padeg PopOver
     */
    static class PadegTextFieldPopOver extends PopOver {
        private final TextField sourceTextField;
        private final TextField padegTextField = new TextField();
        private final GridPane gridPane = new GridPane();
        private final CheckBox padegCheckbox = new CheckBox("Ручной ввод");


        PadegTextFieldPopOver(TextField sourceTextField) {
            this.sourceTextField = sourceTextField;
            this.initialize();
        }

        {
            gridPane.setPadding(new Insets(5, 5, 5, 5));
            gridPane.add(padegTextField, 0, 0);
            gridPane.add(padegCheckbox, 0, 2);
            setContentNode(gridPane);
        }


        private void initialize() {
            initSourceTextField();
            initPadegTextField();
            initCheckbox();

        }

        private void initSourceTextField(){
            sourceTextField.focusedProperty()
                    .addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
                if (newPropertyValue) {
                    this.show(sourceTextField);
                } else {
                    this.hide();
                }
            });
        }

        private void initPadegTextField(){
            padegTextField.textProperty()
                    .bindBidirectional(sourceTextField.textProperty(), new PadegStringConverter());
            padegTextField.disableProperty().bind(
                    Bindings.when(padegCheckbox.selectedProperty())
                            .then(false)
                            .otherwise(true)
            );
        }
        private void initCheckbox(){
            padegCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (Objects.nonNull(newValue)) {
                    if(newValue) {
                        padegTextField.textProperty().unbindBidirectional(sourceTextField.textProperty());
                        padegTextField.requestFocus();
                    }else {
                        padegTextField.textProperty()
                                .bindBidirectional(sourceTextField.textProperty(), new PadegStringConverter());
                        sourceTextField.requestFocus();
                    }
                }
            });
        }
    }
}
