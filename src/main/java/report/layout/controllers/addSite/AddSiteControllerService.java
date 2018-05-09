package report.layout.controllers.addSite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CounterAgentDAO;
import report.models.view.wrappers.toString.CounterAgentToStringWrapper;

import java.util.Collection;

public class AddSiteControllerService {

    static ObservableList<CounterAgentToStringWrapper> getConterAgentList() {
        final CommonNamedDAO<Collection<CountAgentTVI>> dao = new CounterAgentDAO();
        final ObservableList<CounterAgentToStringWrapper> newList = FXCollections.observableArrayList();
        for (CountAgentTVI item : dao.getData()) {
            newList.add(new CounterAgentToStringWrapper(item));
        }
        return newList;
    }
}
