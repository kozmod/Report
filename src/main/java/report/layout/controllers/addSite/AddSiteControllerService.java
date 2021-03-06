package report.layout.controllers.addSite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.dao.CommonDao;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CounterAgentDao;
import report.models.view.wrappers.toString.CounterAgentToStringWrapper;

import java.util.Collection;
import java.util.Objects;

public class AddSiteControllerService {
    private static final int CONTRACTOR_TYPE = 1;

    static ObservableList<CounterAgentToStringWrapper> getConterAgentList() {

        final CommonDao<Collection<CountAgentTVI>> dao = new CounterAgentDao();
        final ObservableList<CounterAgentToStringWrapper> newList = FXCollections.observableArrayList();
        for (CountAgentTVI item : dao.getData()) {
            if (Objects.equals(item.getIdType(), CONTRACTOR_TYPE)) {
                newList.add(new CounterAgentToStringWrapper(item));
            }
        }
        return newList;
    }
}
