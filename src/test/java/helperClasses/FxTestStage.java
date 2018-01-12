package helperClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import report.usage_strings.PathStrings;

import java.awt.*;

public class FxTestStage extends Application {
    private static Stage primaryStage;
    private  static String fxmlFile;
    private  static int width;
    private  static  int height;




    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        primaryStage.setTitle("TEST Scene");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
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
    public static void launch(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(args.length > 0)
        FxTestStage.launch(
                screenSize.width-200,
                screenSize.height-200,
                args[0],
                args
        );
    }

    public static Stage getS(){
        return primaryStage;
    }
    /**
     * <b>**Factory Method**</b>
     * <br>
     * Launch the application with  "SQUARE" size.
     * @param squareSize int (squareSize size)
     * @param fxmlPath String (Path of FXML File)
     * @param args String[] (args)
     */
    public static void launch(int squareSize, String fxmlPath, String ... args) {
        FxTestStage.launch(squareSize, squareSize,fxmlPath, args);
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
    public static void launch(int width,int height, String fxmlPath, String ... args) {
        FxTestStage.width = width;
        FxTestStage.height = height;
        FxTestStage.fxmlFile = fxmlPath;
        if(args.length > 0)
        Application.launch(args);
    }
}
