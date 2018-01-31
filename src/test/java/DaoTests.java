
import javafx.collections.ObservableList;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.commonItems.CommonDItem;
import report.entities.commonItems.CommonItemDAO;

@FixMethodOrder(MethodSorters.JVM)
public class DaoTests {


    @Test
    @DisplayName("Общие параметры")
//    @Disabled
    public void firstTest(){
        ObservableList<CommonDItem<String,String>> list
                = (ObservableList<CommonDItem<String, String>>) new CommonItemDAO("Contractors","Director","Comments")
                .getData();
        System.out.println(list);

    }
}
