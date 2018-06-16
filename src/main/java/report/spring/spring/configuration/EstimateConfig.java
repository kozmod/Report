package report.spring.spring.configuration;

import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;
import report.layout.controllers.estimate.new_estimate.EstimateTabPaneController;
import report.layout.controllers.estimate.new_estimate.KsGridController;
import report.layout.controllers.estimate.new_estimate.BaseEstimateTableController;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.layout.controllers.estimate.new_estimate.SumLabelGridController;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.KsData;
import report.spring.views.ViewFx;

import java.io.IOException;

import static report.spring.spring.components.ApplicationContextProvider.getBean;

@Configuration
public class EstimateConfig implements FxConfig {


    public final static String ESTIMATE_TAB_PANE_PATH = "/view/estimate/EstimateTabPane.fxml";
    //    public final static String ESTIMATE_Tab_FXML_PATH = "/view/estimate/EstimateTab.fxml";
    public final static String ADDITIONAL_ESTIMATE_GRID_PATH = "/view/estimate/AdditionalEstimateGridPane.fxml";
    public final static String KS_GRID_PATH = "/view/estimate/KsGridPane.fxml";
    public final static String ESTIMATE_VBOX__FXML_PATH = "/view/estimate/EstimateVbox.fxml";
    public final static String BASE_STACK_PANE_TABLE_PATH = "/view/estimate/TitleStackPaneTable.fxml";
    public final static String SUM_LABLE_GRID_PATH = "/view/estimate/SumLabelGridPane.fxml";
    public final static String KS_ADD_FXML_PATH = "/view/KSAddLayout.fxml";


    @Lazy
    @Bean
    public EstimateData estimateData() {
        return new EstimateData();
    }

    @Lazy
    @Bean
    public KsData ksData() {
        return new KsData();
    }


    @Bean(name = "estimateTabPaneView")
    @Description("Root TabView to Estimate TabPane ")
    public ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneView() throws IOException {
        return loadView(ESTIMATE_TAB_PANE_PATH);
    }

    @Bean
    public EstimateTabPaneController estimateTabPaneController() throws IOException {
        return estimateTabPaneView().getController();
    }

    @Lazy
    @Bean
    @Description("Sum LablelGrid Table View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, SumLabelGridController> sumLabelGridView() throws IOException {
        return loadView(
                SUM_LABLE_GRID_PATH,
                getBean(SumLabelGridController.class)
        );
    }

    @Lazy
    @Bean
    @Description("Sum LabelGrid Table View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SumLabelGridController sumLabelGridController() throws IOException {
        return new SumLabelGridController();
    }

    @Bean
    @Description("Base-StackPane Table View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<StackPane, BaseEstimateTableController> baseStackPaneTableView() throws IOException {
        return loadView(
                BASE_STACK_PANE_TABLE_PATH,
                getBean(BaseEstimateTableController.class)
        );
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BaseEstimateTableController baseStackPaneTableController() throws IOException {
        return new BaseEstimateTableController();
    }

    @Bean
    @Description("KS View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, KsGridController> ksView() throws IOException {
        return loadView(KS_GRID_PATH,
                getBean(KsGridController.class)
                );
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public KsGridController ksController(){
        return new KsGridController();
    }


//    @Bean
//    @Description("Changed-StackPane Table View")
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
//    public ViewFx<StackPane, BaseEstimateTableController> changedStackPaneTableView() throws IOException {
//        return loadView(BASE_STACK_PANE_TABLE_PATH, new BaseEstimateTableController());
//    }


    //    @Bean(name = "addKsView")
//    public ViewFx<GridPane, AddKSController> addKsView() throws IOException {
//        return loadView(KS_ADD_FXML_PATH);
//    }
    /*!***************************************************************************************************************
     *                                                                                                      Controller
     **!*************************************************************************************************************/


    //    @Bean
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
//    public EstimateVboxController estimateVboxController() throws IOException {
//        return new EstimateVboxController();
//    }
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
