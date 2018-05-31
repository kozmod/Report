
package report.models.view.nodesHelpers;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import report.Report;


public class FxmlStage extends FXMLLoader {

    private static Report reportMain;

    private Stage stage;
    private String stageName;
    private String fxmlFilePath;


    /*!******************************************************************************************************************
     *                                                                                                       CONSTRUCTORS
     ********************************************************************************************************************/


    public FxmlStage(String fxmlFilePath) {
        this(fxmlFilePath, "");
    }

    public FxmlStage(String fxmlFilePath, String stageName) {
        this.fxmlFilePath = fxmlFilePath;
        this.stageName = stageName;
    }


    /*!******************************************************************************************************************
     *                                                                                                             METHODS
     ********************************************************************************************************************/

    public Stage getStage() {
        return stage;
    }

    public static void setReportMain(Report reportMain) {
        FxmlStage.reportMain = reportMain;
    }

    public FxmlStage loadAndShowNewWindow() {
        loadNewWindow();
        stage.show();
        return this;
    }

    public FxmlStage loadNewWindow() {
        try {
            setLocation(Report.class.getResource(fxmlFilePath));
            Parent root = this.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(stageName);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FxmlStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public FxmlStage loadIntoRootBorderPaneCenter() {

        try {
            BorderPane rootLayout = reportMain.getRootLayout();
            this.setLocation(Report.class.getResource(fxmlFilePath));
            Node nodeContainer = this.load();
            rootLayout.setCenter(nodeContainer);
            reportMain.setCenterController(this.getController());

        } catch (IOException ex) {
            Logger.getLogger(FxmlStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }


}
