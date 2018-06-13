package spring;

import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.EstimateDocumentType;
import spring.config.TestEstimateConfig;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestEstimateConfig.class)
public class EstimateDataTest {
    @Autowired
    private EstimateData estimateData;

    private  CountAgentTVI contractor;

    @Value("13")
    private String SITE_NUMBER;

    @Before
    public void initMock(){
        contractor = mock(CountAgentTVI.class);
        when(contractor.getIdCountConst()).thenReturn(11);
    }


    @Test
    public void shouldBeConsistent() {
        estimateData.init(SITE_NUMBER,contractor);

        ObservableList<? extends AbstractEstimateTVI> est = estimateData.getDocument(EstimateDocumentType.BASE,"ФУНДАМЕНТ",false);

        est.clear();

        ObservableList<? extends AbstractEstimateTVI> est2 = estimateData.getDocument(EstimateDocumentType.BASE,"ФУНДАМЕНТ",false);
        est2.get(1);

    }
}
