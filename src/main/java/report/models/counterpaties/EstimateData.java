package report.models.counterpaties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KS_TIV;
import report.entities.items.KS.KsDao;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;
import report.entities.items.site.SiteEntityDao;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EstimateData {

    @Autowired
    private SiteEntityDao siteEntityDao;
    @Autowired
    private EstimateDao estimateDao;
    @Autowired
    private KsDao ksDao;

    private SiteWrapper siteWrapper;
    private CountAgentTVI selectedCounterAgent;
    private final EnumMultiMap<EstimateDocumentType, Object, ObservableList<? extends AbstractEstimateTVI>> documents;

    private final Function<EstimateTVI, String> estimateGroupFunction;
    private final Function<KS_TIV, Integer> ksGroupFunction;


    {
        estimateGroupFunction = EstimateTVI::getBuildingPart;
        ksGroupFunction = KS_TIV::getKSNumber;
        documents = new EnumMultiMap<>(EstimateDocumentType.class);
    }


    public void init(String siteNumber, CountAgentTVI counterAgent){
        this.selectedCounterAgent = counterAgent;
        this.siteWrapper = new SiteWrapper(
                siteEntityDao.getDataByBusinessKey(siteNumber, counterAgent.getIdCountConst())
        );
        documents.fullClear();
        putDocuments(
                EstimateDocumentType.BASE,
                estimateDao.getData(siteNumber,  counterAgent,1),
                estimateGroupFunction
        );
        putDocuments(
                EstimateDocumentType.BASE_KS,
                ksDao.getData(siteNumber,  counterAgent,1),
                ksGroupFunction
        );
        putDocuments(
                EstimateDocumentType.CHANGED,
                estimateDao.getData(siteNumber,  counterAgent,2),
                estimateGroupFunction
        );
        putDocuments(
                EstimateDocumentType.CHANGED_KS,
                ksDao.getData(siteNumber,  counterAgent,2),
                ksGroupFunction
        );
    }

    /*!******************************************************************************************************************
     *                                                                                                             METHODS
     ********************************************************************************************************************/

    public EnumMultiMap<EstimateDocumentType, Object, ObservableList<? extends AbstractEstimateTVI>> getDocuments() {
        return documents;
    }

    public boolean isDocumentExist(EstimateDocumentType type) {
        return !documents.get(type).isEmpty();
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractEstimateTVI> void putDocuments(EstimateDocumentType type,
                             List<T> items,
                             Function<T, ? extends Object> groupFunction){
            Map preValueMap = items.stream()
                    .collect(Collectors.groupingBy(groupFunction,
                            Collector.of(
                                    FXCollections::observableArrayList,
                                    ObservableList::add,
                                    (listA, listB) -> {
                                        listA.addAll(listB);
                                        return listA;
                                    })
                    ));
            documents.put(type,preValueMap);
    }

    public ObservableList<? extends AbstractEstimateTVI> getNotDeletedDocument(EstimateDocumentType type,String key){
        return getDocument(type,key,false);
    }

    public ObservableList<? extends AbstractEstimateTVI> getDocument(EstimateDocumentType type, Object key, boolean includeDeleted) {
        if (!includeDeleted) {
            return documents.getByBiKey(type,key)
                    .stream()
                    .filter(item -> item.getDel() != 1)
                    .sorted(Comparator.comparing(AbstractEstimateTVI::getJM_name))
                    .collect(Collector.of(
                            () -> FXCollections.observableArrayList(AbstractEstimateTVI.extractor()),
                            ObservableList::add,
                            (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            }
                    ));
        }
        return documents.getByBiKey(type,key);
    }


    public CountAgentTVI getSelectedCounterAgent() {
        return selectedCounterAgent;
    }

    public SiteEntity getSiteEntity() {
        return siteWrapper.getSiteEntity();
    }



//    public ObservableList getAllItemsList_Live() {
//        return this.estDocuments.values()
//                .stream()
//                .flatMap(Collection::stream).collect(Collector.of(
//                        FXCollections::observableArrayList,
//                        ObservableList::add,
//                        (l1, l2) -> {
//                            l1.addAll(l2);
//                            return l1;
//                        })
//                );
//    }

//    @SuppressWarnings("unchecked")
//    public <T extends AbstractEstimateTVI> AbstractEstimateTVI findEqualsElement(T inpAbstractEstimateTVI) {
//        return (T) this.estDocuments.values()
//                .stream()
//                .flatMap(mapItem -> ((List) mapItem).stream())
//                .filter(item -> ((AbstractEstimateTVI) item).equalsSuperClass(inpAbstractEstimateTVI))
//                .findFirst()
//                .orElse(null);
//    }

    public static class EnumMultiMap<E extends Enum<E>, K, V> {
        private EnumMap<E, Map<K, V>> enumMap;

        public EnumMultiMap(Class<E> enumClass) {
            this.enumMap = new EnumMap<>(enumClass);
        }

        public void put(E enumKey, K key, V value) {
            get(enumKey).put(key, value);
        }
        public void put(E enumKey, Map<K, V> map) {
            enumMap.put(enumKey,map);
        }

        public V getByBiKey(E enumKey, K key) {
            return enumMap.get(enumKey).get(key);
        }

        public Map<K, V> get(E enumKey) {
            return enumMap.computeIfAbsent(enumKey, k -> new HashMap<>());
        }

        public void fullClear(){
            enumMap.clear();
        }

        public void clear(E enumKey){
            enumMap.get(enumKey).clear();
        }
    }

}


