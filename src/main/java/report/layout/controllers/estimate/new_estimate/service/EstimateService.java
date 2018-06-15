package report.layout.controllers.estimate.new_estimate.service;

import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;
import report.models.counterpaties.EstimateData;

public class EstimateService {

    @Autowired
    private SiteEntityDao siteEntityDao;
    @Autowired
    private EstimateDao estimateDao;
    @Autowired
    private KsDao ksDao;
    @Autowired
    private EstimateData estimateData;



}
