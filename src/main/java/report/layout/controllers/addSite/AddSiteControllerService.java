package report.layout.controllers.addSite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.AgentTVI.CounterAgentDAO;
import report.models.view.wrappers.toString.CounterAgentToStringWrapper;

public class AddSiteControllerService {


    public static ObservableList<CounterAgentToStringWrapper> getConterAgentList(){

        final ObservableList<CounterAgentToStringWrapper> newList = FXCollections.observableArrayList();
        for (CountAgentTVI item : new CounterAgentDAO().getData()) {
            newList.add(new CounterAgentToStringWrapper(item));
        }
        return newList;
    }
}
