package report.services.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KS_DAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateDAO;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;
import report.entities.items.site.SiteEntityDAO;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CounterAgentHolder {

    public enum SelectedCounterAgent {BASE, CHANGED, ADDITIONAL, KS}

    private  final SiteEntity siteEntity;
    private final CountAgentTVI selectedCounterAgen;
    public Map<SelectedCounterAgent, ObservableList<? extends AbstractEstimateTVI>> estDocuments;

    /*!******************************************************************************************************************
     *                                                                                                       CONSTRUCTORS
     ********************************************************************************************************************/

    public CounterAgentHolder(SiteEntity siteEntity, CountAgentTVI selectedCounterAgen) {
        this.selectedCounterAgen = selectedCounterAgen;
        this.siteEntity = siteEntity;
//        this.estDocuments = new EnumMap<>(SelectedCounterAgent.class);
        this.initDocuments();
    }

    public CounterAgentHolder(String siteNumber, CountAgentTVI selectedCounterAgen) {
        this(
                new SiteEntityDAO().getDataByBusinessKey(siteNumber, selectedCounterAgen.getIdCountConst()),
                selectedCounterAgen
        );
    }

    /*!******************************************************************************************************************
     *                                                                                                             METHODS
     ********************************************************************************************************************/
    public void putDocument(SelectedCounterAgent type, ObservableList<AbstractEstimateTVI> listOfFields) {
        estDocuments.put(type, listOfFields);
    }

    public ObservableList<? extends AbstractEstimateTVI> getDocument(SelectedCounterAgent type, boolean isNotDelete) {
        if (isNotDelete) {
            estDocuments.get(type)
                    .stream()
                    .filter(item -> item.getDel() != 1)
                    .sorted(Comparator.comparing(AbstractEstimateTVI::getJM_name))
                    .collect(
                            Collectors.groupingBy(AbstractEstimateTVI::getBuildingPart,
                                    Collector.of(
                                            () -> FXCollections.observableArrayList(EstimateTVI.extractor()),
                                            ObservableList::add,
                                            (l1, l2) -> {
                                                l1.addAll(l2);
                                                return l1;
                                            }
                                    )
                            ));
        }
        return estDocuments.get(type);
    }


    public CountAgentTVI getSelectedCounterAgen() {
        return selectedCounterAgen;
    }

    public SiteEntity getSiteEntity() {
        return siteEntity;
    }

    private void initDocuments() {
        final String siteNumber = siteEntity.getSiteNumber();
        estDocuments.put(
                SelectedCounterAgent.BASE,
                new EstimateDAO().getData(siteNumber, 1, selectedCounterAgen)
        );
        estDocuments.put(
                SelectedCounterAgent.CHANGED,
                new EstimateDAO().getData(siteNumber, 2, selectedCounterAgen)
        );
        estDocuments.put(
                SelectedCounterAgent.ADDITIONAL,
                new EstimateDAO().getData(siteNumber, 3, selectedCounterAgen)
        );
        estDocuments.put(
                SelectedCounterAgent.KS,
                new KS_DAO().getData(siteNumber, selectedCounterAgen)
        );
    }


    public ObservableList getAllItemsList_Live() {
        return this.estDocuments.values()
                .stream()
                .flatMap(Collection::stream).collect(Collector.of(
                        FXCollections::observableArrayList,
                        ObservableList::add,
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        })
                );
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractEstimateTVI> AbstractEstimateTVI findEqualsElement(T inpAbstractEstimateTVI) {
        return (T) this.estDocuments.values()
                .stream()
                .flatMap(mapItem -> ((List) mapItem).stream())
                .filter(item -> ((AbstractEstimateTVI) item).equalsSuperClass(inpAbstractEstimateTVI))
                .findFirst()
                .orElse(null);
    }
}


