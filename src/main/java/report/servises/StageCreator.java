
package report.servises;

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

    public StageCreator(String fxmlPath, String stageName ){
        this.fxmlPath  = fxmlPath;
        this.stageName = stageName;
    }

    public StageCreator(String fxmlPath){
        this.fxmlPath  = fxmlPath;
        this.stageName = "";
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
            root = load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(stageName);
             
        } catch (IOException ex) {
            Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    
    public StageCreator loadIntoRootCenter(){

       try {
           BorderPane rootLayout = reportMain.getRootLayout();
           setLocation(Report.class.getResource(fxmlPath));
           
           Node nodeContainer = load();
           rootLayout.setCenter(nodeContainer);
           
       } catch (IOException ex) {
           Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
       }
        return this;
   }
  
    
   
    
}
