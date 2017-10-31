package report.layoutControllers.planning;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import report.entities.items.plan.ItemPlanDAO;
import report.entities.items.plan.TableItemFact;
import report.entities.items.plan.TableItemPlan;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.ContextMenuOptional;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.ContextMenuFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class PlanningController implements Initializable{

    @FXML private TextField planTypeTF,planSmetTF,planSaleTF, planQuantityTF ;
    @FXML private TextField planSmetSumTF,planSaleSumTF, planProfitSumTF;
    @FXML private TextField factSmetSumTF,factSaleSumTF, factProfitSumTF;

    @FXML private CheckBox  planEditСheckBox;
    @FXML private Button planAddItemButton;

    @FXML private TableView planTable,factTable;

    private TableWrapper<TableItemPlan> planTableWrapper;
    private TableWrapper<TableItemFact> factTableWrapper;

    /*!******************************************************************************************************************
    *                                                                                                               INIT
    ********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add Plan TableView
        planTableWrapper = PlaningControllerTF.decorPlan(planTable);
        planTableWrapper.setTableDataFromBASE();
        ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);

        //add Fact TableView
        factTableWrapper = PlaningControllerTF.decorFact(factTable);
        factTableWrapper.setTableData(new ItemPlanDAO().getListFact());

        init_PlanTab();

    }

    private void init_PlanTab(){
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

        this.setGroupNodeDisableProperty(planEditСheckBox.selectedProperty(),
                planTypeTF,
                planSmetTF,
                planSaleTF,
                planQuantityTF,
                planAddItemButton);

        planTableWrapper.getItems().addListener((ListChangeListener.Change<? extends TableItemPlan> c) -> {
            System.out.println("Changed on " + c + " - allProperty /// init_PlanTab");
            if(c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                this.computeSumPlanTextFields();
            }
        });


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

//    @FXML
//    private void test(ActionEvent event) {
//        System.out.println(planTableWrapper.editableProperty().get());
//        System.out.println(planEditСheckBox.selectedProperty().get());
//
//    }
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

    /**
     * Bind disable property to group of
     */
    private void setGroupNodeDisableProperty(BooleanProperty booleanProperty, Node... nodes){
//        for(Node node : nodes)node.disableProperty().bind(Bindings.when(booleanProperty).then(false).otherwise(true));
        Stream.of(nodes).forEach(
                node -> node.disableProperty()
                        .bind(Bindings
                                .when(booleanProperty)
                                .then(false)
                                .otherwise(true)
                        )
        );
    }

    /**
     * Clear for of TextInputControl like TextField, TextArea, etc.
     */
    private void clearGroupOfTextInputControl(TextInputControl ... nodes){
//        for(TextInputControl node : nodes)node.clear();
        Stream.of(nodes).forEach(TextInputControl::clear);
    }

}
