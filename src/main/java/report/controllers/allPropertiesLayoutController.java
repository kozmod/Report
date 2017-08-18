
package report.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
import report.models.coefficient.Quantity;
//import report.models.Formula_test;
import report.view_models.nodes_factories.ContextMenuFactory;
import report.view_models.data_models.DecimalFormatter;
import report.view_models.nodes_factories.TableFactory;
import report.entities.items.contractor.TableItemContractor;
import report.entities.items.osr.TableItemOSR;
import report.entities.items.plan.TableItemPlan;
import report.entities.items.variable.TableItemVariable;
import report.entities.items.plan.ItemPlanDAO;
import report.view_models.nodes.ContextMenuOptional;
import report.view_models.nodes.Table;


public class allPropertiesLayoutController implements Initializable {
    
@FXML private GridPane  osrGP,
                        variableGP,
                        contractorGP,
                        planGP;
@FXML private TextField osrAddTextTF, osrAddValueTF,
                        siteQuantityTF, sumExpTF, sumExpPerSiteTF;
//@FXML private TextField variableAddTextTF,variableAddValueTF;
@FXML private TextField contractorNameTF,contractorDirectorTF;
@FXML private TextField planTypeTF,planSmetTF,planSaleTF, planQuantityTF ;
@FXML private TextField planSmetSumTF,planSaleSumTF, planProfitSumTF;
@FXML private TextField factSmetSumTF,factSaleSumTF, factProfitSumTF;
//@FXML private TextField contractorNameTF,contractorDirectorTF;
@FXML private TextArea  contractorAdressTA,contractorCommentsTA;
@FXML private CheckBox  osrEditСheckBox, variableEditСheckBox, contractorEditСheckBox, planEditСheckBox;
@FXML private Button    osrAddItemButton, planAddItemButton;
@FXML private Button    contractorAddItemButton, contractorSaveItemButton, contractorCencelItemButton;

private final Table<TableItemOSR>        osrTableView         = TableFactory.getOSR();
private final Table<TableItemVariable>   variableTableView    = TableFactory.getVariable();
private final Table<TableItemContractor> contractorTableView  = TableFactory.getContractor();
private final Table<TableItemPlan>       planTableView        = TableFactory.getPlan();
private final Table<TableItemPlan>       factTableView        = TableFactory.getFact();
    
    {
     osrTableView.setTableDataFromBASE();        
        ContextMenuOptional.setTableItemContextMenuListener(osrTableView);
     variableTableView.setTableDataFromBASE();   
        ContextMenuOptional.setTableItemContextMenuListener(variableTableView);
     contractorTableView.setTableDataFromBASE(); 
        ContextMenuOptional.setTableItemContextMenuListener(contractorTableView);
     planTableView.setTableDataFromBASE();       
        ContextMenuOptional.setTableItemContextMenuListener(planTableView);
     factTableView.setTableData(new ItemPlanDAO().getListFact());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add OSR TableView
        osrGP.add(osrTableView, 0,0,3,1);
        GridPane.setMargin(osrTableView, new Insets(5,5,5,5));
        
        //add OSR TableView
        variableGP.add(variableTableView, 0,0,3,1);
        GridPane.setMargin(variableTableView, new Insets(5,5,5,5));
        
        //add OSR TableView
        contractorGP.add(contractorTableView, 0,0,1,8);
        GridPane.setMargin(contractorTableView, new Insets(5,5,5,5));
        
        //add Paln TableView
        planGP.add(factTableView, 0,6,6,1);
        GridPane.setMargin(factTableView, new Insets(5,5,5,5));
        
        //add Paln TableView
        planGP.add(planTableView, 0,1,6,1);
        GridPane.setMargin(planTableView, new Insets(5,5,5,5));
        
        init_OSRTab();
        init_VariableTab();
        init_ContractorTab();
        init_PlanTab();
    }   
    
