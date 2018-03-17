import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import padeg.lib.Padeg;

import java.sql.SQLException;

public class PadegTest {

    @Test
//    @Disabled
    @DisplayName("PADEG")
    public void padegTest() throws SQLException {
        String text = Padeg.getFIOPadegFS("ЖОПА", true, 2);
        System.out.println(text);

    }

}
