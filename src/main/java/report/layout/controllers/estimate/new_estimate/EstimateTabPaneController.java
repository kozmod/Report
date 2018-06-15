package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.layout.controllers.estimate.new_estimate.service.BaseStackTableController;
import report.layout.controllers.estimate.new_estimate.service.EstimateControllerNodeFactory;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.EstimateDocumentType;
import report.models.view.customNodes.newNode.SumVboxModel;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.RootViewFx;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.ResourceBundle;


public class EstimateTabPaneController implements Initializable {

    @Autowired
    private Logger logger;

    @Autowired
    private ViewFx<TabPane, EstimateTabPaneController> estimateTabPaneControllerViewFx;
    @Autowired
    private ViewFx<GridPane, KsGridController> baseKsViewFx, changedKsViewFx;
    @Autowired
    private EstimateService estimateService;
    @Autowired
    private EstimateControllerNodeFactory estimateControllerNodeFactory;
    @Autowired
    private RootViewFx rootViewFx;

    @Autowired
    private EstimateData estimateData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //no need
    }

    public void initTabPane(String siteNumber, CountAgentTVI countAgent) {
        initEstimateDataWrapper(siteNumber, countAgent);
    }

    private void initEstimateDataWrapper(String siteNumber, CountAgentTVI countAgent) {
        estimateData.init(siteNumber, countAgent);
        addTabs();
    }

    private void addTabs() {
        TabPane tabPane = estimateTabPaneControllerViewFx.getView();
        tabPane.getTabs().clear();


        if (estimateData.isDocumentExist(EstimateDocumentType.BASE)) {
            ViewFx<StackPane,BaseStackTableController> v = ApplicationContextProvider.getBean("baseStackPaneTableView");




//            tabPane.getTabs().add(
//                    estimateControllerNodeFactory.newTab("BASE_KS", baseKsViewFx .getView())
//            );
//           if (estimateData.isDocumentExist(EstimateDocumentType.CHANGED)) {
//               SumVboxModel changedTabContent = estimateControllerNodeFactory.newEstimateVboxModel(EstimateDocumentType.CHANGED, estimateData);
//               tabPane.getTabs().add(estimateControllerNodeFactory.newTab("Changed", changedTabContent));
//               tabPane.getTabs().add(
//                       estimateControllerNodeFactory.newTab("Changed_KS", changedKsViewFx.getView())
//               );
//           }
        }
//   if (estimateData.isDocumentExist(EstimateDocumentType.BASE)) {
//            SumVboxModel baseTabContent = estimateControllerNodeFactory.newEstimateVboxModel(EstimateDocumentType.BASE, estimateData);
//            tabPane.getTabs().add(estimateControllerNodeFactory.newTab("Base", baseTabContent));
//
//            tabPane.getTabs().add(
//                    estimateControllerNodeFactory.newTab("BASE_KS", baseKsViewFx .getView())
//            );
//           if (estimateData.isDocumentExist(EstimateDocumentType.CHANGED)) {
//               SumVboxModel changedTabContent = estimateControllerNodeFactory.newEstimateVboxModel(EstimateDocumentType.CHANGED, estimateData);
//               tabPane.getTabs().add(estimateControllerNodeFactory.newTab("Changed", changedTabContent));
//               tabPane.getTabs().add(
//                       estimateControllerNodeFactory.newTab("Changed_KS", changedKsViewFx.getView())
//               );
//           }
//        }
    }


}
