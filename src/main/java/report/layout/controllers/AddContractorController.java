
package report.layout.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.util.Duration;
import report.entities.items.contractor.ContractorDAO;
import report.layout.controllers.root.RootLayoutController;
import report.entities.items.site.SiteCommonDAO;
import report.usage_strings.SQL;
import report.layout.controllers.estimate.EstimateController.Est;
import report.models.view.nodesHelpers.InputValidator;


public class AddContractorController implements Initializable {

    private RootLayoutController rootController;

    @FXML
    private Label siteNumLabel, typeHomeLabel, queueLabel, errorLabel;

    @FXML
    private ComboBox contComboBox;

    @FXML
    private TextField contTextField;

    @FXML
    private RadioButton contRadioButton_list, contRadioButton_new;

    private TreeView<String> treeViewSite;

    /*!******************************************************************************************************************
     *                                                                                                      Getter/Setter
     ********************************************************************************************************************/

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void setTreeViewSite(TreeView<String> treeViewSite) {
        this.treeViewSite = treeViewSite;
    }


    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_ContComco();
        init_RadioGroups();

    }

    void init_ContComco() {
        contComboBox.setItems(new ContractorDAO().getDistinct(SQL.Contractors.CONTRACTOR));
//        contComboBox.setItems( new CommonQuery().getObsDISTINCT("Site", "Contractor", this));

    }

    public void init_InfLabels(String selectedTreeElemParent) {
        siteNumLabel.setText(Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
        typeHomeLabel.setText(Est.Common.getSiteSecondValue(SQL.Common.TYPE_HOME));
        queueLabel.setText(Est.Common.getSiteSecondValue(SQL.Site.QUEUE_BUILDING));

    }

    void init_RadioGroups() {
        final ToggleGroup contToggleGroup = new ToggleGroup();
        contRadioButton_list.setToggleGroup(contToggleGroup);
        contRadioButton_list.setSelected(true);
        contTextField.setDisable(true);
        contRadioButton_new.setToggleGroup(contToggleGroup);

    }

    /*!******************************************************************************************************************
     *                                                                                                           METHODS
     ********************************************************************************************************************/
    public void addTreeItem(String contractor) {

        TreeItem<String> ContractorLeaf = new TreeItem<String>(contractor);
        treeViewSite.getSelectionModel().getSelectedItem().getParent().getChildren().add(ContractorLeaf);
    }

    /*!******************************************************************************************************************
     *                                                                                                     	    HANDLERS
     ********************************************************************************************************************/
    @FXML
    private void handle_contRadioButtons(ActionEvent event) {                    // Action listener Contactors Radio Buttons
        if (event.getSource() == contRadioButton_list
                && contRadioButton_list.selectedProperty().get() == true) {
            contComboBox.setDisable(false);
            contTextField.setDisable(true);
            contTextField.clear();
        }


        if (event.getSource() == contRadioButton_new
                && contRadioButton_new.selectedProperty().get() == true) {
            contComboBox.setDisable(true);
            contTextField.setDisable(false);
            contTextField.setEditable(true);
        }
    }


    @FXML
    private void handle_CreateButton(ActionEvent event) {                        //Добавить insert
        String InsertSiteVel = null,
                InsertContVal = null,
                InsertTypeVal = rootController.getPreviewTable().getItems().get(3).toString(),
                InsertTypeHomeVal = null,
                InsertQueueVal = null;


        InsertSiteVel = siteNumLabel.getText();
        InsertTypeHomeVal = typeHomeLabel.getText();
        InsertQueueVal = queueLabel.getText();


        if (contRadioButton_list.selectedProperty().get() == true)
            InsertContVal = (String) contComboBox.getValue();

        if (contRadioButton_new.selectedProperty().get() == true)
            InsertContVal = contTextField.getText();

        // проверка полей
        if (isInputValid()) {
//      System.out.println(InsertSiteVel +"  "+ InsertQueueVal + "   "+ InsertContVal +"  "+ InsertTypeHomeVal);
            new SiteCommonDAO().insertSite(InsertSiteVel,
                    InsertQueueVal,
                    InsertTypeVal,
                    InsertTypeHomeVal,
                    1);

            Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
//            rootController.update_TreeView();
            appStage.close();

            this.addTreeItem(InsertContVal);


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
        String errorMessage = "";

        if (contRadioButton_list.selectedProperty().get() == true) {
            if (contComboBox.getValue() == "%" || contComboBox.getValue() == null) {
                errorMessage += "Не выбран Субподрядчик\n";

                contComboBox.setStyle("-fx-border-color: red;");
//              System.err.println("No valid List of Contractors");
            }
        }
        if (contRadioButton_new.selectedProperty().get() == true) {
            if (contTextField.getText() == null || contTextField.getText().length() == 0) {
                errorMessage += "Не указан Субподрядчик\n";

                contTextField.setStyle("-fx-border-color: red;");

            } else if (InputValidator.isStringValid(contTextField.getText()) == false) {
                errorMessage += "Не верно указан Субподрядчик\n";

                contTextField.setStyle("-fx-border-color: red;");
            }
        }
        if (errorMessage.length() > 0) {
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);

        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
//          visiblePause.setOnFinished(event -> errorLabel.setVisible(false));
        visiblePause.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                contComboBox.setStyle(null);
                contTextField.setStyle(null);
                errorLabel.setVisible(false);
            }
        });

        visiblePause.play();

        if (errorMessage.length() == 0) {
            return true;
        } else {

            //Показываем сообщение об ошибке.
            return false;
        }

    }

}
