package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.spring.event.EstimateEditEventListener;
import report.spring.views.ViewFx;

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
    private EstimateEditEventListener eventListener;

    @Autowired
    private ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneControllerViewFx;

    @Autowired
    private EstimateService estimateService;

    private Map<EstimateTabsContent, ViewFx<?, ? extends AbstractInitializable>> tabsContent = new EnumMap<>(EstimateTabsContent.class);

    public void putContent(EstimateTabsContent key, ViewFx<?, ? extends AbstractInitializable> content) {
        tabsContent.put(key, content);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //not deed
    }

    public void initTabPane(String siteNumber, CountAgentTVI countAgent) {
        estimateService.init(siteNumber, countAgent);
        addTabs();
        initEventBus();
    }

    private void initEventBus() {
        eventListener.setEventConsumer(event -> {
            final TabPane estimateTabPane = estimateTabPaneControllerViewFx.getView();
            estimateTabPane.getTabs().forEach(tab -> {
                if (event.isEditing() && !tab.equals(estimateTabPane.getSelectionModel().getSelectedItem())) {
                    tab.setDisable(true);
                } else if (!event.isEditing()) {
                    tab.setDisable(false);
                }
            });
        });
    }

    private void addTabs() {
        removeAllTabs();
//        logger.info("All Tab was removed");
//        if(addTabWithContent("Смета",DocumentType.BASE)){
//            addNewKsTab("Базовые КС",DocumentType.BASE);
//            logger.info("Init: BASE and BASE_KS");
//            if(addTabWithContent("Измененная Смета",DocumentType.CHANGED)){
//                addNewKsTab("Измененный КС",DocumentType.CHANGED);
//                logger.info("Init: CHANGED and CHANGED_KS");
//            }
//        }

        logger.info("All Tab was removed");
        if (addTabWithContent(DocumentType.BASE, EstimateTabsContent.BASE)) {
            addTabWithContent(DocumentType.BASE, EstimateTabsContent.BASE_KS);
            logger.info("Init: BASE and BASE_KS");
            if (addTabWithContent(DocumentType.CHANGED, EstimateTabsContent.CHANGED)) {
                addTabWithContent(DocumentType.CHANGED, EstimateTabsContent.CHANGED_KS);
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
//todo
    private Tab newAddTab(String title, Node content) {
        Label button = new Label("+");
        button.setOnMouseClicked(event -> {

        });
        Tab tab = new Tab();
        tab.setContent(button);
        tab.setText(title);
        tab.setContent(content);
        tab.setClosable(false);
        return tab;
    }


    private void removeAllTabs() {
        estimateTabPaneControllerViewFx.getView().getTabs().clear();
    }

    private boolean addTabWithContent(DocumentType documentType, EstimateTabsContent content) {
        if (estimateService.getEstimateData().isContains(documentType)) {
            ViewFx<?, ? extends AbstractInitializable> sumLabelView = tabsContent.get(content);
            sumLabelView.getController().initData();
            return estimateTabPaneControllerViewFx.getView()
                    .getTabs()
                    .add(newTab(
                            content.getTitle(),
                            sumLabelView.getView()
                    ));

        }
        return false;
    }

//    private boolean addNewKsTab(DocumentType documentType, EstimateTabsContent content){
//        if(estimateService.getEstimateData().isContains(documentType)){
//            ViewFx<?, AbstractInitializable> ksGridView = tabsContent.get(content);
//            ksGridView.getController().initData();
//            return estimateTabPaneControllerViewFx.getView()
//                    .getTabs()
//                    .add(newTab(
//                            content.getTitle(),
//                            ksGridView.getView()
//                    ));
//
//        }
//        return false;
//    }

}
