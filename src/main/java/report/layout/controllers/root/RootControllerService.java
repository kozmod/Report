package report.layout.controllers.root;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.site.SiteDAO;
import report.layout.controllers.estimate.EstimateController;
import report.entities.items.site.SiteCommonDAO;
import report.models.printer.PrintEstimate;
import report.models.utils.ConcurrentUtils;
import report.models.view.wrappers.toString.ToStringWrapper;
import report.services.common.CounterAgentHolder;
import report.usage_strings.SQL;
import report.usage_strings.ServiceStrings;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RootControllerService {

    private static CounterAgentHolder counterAgentHolder;

    private RootLayoutController rootLayout;

    public RootControllerService(RootLayoutController root) {
        this.rootLayout = root;
    }

    public ObservableList<Object> getComboQueueValues() {
        return new SiteDAO().getDistinct(SQL.Site.QUEUE_BUILDING, ServiceStrings.PERCENT);
    }

    public ObservableList<Object> getComboSiteConditionValues() {
        return new SiteDAO().getDistinct(SQL.Site.STATUS_PAYMENT, ServiceStrings.PERCENT);
    }

    public static CounterAgentHolder initCounterAgentHolder(String siteNumber, CountAgentTVI countAgentTVI) {
        return counterAgentHolder;
    }

    public static Optional<CounterAgentHolder> getCounterAgentHolder() {
        return Optional.of(counterAgentHolder);
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
