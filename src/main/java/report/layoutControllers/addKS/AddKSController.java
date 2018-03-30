package report.layoutControllers.addKS;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import report.entities.items.Item;
import report.layoutControllers.estimate.EstimateController;
import report.usage_strings.SQL;

import report.layoutControllers.estimate.EstimateController.Est;
import report.models.view.nodesHelpers.InputValidator;
import report.entities.items.KS.KS_DAO;


public class AddKSController implements Initializable {

    private EstimateController showEstController;

    @FXML
    private Label siteNumLabel, contLabel, errorLabel;
    @FXML
    private ComboBox<Object> comboBuildingPart;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField ksNumTextField;
    @FXML
    private DatePicker ksDatePicker;
    @FXML
    private TableView<Item> allJMTable, selectedJMTable;

//    private TableWrapperEST<KS_TIV> allJMTable      = TableFactory.getKS_add(),
//                                  selectedJMTable = TableFactory.getKS_add();

    private ObservableList<Item> obsAllJM, obsSelectedJM;
    private ObservableList<Object> comboBuildingPartList;

    {
        //All Job_Mat list init
        obsAllJM = FXCollections.observableArrayList(Est.Changed.getAllItemsList_Live());

        //Empty Selected Job_Mat list init
        obsSelectedJM = FXCollections.observableArrayList();

        //ComboBox Items List
        comboBuildingPartList = FXCollections.observableArrayList(Est.Changed.getTabMap().keySet());
        comboBuildingPartList.add("Все");
    }

    /*!******************************************************************************************************************
     *                                                                                                     Getter/Setter
     ********************************************************************************************************************/

    public void setShowEstController(EstimateController showEstController) {
        this.showEstController = showEstController;
    }


    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set data to Labels
        siteNumLabel.setText(Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
        contLabel.setText(Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));

        AddKSControllerTF.decorAddKS(allJMTable);
        AddKSControllerTF.decorAddKS(selectedJMTable);
        //set Items of allJMTable
        allJMTable.setItems(obsAllJM);
        selectedJMTable.setItems(obsSelectedJM);

        init_Combo();
    }

    private void init_Combo() {
        //add DATA into ComboBox
        comboBuildingPart.setItems(comboBuildingPartList);

        //add ComboBox Listener
        comboBuildingPart.getSelectionModel().selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    if (newValue.equals("Все"))
                        obsAllJM = FXCollections.observableArrayList(Est.Changed.getAllItemsList_Live());
                    else
                        obsAllJM = FXCollections.observableArrayList((List) Est.Changed.getTabMap().get(comboBuildingPart.getValue()));
                    allJMTable.setItems(obsAllJM);
                });
    }



    /*!******************************************************************************************************************
     *                                                                                                     HANDLERT
     ********************************************************************************************************************/


    @FXML
    private void handle_addSelectedTableButton(ActionEvent event) {                   //add Selected List            
        if (allJMTable.getSelectionModel().getSelectedItem() != null) {
            Item sItem_allJM = allJMTable.getSelectionModel().getSelectedItem();
            obsAllJM.remove(sItem_allJM);
            obsSelectedJM.add(sItem_allJM);
        }

    }

    @FXML
    private void handle_removeSelectedTableButton(ActionEvent event) {                 //remove Selected List
        if (selectedJMTable.getSelectionModel().getSelectedItem() != null) {
            Item sItem = selectedJMTable.getSelectionModel().getSelectedItem();
            obsSelectedJM.remove(sItem);
            obsAllJM.add(sItem);
        }
    }

    @FXML
    private void handle_addNewKSButton(ActionEvent event) {                     //add NEW KS     
        if (isInputValid()) {
            int ksNumber = Integer.parseInt(ksNumTextField.getText());
            int ksDate = (int) ksDatePicker.getValue().toEpochDay();

            new KS_DAO().insertNewKS(ksNumber, ksDate, obsSelectedJM);
            //System.out.println(obsSelectedJM);
            showEstController.update_TapKS();

            Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            appStage.close();
        }
    }

    @FXML
    private void handle_cencelButton(ActionEvent event) {
        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();
    }


    /*!******************************************************************************************************************
     *                                                                                                     InputValidator
     ********************************************************************************************************************/
    private boolean isInputValid() {
        StringBuffer errorString = new StringBuffer();
        if (ksNumTextField.getText().length() == 0 || ksNumTextField.getText() == null) {
            errorString.append("'№ KC' ");
            ksNumTextField.setStyle("-fx-border-color: red;");

        } else if (!InputValidator.isNumericValid(ksNumTextField.getText())) {
            errorString.append("'№ KC' ");

            ksNumTextField.setStyle("-fx-border-color: red;");
        }
        if (ksDatePicker.getValue() == null) {
            errorString.append("'Дата составления КС' ");
            ksDatePicker.setStyle("-fx-border-color: red;");

        }
        if (selectedJMTable.getItems().isEmpty()) {
            errorString.append("'КС пуста' ");
        }

        if (errorString.length() > 0) {
            errorLabel.setText("Ошибки в полях: " + errorString);
            errorLabel.setVisible(true);
        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(e -> {
            ksNumTextField.setStyle(null);
            ksDatePicker.setStyle(null);
            errorLabel.setVisible(false);

        });
        visiblePause.play();


        return errorString.length() == 0;
    }
}


//    private void init_allJMTanle(){
//        TableColumn JMnameColumb = new TableColumn("Название работ или материалов");
//        JMnameColumb.setCellValueFactory(new PropertyValueFactory("firstValue"));
//        
//        TableColumn BindedJobColumn = new TableColumn("Отношение к работам"); 
//        BindedJobColumn.setCellValueFactory(new PropertyValueFactory("secondValue"));
//        
//        
//        allJMTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        allJMTable.getColumns().setAll(JMnameColumb, BindedJobColumn);
//        
//        allJMTable.setItems(obsAllJM);
// 
//    }
//    private void init_selectedJMTanle(){
//        TableColumn JMnameColumb = new TableColumn("Название работ или материалов");
//        JMnameColumb.setCellValueFactory(new PropertyValueFactory("firstValue"));
//        
//        TableColumn BindedJobColumn = new TableColumn("Отношение к работам"); 
//        BindedJobColumn.setCellValueFactory(new PropertyValueFactory("secValue"));
//        
//        
//        selectedJMTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        selectedJMTable.getColumns().setAll(JMnameColumb, BindedJobColumn);
//        
//    }
//    private void init_Table(TableView table){
//        TableColumn JMnameColumb = new TableColumn("Название работ или материалов");
//        JMnameColumb.setCellValueFactory(new PropertyValueFactory("firstValue"));
//        
//        TableColumn BindedJobColumn = new TableColumn("Отношение к работам"); 
//        BindedJobColumn.setCellValueFactory(new PropertyValueFactory("secondValue"));
//        
//        TableColumn BuildingPartColumn = new TableColumn("Часть"); 
//        BuildingPartColumn.setCellValueFactory(new PropertyValueFactory("buildingPart"));
//        
//        
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        table.getColumns().setAll(JMnameColumb, BindedJobColumn, BuildingPartColumn);
//            
//        if(table.equals(allJMTable)) table.setItems(obsAllJM);
//    }
//    