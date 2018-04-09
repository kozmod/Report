
package report.layout.controllers.allProperties;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.PropertySheet;
import report.entities.contract.ContractService;
import report.entities.contract.ContractTIV;
import report.entities.items.contractor.ContractorTIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.site.month.ReportingMonth;
import report.entities.items.site.name.SiteNameDAO;
import report.entities.items.site.name.SiteNameTIV;
import report.entities.items.variable.VariableTIV_new;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.models.view.wrappers.propertySheetWrappers.PropertySheetWrapper;
import report.models.view.wrappers.tableWrappers.ReverseTableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.customNodes.ContextMenuOptional;


public class AllPropertiesController implements Initializable {
    /***************************************************************************
     *                                                                         *
     *Tab 4 FXML                                                               *
     *                                                                         *
     **************************************************************************/
    @FXML
    private TableView<ReportingMonth> reportingMonthTable;
    @FXML
    private DatePicker monthFromDP;
    @FXML
    private DatePicker monthToDP;
    /***************************************************************************
     *                                                                         *
     *Tab 3 FXML                                                               *
     *                                                                         *
     **************************************************************************/
    @FXML
    private TableView<ContractTIV> contractTable;
    @FXML
    private TableView<SiteNameTIV> siteTable;
    @FXML
    private TableView<CountAgentTVI> countAgentTable;
    @FXML
    private ScrollPane reqBankScrollPane;
    @FXML
    private CheckBox countAgentСheckBox;
    @FXML
    private Button addCoutButton;
    @FXML
    private GridPane linkedNamesGP;

    private PropertySheetWrapper counterPropSheet;
    /***************************************************************************
     *                                                                         *
     *Tab Variable FXML                                                        *
     *                                                                         *
     **************************************************************************/
    @FXML
    private TableView variableTable;
    @FXML
    private CheckBox variableEditСheckBox;
    /***************************************************************************
     *                                                                         *
     *Tab Contractor FXML                                                      *
     *                                                                         *
     **************************************************************************/
    @FXML
    private TableView contractorTable;
    @FXML
    private TextField contractorNameTF, contractorDirectorTF;
    @FXML
    private TextArea contractorAdressTA, contractorCommentsTA;
    @FXML
    private CheckBox contractorEditСheckBox, planEditСheckBox;
    @FXML
    private Button contractorAddItemButton, contractorSaveItemButton, contractorCencelItemButton;


    private ReverseTableWrapper<VariableTIV_new> variableTableWrapper;
    private TableWrapper<ContractorTIV> contractorTableWrapper;
    private TableWrapper<CountAgentTVI> countAgentTableWrapper;
    private TableWrapper<ReportingMonth> reportingMonthTableWrapper;
    private TableWrapper<ContractTIV> contractTableWrapper;
    private TableWrapper<SiteNameTIV> siteNameTableWrapper;

    /***************************************************************************
     *                                                                         *
     * Initialize                                                              *
     *                                                                         *
     **************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportingMonthTableWrapper = AllPropertiesControllerND.decorMonthTable(reportingMonthTable);
        contractTableWrapper = AllPropertiesControllerND.decorContractTable(contractTable);
        siteNameTableWrapper = AllPropertiesControllerND.decorSiteNameTable(siteTable);
        this.init_VariableTab();
        this.init_ContractorTab();
        this.init_CounterPatiesTab();


        //TODO: Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
        this.initData();
    }

    /**
     * Init Data from Base
     */
    public void initData() {
        variableTableWrapper.setFromBase();
        contractorTableWrapper.setFromBase();
        countAgentTableWrapper.setFromBase();

    }

    /***************************************************************************
     *                                                                         *
     * Init Variable                                                           *
     *                                                                         *
     **************************************************************************/

