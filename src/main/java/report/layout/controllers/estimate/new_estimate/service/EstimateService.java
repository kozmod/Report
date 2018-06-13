package report.layout.controllers.estimate.new_estimate.service;

import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.KS.KsDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.site.SiteEntityDao;

public class EstimateService {

    @Autowired
    private SiteEntityDao siteEntityDao;
    @Autowired
    private EstimateDao estimateDao;
    @Autowired
    private KsDao ksDao;

//    public EstimateData getEstimateDataWrapper(String siteNumber, CountAgentTVI counterAgent) {
//        SiteWrapper siteWrapper = getSiteWrapper(siteNumber, counterAgent.getIdCountConst());
//
//        EstimateData estimateData = new EstimateData(siteWrapper, counterAgent);
//        Comparator<AbstractEstimateTVI> estimateComparator = Comparator.comparing(AbstractEstimateTVI::getJM_name);
//        estimateData.putDocuments(
//                EstimateDocumentType.BASE,
//                estimateComparator,
//                estimateDao.getData(siteNumber, 1, counterAgent)
//        );
//        estimateData.putDocuments(
//                EstimateDocumentType.CHANGED,
//                estimateComparator,
//                estimateDao.getData(siteNumber, 2, counterAgent)
//        );
////        estimateData.putDocuments(
////                EstimateDocumentType.BASE_KS,
////                estimateComparator,
////                estimateDao.getData(siteNumber, 2, counterAgent)
////        );
////
//        return estimateData;
//    }
//
//    private SiteWrapper getSiteWrapper(String siteNumber, int counterAgentId) {
//        return new SiteWrapper(
//                siteEntityDao.getDataByBusinessKey(siteNumber, counterAgentId)
//        );
//    }

//    private  ObservableList<? extends AbstractEstimateTVI> getDocuments(EstimateDocumentType estimateDocumentType,String siteNumber, CountAgentTVI selectedCounterAgent) {
//            return   estimateDao.getData(siteNumber, 1, selectedCounterAgent)
//        docs.put(
//                EstimateDocumentType.CHANGED,
//                estimateDao.getData(siteNumber, 2, selectedCounterAgent)
//        );
//
//        docs.put(
//                EstimateDocumentType.BASE_KS,
//                new KsDao().getData(siteNumber, selectedCounterAgent,1)
//        );
//        docs.put(
//                EstimateDocumentType.CHANGED_KS,
//                new KsDao().getData(siteNumber, selectedCounterAgent,2)
//        );
//        return docs;
//    }

}
