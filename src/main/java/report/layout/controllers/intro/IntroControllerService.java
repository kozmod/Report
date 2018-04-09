package report.layout.controllers.intro;

import javafx.collections.ObservableList;
import report.entities.items.intro.FinishedSiteDAO;
import report.entities.items.intro.FinishedSiteTIV;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteCommonDAO;

public class IntroControllerService {

    private IntroLayoutController introController;

    public IntroControllerService(IntroLayoutController intro) {
        this.introController = intro;
    }


    public ObservableList<PreviewTIV> getListIntro() {
        return new SiteCommonDAO().getListIntro();
    }

    public ObservableList<FinishedSiteTIV> getFinishedSiteList() {
        return new FinishedSiteDAO().getList();
    }

}
