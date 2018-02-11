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




}
