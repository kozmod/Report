package helperClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import report.usage_strings.PathStrings;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class FxTestStage extends Application {
    static FxTestStage thisApplication;
    private static String fxmlFile;
    private static int width;
    private static int height;
    private static String[] args;

    private Stage primaryStage;
    private Object controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        primaryStage.setTitle("TEST Scene");
        if (width == 0 || height == 0)
            primaryStage.setScene(new Scene(root));
        else
            primaryStage.setScene(new Scene(root, width, height));
        this.controller = loader.getController();
        this.primaryStage = primaryStage;
        primaryStage.show();

    }


    public FxTestStage() {
        thisApplication = this;
    }


    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/


    public static FxTestStage applicationInstance() {
        return thisApplication;
    }

    /***************************************************************************
     *                                                                         *
     * Factory Methods                                                         *
     *                                                                         *
     **************************************************************************/

    public static void launch(String fxmlFile) {
        FxTestStage.launch(fxmlFile, 0);

    }

    public static void launch(String fxmlFile, int sleep) {
        FxTestStage.fxmlFile = fxmlFile;
        new Thread(() -> {
            Application.launch();
        }).start();
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***************************************************************************
     *                                                                         *
     * Setters                                                                 *
     *                                                                         *
     **************************************************************************/
    public static void setSize(int width, int height) {
        FxTestStage.width = width;
        FxTestStage.height = height;
    }
}
