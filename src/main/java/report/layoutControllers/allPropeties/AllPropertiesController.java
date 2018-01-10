
package report.layoutControllers.allPropeties;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import report.entities.items.contractor.ContractorTIV;
import report.entities.items.counterparties.CountAgentTVI;
import report.entities.items.variable.VariableTIV_new;
import report.models_view.nodes.node_wrappers.ReverseTableWrapper;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.ContextMenuFactory;
import report.models_view.nodes.ContextMenuOptional;


public class AllPropertiesController implements Initializable {
    /*!******************************************************************************************************************
    *                                                                                                         Tab 3 FXML
    ********************************************************************************************************************/
    @FXML
    private TextField commonOgrnTF,  commonInnTF, commonAdresTF;
    @FXML
    private CheckBox commonChB;
    @FXML
    private DatePicker commonDateOgrnDP;
    @FXML
    private TextField bankNameTF, bankBicTF, bankAccNumTF, bankCorAccTF;
    @FXML
    private CheckBox bankChB;
    @FXML
    private TextField exBodyTF, exBodyNameTF, exBodySurnameTF, exFNameBodyTF,exBaseTF,
            exBcNameTF,exBcSurnameTF,exBcFNameTF,
            exBodyIdSeriesTF,exBodyIdNumberTF, exBodyIdCodeTF;
    @FXML
    private TextArea exBodyIdTextTA;
    @FXML
    private CheckBox exBodyChB;
    @FXML
    private DatePicker exBodyDP;
    @FXML
    private TableView<CountAgentTVI> countAgentTable;


    /*!******************************************************************************************************************
    *                                                                                                 Tab Variable FXML
    ********************************************************************************************************************/
    @FXML private TableView variableTable;
    @FXML private CheckBox  variableEditСheckBox;

    /*!******************************************************************************************************************
     *                                                                                                 Tab Contractor FXML
     ********************************************************************************************************************/
    @FXML private TableView contractorTable;
    @FXML private TextField contractorNameTF,contractorDirectorTF;
//    @FXML private TextField planTypeTF,planSmetTF,planSaleTF, planQuantityTF ;
//    @FXML private TextField planSmetSumTF,planSaleSumTF, planProfitSumTF;
//    @FXML private TextField factSmetSumTF,factSaleSumTF, factProfitSumTF;
    @FXML private TextArea  contractorAdressTA,contractorCommentsTA;
//    @FXML private CheckBox  osrEditСheckBox;
    @FXML private CheckBox  contractorEditСheckBox, planEditСheckBox;
//    @FXML private Button    osrAddItemButton, planAddItemButton;
    @FXML private Button    contractorAddItemButton, contractorSaveItemButton, contractorCencelItemButton;


    private ReverseTableWrapper<VariableTIV_new> variableTableWrapper ;
    private TableWrapper<ContractorTIV> contractorTableWrapper;
    private TableWrapper<CountAgentTVI> countAgentTableWrapper;
//    private TableWrapper<PlanTIV> planTableWrapper;
//    private TableWrapper<PlanTIV> factTableWrapper ;

    /*!******************************************************************************************************************
    *                                                                                                         initialize
    ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
////        //add OSR TableView
//        osrTableWrapper = AllPropertiesControllerTF.decorOSR(osrTable);
//        osrTableWrapper.setDataFromBASE();
//        ContextMenuOptional.setTableItemContextMenuListener(osrTableWrapper);

//        //add OSR TableView
        variableTableWrapper = AllPropertiesControllerTF.decorVariable(variableTable);
        variableTableWrapper.setDataFromBASE();
//        ContextMenuOptional.setTableItemContextMenuListener(variableTableWrapper);


        //add Contractors TableView
        contractorTableWrapper = AllPropertiesControllerTF.decorContractor(contractorTable);
        contractorTableWrapper.setDataFromBASE();
        ContextMenuOptional.setTableItemContextMenuListener(contractorTableWrapper);

        countAgentTableWrapper = AllPropertiesControllerTF.decorCountAgent(countAgentTable);
        countAgentTableWrapper.setDataFromBASE();
//        //add Plan TableView
//        planTableWrapper = TableFactory.decorPlan(planTable);
//        planTableWrapper.setDataFromBASE();
//        ContextMenuOptional.setTableItemContextMenuListener(planTableWrapper);
//
//        //add Fact TableView
//        factTableWrapper = TableFactory.decorFact(factTable);
//        factTableWrapper.setTableData(new PlanDAO().getListFact());


//        init_OSRTab();
        init_VariableTab();
        init_ContractorTab();
//        init_PlanTab();
    }
    
/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/
//    /**
//     * Initialization of OSR Tab.
//     */
//    private void init_OSRTab(){
//
//       computeSumExpTextFields();
//       osrTableWrapper.getItems().addListener((ListChangeListener.Change<? extends OSR_TIV> c) -> {
//                System.out.println("Changed on " + c + " report.layoutControllers.allPropeties.AllPropertiesController.init_OSRTab()" );
//                if(c.next() &&
//                        (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
//                            computeSumExpTextFields();
//                }
//        });
//
//       //table Context menu property
//        osrTableWrapper.treeTableView().contextMenuProperty().bind(
//            Bindings.when(osrEditСheckBox.selectedProperty() )
//                .then(ContextMenuFactory.getCommonDSU(osrTableWrapper))
//                .otherwise( (ContextMenu) null  ));
//        //TableWrapper Editable property
//        osrTableWrapper.treeTableView().editableProperty()
//                    .bind(osrEditСheckBox.selectedProperty());
//        setGroupNodeDisableProperty(osrEditСheckBox.selectedProperty(),
//                                    osrAddTextTF,
//                                    osrAddValueTF,
//                                    osrAddItemButton);
//
//       siteQuantityTF.textProperty().bindBidirectional(Quantity.getQuantityProperty(), new NumberStringConverter());
//
//    }
    
