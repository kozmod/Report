import helperClasses.FxTestStage;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import report.entities.items.counterparties.ReqBankDao;
import report.entities.items.counterparties.ReqCommonDao;
import report.entities.items.counterparties.ReqExBodyDao;
import report.entities.items.propertySheet__TEST.ObjectPSI;

import report.models.view.wrappers.propertySheet.PropertySheetWrapper;
import report.usage_strings.PathStrings;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.JVM)
public class ViewTest {
    /**
     * Get Class's field reference
     *
     * @param field      String(Field's name)
     * @param instObject Object(Instance)
     * @param <T>        return type
     * @return T
     * @throws NoSuchFieldException   (.getDeclaredField( ... ))
     * @throws IllegalAccessException (Field.get( ... ))
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(String field, Object instObject) throws NoSuchFieldException, IllegalAccessException {
        Field wrapperField = instObject.getClass().getDeclaredField(field);
        wrapperField.setAccessible(true);
        return (T) wrapperField.get(instObject);
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
    public void firstTest() {
        FxTestStage.launch(PathStrings.Layout.ALL_PROPERTIES);
        try {
            TimeUnit.SECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    public void checkToString() {
        ObservableList<ObjectPSI> list = (ObservableList<ObjectPSI>) new ReqBankDao().getByID(55);
        list.addListener((ListChangeListener<? super ObjectPSI>) e -> {
            System.out.println("AAAAAAAAAA");
        });
        list.get(1).setValue("12344");

        "dsdsd".matches("\\d+");
    }

    @Test
    @Disabled
    @DisplayName("Stream test")
    public void streamTest() {
        FxTestStage.launch(PathStrings.Layout.ALL_PROPERTIES);
        PropertySheet countSheet = new PropertySheet();
        PropertySheetWrapper wrapper = new PropertySheetWrapper(
                countSheet,
                new ReqCommonDao(),
                new ReqBankDao(),
                new ReqExBodyDao()
        );

        for (int i = 0; i < 300; i++) {
            wrapper.setFromBase(i);
            System.out.println(wrapper.getSheet().getItems().get(13).getValue());

        }

        try {
            TimeUnit.SECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
