package report.layout.controllers.intro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;


import java.net.URL;
import java.util.ResourceBundle;

public class IntroLayoutController implements Initializable {


    @FXML
    TableView infoTV, finishedSiteTV;

    //Service
    private IntroControllerService introService = new IntroControllerService(this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //decorate TableViews
        IntroControllerNodeUtils.decorPreview(infoTV);
        IntroControllerNodeUtils.decorIntroFinishedSite(finishedSiteTV);


    }

    public void updateTables() {
        infoTV.setItems(introService.getListIntro());
        finishedSiteTV.setItems(introService.getFinishedSiteList());
    }
}
