package report.controllers.root;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import report.controllers.root.rootLayoutController;
import report.controllers.showEstLayoutController;
import report.entities.items.site.ItemSiteDAO;
import report.entities.items.site.SiteCommonDAO;
import report.models.printer.PrintEstimate;
import report.usege_strings.SQL;
import report.usege_strings.ServiceStrings;

import java.io.File;

public class RootControllerService {

    private rootLayoutController rootLayout;

/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/
    public RootControllerService(rootLayoutController root) {
        this.rootLayout = root;
    }

/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/

    public  ObservableList<Object> getComboQueueValues(){
       return  new ItemSiteDAO().getDistinctOfColumn(SQL.Site.QUEUE_BUILDING, ServiceStrings.PERCENT);
    }

    public  ObservableList<Object> getComboSiteConditionValues(){
       return  new ItemSiteDAO().getDistinctOfColumn(SQL.Site.STATUS_PAYMENT,ServiceStrings.PERCENT);
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


    public  void printEstBase(File selectedFile){
        new PrintEstimate(showEstLayoutController.Est.Base.getAllItemsList_Live(), selectedFile.toPath());
    }
    public  void printEstChange(File selectedFile){
        new PrintEstimate(showEstLayoutController.Est.Changed.getAllItemsList_Live(), selectedFile.toPath());
    }
//





}
