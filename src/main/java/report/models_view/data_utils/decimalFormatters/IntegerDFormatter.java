package report.models_view.data_utils.decimalFormatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class IntegerDFormatter implements DFormatter {

    private final DFormatter decimalFormatter;

    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/
    /**
     * Ctor.
     */
    public IntegerDFormatter(DFormatter df) {
        decimalFormatter = df;
    }

    /**
     * Ctor.
     *<br>Patter            : "###,##0"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public IntegerDFormatter() {
        decimalFormatter = new DefaultDFormatter("###,##0");
    }

    /**
     * Ctor.
     *<br>Patter            : <b>?</b><br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public IntegerDFormatter(final String pattern) {
        decimalFormatter = new DefaultDFormatter(pattern);
    }


    /*!******************************************************************************************************************
    *                                                                                                        Methods
    ********************************************************************************************************************/
    /**
     * Parse Number to String.
     * <br>
     * Output format format use pattern (default - "0.00")
     * <br>
     * @param number Number
     * @return String
     */
    @Override
    public String toString(Number number) {
        return decimalFormatter.toString(number);
    }


    /**
     * Parse to Integer.
     * <br>
     * Input String (doubleString) format use pattern (default - "0.00")
     * <br>
     *
     * @param numberString String
     * @return Number
     */
    @Override
    public Integer fromString(String numberString)  {
        Integer integer = 0;
        try {
            Number nn = decimalFormatter.fromString(numberString).intValue();
            integer = nn.intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return integer;
    }

    /**
     * Return DecimalFormat with current settings.
     * <br>
     * @return DecimalFormat
     */
    @Override
    public DecimalFormat format() {
        return null;
    }

    /**
     * Return DecimalFormatSymbols with current settings.
     * <br>
     * @return DecimalFormatSymbols
     */
    @Override
    public DecimalFormatSymbols formatSymbols() {
        return decimalFormatter.formatSymbols();
    }
}
