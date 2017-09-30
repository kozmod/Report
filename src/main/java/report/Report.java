/* ---------------------------------------------------------------------------------------------------------------------
Name    : Report
Version : 23.09.2017
Author  : AORA-K
--------------------------------------------------------------------------------------------------------------------- */



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

import report.layoutControllers.root.RootLayoutController;
import report.models_view.StageCreator;
import report.usage_strings.PathStrings;


public class Report extends Application {

/*!******************************************************************************************************************
*                                                                                                               ENUMS
********************************************************************************************************************/
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

/*!******************************************************************************************************************
*                                                                                                              FIELDS
********************************************************************************************************************/

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene scene;
    private RootLayoutController rootController;
    private Object centerController;
    private StageCreator stageCreator;

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public RootLayoutController getRLController() {
        return rootController;
    }

    public <T> T getCenterController() {
        return (T)centerController;
    }

    public <T> void  setCenterController(T controller) {
        centerController = controller;
    }


/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/

//Init ROOT Layout
    private void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Report.class.getResource(PathStrings.Layout.ROOT));
            rootLayout = (BorderPane)loader.load();
            
            scene = new Scene(rootLayout,ScreenSize.width.getValue() - 100,ScreenSize.height.getValue() - 100);
//            scene = new Scene(rootlayout,1800,900);
            primaryStage.setScene(scene);
            rootController = loader.getController(); //set controller
            rootController.setReportApp(this);



        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //init Intro Layout
    public StageCreator initIntroLayout(){
        StageCreator.setReportMain(this);
        StageCreator center = new StageCreator(PathStrings.Layout.INTRO,"").loadIntoRootBorderPaneCenter();
        Object centerController = center.getController();
        return center;
    }


/*!******************************************************************************************************************
*                                                                                                              START
********************************************************************************************************************/
    @Override
    public void start(Stage stage){
        ScreenSize.width.setValue();
        ScreenSize.height.setValue();
        
        this.primaryStage = stage;
        this.primaryStage.setTitle("App");

        //init ROOT
        this.initRootLayout();
        this.primaryStage.sizeToScene();
        primaryStage.show();

        //Init Intro
        this.initIntroLayout();
        
    }

/*!******************************************************************************************************************
*                                                                              public static void main(String[] args)
********************************************************************************************************************/
    public static void main(String[] args) {
        launch(args);
    }
    
}
