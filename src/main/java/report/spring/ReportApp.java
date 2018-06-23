package report.spring;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import report.spring.spring.configuration.RootFxConfig;
import report.spring.views.RootViewFx;

public class ReportApp extends AbstractJavaFxApplication {

    @Value("${ui.title:JavaFX приложение}")
    private String windowTitle;

    @Autowired
    private RootViewFx view;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(windowTitle);
        stage.setScene(
                new Scene(
                        view.getView(),
                        view.PRIMARY_WINDOW_WIDTH,
                        view.PRIMARY_WINDOW_HEIGHT
                )
        );
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(ReportApp.class, args);
    }

    @Override
    protected Class<?>[] configurationsArray() {
        return new Class<?>[]{RootFxConfig.class};
    }


}
