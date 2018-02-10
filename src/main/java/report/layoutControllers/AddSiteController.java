
package report.layoutControllers;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toCollection;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;
import report.entities.items.plan.FactTIV;
import report.entities.items.plan.PlanDAO;
import report.entities.items.plan.PlanTIV;
import report.entities.items.site.SiteDAO;
import report.layoutControllers.root.RootLayoutController;
import report.usage_strings.SQL;
import report.entities.items.site.SiteCommonDAO;
import report.usage_strings.ServiceStrings;
import report.models.view.nodesHelpers.InputValidator;



public class AddSiteController implements Initializable {
    
    private RootLayoutController rootController;

    @FXML private ComboBox<Object>    queueComboBox, classComboBox, contractorComboBox, typeHomeComboBox;
    @FXML private TextField   siteNumTF, queueTF, planTF, factTF;
    @FXML private RadioButton queueRB_list, queueRB_new;

    final private ToggleGroup radioButtonsTG = new ToggleGroup();

    private ObservableList<PlanTIV> listPlan;
    private ObservableList<FactTIV> listFact;
    private ObservableList<Object>        listQueue;
    private ObservableList<Object>        listContractors;
    private ObservableList<Object>        listTypes;

    {
        listPlan  = new PlanDAO().getData();
        listFact  = new PlanDAO().getListFact();
        listQueue = new SiteDAO().getDistinct(SQL.Site.QUEUE_BUILDING);
        listContractors = new SiteDAO().getDistinct(SQL.Site.CONTRACTOR,ServiceStrings.Line);
        listTypes       = new SiteDAO().getDistinct(SQL.Site.TYPE_HOME, ServiceStrings.Line);


    }
                      
/*!******************************************************************************************************************
*                                                                                                     Getter/Setter
********************************************************************************************************************/

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }
    
    public TextField getQueueTextField() {                                      
        return queueTF;
    }

    
/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init Toggle group
        queueRB_list.setToggleGroup(radioButtonsTG);
        queueRB_new.setToggleGroup(radioButtonsTG);
        queueRB_list.setSelected(true);

        //Bing Disable properties RadioButton / TextField/ ComboBox
        queueComboBox.disableProperty().bind(queueRB_list.selectedProperty().not());
        queueTF.disableProperty().bind(queueRB_new.selectedProperty().not());

        //set def quantity TextFields
        planTF.setText(" - ");
        factTF.setText(" - ");

        //init ComboBoxesValue
        init_ComboBoxesValue();
    }  
    
    private void init_ComboBoxesValue(){
        queueComboBox.setItems(listQueue);
//        queueComboBox.setItems( new CommonQuery().getObsDISTINCT(SQL.Tables.SITE, SQL.Site.QUEUE_BUILDING, this));
        classComboBox.setItems(
                listFact.stream()
                .map(FactTIV::getType)
                .collect(toCollection(FXCollections::observableArrayList)));
        
        classComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (options, oldValue, newValue) -> {
           PlanTIV plan = listPlan.stream()
                   .filter(o -> o.getType().equals(newValue))
                   .findFirst()
                   .orElse(null);
           FactTIV fact = listFact.stream()
                   .filter(o -> o.getType().equals(newValue))
                   .findFirst()
                   .orElse(null);
           if(plan != null)
               planTF.setText(plan.getQuantity().toString());
           else planTF.setText(" - ");
           if(fact != null)
               factTF.setText(fact.getQuantity().toString());
           else factTF.setText(" - ");

            
        });
        contractorComboBox.setItems(listContractors);
        contractorComboBox.getSelectionModel().select(ServiceStrings.Line);

        typeHomeComboBox.setItems(listTypes);
        typeHomeComboBox.getSelectionModel().select(ServiceStrings.Line);
    }

/*!******************************************************************************************************************
*                                                                                                     HANDLERS
********************************************************************************************************************/

   @FXML
   private void handle_CreateButton(ActionEvent event){

       if(isInputValid()){
           String insertQueueName = null;
            if(radioButtonsTG.selectedToggleProperty().get().equals(queueRB_list))
                insertQueueName = (String) queueComboBox.getValue();
            else
                if(radioButtonsTG.selectedToggleProperty().get().equals(queueRB_new))
                insertQueueName = queueTF.getText();

//       System.out.println(InsertSiteVel +"  "+ InsertQueueVal + "   "+ InsertContVal +"  "+ InsertTypeHomeVal);
       new SiteCommonDAO().insertSite(
               siteNumTF.getText(),
               insertQueueName,
               classComboBox.getValue().toString(),
               typeHomeComboBox.getValue().toString(),
               contractorComboBox.getValue().toString()
       );

       Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
       rootController.update_TreeView();
       appStage.close();
       }
   }
   
   @FXML
   private void handle_censelButton(ActionEvent event){
       Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
       appStage.close();
       
       
   }

/*!******************************************************************************************************************
*                                                                                                     InputValidator
********************************************************************************************************************/
  private boolean isInputValid() {
       StringBuffer errorString = new StringBuffer();

      if (siteNumTF.getText().length() == 0 || siteNumTF.getText() == null ){
          errorString.append("'№ Участка' ");
          siteNumTF.setStyle("-fx-border-color: red;");
                
      } else if(InputValidator.isStringValid(siteNumTF.getText()) == false){
          errorString.append("'№ Участка' ");
          siteNumTF.setStyle("-fx-border-color: red;");
      }
      if (classComboBox.getSelectionModel().getSelectedItem() == null){
          errorString.append("'Класс' ");
          classComboBox.setStyle("-fx-border-color: red;");
      }

      if(radioButtonsTG.selectedToggleProperty().get().equals(queueRB_list)){
          if(queueComboBox.getValue() == "%"
                  || queueComboBox.getValue() == null){
              errorString.append("'Очередь' ");
              queueComboBox.setStyle("-fx-border-color: red;");
          }
      }else
      if(radioButtonsTG.selectedToggleProperty().get().equals(queueRB_new)){
          if(queueTF.getText() == null
                  || queueTF.getText().length() == 0){
              errorString.append("'Очередь' ");
              queueTF.setStyle("-fx-border-color: red;");
          } else
          if(InputValidator.isNumericValid(queueTF.getText()) == false){
              errorString.append("'Очередь' ");
              queueTF.setStyle("-fx-border-color: red;");
          }
      }

//        if(errorMessage.length() >0){
////            errorLabel.setText("Ошибки в полях:\n" +errorMessage);
////            errorLabel.setVisible(true);
////            
//        }

      PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
      visiblePause.setOnFinished(e -> {
                            siteNumTF.setStyle(null);
                            queueComboBox.setStyle(null);
                            queueTF.setStyle(null);
                            classComboBox.setStyle(null);

                    });
      visiblePause.play();

      return errorString.length() == 0 ? true : false;
  }

    
}

