package report.spring.spring.configuration;


import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import report.layout.controllers.CorAccountController;
import report.layout.controllers.finRes.FinResController;
import report.layout.controllers.intro.IntroLayoutController;
import report.layout.controllers.planning.PlanningController;
import report.layout.controllers.root.RootControllerNodeFactory;
import report.layout.controllers.root.RootControllerService;
import report.layout.controllers.root.RootLayoutController;
import report.spring.views.RootViewFx;
import report.spring.views.ViewFx;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import static report.spring.utils.MathUtils.deductPercent;

@Configuration
@Import({EstimateConfig.class})
@ComponentScan("report.spring.spring.components")
public class RootFxConfig implements FxConfig {

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("javafx-application");
    }

    public final static String ROOT_FXML_PATH = "/view/RootLayout.fxml";
    public final static String INTRO_FXML_PATH = "/view/IntroLayout.fxml";


    @Bean
    public RootViewFx rootView() throws IOException {
        final double WIDTH_PERCENT = 10;
        final double HEIGHT_PERCENT = 10;

        RootViewFx root = new RootViewFx(
                loadView(ROOT_FXML_PATH),
                deductPercent(screenDimension().getWidth(), WIDTH_PERCENT),
                deductPercent(screenDimension().getHeight(), HEIGHT_PERCENT)
        );
        root.setCenter(introView());
        return root;
    }

    @Bean
    public RootLayoutController rootLayoutController() throws IOException {
        return rootView().getController();
    }

    @Bean
    public RootControllerNodeFactory rootControllerNodeService() {
        return new RootControllerNodeFactory();
    }

    @Bean
    public RootControllerService rootControllerService() {
        return new RootControllerService();
    }

    @Lazy
    @Bean(name = "introView")
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, IntroLayoutController> introView() throws IOException {
        return loadView(INTRO_FXML_PATH);
    }

    @Lazy
    @Bean(name = "planingView")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<TabPane, PlanningController> planingView() throws IOException {
        return loadView("/view/PlanningLayout.fxml");
    }

    @Lazy
    @Bean(name = "finResView")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, FinResController> finResView() throws IOException {
        return loadView("/view/FinResLayout.fxml");
    }

    @Lazy
    @Bean(name = "corAccountView")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, CorAccountController>  corAccountView() throws IOException {
        return loadView("/view/CorAccountLayout.fxml");
    }

    @Lazy
    @Bean(name = "screenDimension")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Dimension screenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }


}
