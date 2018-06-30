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
import report.layout.controllers.estimate.new_estimate.AddBaseOrChangeController;
import report.layout.controllers.estimate.new_estimate.ChangedEstimateTableController;
import report.layout.controllers.estimate.new_estimate.EstimateTabPaneController;
import report.layout.controllers.estimate.new_estimate.EstimateTabsContent;
import report.layout.controllers.estimate.new_estimate.KsGridController;
import report.layout.controllers.estimate.new_estimate.BaseEstimateTableController;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.layout.controllers.estimate.new_estimate.SumLabelGridController;
import report.models.counterpaties.BuildingPart;
import report.models.counterpaties.DocumentType;
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
    public final static String ADD_BASE_OR_CHANGED_PATH = "/view/estimate/AddBaseOrChangeGridPane.fxml";

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
        EstimateTabPaneController controller = new EstimateTabPaneController();

        ViewFx<GridPane, KsGridController> baseKsView = ksView();
        baseKsView.getController().setDocumentType(DocumentType.BASE);

        ViewFx<GridPane, KsGridController> changedKsView = ksView();
        changedKsView.getController().setDocumentType(DocumentType.CHANGED);

        controller.putContent(EstimateTabsContent.BASE,baseSumLabelGridView());
        controller.putContent(EstimateTabsContent.BASE_KS,baseKsView);
        controller.putContent(EstimateTabsContent.CHANGED,changedSumLabelGridView());
        controller.putContent(EstimateTabsContent.CHANGED_KS,changedKsView);

        return new ViewFx<>(new TabPane(), controller);
    }

    @Bean
    public EstimateTabPaneController estimateTabPaneController() throws IOException {
        return estimateTabPaneView().getController();
    }

    @Lazy
    @Bean
    @Description("BASE: Grid contains TableView and SumLabel to one of columns")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ViewFx<GridPane, SumLabelGridController> baseSumLabelGridView() throws IOException {
        SumLabelGridController baseController = sumLabelGridController();
        baseController.setDocumentType(DocumentType.BASE);

        ViewFx<StackPane, BaseEstimateTableController> fundament = baseStackPaneTableView();
        ViewFx<StackPane, BaseEstimateTableController> proemi = baseStackPaneTableView();
        ViewFx<StackPane, BaseEstimateTableController> otdelka = baseStackPaneTableView();
        ViewFx<StackPane, BaseEstimateTableController> krowlia = baseStackPaneTableView();
        ViewFx<StackPane, BaseEstimateTableController> steni = baseStackPaneTableView();
        fundament.getController().setBuildingPart(BuildingPart.FUNDAMENT);
        proemi.getController().setBuildingPart(BuildingPart.PROEMI);
        otdelka.getController().setBuildingPart(BuildingPart.OTDELKA);
        krowlia.getController().setBuildingPart(BuildingPart.KROWLIA);
        steni.getController().setBuildingPart(BuildingPart.STENI);

        baseController.addContent(
                fundament,otdelka,krowlia,proemi,steni
        );
        return loadView(SUM_LABLE_GRID_PATH, baseController);
    }

    @Lazy
    @Bean
    @Description("CHANGED: Grid contains TableView and SumLabel to one of columns")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ViewFx<GridPane, SumLabelGridController> changedSumLabelGridView() throws IOException {
        SumLabelGridController changedController = sumLabelGridController();
        changedController.setDocumentType(DocumentType.CHANGED);

        ViewFx<StackPane, ChangedEstimateTableController> fundament = changedStackPaneTableView();
        ViewFx<StackPane, ChangedEstimateTableController> proemi = changedStackPaneTableView();
        ViewFx<StackPane, ChangedEstimateTableController> otdelka = changedStackPaneTableView();
        ViewFx<StackPane, ChangedEstimateTableController> krowlia = changedStackPaneTableView();
        ViewFx<StackPane, ChangedEstimateTableController> steni = changedStackPaneTableView();
        fundament.getController().setBuildingPart(BuildingPart.FUNDAMENT);
        proemi.getController().setBuildingPart(BuildingPart.PROEMI);
        otdelka.getController().setBuildingPart(BuildingPart.OTDELKA);
        krowlia.getController().setBuildingPart(BuildingPart.KROWLIA);
        steni.getController().setBuildingPart(BuildingPart.STENI);

        changedController.addContent(
                fundament,otdelka,krowlia,proemi,steni
        );
        return loadView(SUM_LABLE_GRID_PATH, changedController);
    }

    @Lazy
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SumLabelGridController sumLabelGridController() {
        return new SumLabelGridController();
    }

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
    public KsGridController ksController() throws IOException {
        return  new KsGridController();
    }

//    @Bean
//    @Description("Add Base Or Change View")
//    public ViewFx<GridPane, AddBaseOrChangeController> addBaseOrChangeView() throws IOException {
//        return loadView(
//                ADD_BASE_OR_CHANGED_PATH,
//                addBaseOrChangeController()
//        );
//    }
//
//    @Bean
//    public AddBaseOrChangeController addBaseOrChangeController(){
//        return  new AddBaseOrChangeController();
//    }

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
