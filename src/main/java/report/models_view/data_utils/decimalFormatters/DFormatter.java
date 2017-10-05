package report.models_view.data_utils.decimalFormatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public interface DFormatter {
    String toString(Number obj);
    Number fromString(String doubleString) throws ParseException;
    DecimalFormat format();
    DecimalFormatSymbols formatSymbols ();


}
