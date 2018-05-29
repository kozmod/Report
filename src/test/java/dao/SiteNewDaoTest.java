package dao;

import org.junit.Test;
import report.entities.items.site.SiteEntity;
import report.entities.items.site.SiteEntityDao;

public class SiteNewDaoTest {
    @Test
    public void shouldGetSiteBySiteNumberAndCountAgentId() {
        SiteEntityDao dao =  new SiteEntityDao();

        SiteEntity site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);

    }

    @Test
    public void shouldInsertNewSite() {
        SiteEntityDao dao =  new SiteEntityDao();
        SiteEntity site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);
        dao.insert(site);
        site = dao.getDataByBusinessKey("3.0",1);
        System.out.println(site);

    }
}
