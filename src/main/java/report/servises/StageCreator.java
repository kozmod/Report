
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
    private Report reportApp;
    private Parent root;
    private String fxmlPath;
    private Stage  stage;
    private String stageName;
   
    
    public StageCreator(String fxmlPath, String stageName ){
        this.fxmlPath = fxmlPath;
        this.stageName = stageName;
        loadNew();
    }
    
    public StageCreator(Report reportApp, String fxmlPath){
        this.fxmlPath = fxmlPath;
        this.reportApp = reportApp;
        loadIntoRootCenter();
    }
   
//Getter/Setter====================================================================
    public Stage getStage() {
        return stage;
    }
    
    public void setReportApp(Report reportApp) {                                
        this.reportApp = reportApp;
    }
    
//Met===============================================================================
    private void loadNew(){
        try {
            setLocation(Report.class.getResource(fxmlPath));
            root = load();
            
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(stageName);
             
        } catch (IOException ex) {
            Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void loadIntoRootCenter(){
       try {
           BorderPane rootLayout = reportApp.getRootlayout();
           setLocation(Report.class.getResource(fxmlPath));
           
           Node nodeContainer = load();
           rootLayout.setCenter(nodeContainer);
           
       } catch (IOException ex) {
           Logger.getLogger(StageCreator.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
  
    
   
    
}
