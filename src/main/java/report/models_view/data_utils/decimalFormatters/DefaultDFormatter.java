package report.models_view.data_utils.decimalFormatters;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public  class DefaultDFormatter implements DFormatter{

    private final DecimalFormatSymbols decimalFormatSymbols;
    private final DecimalFormat decimalFormat;

    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/

    /**
     * Ctor.
     *<br>Patter            : "0.00"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public DefaultDFormatter() {
        this("0.00",
                ' ',
                '.',
                RoundingMode.DOWN
        );
    }

    /**
     * Ctor.
     *<br>Patter            : "0.00"<br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : <b>?</b><br/>
     */
    public DefaultDFormatter(final RoundingMode roundingMode) {
        this(
                "0.00",
                ' ',
                '.',
                roundingMode
        );
    }

    /**
     * Ctor.
     *<br>Patter            : <b>?</b><br/>
     *<br>GroupingSeparator : ' '<br/>
     *<br>DecimalSeparator  : '.'<br/>
     *<br>RoundingMode      : DOWN (1)<br/>
     */
    public DefaultDFormatter(final String pattern) {
        this(
                pattern,
                ' ',
                '.',
                RoundingMode.DOWN
        );
    }


    /**
     * Main Ctor.
     *@param pattern String
     *@param groupingSeparator GroupingSeparator
     *@param decimalSeparator DecimalSeparator
     *@param roundingMode RoundingMode
     */
    public DefaultDFormatter(String pattern, char groupingSeparator, char decimalSeparator, RoundingMode roundingMode) {

        this.decimalFormatSymbols = new DecimalFormatSymbols(){
            {
                setGroupingSeparator(groupingSeparator);
                setDecimalSeparator(decimalSeparator);
            }
        };
        this.decimalFormat =  new DecimalFormat(pattern,decimalFormatSymbols){
            {
                setRoundingMode(roundingMode);
            }
        };
    }

    /*!******************************************************************************************************************
    *                                                                                                        Methods
    ********************************************************************************************************************/

    /**
     * Parse to Number.
     * <br>
     * Input String (numberString) format use pattern (default - "0.00")
     * <br>
     * @param numberString String
     * @return Number
     */
    @Override
    public  Number fromString(String numberString) throws ParseException {
        Number number ;

        number =  decimalFormat.parse(numberString);

        return  number;
    }


    /**
     * Parse Number to String.
     * <br>
     * Output format format use pattern (default - "#.00")
     * <br>
     * @param number Number
     * @return String
     */
    @Override
    public  String toString(Number number){
        return decimalFormat.format(number);
    }


    /**
     * Return DecimalFormat with current settings.
     * <br>
     * @return DecimalFormat
     */
    @Override
    public DecimalFormat format() {
        return decimalFormat;
    }

    /**
     * Return DecimalFormatSymbols with current settings.
     * <br>
     * @return DecimalFormatSymbols
     */
    @Override
    public DecimalFormatSymbols formatSymbols() {
        return null;
    }


}
