package report.layoutControllers.root;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import report.layoutControllers.EstimateController;
import report.entities.items.site.SiteItemDAO;
import report.entities.items.site.SiteCommonDAO;
import report.models.printer.PrintEstimate;
import report.models.utils.ConcurrentUtils;
import report.usage_strings.SQL;
import report.usage_strings.ServiceStrings;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RootControllerService {

    private RootLayoutController rootLayout;

/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/
    public RootControllerService(RootLayoutController root) {
        this.rootLayout = root;
    }

/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/

    public  ObservableList<Object> getComboQueueValues(){
       return  new SiteItemDAO().getDistinctOfColumn(SQL.Site.QUEUE_BUILDING, ServiceStrings.PERCENT);
    }

    public  ObservableList<Object> getComboSiteConditionValues(){
       return  new SiteItemDAO().getDistinctOfColumn(SQL.Site.STATUS_PAYMENT,ServiceStrings.PERCENT);
    }

    /**
     * Get TreeView item List contains all elements
     * @return TreeItem<String>
     */
    public  TreeItem<String> getTreeViewList(){
       return  new SiteCommonDAO().getTreeObsList(ServiceStrings.PERCENT.trim(),
                                                   ServiceStrings.PERCENT.trim(),
                                                   ServiceStrings.PERCENT.trim());
    }

    /**
     * Get TreeView item List contains elements use  parameters : Queue, Payment Status
     * @return TreeItem<String>
     */
    public  TreeItem<String> getTreeViewList(String Queue, String paymentStatus){
       return  new SiteCommonDAO().getTreeObsList(Queue.trim(),
                                                    paymentStatus.trim(),
                                                    ServiceStrings.PERCENT.trim());
    }

    /**
     * Get TreeView item List contains elements use  Site Number and  Queue = Payment Status = %
     * @return TreeItem<String>
     */
    public  TreeItem<String> finTreeViewListWithOneElement(String siteNumber){
       return  new SiteCommonDAO().getTreeObsList(ServiceStrings.PERCENT.trim(),
                                                    ServiceStrings.PERCENT.trim(),
                                                    siteNumber);
    }

    /**
     * Print to XML Base use new Thread
     */
    public  void printEstBase(File selectedFile){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->
                new PrintEstimate(EstimateController.Est.Base.getAllItemsList_Live(),
                        selectedFile.toPath()));


        ConcurrentUtils.stop(executor);
    }

    /**
     * Print to XML Change use new Thread
     */
    public  void printEstChange(File selectedFile){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->
                new PrintEstimate(EstimateController.Est.Changed.getAllItemsList_Live(),
                        selectedFile.toPath()));
        ConcurrentUtils.stop(executor);
    }
//





}
