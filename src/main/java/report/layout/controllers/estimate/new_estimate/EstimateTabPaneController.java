package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.ViewFx;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;


public class EstimateTabPaneController implements Initializable {

    private static final String ESTIMATE_BASE = "sumLabelGridView";
    private static final String KS_BEAN = "ksView";

    @Autowired
    private Logger logger;
    @Autowired
    private ApplicationContextProvider context;

    @Autowired
    private ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneControllerViewFx;
    @Autowired
    private EstimateService estimateService;

    private Map<EstimateTabsContent,ViewFx<?,? extends Initializable>> tabsContent = new EnumMap<>(EstimateTabsContent.class);

    public void putContent(EstimateTabsContent key,ViewFx<?,? extends Initializable> content){
        tabsContent.put(key,content);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //no need
    }

    public void initTabPane(String siteNumber, CountAgentTVI countAgent) {
        estimateService.init(siteNumber, countAgent);
        addTabs();
    }

    private void addTabs() {
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
        tab.setClosable(false);
        return tab;
    }

    private void removeAllTabs(){
        estimateTabPaneControllerViewFx.getView().getTabs().clear();
    }

    private boolean addNewEstimateTab(String tabName, DocumentType documentType){
        if(estimateService.getEstimateData().isContains(documentType)){
            ViewFx<GridPane, SumLabelGridController> sumLabelView = context.getBean(ESTIMATE_BASE);
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
            ViewFx<GridPane, KsGridController> ksGridView = context.getBean(KS_BEAN);
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
