package report.spring.views;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.Optional;

public class ViewFx<N extends Node, C extends Initializable> extends ControlFx<N, C> {

    public ViewFx(N view, C controller) {
        super(view, controller);
    }

    public Optional<Stage> getStage() {
        if (getView().getScene() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable((Stage) getView().getScene().getWindow());
    }

}
