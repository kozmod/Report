package report.layoutControllers.planning;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import report.entities.items.DItem;
import report.entities.items.osr.OSR_TIV;
import report.entities.items.plan.PlanTIV;
import report.entities.items.plan.PlanDAO;
import report.entities.items.plan.FactTIV;
import report.models.coefficient.Quantity;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.customNodes.ContextMenuOptional;
import report.models.view.wrappers.tableWrappers.DiscountTreeTableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.nodesFactories.ContextMenuFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class PlanningController implements Initializable{

    @FXML private TextField planTypeTF,planSmetTF,planSaleTF, planQuantityTF ;
    @FXML private TextField planSmetSumTF,planSaleSumTF, planProfitSumTF;
    @FXML private TextField factSmetSumTF,factSaleSumTF, factProfitSumTF;
    @FXML private TextField osrAddTextTF, osrAddValueTF, siteQuantityTF, sumExpTF,  sumExpPerSiteTF;

    @FXML private CheckBox  planEditСheckBox;
    @FXML private CheckBox  osrEditСheckBox;
    @FXML private CheckBox  kdEditСheckBox;
    @FXML private HBox      planAddRowHB;
    @FXML private HBox      osrAddRowHB;
    @FXML private GridPane planGP,osrGP;
    @FXML private Button   planAddItemButton;
    @FXML private Button   osrAddItemButton;

    @FXML private TableView planTable,factTable,osrTable;
    @FXML private TreeTableView<DItem> kdTreeTable;

    private TableWrapper<PlanTIV> planTableWrapper;
    private TableWrapper<FactTIV> factTableWrapper;
    private TableWrapper<OSR_TIV> osrTableWrapper;
    private DiscountTreeTableWrapper kdTreeTableWrapper;

    /*!******************************************************************************************************************
    *                                                                                                               INIT
    ********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_PlanTab();
        init_OSRTab();
        init_KDTab();

        Quantity.getQuantityProperty().addListener(e ->{
            Quantity.updateFromBase();
            //plan table
            planTableWrapper.setFromBase();
            ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);
            //osr table
            osrTableWrapper.setFromBase();
            ContextMenuOptional.setTableItemContextMenuListener(osrTableWrapper);
            //TF
            computeSumExpTextFields();

        });

    }

    private void init_PlanTab(){
        //add Plan TableView
        planTableWrapper = PlaningControllerTF.decorPlan(planTable);
        planTableWrapper.setFromBase();
        ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);

        //add Fact TableView
        factTableWrapper = PlaningControllerTF.decorFact(factTable);
        factTableWrapper.setTableData(new PlanDAO().getListFact());

        //table Context menu property
        planTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(planEditСheckBox.selectedProperty() )
                        .then( ContextMenuFactory.getCommonDSU(planTableWrapper))
                        .otherwise( (ContextMenu) null));
        //TableWrapper Editable property
        planTableWrapper.tableView().editableProperty()
                .bind(planEditСheckBox.selectedProperty());
        //Compute Sum Text fields
        this.computeSumPlanTextFields();
        this.computeSumFactTextFields();

        planAddRowHB.disableProperty().bind(Bindings
                .when(planEditСheckBox.selectedProperty())
                .then(false)
                .otherwise(true));
        planAddRowHB.visibleProperty().bind(Bindings
                .when(planEditСheckBox.selectedProperty())
                .then(true)
                .otherwise(false));

        planTableWrapper.getItems().addListener((ListChangeListener.Change<? extends PlanTIV> c) -> {
            System.out.println("Changed on " + c + " - allProperty /// init_PlanTab");
            if(c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                this.computeSumPlanTextFields();
            }
        });
    }

    /**
     * Initialization of OSR Tab.
     */
    private void init_OSRTab(){
        //add OSR TableView
        osrTableWrapper = PlaningControllerTF.decorOSR(osrTable);
        osrTableWrapper.setFromBase();
        ContextMenuOptional.setTableItemContextMenuListener(osrTableWrapper);

        computeSumExpTextFields();
        osrTableWrapper.getItems().addListener((ListChangeListener.Change<? extends OSR_TIV> c) -> {
            System.out.println("Changed on " + c + " report.layoutControllers.allProperties.AllPropertiesController.init_OSRTab()" );
            if(c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                computeSumExpTextFields();
            }
        });
        //table Context menu property
        osrTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(osrEditСheckBox.selectedProperty() )
                        .then(ContextMenuFactory.getCommonDSU(osrTableWrapper))
                        .otherwise( (ContextMenu) null  ));
        //TableWrapper Editable property
        osrTableWrapper.tableView().editableProperty()
                .bind(osrEditСheckBox.selectedProperty());
        osrAddRowHB.disableProperty().bind(Bindings
                .when(osrEditСheckBox.selectedProperty())
                .then(false)
                .otherwise(true));
        osrAddRowHB.visibleProperty().bind(Bindings
                .when(osrEditСheckBox.selectedProperty())
                .then(true)
                .otherwise(false));
//        setGroupNodeDisableProperty(osrEditСheckBox.selectedProperty(),
//                osrAddTextTF,
//                osrAddValueTF,
//                osrAddItemButton);
        siteQuantityTF.textProperty().bindBidirectional(Quantity.getQuantityProperty(), new NumberStringConverter());
        osrAddRowHB.disableProperty().bind(Bindings
                .when(osrEditСheckBox.selectedProperty())
                .then(false)
                .otherwise(true));
        osrAddRowHB.visibleProperty().bind(Bindings
                .when(osrEditСheckBox.selectedProperty())
                .then(true)
                .otherwise(false));
    }
    /**
     * Initialization of KD Tab.
     */
    private void init_KDTab(){
        kdTreeTableWrapper = PlaningControllerTF.decorKD(kdTreeTable);
//        PlaningControllerTF.decorKD(kdTreeTable);
//        kdTreeTableWrapper.tableView().setRoot(new DiscountQuery().getData().tree());
        kdTreeTableWrapper.setFromBase();
        kdTreeTableWrapper.tableView().editableProperty().bind(Bindings
                .when(kdEditСheckBox.selectedProperty())
                .then(true)
                .otherwise(false));
        kdTreeTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(kdEditСheckBox.selectedProperty() )
                        .then( ContextMenuFactory.getCommonSU(kdTreeTableWrapper))
                        .otherwise( (ContextMenu) null));
    }

    /*!******************************************************************************************************************
    *                                                                                                     	    HANDLERS
    ********************************************************************************************************************/


    @FXML
    private void handle_planAddItemButton(ActionEvent event) {
        double SmetCost = new DoubleStringConverter().fromString(planSmetTF.getText());
        double SaleCost = new DoubleStringConverter().fromString(planSaleTF.getText());
        if(planTableWrapper.getItems()
                .add(new PlanTIV(
                                0,
                                new Timestamp(System.currentTimeMillis()),
                                (planTableWrapper.getItems().stream()
                                        .max(Comparator.comparing(f -> f.getTypeID()))
                                        .get().getTypeID() + 1),
                                planTypeTF.getText(),
                                Integer.parseInt(planQuantityTF.getText()),
                                0,
                                SmetCost,
                                (double)0,
                                SaleCost,
                                (double)0,
                                (double)0
                        )
                )
                )
            clearGroupOfTextInputControl(planTypeTF,planQuantityTF,planSmetTF,planSaleTF);
    }

    @FXML
    private void planEditСheckBox_handler(ActionEvent event) {
        if(planEditСheckBox.isSelected()){
            planGP.getRowConstraints().get(2).setPrefHeight(30);
        }else {
            planGP.getRowConstraints().get(2).setPrefHeight(0);
        }

    }
    @FXML
    private void osrEditСheckBox_handler(ActionEvent event) {
        if(osrEditСheckBox.isSelected()){
            osrGP.getRowConstraints().get(1).setPrefHeight(30);
        }else {
            osrGP.getRowConstraints().get(1).setPrefHeight(0);
        }

    }

    @FXML
    private void handle_osrAddItemButton(ActionEvent event) {
        Double expenses = new DoubleStringConverter().fromString(osrAddValueTF.getText());
        Double expensesPerHouse = expenses/ Quantity.value();
        osrTableWrapper.getItems()
                .add(new OSR_TIV(0,osrAddTextTF.getText(),expenses,expensesPerHouse ));


    }

    /*!******************************************************************************************************************
    *                                                                                                             METHODS
    ********************************************************************************************************************/

    private void computeSumPlanTextFields(){
        planSmetSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(PlanTIV::getSmetCostSum)
                            .sum()
                )
        );
        planSaleSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(PlanTIV::getSaleCostSum)
                            .sum()
                )
        );
        planProfitSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(PlanTIV::getProfit)
                            .sum()
                )
        );
    }

    private void computeSumFactTextFields(){
        factSmetSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(FactTIV::getSmetCostSum)
                        .sum()
                )
        );
        factSaleSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(FactTIV::getSaleCostSum)
                        .sum()
            )
        );
        factProfitSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(FactTIV::getProfit)
                        .sum()
                )
        );
    }

