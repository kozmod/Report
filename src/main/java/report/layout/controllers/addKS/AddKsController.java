package report.layout.controllers.addKS;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.layout.controllers.estimate.new_estimate.KsGridController;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.DocumentType;

import report.models.view.nodesHelpers.InputValidator;


public class AddKsController extends AbstractInitializable {

//    private EstimateController_old showEstController;

    private final static String ALL_COMBO_BOX_ITEM =  "Все";

    @Autowired
    private EstimateService estimateService;

    @FXML
    private Label siteNumLabel, contLabel, errorLabel;
    @FXML
    private ComboBox<String> comboBuildingPart;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField ksNumTextField;
    @FXML
    private DatePicker ksDatePicker;
    @FXML
    private TableView<AbstractEstimateTVI> allJMTable, selectedJMTable;



    private DocumentType documentType;
    private ObservableList<AbstractEstimateTVI> obsAllJM;
    private ObservableList<AbstractEstimateTVI> obsSelectedJM;
    private KsGridController ksController;

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }


    public void setKsController(KsGridController ksController) {
        this.ksController = ksController;
    }

    @Override
    public void initData() {

        obsAllJM = estimateService.getEstimateData().getNotDeletedItems(documentType);
        obsSelectedJM = FXCollections.observableArrayList();
        comboBuildingPart.setItems(getBuildingPartComboList(obsAllJM));
        //set Items of allJMTable
        allJMTable.setItems(obsAllJM);
        selectedJMTable.setItems(obsSelectedJM);

        siteNumLabel.setText(estimateService.getEstimateData().getSiteEntity().getSiteNumber());
        contLabel.setText(estimateService.getEstimateData().getSelectedCounterAgent().getName());
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set data to Labels
        AddKSControllerNodeFactory.decorAddKS(allJMTable);
        AddKSControllerNodeFactory.decorAddKS(selectedJMTable);

        comboBuildingPart.getSelectionModel().selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    if (newValue.equals("Все")) {
                        allJMTable.setItems(obsAllJM);
//                        obsAllJM = FXCollections.observableArrayList(EstimateController_old.Est.Changed.getAllItemsList_Live());
                    }else {
//                        obsAllJM = FXCollections.observableArrayList((List) EstimateController_old.Est.Changed.getTabMap().get(comboBuildingPart.getValue()));
                        allJMTable.setItems(filterByBuildingPart(obsAllJM, newValue));
                    }
                });
    }

    /*!******************************************************************************************************************
     *                                                                                                     HANDLERT
     ********************************************************************************************************************/

    @FXML
    private void handle_addSelectedTableButton(ActionEvent event) {                   //add Selected
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
    private void handle_addNewKSButton(ActionEvent event) {
        if (isInputValid()) {
            final int ksNumber = Integer.parseInt(ksNumTextField.getText());
            final int ksDate = (int) ksDatePicker.getValue().toEpochDay();

            estimateService.insertNewKs(ksNumber,ksDate,obsSelectedJM, documentType.getType());
            ksController.initData();


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

    private ObservableList<String> getBuildingPartComboList(ObservableList<AbstractEstimateTVI> list) {
        final Set<String> buildingPartSet = list.stream()
                .map(AbstractEstimateTVI::getBuildingPart)
                .collect(Collectors.toSet());
        ObservableList<String> tempList =FXCollections.observableArrayList(buildingPartSet);
        tempList.add(0,ALL_COMBO_BOX_ITEM);
        return tempList;
    }

    private ObservableList<AbstractEstimateTVI> filterByBuildingPart(ObservableList<AbstractEstimateTVI> list, String buildingPart) {
        return list.stream()
                .filter(item -> item.getBuildingPart().equals(buildingPart))
                .collect(
                        Collector.of(
                                FXCollections::observableArrayList,
                                ObservableList::add,
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                })
                );

    }
}
