package report.models.converters.numberStringConverters;

import java.text.DecimalFormat;

public class IntegerStringConverter extends DefaultNumberStringConverter<Integer> {

//    private final NFormat<Number> decimalFormatter;

    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/
    /**
     * Ctor.
     */
//    public IntegerStringConverter(NFormat<Number> df) {
//        decimalFormatter = df;
//    }

    /**
     * Ctor.
     *<br>Patter            : "###,##0"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public IntegerStringConverter() {
        super("###,##0");
    }

    /**
     * Ctor.
     *<br>Patter            : <b>?</b><br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public IntegerStringConverter(final String pattern) {
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
     * @param number Number
     * @return String
     */
    @Override
    public String toString(Number number) {
        return super.toString(number);
//        return decimalFormatter.toString(number);
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
            Number nn = super.fromString(numberString);
            integer = nn.intValue();

        return integer;
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

    /**
     * Return DecimalFormatSymbols with current settings.
     * <br>
     * @return DecimalFormatSymbols
     */
}
