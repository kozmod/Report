
package report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import report.controllers.rootLayoutController;
import report.servises.StageCreator;
import report.usege_strings.PathStrings;


public class Report extends Application {

    public  enum ScreenSize {
         width , height; 
         
        private ScreenSize() {  }
        
        public void setValue() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            switch(this){
                case width:  value = screenSize.getWidth(); break;
                case height: value = screenSize.getHeight(); break;
            }
            
        }
        
        public double getValue(){return value;}
        
        private double value;
    }
    
    
    private Stage primaryStage;
    private BorderPane rootlayout;
    private Scene scene;
    private rootLayoutController RLController;
    private StageCreator stageCreator;

//Getter - Setter ==============================================================    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootlayout() {
        return rootlayout;
    }

    public rootLayoutController getRLController() {
        return RLController;
    }
    
// Start =======================================================================    
       
    void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Report.class.getResource(PathStrings.Layout.ROOT));
            rootlayout = (BorderPane)loader.load();
            
            scene = new Scene(rootlayout,ScreenSize.width.getValue() - 100,ScreenSize.height.getValue() - 100);
//            scene = new Scene(rootlayout,1800,900);
            primaryStage.setScene(scene);
            RLController = loader.getController(); //set controller
            RLController.setReportApp(this);  
        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    
    @Override
    public void start(Stage stage){
        ScreenSize.width.setValue();
        ScreenSize.height.setValue();
        
        this.primaryStage = stage;
        this.primaryStage.setTitle("App");
        
        initRootLayout();
        this.primaryStage.sizeToScene();
        primaryStage.show();
        
    }
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
