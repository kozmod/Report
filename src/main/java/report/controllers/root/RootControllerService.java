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

    public RootControllerService(rootLayoutController root) {
        this.rootLayout = root;
    }

    public  ObservableList<Object> getComboQueueValues(){
       return  new ItemSiteDAO().getDistinctOfColumn(SQL.Site.QUEUE_BUILDING, ServiceStrings.PERCENT);
    }

    public  ObservableList<Object> getComboSiteConditionValues(){
       return  new ItemSiteDAO().getDistinctOfColumn(SQL.Site.STATUS_PAYMENT,ServiceStrings.PERCENT);
    }

    public  TreeItem<String> getTreeViewList(){
       return  new SiteCommonDAO().getTreeObsList(ServiceStrings.PERCENT.trim(), ServiceStrings.PERCENT.trim());
    }

    public  TreeItem<String> getTreeViewList(String parameterFirst, String parameterSecond){
       return  new SiteCommonDAO().getTreeObsList(parameterFirst.trim(), parameterSecond.trim());
    }


    public  void printEstBase(File selectedFile){
        new PrintEstimate(showEstLayoutController.Est.Base.getAllItemsList_Live(), selectedFile.toPath());
    }
    public  void printEstChange(File selectedFile){
        new PrintEstimate(showEstLayoutController.Est.Changed.getAllItemsList_Live(), selectedFile.toPath());
    }
//





}
