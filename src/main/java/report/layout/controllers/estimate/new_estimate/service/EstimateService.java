package report.layout.controllers.estimate.new_estimate.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.entities.items.KS.KsTIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntityDao;
import report.models.counterpaties.DocumentType;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.KsData;
import report.models.counterpaties.SiteWrapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class EstimateService {

    @Autowired
    private SiteEntityDao siteEntityDao;
    @Autowired
    private EstimateDao estimateDao;
    @Autowired
    private KsDao ksDao;
    @Autowired
    private EstimateData estimateData;
    @Autowired
    private KsData ksData;

    public void init(String siteNumber, CountAgentTVI counterAgent) {
        //est
        EnumMap<DocumentType, ObservableList<EstimateTVI>> estimateDocuments = new EnumMap<>(DocumentType.class);
        estimateDocuments.put(DocumentType.BASE, estimateDao.getData(siteNumber, counterAgent, DocumentType.BASE.getType()));
        estimateDocuments.put(DocumentType.CHANGED, estimateDao.getData(siteNumber, counterAgent, DocumentType.CHANGED.getType()));

        SiteWrapper siteWrapper = new SiteWrapper(
                siteEntityDao.getDataByBusinessKey(siteNumber, counterAgent.getIdCountConst())
        );
        estimateData.init(siteWrapper, counterAgent, estimateDocuments);

        //ks
        EnumMap<DocumentType, ObservableList<KsTIV>> ksDocuments = new EnumMap<>(DocumentType.class);
        ksDocuments.put(DocumentType.BASE, ksDao.getData(siteNumber, counterAgent, DocumentType.BASE.getType()));
        ksDocuments.put(DocumentType.CHANGED, ksDao.getData(siteNumber, counterAgent, DocumentType.CHANGED.getType()));

        ksData.init(siteWrapper, counterAgent, ksDocuments);
    }

    public void refresh() {
        init(estimateData.getSiteEntity().getSiteNumber(),
                estimateData.getSelectedCounterAgent()
        );
    }

    public EstimateData getEstimateData() {
        return estimateData;
    }

    public KsData getKsData() {
        return ksData;
    }

    public Map<Integer, ObservableList<KsTIV>> getKsMap(DocumentType type) {
        return ksData.get(type)
                .stream()
                .filter(item -> item.getDel() != 1)
                .collect(Collectors.groupingBy(KsTIV::getKSNumber,
                        Collector.of(
                                () -> FXCollections.observableArrayList(KsTIV.extractor()),
                                ObservableList::add,
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                })
                ));
    }

    public Map<String, ObservableList<EstimateTVI>> getEstimateMap(DocumentType type) {
        return estimateData.get(type)
                .stream()
                .filter(item -> item.getDel() != 1)
                .collect(Collectors.groupingBy(EstimateTVI::getBuildingPart,
                        Collector.of(
                                () -> FXCollections.observableArrayList(EstimateTVI.extractor()),
                                ObservableList::add,
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                })
                ));
    }

    public  Optional<AbstractEstimateTVI> findEqualsEstimateElement(DocumentType documentType, AbstractEstimateTVI inpAbstractEstimateTVI) {
        return estimateData.get(documentType)
                .stream()
                .filter(item -> item.getDel() != 1)
                .filter(item -> item.businessKeyEquals(inpAbstractEstimateTVI))
                .map(AbstractEstimateTVI.class::cast)
                .findFirst();
    }

    public void insertEstimate(Collection<EstimateTVI> insert, Collection<EstimateTVI> delete){
        estimateDao.delete(delete);
        estimateDao.insert(insert);
    }
}
