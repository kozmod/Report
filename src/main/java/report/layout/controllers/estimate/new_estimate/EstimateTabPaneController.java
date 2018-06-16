package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.DocumentType;
import report.models.counterpaties.KsData;
import report.spring.views.RootViewFx;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.ResourceBundle;

import static report.spring.spring.components.ApplicationContextProvider.getBean;


public class EstimateTabPaneController implements Initializable {

    private static final String ESTIMATE_BEAN = "sumLabelGridView";
    private static final String KS_BEAN = "ksView";

    @Autowired
    private Logger logger;

    @Autowired
    private ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneControllerViewFx;
    @Autowired
    private EstimateService estimateService;
//    @Autowired
//    private ViewFx<GridPane, KsGridController> baseKsViewFx, changedKsViewFx;

//    @Autowired
//    private EstimateControllerNodeFactory estimateControllerNodeFactory;
//    @Autowired
//    private RootViewFx rootViewFx;

//    @Autowired
//    private EstimateData estimateData;
//    @Autowired
//    private KsData ksData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //no need
    }

    public void initTabPane(String siteNumber, CountAgentTVI countAgent) {
        initEstimateDataWrapper(siteNumber, countAgent);
    }

    private void initEstimateDataWrapper(String siteNumber, CountAgentTVI countAgent) {
        estimateService.init(siteNumber, countAgent);
        addTabs();
    }

    private void addTabs() {
//        TabPane tabPane = estimateTabPaneControllerViewFx.getView();
//        tabPane.getTabs().clear();
//        if (estimateService.getEstimateData().isContains(DocumentType.BASE)) {
//            tabPane.getTabs().add(
//                    newTab("Смета",loadEstimateView(DocumentType.BASE))
//            );
//            if (estimateService.getEstimateData().isContains(DocumentType.CHANGED)) {
//                tabPane.getTabs().add(
//                        newTab("Базовые КС",loadKsView(DocumentType.BASE))
//                );
//            }
//        }
        removeAllTabs();
        logger.info("All Tab was removed");
        if(addNewEstimateTab("Смета",DocumentType.BASE)){
            addNewKsTab("Базовые КС",DocumentType.BASE);
            logger.info("Init: BASE and BASE_KS");
            if(addNewEstimateTab("Измененная Смета",DocumentType.CHANGED)){
                addNewKsTab("Измененный КС",DocumentType.CHANGED);
                logger.info("Init: CHANGED and CHANGED_KS");
            }
        }


    }

    private Tab newTab(String title, Node content) {
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(content);
        return tab;
    }

    private void removeAllTabs(){
        estimateTabPaneControllerViewFx.getView().getTabs().clear();
    }

    private boolean addNewEstimateTab(String tabName, DocumentType documentType){
        if(estimateService.getEstimateData().isContains(documentType)){
            ViewFx<GridPane, SumLabelGridController> sumLabelView = getBean(ESTIMATE_BEAN);
            sumLabelView.getController().initData(documentType);
            return estimateTabPaneControllerViewFx.getView()
                    .getTabs()
                    .add(newTab(
                            tabName,
                            sumLabelView.getView()
                    ));

        }
        return false;
    }

    private boolean addNewKsTab(String tabName, DocumentType documentType){
        if(estimateService.getEstimateData().isContains(documentType)){
            ViewFx<GridPane, KsGridController> ksGridView = getBean(KS_BEAN);
            ksGridView.getController().initData(documentType);
            return estimateTabPaneControllerViewFx.getView()
                    .getTabs()
                    .add(newTab(
                            tabName,
                            ksGridView.getView()
                    ));

        }
        return false;
    }

}
