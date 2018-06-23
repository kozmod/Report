package report.spring.spring.configuration;

import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;
import report.layout.controllers.addEstimateRow.AddEstimateRowController;
import report.layout.controllers.addKS.AddKsController;
import report.layout.controllers.estimate.new_estimate.ChangedEstimateTableController;
import report.layout.controllers.estimate.new_estimate.EstimateTabPaneController;
import report.layout.controllers.estimate.new_estimate.KsGridController;
import report.layout.controllers.estimate.new_estimate.BaseEstimateTableController;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.layout.controllers.estimate.new_estimate.SumLabelGridController;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.KsData;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.ViewFx;

import java.io.IOException;

@Configuration
public class EstimateConfig implements FxConfig {


    public final static String ESTIMATE_TAB_PANE_PATH = "/view/estimate/EstimateTabPane.fxml";
    public final static String ADDITIONAL_ESTIMATE_GRID_PATH = "/view/estimate/AdditionalEstimateGridPane.fxml";
    public final static String KS_GRID_PATH = "/view/estimate/KsGridPane.fxml";
    public final static String ESTIMATE_STACK_PANE_TABLE_PATH = "/view/estimate/TitleStackPaneTable.fxml";
    public final static String SUM_LABLE_GRID_PATH = "/view/estimate/SumLabelGridPane.fxml";
    public final static String KS_ADD_PATH = "/view/estimate/AddKsLayout.fxml";
    public final static String ADD_ESTIMATE_ROW_PATH = "/view/estimate/AddEstimateRowLayout.fxml";
    public final static String ADD_KS_PATH = "/view/estimate/AddKsLayout.fxml";

    private final ApplicationContextProvider context;

    @Autowired
    public EstimateConfig(ApplicationContextProvider context) {
        this.context = context;
    }

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
        return new ViewFx<>(
                new TabPane(),
                new EstimateTabPaneController()
        );
    }

    @Bean
    public EstimateTabPaneController estimateTabPaneController() throws IOException {
        return estimateTabPaneView().getController();
    }

//    @Bean
//    @Description("Convenient way to 'init' -> SumLabelGrid")
//    public BeanFunction<DocumentType, ViewFx<GridPane, SumLabelGridController>> sumLabelGridViewFunction() {
//        return this::sumLabelGridView;
//    }

    @Lazy
    @Bean
    @Description("Grid contains TableView and SumLabel to one of columns")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ViewFx<GridPane, SumLabelGridController> sumLabelGridView() throws IOException {
        return loadView(SUM_LABLE_GRID_PATH, sumLabelGridController());
    }

    @Lazy
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SumLabelGridController sumLabelGridController() {
        return new SumLabelGridController();
    }
//
//    @Bean
//    @Description("Convenient way to 'init' -> SumLabelGrid")
//    public BeanFunction<BuildingPart, ViewFx<StackPane, BaseEstimateTableController>> baseStackPaneTableViewFunction() {
//        return t -> baseStackPaneTableView(t) ;
//    }

    @Bean
    @Description("Base-StackPane Table View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<StackPane, BaseEstimateTableController> baseStackPaneTableView() throws IOException {
        return loadView(
                ESTIMATE_STACK_PANE_TABLE_PATH,
                baseStackPaneTableController()
        );
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public BaseEstimateTableController baseStackPaneTableController() {
        return new BaseEstimateTableController();
    }

    @Bean
    @Description("Base-StackPane Table View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<StackPane, ChangedEstimateTableController> changedStackPaneTableView() throws IOException {
        return loadView(
                ESTIMATE_STACK_PANE_TABLE_PATH,
                changedStackPaneTableController()
        );
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ChangedEstimateTableController changedStackPaneTableController() {
        return new ChangedEstimateTableController();
    }

    @Bean
    @Description("KS View")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, KsGridController> ksView() throws IOException {
        return loadView(
                KS_GRID_PATH,
                ksController()
        );
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public KsGridController ksController() {
        return new KsGridController();
    }


    @Lazy
    @Bean
    @Description("Add Estimate Row View")
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, AddEstimateRowController> addEstimateRowView() throws IOException {
        return loadView(
                ADD_ESTIMATE_ROW_PATH,
                addEstimateRowController()
        );
    }

    @Bean
    public AddEstimateRowController addEstimateRowController() throws IOException {
        return new AddEstimateRowController();
    }

    @Lazy
    @Bean
    @Description("Add Ks View")
//    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, AddKsController> addKsView() throws IOException {
        return loadView(
                ADD_KS_PATH,
                addKsController()
        );
    }

    @Lazy
    @Bean
    public AddKsController addKsController(){
        return new AddKsController();
    }

    //    @Bean(name = "addKsView")
//    public ViewFx<GridPane, AddKsController> addKsView() throws IOException {
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
