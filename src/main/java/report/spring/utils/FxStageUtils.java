package report.spring.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class FxStageUtils {

    public static Stage newStage(Parent sceneNode,String stageTitle){
        Stage stage = new Stage();
        stage.setScene(new Scene(sceneNode));
        stage.setTitle(stageTitle);
        stage.show();
        return stage;
    }

    public static Stage newStage(Parent sceneNode){
        Stage stage = new Stage();
        stage.setScene(new Scene(sceneNode));
        stage.show();
        return stage;
    }
}
