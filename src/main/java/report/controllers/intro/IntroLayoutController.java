package report.controllers.intro;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import report.entities.items.intro.ItemIntroDAO;
import report.view_models.nodes_factories.TableViewFxmlDecorator;

import java.net.URL;
import java.util.ResourceBundle;

public class IntroLayoutController implements Initializable {


    @FXML TableView infoTV, finishedSiteTV;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //decorate TableViews
        TableViewFxmlDecorator.decorPreview(infoTV);
        TableViewFxmlDecorator.decorIntroFinishedSite(finishedSiteTV);


    }

    public void updateTables(){
        finishedSiteTV.setItems(new ItemIntroDAO().getList());
    }
}
