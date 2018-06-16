package report.models.counterpaties;


import javafx.collections.ObservableList;
import report.entities.items.KS.KsTIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;

import java.util.EnumMap;
import java.util.function.Function;


public class KsData implements DocumentData<KsTIV> {


    private  SiteWrapper siteWrapper;
    private  CountAgentTVI selectedCounterAgent;
    private  EnumMap<DocumentType, ObservableList<KsTIV>> estimateDocuments;

    private final Function<EstimateTVI, String>  estimateGroupFunction = EstimateTVI::getBuildingPart;

    public void init(SiteWrapper siteWrapper, CountAgentTVI counterAgent,EnumMap<DocumentType, ObservableList<KsTIV>> map) {
        this.selectedCounterAgent = counterAgent;
        this.siteWrapper = siteWrapper;
        this.estimateDocuments = map;
    }


    @Override
    public void putDocument(DocumentType type, ObservableList<KsTIV> documents) {
        estimateDocuments.put(type, documents);
    }

    @Override
    public ObservableList<KsTIV> get(DocumentType type) {
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

    public boolean isContanes(DocumentType type){
        return estimateDocuments.get(type)
                .stream()
                .anyMatch(e -> e.getDel() != 1);
    }
}


