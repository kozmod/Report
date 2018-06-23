package report;


import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import report.layout.controllers.root.RootLayoutController;
import report.models.view.nodesHelpers.FxmlStage;
import report.usage_strings.PathStrings;


public class Report extends Application {

    /*!******************************************************************************************************************
     *                                                                                                               ENUMS
     ********************************************************************************************************************/
    public enum ScreenSize {
        width, height;

        private ScreenSize() {
        }

        public void setValue() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            switch (this) {
                case width:
                    value = screenSize.getWidth();
                    break;
                case height:
                    value = screenSize.getHeight();
                    break;
            }

        }

        public double getValue() {
            return value;
        }

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
    private FxmlStage fxmlStage;

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
        return (T) centerController;
    }

    public <T> void setCenterController(T controller) {
        centerController = controller;
    }


    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/

    //Init ROOT Layout
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Report.class.getResource(PathStrings.Layout.ROOT));
            rootLayout = (BorderPane) loader.load();

            scene = new Scene(rootLayout, ScreenSize.width.getValue() - 100, ScreenSize.height.getValue() - 100);
//            scene = new Scene(rootlayout,1800,900);
            primaryStage.setScene(scene);
            rootController = loader.getController(); //set controller
//            rootController.setReportApp(this);


        } catch (IOException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //initData Intro Layout
    public FxmlStage initIntroLayout() {
        FxmlStage.setReportMain(this);
        return new FxmlStage(PathStrings.Layout.INTRO, "").loadIntoRootBorderPaneCenter();
    }


    /*!******************************************************************************************************************
     *                                                                                                              START
     ********************************************************************************************************************/
    @Override
    public void start(Stage stage) {
        ScreenSize.width.setValue();
        ScreenSize.height.setValue();

        this.primaryStage = stage;
        this.primaryStage.setTitle("App");

        //initData ROOT
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
        System.out.println(System.getProperty("user.dir"));
        launch(args);
    }

}

