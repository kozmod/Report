import helperClasses.FxTestStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import report.entities.items.counterparties.CountAgentDAO;
import report.entities.items.counterparties.CountAgentTVI;
import report.layoutControllers.allProperties.AllPropertiesController;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.usage_strings.PathStrings;

import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.JVM)
public class ViewTests {


    static Thread appThread;
    @BeforeAll
    @DisplayName("Общие параметры")
//    @Disabled
    public static void startApp() throws InterruptedException {
        appThread = new Thread(() -> {
            FxTestStage.launchApp(
                    PathStrings.Layout.ALL_PROPERTIES
            );
        });
        appThread.start();
        TimeUnit.SECONDS.sleep(3);
    }

        @Test
        @DisplayName("Общие параметры")
//    @Disabled
        public void allPropLayoutTest() throws InterruptedException, NoSuchFieldException, IllegalAccessException {

            AllPropertiesController  controller = FxTestStage.controller();
            AbstractTableWrapper wrapper = FxTestStage.getField("countAgentTableWrapper");

            CountAgentDAO dao =  Mockito.mock(CountAgentDAO.class);
            ObservableList<CountAgentTVI> list = FXCollections.observableArrayList(CountAgentTVI.extractor());
            list.addAll(
                    new CountAgentTVI(1,"GREM", "OOO"," ИП"),
                    new CountAgentTVI(2,"УЮТ", "OфO"," Подрядчик"),
                    new CountAgentTVI(3,"САРАЙ", "ААO","Клиент")
            );
            Mockito.when(dao.getData()).thenReturn(list);
        wrapper.setDAO(dao);
        controller.initData();
        appThread.join();

    }


}
