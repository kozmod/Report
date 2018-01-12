import helperClasses.FxTestStage;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.usage_strings.PathStrings;

@FixMethodOrder(MethodSorters.JVM)
public class ViewTests {


    @Test
    @DisplayName("Общие параметры")
    public void allPropLayoutTest(){
        FxTestStage.launch(
                PathStrings.Layout.ALL_PROPERTIES
        );
        FxTestStage.getS();
//        FxTestStage.launch();

    }
}
