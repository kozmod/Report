package report.layoutControllers.intro;

import javafx.collections.ObservableList;
import report.entities.items.intro.FinishedSiteDAO;
import report.entities.items.intro.FinishedSiteTIV;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteCommonDAO;

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

    public ObservableList<PreviewTIV> getListIntro() {
        return new SiteCommonDAO().getListIntro();
    }

    public ObservableList<FinishedSiteTIV> getFinishedSiteList() {
        return new FinishedSiteDAO().getList();
    }

}