    /**
     * Initialization of Variable Tab.
     */
    private void init_VariableTab() {
        //add OSR TableView
        variableTableWrapper = AllPropertiesControllerND.decorVariable(variableTable);
        //table Context menu property
        variableTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(variableEditСheckBox.selectedProperty())
                        .then(ContextMenuFactory.getCommonSU(variableTableWrapper))
                        .otherwise((ContextMenu) null));
        //TableWrapper Editable property
        variableTableWrapper.tableView().editableProperty()
                .bind(variableEditСheckBox.selectedProperty());

    }
    /***************************************************************************
     *                                                                         *
     * Init Contractor                                                         *
     *                                                                         *
     **************************************************************************/
    /**
     * Initialization of Contractor Tab.
     */
    private void init_ContractorTab() {
        //add Contractors TableView
        contractorTableWrapper = AllPropertiesControllerND.decorContractor(contractorTable);
        ContextMenuOptional.setTableItemContextMenuListener(contractorTableWrapper);

        //bing ContextMenu
        contractorTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(contractorEditСheckBox.selectedProperty())
                        .then(ContextMenuFactory.getCommonDSU(contractorTableWrapper))
                        .otherwise((ContextMenu) null));

        //bind editableProperty to contractorEditСheckBox
        contractorNameTF.editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorDirectorTF.editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorAdressTA.editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorCommentsTA.editableProperty().bind(contractorEditСheckBox.selectedProperty());

        //bind disableProperty to contractorEditСheckBox
        contractorAddItemButton.disableProperty().bind(contractorEditСheckBox.selectedProperty().not());

        contractorTableWrapper.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ContractorTIV item = (newValue != null) ? newValue : oldValue;
            if (newValue != null && oldValue != null) {
                Bindings.unbindBidirectional(contractorNameTF.textProperty(), oldValue.getContractorProperty());
                Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), oldValue.getDirectorProperty());
                Bindings.unbindBidirectional(contractorAdressTA.textProperty(), oldValue.getAdressProperty());
                Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), oldValue.getCommentsProperty());
            }

            clearGroupOfTextInputControl(contractorNameTF, contractorDirectorTF, contractorAdressTA, contractorCommentsTA);

            if (contractorTableWrapper.getItems().size() > 0 && !contractorTableWrapper.getSelectionModel().isEmpty()) {
                Bindings.bindBidirectional(contractorNameTF.textProperty(), item.getContractorProperty());
                Bindings.bindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                Bindings.bindBidirectional(contractorAdressTA.textProperty(), item.getAdressProperty());
                Bindings.bindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
            }
        });
    }

    /***************************************************************************
     *                                                                         *
     * Init 3                                                                  *
     *                                                                         *
     **************************************************************************/
    private void init_CounterPatiesTab() {
        countAgentTableWrapper = AllPropertiesControllerND.decorCountAgent(countAgentTable);
        countAgentTableWrapper.tableView()
                .editableProperty()
                .bind(countAgentСheckBox.selectedProperty());
        addCoutButton.visibleProperty()
                .bind(countAgentСheckBox.selectedProperty());
        counterPropSheet = AllPropertiesControllerND.getCountPropertySheet(countAgentTableWrapper);


        reqBankScrollPane.setContent(counterPropSheet.getSheet());
        //bing ContextMenu
        countAgentTableWrapper.tableView()
                .contextMenuProperty()
                .bind(Bindings.when(countAgentСheckBox.selectedProperty())
                        .then(ContextMenuFactory.getCommonDSU(countAgentTableWrapper))
                        .otherwise((ContextMenu) null)
                );
        countAgentTableWrapper.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                siteNameTableWrapper.tableView().setItems(new SiteNameDAO().getData(newSelection.getName()));
            }
        });

        siteNameTableWrapper.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    contractTableWrapper.tableView()
                            .setItems(
                                    FXCollections.observableArrayList(
                                            new ContractService().listBySiteName(newSelection.getSiteName())
                                    )
                            );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        counterPropSheet.getObservableItems().addListener((ListChangeListener<? super PropertySheet.Item>) listener -> {
            if (listener.next()) {
//                System.out.println(counterPropSheet.getSheet().getItems().get(listener.getFrom()).getValue());
            }
        });
        AllPropertiesControllerND.decorLinkedNamesGP(linkedNamesGP, countAgentTableWrapper);

    }


    /***************************************************************************
     *                                                                         *
     * HANDLERS                                                                *
     *                                                                         *
     **************************************************************************/
    @FXML
    private void handle_contractorItemButton(ActionEvent event) {
        if (event.getSource() == contractorAddItemButton) {
            setVisibleDisableContractorNodes(true);

            ContractorTIV item = contractorTableWrapper.getSelectionModel().getSelectedItem();
            if ((item != null) && !contractorTableWrapper.getSelectionModel().isEmpty()) {
                Bindings.unbindBidirectional(contractorNameTF.textProperty(), item.getContractorProperty());
                Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                Bindings.unbindBidirectional(contractorAdressTA.textProperty(), item.getAdressProperty());
                Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
            }
            clearGroupOfTextInputControl(contractorNameTF, contractorDirectorTF, contractorAdressTA, contractorCommentsTA);
        } else if (event.getSource() == contractorSaveItemButton) {
            setVisibleDisableContractorNodes(false);
            if (contractorTableWrapper.getItems().add(new ContractorTIV(
                    0,
                    contractorNameTF.getText(),
                    contractorDirectorTF.getText(),
                    contractorAdressTA.getText(),
                    contractorCommentsTA.getText()
            ))
                    )
                clearGroupOfTextInputControl(contractorNameTF, contractorDirectorTF, contractorAdressTA, contractorCommentsTA);
        }
        if (event.getSource() == contractorCencelItemButton) {
            setVisibleDisableContractorNodes(false);
            clearGroupOfTextInputControl(
                    contractorNameTF,
                    contractorDirectorTF,
                    contractorAdressTA,
                    contractorCommentsTA
            );
        }
    }

    /**
     * Bind disable property to group of
     */
    private void setGroupNodeDisableProperty(BooleanProperty booleanProperty, Node... nodes) {
        for (Node node : nodes) node.disableProperty().bind(Bindings.when(booleanProperty).then(false).otherwise(true));
    }

    /**
     * Clear for of TextInputControl like TextField, TextArea, etc.
     */
    private void clearGroupOfTextInputControl(TextInputControl... nodes) {
        for (TextInputControl node : nodes) node.clear();
    }


    private void setVisibleDisableContractorNodes(boolean value) {
        contractorAddItemButton.setVisible(!value);
        contractorSaveItemButton.setVisible(value);
        contractorCencelItemButton.setVisible(value);
        contractorEditСheckBox.setDisable(value);
        contractorTableWrapper.setDisable(value);
    }

    /***************************************************************************
     *                                                                         *
     * Handlers                                                                *
     *                                                                         *
     **************************************************************************/
    @FXML
    private void handle_addCoutButton(ActionEvent event) {
        boolean addResult = false;
        switch (addCoutButton.getText()) {
            case "+":
                if (countAgentTableWrapper.tableView().isEditable()) {
                    addResult = countAgentTableWrapper.getItems().add(
                            new CountAgentTVI(-1, "-", 0, "-", 0, "-")
                                    .setNewStatus(true)
                    );
                }
                if (addResult) {
                    addCoutButton.setText("OK");
                    countAgentСheckBox.setDisable(true);
                }
                break;
            case "OK":
                CountAgentTVI lastItem = countAgentTableWrapper.getItems().get(countAgentTableWrapper.getItems().size() - 1);
                if (lastItem.getForm().equals("-")
                        || lastItem.getType().equals("-")
                        || lastItem.getName().equals("-")) {
                    System.out.println("\033[34m ---> wrong edit in last added item");
                    System.out.println(lastItem.toString());

                } else {
                    addCoutButton.setText("+");
                    countAgentTableWrapper.refresh();
                    countAgentСheckBox.setDisable(false);
//                    countAgentTableWrapper.setEditable(false);
                }
                break;

        }
    }

    @FXML
    private void handle_SetMonthTableItem(ActionEvent event) {
        monthFromDP.setConverter(new LocalDayStringConverter());
        monthToDP.setConverter(new LocalDayStringConverter());

    }
}
