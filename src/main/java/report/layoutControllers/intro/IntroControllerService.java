package report.layoutControllers.intro;

import javafx.collections.ObservableList;
import report.entities.items.intro.ItemFinishedSiteDAO;
import report.entities.items.intro.TableItemFinishedSite;
import report.entities.items.site.SiteCommonDAO;
import report.entities.items.site.TableItemPreview;

public class IntroControllerService {

    private IntroLayoutController introController;

/*!******************************************************************************************************************
 *                                                                                                       CONSTRUCTORS
********************************************************************************************************************/
    public IntroControllerService(IntroLayoutController intro) {
        this.introController = intro;
    }

/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/

    public ObservableList<TableItemPreview> getListIntro(){
        return  new SiteCommonDAO().getListIntro();
    }

    public ObservableList<TableItemFinishedSite> getFinishedSiteList(){
        return  new ItemFinishedSiteDAO().getList();
    }

}
