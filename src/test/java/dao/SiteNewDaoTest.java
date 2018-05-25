package dao;

import org.junit.Test;
import report.entities.items.site.SiteEntity;
import report.entities.items.site.SiteEntityDAO;

public class SiteNewDaoTest {
    @Test
    public void shouldGetSiteBySiteNumberAndCountAgentId() {
        SiteEntityDAO dao =  new SiteEntityDAO();

        SiteEntity site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);

    }

    @Test
    public void shouldInsertNewSite() {
        SiteEntityDAO dao =  new SiteEntityDAO();
        SiteEntity site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);
        dao.insert(site);
        site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);

    }
}
