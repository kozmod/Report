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
import report.entities.items.plan.TableItemPlan;
import report.models_view.data_utils.DecimalFormatter;
import report.models_view.nodes.ContextMenuOptional;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.ContextMenuFactory;
import report.models_view.nodes_factories.TableFactory;

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
    private TableWrapper<TableItemPlan> factTableWrapper;

    /*!******************************************************************************************************************
    *                                                                                                               INIT
    ********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add Plan TableView
        planTableWrapper = TableFactory.decorPlan(planTable);
        planTableWrapper.setTableDataFromBASE();
        ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);

        //add Fact TableView
        factTableWrapper = TableFactory.decorFact(factTable);
        factTableWrapper.setTableData(new ItemPlanDAO().getListFact());

        init_PlanTab();

    }

    private void init_PlanTab(){
        //table Context menu property
        planTableWrapper.contextMenuProperty().bind(
                Bindings.when(planEditСheckBox.selectedProperty() )
                        .then( ContextMenuFactory.getCommonDSU(planTableWrapper))
                        .otherwise( (ContextMenu) null));
        //TableWrapper Editable property
        planTableWrapper.editableProperty()
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
        double SmetCost = DecimalFormatter.stringToDouble(planSmetTF.getText());
        double SaleCost = DecimalFormatter.stringToDouble(planSaleTF.getText());
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
                )clearGroupOfTextInputControl(planTypeTF,planQuantityTF,planSmetTF,planSaleTF);


    }
    /*!******************************************************************************************************************
    *                                                                                                             METHODS
    ********************************************************************************************************************/

    private void computeSumPlanTextFields(){
        planSmetSumTF.setText(
                DecimalFormatter.toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getSmetCostSum)
                            .sum()
                )
        );
        planSaleSumTF.setText(
                DecimalFormatter.toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getSaleCostSum)
                            .sum()
                )
        );
        planProfitSumTF.setText(
                DecimalFormatter.toString(
                    planTableWrapper.getItems().stream()
                            .mapToDouble(TableItemPlan::getProfit)
                            .sum()
                )
        );
    }

    private void computeSumFactTextFields(){
        factSmetSumTF.setText(
                DecimalFormatter.toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemPlan::getSmetCostSum)
                        .sum()
                )
        );
        factSaleSumTF.setText(
                DecimalFormatter.toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemPlan::getSaleCostSum)
                        .sum()
            )
        );
        factProfitSumTF.setText(
                DecimalFormatter.toString(
                    factTableWrapper.getItems().stream()
                        .mapToDouble(TableItemPlan::getProfit)
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
