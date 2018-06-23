package report.layout.controllers.allProperties;

import javafx.util.StringConverter;
import padeg.lib.Padeg;

public class PadegStringConverter extends StringConverter<String> {
    private static final int ROD_PADEG = 2;

    @Override
    public String toString(String object) {
        return changeToRodPadeg(object);
    }

    @Override
    public String fromString(String string) {
        return changeToRodPadeg(string);
    }

    private static String changeToRodPadeg(String name) {
        return Padeg.getFIOPadegFS(name, true, ROD_PADEG);
    }
}