     /**
     * Initialization of Variable Tab. 
     */
    private void init_VariableTab(){
        
        //table Context menu property
        variableTableWrapper.tableView().contextMenuProperty().bind(
            Bindings.when(variableEditСheckBox.selectedProperty() )
                .then( ContextMenuFactory.getCommonSU(variableTableWrapper))
                .otherwise( (ContextMenu) null));
        //TableWrapper Editable property
        variableTableWrapper.tableView().editableProperty()
                         .bind(variableEditСheckBox.selectedProperty());
    
        
       
    }
     /**
     * Initialization of Contractor Tab. 
     */
    private void init_ContractorTab(){
        //bing ContextMenu
        contractorTableWrapper.tableView().contextMenuProperty().bind(
                Bindings.when(contractorEditСheckBox.selectedProperty() )
                        .then( ContextMenuFactory.getCommonDSU(contractorTableWrapper))
                        .otherwise( (ContextMenu) null));

        //bind editableProperty to contractorEditСheckBox
        contractorNameTF    .editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorDirectorTF.editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorAdressTA  .editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorCommentsTA.editableProperty().bind(contractorEditСheckBox.selectedProperty());

        //bind disableProperty to contractorEditСheckBox
        contractorAddItemButton.disableProperty().bind(contractorEditСheckBox.selectedProperty().not());

        contractorTableWrapper.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ContractorTIV item = (newValue != null) ? newValue : oldValue;
//            if(newValue != null)item = newValue; else item = oldValue;
            if(newValue != null && oldValue != null){
                Bindings.unbindBidirectional(contractorNameTF    .textProperty(), oldValue.getContractorProperty());
                Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), oldValue.getDirectorProperty());
                Bindings.unbindBidirectional(contractorAdressTA  .textProperty(), oldValue.getAdressProperty());
                Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), oldValue.getCommentsProperty());
            }

            clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);

            if(contractorTableWrapper.getItems().size() > 0 && !contractorTableWrapper.getSelectionModel().isEmpty()){
                Bindings.bindBidirectional(contractorNameTF    .textProperty(), item.getContractorProperty());
                Bindings.bindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                Bindings.bindBidirectional(contractorAdressTA  .textProperty(), item.getAdressProperty());
                Bindings.bindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
            }
        });
//        contractorTableWrapper.getItems().addListener((ListChangeListener.Change<? extends ContractorTIV> c) -> {
//            System.out.println("init_ContractorTab => Changed on " + c);
//                if(c.next()){ 
//                   if(c.wasUpdated())  contractorTableWrapper.getCRUD().addUpdate(contractorTableWrapper.getItems().saveEst(c.getFrom()));
//                   if(c.wasAdded()) contractorTableWrapper.getCRUD().addCreate(c.getAddedSubList());
//                   if(c.wasRemoved()) contractorTableWrapper.getCRUD().addDelete(c.getRemoved());
//                }
//        });


//        variableTableWrapper.editableProperty().bind(variableEditСheckBox.selectedProperty());
//
    }
    
//    private void init_PlanTab(){
//        //table Context menu property
//        planTableWrapper.contextMenuProperty().bind(
//            Bindings.when(planEditСheckBox.selectedProperty() )
//                .then( ContextMenuFactory.getCommonDSU(planTableWrapper))
//                .otherwise( (ContextMenu) null));
//        //TableWrapper Editable property
//        planTableWrapper.editableProperty()
//                     .bind(planEditСheckBox.selectedProperty());
//        //Compute Sum Text fields
//        this.computeSumPlanTextFields();
//        this.computeSumFactTextFields();
//
//        setGroupNodeDisableProperty(planEditСheckBox.selectedProperty(),
//                                    planTypeTF,
//                                    planSmetTF,
//                                    planSaleTF,
//                                    planQuantityTF,
//                                    planAddItemButton);
//
//        planTableWrapper.getItems().addListener((ListChangeListener.Change<? extends PlanTIV> c) -> {
//            System.out.println("Changed on " + c + " - allProperty /// init_PlanTab");
//            if(c.next() &&
//                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
//                    this.computeSumPlanTextFields();
//            }
//        });
//    }
    
/*!******************************************************************************************************************
*                                                                                                     	    HANDLERS
********************************************************************************************************************/

