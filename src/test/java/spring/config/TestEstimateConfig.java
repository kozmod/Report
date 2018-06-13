package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;
import report.models.counterpaties.EstimateData;
import report.spring.spring.configuration.FxConfig;

@Configuration
public class TestEstimateConfig implements FxConfig {

    @Bean
    public EstimateData estimateData() {
        return new EstimateData();
    }

    @Bean
    public SiteEntityDao siteEntityDao() {
        return new SiteEntityDao();
    }

    @Bean
    public EstimateDao EstimateDao() {
        return new EstimateDao();
    }

    @Bean
    public KsDao ksDao() {
        return new KsDao();
    }
}