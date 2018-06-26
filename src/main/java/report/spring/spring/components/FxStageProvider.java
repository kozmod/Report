package report.spring.spring.components;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import report.spring.views.ViewFx;

@Component
public final class FxStageProvider {

    public Stage newStage(Parent sceneNode, String stageTitle) {
        Stage stage = new Stage();
        stage.setScene(new Scene(sceneNode));
        stage.setTitle(stageTitle);
        return stage;
    }

    public Stage newStage(Parent sceneNode) {
        Stage stage = new Stage();
        stage.setScene(new Scene(sceneNode));
        return stage;
    }

    public Stage newStage(ViewFx<? extends Parent, ?> viewFx) {
        Stage stage = new Stage();
        stage.setScene(new Scene(viewFx.getView()));
        return stage;
    }

    public void showStage(ViewFx<? extends Parent, ?> viewFx) {
        if (viewFx.getStage().isPresent()) {
            viewFx.getStage().get().show();
        } else {
            newStage(viewFx.getView()).show();
        }
    }
}
