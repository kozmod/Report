
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.items.counterparties.AgentTVI.CounterAgentDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.utils.CounterAgentDaoUtil;
import report.models.view.LinkedNamePair;

import java.util.regex.Pattern;
import java.util.stream.Collector;

@FixMethodOrder(MethodSorters.JVM)
public class CountAgentsDaoTest {
    @Test
    @DisplayName("Counter Agents")
    public void shouldGetListOfCounterAgentTest() {
        //get all Counter Agents
        ObservableList<CountAgentTVI> countAgentList = new CounterAgentDAO().getData();
        // set to all CounterAgents exist Linked Names
        countAgentList.forEach(countAgent ->
                countAgent.setLinkedNames(
                        CounterAgentDaoUtil.getMatchLinkedNames(countAgent.getName())
                )
        );
        //get all not match names
        ObservableList<LinkedNamePair> allLinkedNames = CounterAgentDaoUtil.getNotMatchLinkedNames();
        // stream get needed Counter Agent
        ObservableList<LinkedNamePair> selectedNamesUsePattern = allLinkedNames.stream()
                .filter(name ->
                        Pattern.compile(".*[гГрРэЭмМ]{4}.*")
                                .matcher(name.getValue())
                                .matches()
                )
                .collect(Collector.of(
                        () -> FXCollections.observableArrayList(),
                        ObservableList::add,
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));

        // prepare list of Counter Agent to insert
        ObservableList<CountAgentTVI> listToInsert =
                countAgentList.stream()
                .filter(countAgent -> countAgent.getName().equals("ГРЭМ"))
                .peek(countAgent ->
                        countAgent.getLinkedNames().addAll(selectedNamesUsePattern)
                )
                .collect(Collector.of(
                () -> FXCollections.observableArrayList(),
                ObservableList::add,
                (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }));
//                .forEach(countAgent -> System.out.println(countAgent.getLinkedNames()));

        new CounterAgentDAO().insert(listToInsert);


    }
    @Test
    @DisplayName("Counter Agents")
    public void shoulDeleteElement_WhenUseDELETE_FRK_ACCOUNT_COUNT_TYPE_FORM_Test() {
        //get all Counter Agents
        ObservableList<CountAgentTVI> countAgentList = new CounterAgentDAO().getData();
        ObservableList<CountAgentTVI> listToDelete =
                countAgentList.stream()
                        .filter(countAgent -> countAgent.getName().equals("ГРЭМ") && countAgent.getIdName() == 1)
                        .collect(Collector.of(
                                () -> FXCollections.observableArrayList(),
                                ObservableList::add,
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                }));
        listToDelete.forEach(e -> System.out.println(e.getIdForm() + " "+ e.getIdName()+ " "+ e.getIdType()));
        new CounterAgentDAO().delete(listToDelete);
    }

    @Test
    @DisplayName("Counter Agents")
    public void shouldMatchRegExTest() {
        String text   = "Привет я Мксим, готов бросить якорь в бухте %harbor_name% , и поехать на поезде";
        text = Pattern.compile("%harbor_name%")
                        .matcher(text).replaceAll("Жемчужная");
        System.out.println(text);
//        System.out.println(
//                Pattern.compile(".*[гГрРэЭмМ]{4}.*")
//                        .matcher("aaaaaaГРЭМфвфвЭ")
//                        .matches()
//        );
    }
}
