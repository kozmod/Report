package report.layout.controllers.root;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.site.SiteDao;
import report.layout.controllers.estimate.EstimateController;
import report.entities.items.site.SiteCommonDAO;
import report.models.printer.PrintEstimate;
import report.models.utils.ConcurrentUtils;
import report.models.view.wrappers.toString.ToStringWrapper;
import report.services.common.CounterAgentWrapper;
import report.usage_strings.SQL;
import report.usage_strings.ServiceStrings;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RootControllerService {

    private static CounterAgentWrapper counterAgentWrapper;

    private RootLayoutController rootLayout;

    public RootControllerService(RootLayoutController root) {
        this.rootLayout = root;
    }

    public ObservableList<Object> getComboQueueValues() {
        return new SiteDao().getDistinct(SQL.Site.QUEUE_BUILDING, ServiceStrings.PERCENT);
    }

    public ObservableList<Object> getComboSiteConditionValues() {
        return new SiteDao().getDistinct(SQL.Site.STATUS_PAYMENT, ServiceStrings.PERCENT);
    }

    public static CounterAgentWrapper initCounterAgentHolder(String siteNumber, CountAgentTVI countAgentTVI) {
        return counterAgentWrapper;
    }

    public static Optional<CounterAgentWrapper> getCounterAgentWrapper() {
        return Optional.of(counterAgentWrapper);
    }

    /**
     * Get TreeView item List contains all elements
     *
     * @return TreeItem<String>
     */
    public TreeItem<ToStringWrapper> getTreeViewList() {
        return new SiteCommonDAO().getTreeObsList(
                ServiceStrings.PERCENT.trim(),
                ServiceStrings.PERCENT.trim(),
                ServiceStrings.PERCENT.trim()
        );
    }

    /**
     * Get TreeView item List contains elements use  parameters : Queue, Payment Status
     *
     * @return TreeItem<String>
     */
    public TreeItem<ToStringWrapper> getTreeViewList(String Queue, String paymentStatus) {
        return new SiteCommonDAO().getTreeObsList(Queue.trim(),
                paymentStatus.trim(),
                ServiceStrings.PERCENT.trim());
    }

    /**
     * Get TreeView item List contains elements use  Site Number and  Queue = Payment Status = %
     *
     * @return TreeItem<String>
     */
    public TreeItem<ToStringWrapper> finTreeViewListWithOneElement(String siteNumber) {
        return new SiteCommonDAO().getTreeObsList(ServiceStrings.PERCENT.trim(),
                ServiceStrings.PERCENT.trim(),
                siteNumber);
    }

    /**
     * Print to XML Base use new Thread
     */
    public void printEstBase(File selectedFile) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->
                new PrintEstimate(EstimateController.Est.Base.getAllItemsList_Live(),
                        selectedFile.toPath()));


        ConcurrentUtils.stop(executor);
    }

    /**
     * Print to XML Change use new Thread
     */
    public void printEstChange(File selectedFile) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->
                new PrintEstimate(EstimateController.Est.Changed.getAllItemsList_Live(),
                        selectedFile.toPath()));
        ConcurrentUtils.stop(executor);
    }
//


}
