package report.models.numberStringConverters.numberStringConverters;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleStringConverter extends DefaultNumberStringConverter<Double> {

//    private final NFormat<Number> decimalFormatter;

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
//    public DoubleStringConverter(NFormat<Number> df) {
//        decimalFormatter = df;
//    }

    /**
     * Ctor.
     *<br>Patter            : "###,##0.00"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public DoubleStringConverter() {
        super("###,##0.00");
    }

    /**
     * Ctor.
     *<p>Patter            : ?<p/>
     *<p>GroupingSeparator : ' '<p/>
     *<p>DecimalSeparator  : '.'<p/>
     *<p>RoundingMode      : DOWN (1)<p/>
     */
    public DoubleStringConverter(final String pattern) {
        super(pattern);

//        decimalFormatter = new DefaultNumberStringConverter(pattern);
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
//        return decimalFormatter.toString(number);
        return super.toString(number);
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
            Number nn  = super.fromString(numberString);
            parserDouble = nn.doubleValue();

        return parserDouble;
    }

    /**
     * Return DecimalFormat with current settings.
     * <br>
     * @return DecimalFormat
     */
    @Override
    public DecimalFormat format() {
        return super.format();
//        return decimalFormatter.format();
    }


}
