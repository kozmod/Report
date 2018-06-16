package report.layout.controllers.addKS;

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
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.layout.controllers.estimate.EstimateController_old;
import report.usage_strings.SQL;

import report.models.view.nodesHelpers.InputValidator;


public class AddKSController implements Initializable {

    private EstimateController_old showEstController;

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
    private TableView<AbstractEstimateTVI> allJMTable, selectedJMTable;

//    private PriceSumTableWrapper<KsTIV> allJMTable      = TableFactory.getKS_add(),
//                                  selectedJMTable = TableFactory.getKS_add();

    private ObservableList<AbstractEstimateTVI> obsAllJM, obsSelectedJM;
    private ObservableList<Object> comboBuildingPartList;

    {
        //All Job_Mat list initData
        obsAllJM = FXCollections.observableArrayList(EstimateController_old.Est.Changed.getAllItemsList_Live());

        //Empty Selected Job_Mat list initData
        obsSelectedJM = FXCollections.observableArrayList();

        //ComboBox Items List
        comboBuildingPartList = FXCollections.observableArrayList(EstimateController_old.Est.Changed.getTabMap().keySet());
        comboBuildingPartList.add("Все");
    }

    /*!******************************************************************************************************************
     *                                                                                                     Getter/Setter
     ********************************************************************************************************************/

    public void setShowEstController(EstimateController_old showEstController) {
        this.showEstController = showEstController;
    }


    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set data to Labels
        siteNumLabel.setText(EstimateController_old.Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
        contLabel.setText(EstimateController_old.Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));

        AddKSControllerNodeFactory.decorAddKS(allJMTable);
        AddKSControllerNodeFactory.decorAddKS(selectedJMTable);
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
                        obsAllJM = FXCollections.observableArrayList(EstimateController_old.Est.Changed.getAllItemsList_Live());
                    else
                        obsAllJM = FXCollections.observableArrayList((List) EstimateController_old.Est.Changed.getTabMap().get(comboBuildingPart.getValue()));
                    allJMTable.setItems(obsAllJM);
                });
    }



    /*!******************************************************************************************************************
     *                                                                                                     HANDLERT
     ********************************************************************************************************************/


    @FXML
    private void handle_addSelectedTableButton(ActionEvent event) {                   //add Selected List            
        if (allJMTable.getSelectionModel().getSelectedItem() != null) {
            AbstractEstimateTVI sAbstractEstimateTVI_allJM = allJMTable.getSelectionModel().getSelectedItem();
            obsAllJM.remove(sAbstractEstimateTVI_allJM);
            obsSelectedJM.add(sAbstractEstimateTVI_allJM);
        }

    }

    @FXML
    private void handle_removeSelectedTableButton(ActionEvent event) {                 //remove Selected List
        if (selectedJMTable.getSelectionModel().getSelectedItem() != null) {
            AbstractEstimateTVI sAbstractEstimateTVI = selectedJMTable.getSelectionModel().getSelectedItem();
            obsSelectedJM.remove(sAbstractEstimateTVI);
            obsAllJM.add(sAbstractEstimateTVI);
        }
    }

    @FXML
    private void handle_addNewKSButton(ActionEvent event) {                     //add NEW KS     
        if (isInputValid()) {
            int ksNumber = Integer.parseInt(ksNumTextField.getText());
            int ksDate = (int) ksDatePicker.getValue().toEpochDay();

            new KsDao().insertNewKS(ksNumber, ksDate, obsSelectedJM);
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