    //INIT -----------------------------------------------------------------------------------------------
    /**
     * Initialization of OSR Tab. 
     */
    private void init_OSRTab(){
       
       computeSumExpTextFields();
       osrTableView.getItems().addListener((ListChangeListener.Change<? extends TableItemOSR> c) -> {
                System.out.println("Changed on " + c + " report.controllers.allPropertiesLayoutController.init_OSRTab()" );
                if(c.next() && 
                        (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                            computeSumExpTextFields();
                }
        });
       
       //table Context menu property
        osrTableView.contextMenuProperty().bind(
            Bindings.when(osrEditСheckBox.selectedProperty() )
                .then(ContextMenuFactory.getCommonDSU(osrTableView))
                .otherwise( (ContextMenu) null  ));
        //Table Editable property
        osrTableView.editableProperty()
                    .bind(osrEditСheckBox.selectedProperty());
        setGroupNodeDisableProperty(osrEditСheckBox.selectedProperty(),
                                    osrAddTextTF, 
                                    osrAddValueTF,
                                    osrAddItemButton);
//       siteQuantityTF.setText(Formula_test.siteQuantity.getValue().toString());
       siteQuantityTF.textProperty().bindBidirectional(Quantity.getQuantityProperty(), new NumberStringConverter());
//       siteQuantityTF.textProperty().bindBidirectional(Coefficient.getQuantityProperty(), new NumberStringConverter());

    }
    
     /**
     * Initialization of Variable Tab. 
     */
    private void init_VariableTab(){
        
        //table Context menu property
        variableTableView.contextMenuProperty().bind(
            Bindings.when(variableEditСheckBox.selectedProperty() )
                .then( ContextMenuFactory.getCommonSU(variableTableView))
                .otherwise( (ContextMenu) null));
        //Table Editable property
        variableTableView.editableProperty()
                         .bind(variableEditСheckBox.selectedProperty());
    
        
       
    }
     /**
     * Initialization of Contractor Tab. 
     */
    private void init_ContractorTab(){
        
        //bing ContextMenu
        contractorTableView.contextMenuProperty().bind(
            Bindings.when(contractorEditСheckBox.selectedProperty() )
                .then( ContextMenuFactory.getCommonDSU(contractorTableView))
                .otherwise( (ContextMenu) null));
     
        //bind editableProperty to contractorEditСheckBox
        contractorNameTF    .editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorDirectorTF.editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorAdressTA  .editableProperty().bind(contractorEditСheckBox.selectedProperty());
        contractorCommentsTA.editableProperty().bind(contractorEditСheckBox.selectedProperty());

     
        //bind disableProperty to contractorEditСheckBox
        contractorAddItemButton.disableProperty().bind(contractorEditСheckBox.selectedProperty().not());
        

        contractorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TableItemContractor item = (newValue != null) ? newValue : oldValue;
//            if(newValue != null)item = newValue; else item = oldValue;
                if(newValue != null && oldValue != null){
                    Bindings.unbindBidirectional(contractorNameTF    .textProperty(), oldValue.getContractorProperty());
                    Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), oldValue.getDirectorProperty());
                    Bindings.unbindBidirectional(contractorAdressTA  .textProperty(), oldValue.getAdressProperty());
                    Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), oldValue.getCommentsProperty());
                }
                
            clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
            
                if(contractorTableView.getItems().size() > 0 && !contractorTableView.getSelectionModel().isEmpty()){
                    Bindings.bindBidirectional(contractorNameTF    .textProperty(), item.getContractorProperty());
                    Bindings.bindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                    Bindings.bindBidirectional(contractorAdressTA  .textProperty(), item.getAdressProperty());
                    Bindings.bindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
                }
        });
        
        

        
//        
//        contractorTableView.getItems().addListener((ListChangeListener.Change<? extends TableItemContractor> c) -> {
//            System.out.println("init_ContractorTab => Changed on " + c);
//                if(c.next()){ 
//                   if(c.wasUpdated())  contractorTableView.getCRUD().addUpdate(contractorTableView.getItems().get(c.getFrom()));
//                   if(c.wasAdded()) contractorTableView.getCRUD().addCreate(c.getAddedSubList());
//                   if(c.wasRemoved()) contractorTableView.getCRUD().addDelete(c.getRemoved());       
//                }
//        });
        

