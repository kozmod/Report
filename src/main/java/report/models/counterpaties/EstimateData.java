package report.models.counterpaties;


import javafx.collections.ObservableList;

import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;

import java.util.*;
import java.util.function.Function;

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

}


