
package report.models_view.nodes.node_helpers;

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


public class StageCreator extends FXMLLoader{

    private Parent root;
    private String fxmlPath;
    private Stage  stage;
    private String stageName;

    private static Report reportMain;

/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/

    public StageCreator(String fxmlPath){
        this(fxmlPath,"");
    }

    public StageCreator(String fxmlPath, String stageName ){
        this.fxmlPath  = fxmlPath;
        this.stageName = stageName;
    }


/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/
    public Stage getStage() {
        return stage;
    }

    public static Report getReportMain() {
        return reportMain;
    }

    public static void setReportMain(Report reportMain) {
        StageCreator.reportMain = reportMain;
    }

/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/
    public StageCreator loadNewWindow(){
        try {
            setLocation(Report.class.getResource(fxmlPath));
            root = this.load();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(stageName);
             
        } catch (IOException ex) {
            Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    
    public StageCreator loadIntoRootBorderPaneCenter(){

       try {
           BorderPane rootLayout = reportMain.getRootLayout();
           this.setLocation(Report.class.getResource(fxmlPath));
           Node nodeContainer = this.load();
           rootLayout.setCenter(nodeContainer);
           reportMain.setCenterController(this.getController());
           
       } catch (IOException ex) {
           Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
       }
        return this;
   }





}
