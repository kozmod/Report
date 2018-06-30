package dao;

import org.junit.Test;
import report.entities.items.site.SiteEntity;
import report.entities.items.site.SiteEntityDao;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SiteNewDaoTest {
    @Test
    public void shouldGetSiteBySiteNumberAndCountAgentId() {
        SiteEntityDao dao = new SiteEntityDao();

        SiteEntity site = dao.getDataByBusinessKey("3.0", 1);
        System.out.println(site);

    }

    @Test
    public void shouldInsertNewSite() {
        SiteEntityDao dao = new SiteEntityDao();
        SiteEntity site = dao.getDataByBusinessKey("3.0", 1);
        System.out.println(site);
        dao.insert(site);
        site = dao.getDataByBusinessKey("3.0", 1);
        System.out.println(site);

    }

    @Test
    public void sss() {
        final long iterateLimit = 20;
        final String sequence = "YYR";
        final String suffix = "YY";
        final String yyrString = Stream.generate(() ->sequence)
                .limit(iterateLimit)
                .collect(Collectors.joining())
                .concat(suffix);
        System.out.println(yyrString);

    }
}