//        variableTableView.editableProperty().bind(variableEditСheckBox.selectedProperty());
//        
       
    }
    
    private void init_PlanTab(){
        //table Context menu property
        planTableView.contextMenuProperty().bind(
            Bindings.when(planEditСheckBox.selectedProperty() )
                .then( ContextMenuFactory.getCommonDSU(planTableView))
                .otherwise( (ContextMenu) null));
        //Table Editable property
        planTableView.editableProperty()
                     .bind(planEditСheckBox.selectedProperty());
        //Compute Sum Text fields
        this.computeSumPlanTextFields();
        this.computeSumFactTextFields();
        
        setGroupNodeDisableProperty(planEditСheckBox.selectedProperty(),
                                    planTypeTF,
                                    planSmetTF,
                                    planSaleTF,
                                    planQuantityTF,
                                    planAddItemButton);

        planTableView.getItems().addListener((ListChangeListener.Change<? extends TableItemPlan> c) -> {
            System.out.println("Changed on " + c + " - allProperty /// init_PlanTab");
            if(c.next() &&
                    (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                    this.computeSumPlanTextFields();
            }
        });


    }
    
    //Hendlers ----------------------------------------------------------------------------------------
    
    @FXML
    private void handle_osrAddItemButton(ActionEvent event) {  
        Double expenses = DecimalFormatter.stringToDouble(osrAddValueTF.getText());
        Double expensesPerHouse = expenses/Quantity.getQuantityValue();
        osrTableView.getItems()
                .add(new TableItemOSR(0,osrAddTextTF.getText(),expenses,expensesPerHouse ));
        
        
    }

    @FXML
    private void handle_contractorItemButton(ActionEvent event) {  
      if(event.getSource() == contractorAddItemButton){
        setVisiblaDisableContractorNodes(true);
        
            TableItemContractor item = contractorTableView.getSelectionModel().getSelectedItem();
                if((item  != null)&& !contractorTableView.getSelectionModel().isEmpty()){
                    Bindings.unbindBidirectional(contractorNameTF    .textProperty(), item.getContractorProperty());
                    Bindings.unbindBidirectional(contractorDirectorTF.textProperty(), item.getDirectorProperty());
                    Bindings.unbindBidirectional(contractorAdressTA  .textProperty(), item.getAdressProperty());
                    Bindings.unbindBidirectional(contractorCommentsTA.textProperty(), item.getCommentsProperty());
                }
        clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
      }else if(event.getSource() == contractorSaveItemButton){
        setVisiblaDisableContractorNodes(false);
        if(
            contractorTableView.getItems()
                .add(new TableItemContractor(0, 
                                             contractorNameTF.getText(),
                                             contractorDirectorTF.getText(), 
                                             contractorAdressTA.getText(), 
                                             contractorCommentsTA.getText()))
        )clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
      }
      
      if(event.getSource() == contractorCencelItemButton){
        setVisiblaDisableContractorNodes(false);
        clearGroupOfTextInputControl(contractorNameTF,contractorDirectorTF,contractorAdressTA,contractorCommentsTA);
          
      }
      
        
    }
    @FXML
    private void handle_planAddItemButton(ActionEvent event) {
        double SmetCost = DecimalFormatter.stringToDouble(planSmetTF.getText());
        double SaleCost = DecimalFormatter.stringToDouble(planSaleTF.getText());
        if(
            planTableView.getItems()
                .add(new TableItemPlan(
                        0,
                        new Timestamp(System.currentTimeMillis()),
                        (planTableView.getItems().stream().max(Comparator.comparing(f -> f.getTypeID())).get().getTypeID() + 1),
                        planTypeTF.getText(),
                        Integer.parseInt(planQuantityTF.getText()),
                        0,
                        SmetCost,
                        (double)0,
                        SaleCost,
                        (double)0,
                        (double)0
                ))
        )clearGroupOfTextInputControl(planTypeTF,planQuantityTF,planSmetTF,planSaleTF);
        
        
    }

    
    
    
    //Methods ------------------------------------------------------------------------------------------
    private void computeSumExpTextFields(){
        sumExpTF.setText(DecimalFormatter.toString(
                                osrTableView.getItems().stream().mapToDouble(TableItemOSR::getExpenses).sum()));
        sumExpPerSiteTF.setText(DecimalFormatter.toString(
                                osrTableView.getItems().stream().mapToDouble(TableItemOSR::getExpensesPerHouse).sum()));
    }
    private void computeSumPlanTextFields(){

        planSmetSumTF.setText(DecimalFormatter.toString(
                                    planTableView.getItems().stream().mapToDouble(TableItemPlan::getSmetCost).sum()));
        planSaleSumTF.setText(DecimalFormatter.toString(
                                    planTableView.getItems().stream().mapToDouble(TableItemPlan::getSaleCost).sum()));
        planProfitSumTF.setText(DecimalFormatter.toString(
                                    planTableView.getItems().stream().mapToDouble(TableItemPlan::getProfit).sum()));

    }

    private void computeSumFactTextFields(){
        factSmetSumTF.setText(DecimalFormatter.toString(
                                    factTableView.getItems().stream().mapToDouble(TableItemPlan::getSmetCost).sum()));
        factSaleSumTF.setText(DecimalFormatter.toString(
                                    factTableView.getItems().stream().mapToDouble(TableItemPlan::getSaleCost).sum()));
        factProfitSumTF.setText(DecimalFormatter.toString(
                                    factTableView.getItems().stream().mapToDouble(TableItemPlan::getProfit).sum()));

    }

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
    

    private void setVisiblaDisableContractorNodes(boolean v){
        contractorAddItemButton.setVisible(!v);
        contractorSaveItemButton.setVisible(v);
        contractorCencelItemButton.setVisible(v);
        
        contractorEditСheckBox.setDisable(v);
        contractorTableView   .setDisable(v);
        
    }
    
//    public void clearContractorTextfields (){
//        contractorNameTF    .clear();
//        contractorDirectorTF.clear();
//        contractorAdressTA  .clear();
//        contractorCommentsTA.clear();
//    }
//    
    

}
