
package report.controllers;

import java.net.URL;
import java.sql.Timestamp;



import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class LogLayoutController implements Initializable {

    @FXML private GridPane gridPane;

    private static TextArea logTextArea = new TextArea(){{
//        this.setWrapText(true);
    }};

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridPane.add(logTextArea, 0,0,2,1);
        gridPane.setMargin(logTextArea, new Insets(5,5,5,5));
    }    
    
    public static TextArea getlogTextArea(){
        return logTextArea;
    }
    
    public static void appendLogViewText(String text){
        logTextArea.appendText("[ "+ new Timestamp(System.currentTimeMillis()) +" ] "+ text +"\n");
    }
}
