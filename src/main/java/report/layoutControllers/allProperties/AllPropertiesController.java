
package report.layoutControllers.allProperties;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import report.entities.items.contractor.ContractorTIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.commonReq.CountAgentCommonReq;
import report.entities.items.variable.VariableTIV_new;
import report.models.numberStringConverters.dateStringConverters.LocalDayStringConverter;
import report.models.numberStringConverters.numberStringConverters.BigIntegerStringConverter;
import report.models_view.nodes.node_wrappers.ReverseTableWrapper;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.ContextMenuFactory;
import report.models_view.nodes.ContextMenuOptional;


public class AllPropertiesController implements Initializable {
    /*!******************************************************************************************************************
    *                                                                                                         Tab 3 FXML
    ********************************************************************************************************************/
    @FXML
    private TextField commonOgrnTF,  commonInnTF, lowAddressTF,factAddressTF,postAddressTF;
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
    @FXML private TextArea  contractorAdressTA,contractorCommentsTA;
    @FXML private CheckBox  contractorEditСheckBox, planEditСheckBox;
    @FXML private Button    contractorAddItemButton, contractorSaveItemButton, contractorCencelItemButton;


    public ReverseTableWrapper<VariableTIV_new> variableTableWrapper ;
    private TableWrapper<ContractorTIV> contractorTableWrapper;
    private TableWrapper<CountAgentTVI> countAgentTableWrapper;

    /***************************************************************************
     *                                                                         *
     * Initialize                                                              *
     *                                                                         *
     **************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.init_VariableTab();
        this.init_ContractorTab();
        this.init_CounterpatiesTab();
//        this.initData();
    }
    /**
     * Init Data from Base
     */
    public void initData(){
        variableTableWrapper.setDataFromBASE();
        contractorTableWrapper.setDataFromBASE();
        countAgentTableWrapper.setDataFromBASE();
    }
    /***************************************************************************
     *                                                                         *
     * Init Variable                                                           *
     *                                                                         *
     **************************************************************************/
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
    /***************************************************************************
     *                                                                         *
     * Init Contractor                                                         *
     *                                                                         *
     **************************************************************************/
     /**
     * Initialization of Contractor Tab. 
     */
    private void init_ContractorTab(){
        //add Contractors TableView
        contractorTableWrapper = AllPropertiesControllerTF.decorContractor(contractorTable);
        ContextMenuOptional.setTableItemContextMenuListener(contractorTableWrapper);
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
    }
    /***************************************************************************
     *                                                                         *
     * Init 3                                                                  *
     *                                                                         *
     **************************************************************************/
    private void init_CounterpatiesTab(){
        countAgentTableWrapper = AllPropertiesControllerTF.decorCountAgent(countAgentTable);
        ContextMenuOptional.setTableItemContextMenuListener(contractorTableWrapper);
        init_CommonReq();
    }
    private void init_CommonReq(){
        countAgentTableWrapper.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CountAgentCommonReq commonReq = new CountAgentCommonReq(
                    1,
                    false,
                    new BigInteger("12345678901234567890"),
                    100000,
                    new BigInteger("12345678901234567890"),
                    new BigInteger("12345678901234567890"),
                    "lowadress",
                    "factAdress",
                    "postAdress"
                    );
            Bindings.bindBidirectional(commonOgrnTF    .textProperty(), commonReq.OGRNProperty(), new BigIntegerStringConverter());
            Bindings.bindBidirectional(commonInnTF     .textProperty(), commonReq.innProperty(),  new BigIntegerStringConverter());
            Bindings.bindBidirectional(commonDateOgrnDP.valueProperty(),commonReq.dateOGRNProperty());
            Bindings.bindBidirectional(lowAddressTF    .textProperty(), commonReq.addressLowProperty());
            Bindings.bindBidirectional(factAddressTF   .textProperty(), commonReq.addressFactProperty());
            Bindings.bindBidirectional(postAddressTF   .textProperty(), commonReq.addressPostProperty());
            commonReq.addListener(listener  ->{
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa");
            });



        });

    }
    /***************************************************************************
     *                                                                         *
     * HANDLERS                                                                *
     *                                                                         *
     **************************************************************************/
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
