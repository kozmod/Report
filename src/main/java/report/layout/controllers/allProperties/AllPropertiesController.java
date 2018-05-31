
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
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.site.month.ReportingMonth;
import report.entities.items.site.name.SiteNameDao;
import report.entities.items.site.name.SiteNameTIV;
import report.entities.items.variable.VariableTIV_new;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.models.view.wrappers.propertySheet.PropertySheetWrapper;
import report.models.view.wrappers.table.ReverseTableWrapper;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.customNodes.ContextMenuOptional;


public class AllPropertiesController implements Initializable {
    /***************************************************************************
     *                                                                         *
     *Tab 4 FXML                                                               *
     *                                                                         *
     **************************************************************************/
    @FXML
    private TableView<ReportingMonth> cashFlowTable;
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

    private ReverseTableWrapper<VariableTIV_new> variableTableWrapper;
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
        reportingMonthTableWrapper = AllPropertiesControllerNodeUtils.decorCashFlowTable(cashFlowTable);
        contractTableWrapper = AllPropertiesControllerNodeUtils.decorContractTable(contractTable);
        siteNameTableWrapper = AllPropertiesControllerNodeUtils.decorSiteNameTable(siteTable);
        this.init_VariableTab();
        this.init_CounterPatiesTab();

        //TODO: Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
        this.initData();
    }

    /**
     * Init Data from Base
     */
    public void initData() {
        variableTableWrapper.setFromBase();
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
        variableTableWrapper = AllPropertiesControllerNodeUtils.decorVariable(variableTable);
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
     * Init 3                                                                  *
     *                                                                         *
     **************************************************************************/
    private void init_CounterPatiesTab() {
        countAgentTableWrapper = AllPropertiesControllerNodeUtils.decorCountAgent(countAgentTable);
        countAgentTableWrapper.tableView()
                .editableProperty()
                .bind(countAgentСheckBox.selectedProperty());
        addCoutButton.visibleProperty()
                .bind(countAgentСheckBox.selectedProperty());
        counterPropSheet = AllPropertiesControllerNodeUtils.getCountPropertySheet(countAgentTableWrapper);


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
                siteNameTableWrapper.tableView().setItems(new SiteNameDao().getData(newSelection.getName()));
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
        AllPropertiesControllerNodeUtils.decorLinkedNamesGP(linkedNamesGP, countAgentTableWrapper);

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
                            new CountAgentTVI(-1, "-", 0, "-", 0, "-", -1)
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
