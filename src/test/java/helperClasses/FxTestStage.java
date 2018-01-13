package helperClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.usage_strings.PathStrings;

import java.awt.*;
import java.lang.reflect.Field;

public class FxTestStage extends Application {
    private  static String fxmlFile;
    private  static int width;
    private  static  int height;
    private  Stage primaryStage;
    private static  Object controller;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        primaryStage.setTitle("TEST Scene");
        primaryStage.setScene(new Scene(root, width, height));
        this.controller = loader.getController();
        this.primaryStage = primaryStage;
        primaryStage.show();

    }

    public FxTestStage() {
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    public static <T> T controller(){
        return (T) controller;
    }

    /***************************************************************************
     *                                                                         *
     * Reflection's Methods                                                    *
     *                                                                         *
     **************************************************************************/
    public static <T> T getField(String field) throws NoSuchFieldException, IllegalAccessException {
        Field wrapperField = controller.getClass().getDeclaredField(field);
        wrapperField.setAccessible(true);

        return (T)  wrapperField.get(controller);
    }

    /***************************************************************************
     *                                                                         *
     * Factory Methods                                                         *
     *                                                                         *
     **************************************************************************/
    /**
     * <b>**Factory Method**</b>
     * <br>
     * Launch the application with default size.
     */
    public  static  void launchApp(String... args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(args.length > 0)
        FxTestStage.launchApp(
                screenSize.width-200,
                screenSize.height-200,
                args[0],
                args
        );
    }
    /**
     * <b>**Factory Method**</b>
     * <br>
     * Launch the application with  "SQUARE" size.
     * @param squareSize int (squareSize size)
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launchApp(int squareSize, String fxmlPath, String ... args) {
        FxTestStage.launchApp(squareSize, squareSize,fxmlPath, args);

    }
    /**
     * <b>**Factory Method**</b>
     * <br>
     * Launch the application with  set width & height of window.
     * @param width int (width of window)
     * @param height int (height of window)
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launchApp(int width,int height, String fxmlPath, String ... args) {
        FxTestStage.width = width;
        FxTestStage.height = height;
        FxTestStage.fxmlFile = fxmlPath;
        if(args.length > 0)
            Application.launch(args);
    }
}
