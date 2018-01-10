package helperClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxStageCreator extends Application {
    private  static String fxmlFile = null;
    private static int width , height;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("TEST");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }

    /***************************************************************************
     *                                                                         *
     * Factory Methods                                                         *
     *                                                                         *
     **************************************************************************/
    /**
     * <b>Factory Methods</b>
     * <br>
     * Launch the application with default size.
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launch(String fxmlPath, String ... args) {
        FxStageCreator.launch(500, 400,fxmlPath, args);
    }

    /**
     * <b>Factory Methods</b>
     * <br>
     * Launch the application with  "SQUARE" size.
     * @param squareSize int (squareSize size)
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launch(int squareSize, String fxmlPath, String ... args) {
        FxStageCreator.launch(squareSize, squareSize,fxmlPath, args);
    }
    /**
     * <b>Factory Methods</b>
     * <br>
     * Launch the application with  set width & height of window.
     * @param width int (width of window)
     * @param height int (height of window)
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launch(int width,int height, String fxmlPath, String ... args) {
        FxStageCreator.width = width;
        FxStageCreator.height = height;
        FxStageCreator.fxmlFile = fxmlPath;
        Application.launch(args);
    }
}
