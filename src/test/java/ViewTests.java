import helperClasses.FxTestStage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import report.entities.items.counterparties.ReqBankDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.layoutControllers.allProperties.AllPropertiesController;
import report.models.view.wrappers.tableWrappers.AbstractTableWrapper;
import report.usage_strings.PathStrings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
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
//    @BeforeAll
    @DisplayName("Запускаем FXML")
    public static void startApp() throws InterruptedException {
            FxTestStage.launch(PathStrings.Layout.ALL_PROPERTIES);

    }
    @Test
//    @Disabled
    @DisplayName("Держатель Приложения")
    public void firstTest(){
        FxTestStage.launch(PathStrings.Layout.ALL_PROPERTIES);
        try {
            TimeUnit.SECONDS.sleep(55);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Disabled
    public void checkToString(){
     ObservableList<ObjectPSI> list = (ObservableList<ObjectPSI>) new ReqBankDAO().getBank(55);
        list.addListener((ListChangeListener<? super ObjectPSI>) e -> {
            System.out.println("AAAAAAAAAA");
        });
        list.get(1).setValue("12344");

        "dsdsd".matches("\\d+");
    }

}
