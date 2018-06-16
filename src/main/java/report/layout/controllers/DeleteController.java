
package report.layout.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import report.layout.controllers.root.RootLayoutController;
import report.entities.items.site.SiteCommonDAO;


public class DeleteController implements Initializable {
    private RootLayoutController rootController;
    private String siteNumber, contractorName;

    @FXML
    private Label delLabel;

    @FXML
    private Button delButton, censelButton;

    /********************************************************************************************************************
     *                                                                                                     Getter/Setter
     ********************************************************************************************************************/

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }


    public void setSiteNumber(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }


    /********************************************************************************************************************
     *                                                                                                     PreConstructor
     ********************************************************************************************************************/


    /********************************************************************************************************************
     *                                                                                                              INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initData delete Button -> press "ENTER"
        delButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                delButton.fire();
                ev.consume();
            }
        });
        //initData censel Button Button -> press "ENTER"
        censelButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                censelButton.fire();
                ev.consume();
            }
        });
    }

    public void init_delLabels(String selectedTreeElem, String selectedTreeElemParent) {
        setSiteNumber(selectedTreeElemParent);
        setContractorName(selectedTreeElem);
        delLabel.setText(siteNumber + " из Участка " + contractorName + " ?");

    }

    /********************************************************************************************************************
     *                                                                                                            HANDLERS
     ********************************************************************************************************************/

    @FXML
    private void handle_delButton(ActionEvent event) {

        if (siteNumber != null && contractorName != null)
            new SiteCommonDAO().dellContractor(siteNumber, contractorName);


        rootController.update_TreeView();

        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();

    }

    @FXML
    private void handle_cencelButton(ActionEvent event) {
        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();
    }

}
