import helperClasses.FxTestStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import report.entities.items.counterparties.AgentTVI.CountAgentDAO;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.counterparties.ObservablWrapper;
import report.entities.items.counterparties.commonReq.CountAgentCommonReq;
import report.layoutControllers.allProperties.AllPropertiesController;
import report.models.view.wrappers.tableWrappers.AbstractTableWrapper;
import report.usage_strings.PathStrings;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.JVM)
public class ViewTests {
    /**
     * Get Class's field reference
     * @param field String(Field's name)
     * @param instObject Object(Instance)
     * @param <T> return type
     * @return T
     * @throws NoSuchFieldException  (.getDeclaredField( ... ))
     * @throws IllegalAccessException (Field.get( ... ))
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(String field, Object instObject) throws NoSuchFieldException, IllegalAccessException {
        Field wrapperField = instObject.getClass().getDeclaredField(field);
        wrapperField.setAccessible(true);
        return (T)  wrapperField.get(instObject);
    }
    /***************************************************************************
     *                                                                         *
     * TESTS                                                                   *
     *                                                                         *
     **************************************************************************/
    static Thread appThread;
    @BeforeAll
    @DisplayName("Общие параметры")
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

        FxTestStage fx = FxTestStage.applicationInstance();
        AllPropertiesController controller = getField("controller",fx);
        AbstractTableWrapper wrapper = getField("countAgentTableWrapper",controller);
        CountAgentDAO dao =  Mockito.mock(CountAgentDAO.class);
        ObservableList<CountAgentTVI> list = FXCollections.observableArrayList(CountAgentTVI.extractor());
//        list.addAll(
//                new CountAgentTVI(1,"GREM", "OOO"," Клиент"),
//                new CountAgentTVI(2,"УЮТ", "OфO"," Подрядчик"),
//                new CountAgentTVI(3,"САРАЙ", "ААO","Клиент")
//        );
        Mockito.when(dao.getData()).thenReturn(list);
        wrapper.setDAO(dao);
        controller.initData();

        appThread.join();

    }

    @Test
    @DisplayName("reflection test")
    @Disabled
    public void refTest() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        CountAgentCommonReq commonReq = new CountAgentCommonReq(
                1,
                false,
                new BigInteger("12345678901234567890"),
                100000,
                new BigInteger("12345678901234567890"),
                new BigInteger("12345678901234567890"),
                "adress",
                "factAdress",
                "postAdress"
        );
        System.out.println(
        commonReq.getDateOGRN()
        );
    }
    @Test
    @DisplayName("reflection test")
    @Disabled
    public void mementoTest() throws InterruptedException, NoSuchFieldException, IllegalAccessException {

        ObservablWrapper o = new  ObservablWrapper();
        CountAgentCommonReq commonReq = new CountAgentCommonReq(
                1,
                false,
                new BigInteger("12345678901234567890"),
                100000,
                new BigInteger("12345678901234567890"),
                new BigInteger("12345678901234567890"),
                "lowadress",
                "factAdress",
                "postAdress"
        );
        o.put(commonReq);

//        System.out.println(o.undoChanges(CountAgentCommonReq.class));
    }



}