//    @FXML
//    private void handle_osrAddItemButton(ActionEvent event) {
//        Double expenses = new DoubleStringConverter().fromString(osrAddValueTF.getText());
//        Double expensesPerHouse = expenses/Quantity.value();
//        osrTableWrapper.getItems()
//                .add(new OSR_TIV(0,osrAddTextTF.getText(),expenses,expensesPerHouse ));
//
//
//    }
    @FXML
    private void handle_contractorItemButton(ActionEvent event) {  
      if(event.getSource() == contractorAddItemButton){
        setVisibleDisableContractorNodes(true);
        
            ContractorTIV item = contractorTableWrapper.getSelectionModel().getSelectedItem();
                if((item  != null)&& !contractorTableWrapper.getSelectionModel().isEmpty()){
                    Bindings.unbindBidirectional(contractorNameTF    .textProperty(), item.getContractorProperty());
                    Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                    Bindings.unbindBidirectional(contractorAdressTA  .textProperty(), item.getAdressProperty());
                    Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
                }
        clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
      }else if(event.getSource() == contractorSaveItemButton){
          setVisibleDisableContractorNodes(false);
          if(
                  contractorTableWrapper.getItems().add(new ContractorTIV(
                                  0,
                                  contractorNameTF.getText(),
                                  contractorDirectorTF.getText(),
                                  contractorAdressTA.getText(),
                                  contractorCommentsTA.getText()
                          )
                  )
                  )clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
      }
      if(event.getSource() == contractorCencelItemButton){
        setVisibleDisableContractorNodes(false);
        clearGroupOfTextInputControl(
                contractorNameTF,
                contractorDirectorTF,
                contractorAdressTA,
                contractorCommentsTA
        );
      }
    }
//    @FXML
//    private void handle_planAddItemButton(ActionEvent event) {
//        double SmetCost = DecimalFormatter.formatString(planSmetTF.getText());
//        double SaleCost = DecimalFormatter.formatString(planSaleTF.getText());
//        if(
//            planTableWrapper.getItems()
//                .add(new PlanTIV(
//                        0,
//                        new Timestamp(System.currentTimeMillis()),
//                        (planTableWrapper.getItems().stream().max(Comparator.comparing(f -> f.getTypeID())).get().getTypeID() + 1),
//                        planTypeTF.getText(),
//                        Integer.parseInt(planQuantityTF.getText()),
//                        0,
//                        SmetCost,
//                        (double)0,
//                        SaleCost,
//                        (double)0,
//                        (double)0
//                ))
//        )clearGroupOfTextInputControl(planTypeTF,planQuantityTF,planSmetTF,planSaleTF);
//
//
//    }




/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/
//    private void computeSumExpTextFields(){
//        sumExpTF.setText(new DoubleStringConverter().toString(
//                                osrTableWrapper.getItems().stream().mapToDouble(OSR_TIV::getExpenses).sum()));
//        sumExpPerSiteTF.setText(new DoubleStringConverter().toString(
//                                osrTableWrapper.getItems().stream().mapToDouble(OSR_TIV::getExpensesPerHouse).sum()));
//    }
//    private void computeSumPlanTextFields(){
//
//        planSmetSumTF.setText(DecimalFormatter.formatNumber(
//                                    planTableWrapper.getItems().stream().mapToDouble(PlanTIV::getSmetCostSum).sum()));
//        planSaleSumTF.setText(DecimalFormatter.formatNumber(
//                                    planTableWrapper.getItems().stream().mapToDouble(PlanTIV::getSaleCostSum).sum()));
//        planProfitSumTF.setText(DecimalFormatter.formatNumber(
//                                    planTableWrapper.getItems().stream().mapToDouble(PlanTIV::getProfit).sum()));
//
//
//
//    }
//
//    private void computeSumFactTextFields(){
//        factSmetSumTF.setText(DecimalFormatter.formatNumber(
//                                    factTableWrapper.getItems().stream().mapToDouble(PlanTIV::getSmetCostSum).sum()));
//        factSaleSumTF.setText(DecimalFormatter.formatNumber(
//                                    factTableWrapper.getItems().stream().mapToDouble(PlanTIV::getSaleCostSum).sum()));
//        factProfitSumTF.setText(DecimalFormatter.formatNumber(
//                                    factTableWrapper.getItems().stream().mapToDouble(PlanTIV::getProfit).sum()));
//
//
//    }

     /**
     * Bind disable property to group of 
     */
    private void setGroupNodeDisableProperty( BooleanProperty booleanProperty, Node ... nodes){
        for(Node node : nodes)node.disableProperty().bind(Bindings.when(booleanProperty).then(false).otherwise(true));   
    }
    
    /**
     * Clear for of TextInputControl like TextField, TextArea, etc.
     */
    private void clearGroupOfTextInputControl(TextInputControl ... nodes){
        for(TextInputControl node : nodes)node.clear();
    }
    

    private void setVisibleDisableContractorNodes(boolean value){
        contractorAddItemButton     .setVisible(!value);
        contractorSaveItemButton    .setVisible(value);
        contractorCencelItemButton  .setVisible(value);
        contractorEditСheckBox      .setDisable(value);
        contractorTableWrapper      .setDisable(value);
        
    }

    

}
