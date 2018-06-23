package report.models.counterpaties;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import report.entities.items.AbstractEstimateTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class EstimateData implements DocumentData<EstimateTVI> {

    private SiteWrapper siteWrapper;
    private CountAgentTVI selectedCounterAgent;
    private EnumMap<DocumentType, ObservableList<EstimateTVI>> estimateDocuments;


    public void init(SiteWrapper siteWrapper, CountAgentTVI counterAgent, EnumMap<DocumentType, ObservableList<EstimateTVI>> map) {
        this.selectedCounterAgent = counterAgent;
        this.siteWrapper = siteWrapper;
        this.estimateDocuments = map;
    }


    @Override
    public void putDocument(DocumentType type, ObservableList<EstimateTVI> documents) {
        estimateDocuments.put(type, documents);
    }

    @Override
    public ObservableList<EstimateTVI> get(DocumentType type) {
        return estimateDocuments.get(type);
    }

    private void clear() {
        estimateDocuments.clear();
    }


    public CountAgentTVI getSelectedCounterAgent() {
        return selectedCounterAgent;
    }

    public SiteEntity getSiteEntity() {
        return siteWrapper.getSiteEntity();
    }

    public boolean isContains(DocumentType type) {
        return estimateDocuments.get(type)
                .stream()
                .anyMatch(e -> e.getDel() != 1);
    }

    public boolean isContains(DocumentType type, String text) {
        return estimateDocuments.get(type)
                .stream()
                .anyMatch(e -> e.getDel() != 1 && e.getBuildingPart().equals(text));
    }

    public ObservableList<EstimateTVI> getEquivalentDeletedItemsList(DocumentType documentType, EstimateTVI selectedEstimateTVI) {
        if (selectedEstimateTVI != null) {
            return estimateDocuments.get(documentType).stream()
                    .filter(i -> i.businessKeyEquals(selectedEstimateTVI))
                    .sorted(
                            Comparator.comparingLong(item -> item.getDateCreate().getTime())
                    )
                    .collect(collectingAndThen(toList(), FXCollections::observableArrayList));
        }
        return FXCollections.observableArrayList();
    }

    public ObservableList getNotDeletedItems(DocumentType documentType) {
        return estimateDocuments.get(documentType)
                .stream()
                .filter(item -> item.getDel() != 1)
                .collect(
                        Collector.of(FXCollections::observableArrayList,
                                ObservableList::add,
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                })
                );
    }

}


