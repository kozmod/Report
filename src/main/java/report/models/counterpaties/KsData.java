package report.models.counterpaties;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsTIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.SiteEntity;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


public class KsData implements DocumentData<KsTIV> {


    private  SiteWrapper siteWrapper;
    private  CountAgentTVI selectedCounterAgent;
    private  EnumMap<DocumentType, ObservableList<KsTIV>> ksDocuments;

    private final Function<EstimateTVI, String>  estimateGroupFunction = EstimateTVI::getBuildingPart;

    public void init(SiteWrapper siteWrapper, CountAgentTVI counterAgent,EnumMap<DocumentType, ObservableList<KsTIV>> map) {
        this.selectedCounterAgent = counterAgent;
        this.siteWrapper = siteWrapper;
        this.ksDocuments = map;
    }


    @Override
    public void putDocument(DocumentType type, ObservableList<KsTIV> documents) {
        ksDocuments.put(type, documents);
    }

    @Override
    public ObservableList<KsTIV> get(DocumentType type) {
        return ksDocuments.get(type);
    }

    private void clear() {
        ksDocuments.clear();
    }


    public CountAgentTVI getSelectedCounterAgent() {
        return selectedCounterAgent;
    }

    public SiteEntity getSiteEntity() {
        return siteWrapper.getSiteEntity();
    }

    public boolean isContanes(DocumentType type){
        return ksDocuments.get(type)
                .stream()
                .anyMatch(e -> e.getDel() != 1);
    }

    public ObservableList<KsTIV> getDeletedItemsList(DocumentType documentType, KsTIV selectedEstimateTVI) {
        if (selectedEstimateTVI != null) {
            return ksDocuments.get(documentType).stream()
                    .filter(i -> i.businessKeyEquals(selectedEstimateTVI))
                    .sorted(
                            Comparator.comparingLong(item -> item.getDateCreate().getTime())
                    )
                    .collect(collectingAndThen(toList(), FXCollections::observableArrayList));
        }
        return FXCollections.observableArrayList();
    }

    public Optional<KsTIV> findEqualElement(DocumentType documentType, AbstractEstimateTVI inpAbstractEstimateTVI) {
        return ksDocuments.get(documentType).stream()
                .filter(item -> item.businessKeyEquals(inpAbstractEstimateTVI))
                .findFirst();
    }


}


