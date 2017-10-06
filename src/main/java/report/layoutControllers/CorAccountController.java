
package report.layoutControllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import report.entities.items.account.ItemAccDAO;
import report.layoutControllers.addKS.AddKSControllerTF;
import report.models_view.data_utils.EpochDatePickerConverter;
import report.models.sql.sqlQuery.InsertFileXLSQuery;
import report.models_view.nodes_factories.TableFactory;


public class CorAccountController implements Initializable {


    @FXML
    private TableView accTable;
   
    @FXML
    private DatePicker dateAccFrom, dateAccTo;
    
//INIT==============================================================================================================      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //decore Acc table
        AddKSControllerTF.decorAcc(accTable);
        accTable.setItems(new ItemAccDAO().getList(0,0));

        init_DatePickers();
//        init_AccTable();
    }
    
    void init_DatePickers(){
        dateAccFrom.setConverter(new EpochDatePickerConverter());
        dateAccTo.setConverter  (new EpochDatePickerConverter());
    }
 
    
//    void init_AccTable(){
//        TableColumn dateCol = new TableColumn("Дата");
//        dateCol.setCellValueFactory(new PropertyValueFactory("date"));
//        dateCol.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
//
//
//        TableColumn numCol = new TableColumn(" № ");
//        numCol.setCellValueFactory(new PropertyValueFactory("num"));
////        numCol.setMinWidth(40);
////        numCol.setMaxWidth(40);
//
//        //client
//        TableColumn ClientCol = new TableColumn("Клиент");
//
//        TableColumn ITNCol = new TableColumn("ИНН");
//        ITNCol.setCellValueFactory(new PropertyValueFactory("ITN_Client"));
//
//        TableColumn nameClientCol = new TableColumn("Наименование");
//        nameClientCol.setCellValueFactory(new PropertyValueFactory("name_Client"));
//
//        TableColumn accClientCol = new TableColumn("Счет");
//        accClientCol.setCellValueFactory(new PropertyValueFactory("accNum_Client"));
//
//        ClientCol.getColumns().addAll(ITNCol, nameClientCol, accClientCol);
//
//        //correspondent
//        TableColumn CorCol = new TableColumn("Корреспондент");
//
//        TableColumn BICCOrCol = new TableColumn("BIC");
//        BICCOrCol.setCellValueFactory(new PropertyValueFactory("BIC_Cor"));
//
//        TableColumn accCorCol = new TableColumn("Счет");
//        accCorCol.setCellValueFactory(new PropertyValueFactory("accNum_Cor"));
//
//        TableColumn nameCorCol = new TableColumn("Наименование");
//        nameCorCol.setCellValueFactory(new PropertyValueFactory("name_Cor"));
//
//        CorCol.getColumns().addAll(BICCOrCol, accCorCol, nameCorCol);
//
//        TableColumn VOCol = new TableColumn("ВО");
//        VOCol.setCellValueFactory(new PropertyValueFactory("VO"));
////        VOCol.setMinWidth(40);
////        VOCol.setMaxWidth(40);
//
//
//        TableColumn dascCol = new TableColumn("Содержание");
//        dascCol.setCellValueFactory(new PropertyValueFactory("description"));
//
//        //turnover
//        TableColumn TurnoverCol = new TableColumn("Обороты");
//
//        TableColumn debCol = new TableColumn("Дебет");
//        debCol.setCellValueFactory(new PropertyValueFactory("deb"));
//        debCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//
//        TableColumn<TableItemAcc,Double> credCol = new TableColumn<>("Кредит");
//        credCol.setCellValueFactory(new PropertyValueFactory<>("cred"));
//        credCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//
//        TableColumn<TableItemAcc,Double> outgoingRestCol = new TableColumn<>("Остаток");
//        outgoingRestCol.setCellValueFactory(new PropertyValueFactory<>("outgoingRest"));
//        outgoingRestCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//
//        TurnoverCol.getColumns().addAll(debCol,credCol,outgoingRestCol);
//
//
//        accTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        accTable.getColumns().setAll(dateCol, numCol,ClientCol,CorCol, VOCol,dascCol,TurnoverCol);
//
//        accTable.setItems(new ItemAccDAO().getList(0,0));
//
//    }

    
// HANDLERT=========================================================================================================== 
    @FXML
    private void handle_buttonAccLoad(ActionEvent event) {                   
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if(selectedFile != null){
            new InsertFileXLSQuery().insertRowsFromXls_Account(selectedFile.getPath());
            accTable.setItems(new ItemAccDAO().getList(0,0));
        }else{
            System.out.println("Отмена FILE CHUSER xls/xlsx");
        }
    }
    
    @FXML
    private void handle_OKButton(ActionEvent event) {  
        if (isInputValid()){
            accTable.setItems(new ItemAccDAO().getList(
                                    (int) dateAccFrom.getValue().toEpochDay(),
                                    (int) dateAccTo.getValue().toEpochDay())
                                                        );
        }
    }
    
    
    
//InputValidator========================================================================================================        
    private boolean isInputValid() {
        String errorMessage = "";

            if (dateAccFrom.getValue() == null ){
                errorMessage += "'Дата поиска: с' ";
                dateAccFrom.setEditable(true);
                dateAccFrom.setStyle("-fx-border-color: red;");
                
            } 
            if (dateAccTo.getValue() == null){
                errorMessage += "'Дата поиска: по' ";
                
                dateAccTo.setStyle("-fx-border-color: red;");
            }
            if(dateAccFrom.getValue() != null && dateAccTo.getValue() != null
                    && dateAccFrom.getValue().toEpochDay() > dateAccTo.getValue().toEpochDay()){
                errorMessage += "'Неверный период' ";
                dateAccFrom.setStyle("-fx-border-color: red;");
                
            }
            

//        if(errorMessage.length() >0){
//            errorLabel.setText("Ошибки в полях: " +errorMessage);
//            errorLabel.setVisible(true);
//        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
                    visiblePause.setOnFinished(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            dateAccFrom.setStyle(null);
                            dateAccTo.setStyle(null);
                        }
                    });
             visiblePause.play();

         if (errorMessage.length() == 0) {
            return true;
            } else { 
             
             // Показываем сообщение об ошибке.
            return false;
        }
  }    
    
    
    
    
    
}