//    /**
//     * Bind disable property to group of
//     */
//    private void setGroupNodeDisableProperty(BooleanProperty booleanProperty, Node... nodes){
////        for(Node node : nodes)node.disableProperty().bind(Bindings.when(booleanProperty).then(false).otherwise(true));
//        Stream.of(nodes).forEach(
//                node -> node.disableProperty()
//                        .bind(Bindings
//                                .when(booleanProperty)
//                                .then(false)
//                                .otherwise(true)
//                        )
//        );
//    }

    /**
     * Clear for of TextInputControl like TextField, TextArea, etc.
     */
    private void clearGroupOfTextInputControl(TextInputControl ... nodes){
//        for(TextInputControl node : nodes)node.clear();
        Stream.of(nodes).forEach(TextInputControl::clear);
    }

//    /**
//     * Bind disable property to group of
//     */
//    private void setGroupNodeDisableProperty( BooleanProperty booleanProperty, Node ... nodes){
//        for(Node node : nodes)node.disableProperty().bind(Bindings.when(booleanProperty).then(false).otherwise(true));
//    }

    /*!******************************************************************************************************************
    *                                                                                                             METHODS
    ********************************************************************************************************************/
    private void computeSumExpTextFields(){
        sumExpTF.setText(new DoubleStringConverter().toString(
                osrTableWrapper.getItems().stream().mapToDouble(OSR_TIV::getExpenses).sum()));
        sumExpPerSiteTF.setText(new DoubleStringConverter().toString(
                osrTableWrapper.getItems().stream().mapToDouble(OSR_TIV::getExpensesPerHouse).sum()));
    }

}
