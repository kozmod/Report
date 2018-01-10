import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import report.usage_strings.PathStrings;

@FixMethodOrder(MethodSorters.JVM)
public class ViewTests {


    @Test
    @DisplayName("Общие параметры")
    public void allPropLayoutTest(){
        FxStageCreator.launch(
                1200,
                1000,
                PathStrings.Layout.ALL_PROPERTIES
        );

    }
}
