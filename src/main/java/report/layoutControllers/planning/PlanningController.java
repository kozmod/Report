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
import report.entities.items.osr.TableItemOSR;
import report.entities.items.plan.TableViewItemPlanDAO;
import report.entities.items.plan.TableItemFact;
import report.entities.items.plan.TableItemPlan;
import report.models.coefficient.Quantity;
import report.entities.items.discount_coef.DiscountQuery;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.ContextMenuOptional;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.ContextMenuFactory;

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
    @FXML private TreeTableView kdTreeTable;

    private TableWrapper<TableItemPlan> planTableWrapper;
    private TableWrapper<TableItemFact> factTableWrapper;
    private TableWrapper<TableItemOSR> osrTableWrapper;

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
            planTableWrapper. setTableDataFromBASE();
            ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);
            //osr table
            osrTableWrapper.setTableDataFromBASE();
            ContextMenuOptional.setTableItemContextMenuListener(osrTableWrapper);
            //TF
            computeSumExpTextFields();

        });

    }

    private void init_PlanTab(){
        //add Plan TableView
        planTableWrapper = PlaningControllerTF.decorPlan(planTable);
        planTableWrapper.setTableDataFromBASE();
        ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);

        //add Fact TableView
        factTableWrapper = PlaningControllerTF.decorFact(factTable);
        factTableWrapper.setTableData(new TableViewItemPlanDAO().getListFact());

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

        planTableWrapper.getItems().addListener((ListChangeListener.Change<? extends TableItemPlan> c) -> {
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
        osrTableWrapper.setTableDataFromBASE();
        ContextMenuOptional.setTableItemContextMenuListener(osrTableWrapper);

        computeSumExpTextFields();
        osrTableWrapper.getItems().addListener((ListChangeListener.Change<? extends TableItemOSR> c) -> {
            System.out.println("Changed on " + c + " report.layoutControllers.allPropeties.AllPropertiesController.init_OSRTab()" );
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
        PlaningControllerTF.decorKD(kdTreeTable);
        PlaningControllerTF.decorKD(kdTreeTable);
        kdTreeTable.setRoot(new DiscountQuery().getList().tree());
        kdTreeTable.editableProperty().bind(Bindings
                .when(kdEditСheckBox.selectedProperty())
                .then(true)
                .otherwise(false));
    }

    /*!******************************************************************************************************************
    *                                                                                                     	    HANDLERS
    ********************************************************************************************************************/

    @FXML
    private void handle_planAddItemButton(ActionEvent event) {
        double SmetCost = new DoubleStringConverter().fromString(planSmetTF.getText());
        double SaleCost = new DoubleStringConverter().fromString(planSaleTF.getText());
        if(planTableWrapper.getItems()
                .add(new TableItemPlan(
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
                .add(new TableItemOSR(0,osrAddTextTF.getText(),expenses,expensesPerHouse ));


    }

    /*!******************************************************************************************************************
    *                                                                                                             METHODS
    ********************************************************************************************************************/

    private void computeSumPlanTextFields(){
        planSmetSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getSmetCostSum)
                            .sum()
                )
        );
        planSaleSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getSaleCostSum)
                            .sum()
                )
        );
        planProfitSumTF.setText(
                new DoubleStringConverter().toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getProfit)
                            .sum()
                )
        );
    }

    private void computeSumFactTextFields(){
        factSmetSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemFact::getSmetCostSum)
                        .sum()
                )
        );
        factSaleSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemFact::getSaleCostSum)
                        .sum()
            )
        );
        factProfitSumTF.setText(
                new DoubleStringConverter().toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemFact::getProfit)
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
                osrTableWrapper.getItems().stream().mapToDouble(TableItemOSR::getExpenses).sum()));
        sumExpPerSiteTF.setText(new DoubleStringConverter().toString(
                osrTableWrapper.getItems().stream().mapToDouble(TableItemOSR::getExpensesPerHouse).sum()));
    }

}
