package report.spring.views;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import report.layout.controllers.root.RootLayoutController;

public class RootViewFx extends ViewFx<BorderPane, RootLayoutController> {

    public final double PRIMARY_WINDOW_HEIGHT;
    public final double PRIMARY_WINDOW_WIDTH;

    private ViewFx<?,?> centerView;

    public RootViewFx(ViewFx<BorderPane, RootLayoutController> preView, double screenWidth, double screenHeight) {
        super(preView.getView(), preView.getController());
        this.PRIMARY_WINDOW_WIDTH = screenWidth;
        this.PRIMARY_WINDOW_HEIGHT = screenHeight;
    }

    public <T extends Initializable > T getCenterController() {
        return (T) centerView.getController();
    }

    public void setCenter(final ViewFx<?,?> centerView) {
        getView().setCenter(centerView.getView());
        this.centerView = centerView;
    }

    public boolean centerControllerEquals(Class<? extends Initializable> controllerClass){
        return getCenterController().getClass().equals(controllerClass);
    }

}
