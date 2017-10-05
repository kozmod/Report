package report.models_view.data_utils.decimalFormatters.stringConvertersFX;

import javafx.util.StringConverter;
import report.models_view.data_utils.decimalFormatters.DFormatter;

import java.text.ParseException;

/**
 * Extends StringConverter<T>.
 *
 */
public class StringNumberConverter<T extends Number> extends StringConverter<T> {


    private final DFormatter formatter;

    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/
    public StringNumberConverter(DFormatter formatter) {
        this.formatter = formatter;
    }

    /*!******************************************************************************************************************
    *                                                                                                        Methods
    ********************************************************************************************************************/
    @Override
    public String toString(Number object) {
        return formatter.toString(object);
    }

    @Override
    public T fromString(String string) {
        Number number = null;
        try {
            number =  formatter.fromString(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (T) number;
    }
}
