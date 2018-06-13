package report.spring.spring.configuration;

import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;
import report.layout.controllers.estimate.new_estimate.EstimateVboxController;
import report.layout.controllers.estimate.new_estimate.EstimateTabPaneController;
import report.layout.controllers.estimate.new_estimate.KsGridController;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.EstimateDocumentType;
import report.spring.views.ViewFx;

import java.io.IOException;

@Configuration
public class EstimateConfig implements FxConfig {

    public final static String ESTIMATE_TAB_PANE_FXML_PATH = "/view/estimate/EstimateTabPane.fxml";
    //    public final static String ESTIMATE_Tab_FXML_PATH = "/view/estimate/EstimateTab.fxml";
    public final static String ADDITIONAL_ESTIMATE_GRID_FXML_PATH = "/view/estimate/AdditionalEstimateGridPane.fxml";
    public final static String KS_GRID_FXML_PATH = "/view/estimate/KsGridPane.fxml";
    public final static String ESTIMATE_VBOX__FXML_PATH = "/view/estimate/EstimateVbox.fxml";
    public final static String KS_ADD_FXML_PATH = "/view/KSAddLayout.fxml";


    @Bean
    public EstimateData estimateData(){
        return new EstimateData();
    }

    /*!***************************************************************************************************************
     *                                                                                                            View
     **!*************************************************************************************************************/

    @Bean(name = "estimateTabPaneView")
    @Description("Root TabView to Estimate TabPane ")
    public ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneView() throws IOException {
        return loadView(ESTIMATE_TAB_PANE_FXML_PATH);
    }

    @Bean(name = "estimateView")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<VBox, EstimateVboxController> estimateView(@Autowired EstimateVboxController estimateVboxController,
                                                             EstimateDocumentType d) throws IOException {
        System.err.println(d);
        return loadView(ESTIMATE_VBOX__FXML_PATH,estimateVboxController);
    }

//    @Bean(name = "ksView")
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, KsGridController> ksView() throws IOException {
        return loadView(KS_GRID_FXML_PATH);
    }

//    @Bean(name = "addKsView")
//    public ViewFx<GridPane, AddKSController> addKsView() throws IOException {
//        return loadView(KS_ADD_FXML_PATH);
//    }
    /*!***************************************************************************************************************
     *                                                                                                      Controller
     **!*************************************************************************************************************/
    @Bean
    public EstimateTabPaneController estimateTabPaneController() throws IOException {
        return estimateTabPaneView().getController();
    }

    @Bean
    public KsGridController ksController() throws IOException {
        return ksView().getController();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public EstimateVboxController estimateVboxController() throws IOException {
        return new EstimateVboxController();
    }
    /*!***************************************************************************************************************
     *                                                                                                         Service
     **!*************************************************************************************************************/
    @Bean
    public EstimateService estimateService() {
        return new EstimateService();
    }

    @Bean
    public EstimateControllerNodeFactory estimateControllerNodeFactory() {
        return new EstimateControllerNodeFactory();
    }

    /*!***************************************************************************************************************
     *                                                                                                             DAO
     **!*************************************************************************************************************/
    @Bean
    public SiteEntityDao siteEntityDao() {
        return new SiteEntityDao();
    }

    @Bean
    public EstimateDao EstimateDao() {
        return new EstimateDao();
    }

    @Bean
    public KsDao ksDao() {
        return new KsDao();
    }
}
