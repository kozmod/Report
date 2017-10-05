package report.models_view.data_utils.decimalFormatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class DoubleDFormatter implements DFormatter {

    private final DFormatter decimalFormatter;

    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/
    /**
     * Ctor.
     *<br>Patter            : ?<br/>
     *<br>GroupingSeparator : ?<br/>
     *<br>DecimalSeparator  : ?<br/>
     *<br>RoundingMode      : ?<br/>
     */
    public DoubleDFormatter(DFormatter df) {
        decimalFormatter = df;
    }

    /**
     * Ctor.
     *<br>Patter            : "###,##0.00"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public DoubleDFormatter() {
        decimalFormatter = new DefaultDFormatter("###,##0.00");
    }

    /**
     * Ctor.
     *<p>Patter            : ?<p/>
     *<p>GroupingSeparator : ' '<p/>
     *<p>DecimalSeparator  : '.'<p/>
     *<p>RoundingMode      : DOWN (1)<p/>
     */
    public DoubleDFormatter(final String pattern) {
        decimalFormatter = new DefaultDFormatter();
    }

    /*!******************************************************************************************************************
    *                                                                                                        Methods
    ********************************************************************************************************************/

    /**
     * Parse Number to String.
     * <br>
     * Output format format use pattern (default - "0.00")
     * <br>
     * @param number String
     * @return String
     */
    @Override
    public String toString(Number number) {
        return decimalFormatter.toString(number);
    }

    /**
     * Parse to Double.
     * <br>
     * Input String (doubleString) format use pattern (default - "0.00")
     * <br>
     *
     * @param numberString String
     * @return Number
     */
    @Override
    public Double fromString(String numberString) {
        Double parserDouble = 0.0;
        try {
            Number nn  = decimalFormatter.fromString(numberString);
            parserDouble = nn.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parserDouble;
    }

    /**
     * Return DecimalFormat with current settings.
     * <br>
     * @return DecimalFormat
     */
    @Override
    public DecimalFormat format() {
        return decimalFormatter.format();
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
