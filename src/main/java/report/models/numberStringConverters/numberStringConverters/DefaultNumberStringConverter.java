package report.models.numberStringConverters.numberStringConverters;




import javafx.util.StringConverter;
import report.models.numberStringConverters.NFormat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.ParseException;

public  abstract class DefaultNumberStringConverter<T extends Number> extends StringConverter<T> implements NFormat<Format> {

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
    public DefaultNumberStringConverter() {
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
    public DefaultNumberStringConverter(final RoundingMode roundingMode) {
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
    public DefaultNumberStringConverter(final String pattern) {
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
    public DefaultNumberStringConverter(final String pattern, char groupingSeparator, char decimalSeparator, final RoundingMode roundingMode) {

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
    @SuppressWarnings( "unchecked" )
    @Override
    public  T fromString(String numberString) {
        Number number = 0;
        try {
            number =  decimalFormat.parse(numberString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  (T)number;
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



}
