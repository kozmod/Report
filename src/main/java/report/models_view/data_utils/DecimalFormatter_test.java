//package report.models_view.data_utils;
//
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.text.ParseException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class DecimalFormatter_test {
//    private final DecimalFormatSymbols decimalFormatSymbols;
//    private final DecimalFormat decimalFormat;
//
//    /*!******************************************************************************************************************
//    *                                                                                                        Constructor
//    ********************************************************************************************************************/
//
//    /**
//     * Ctor.
//     */
//    public DecimalFormatter_test() {
//        this("###,##0.00", ' ','.', RoundingMode.DOWN);
//    }
//
//    /**
//     * Ctor.
//     */
//    public DecimalFormatter_test(final RoundingMode roundingMode) {
//        this("###,##0.00", ' ','.', roundingMode);
//    }
//    /**
//     * Ctor.
//     */
//    public DecimalFormatter_test(final String pattern) {
//        this(pattern, ' ','.', RoundingMode.DOWN);
//    }
//
//
//    /**
//     * Main Ctor.
//     *@param pattern String
//     *@param groupingSeparator GroupingSeparator
//     *@param decimalSeparator DecimalSeparator
//     *@param roundingMode RoundingMode
//     */
//    public DecimalFormatter_test(String pattern, char groupingSeparator, char decimalSeparator, RoundingMode roundingMode) {
//        this.decimalFormatSymbols = new DecimalFormatSymbols(){
//            {
//            setGroupingSeparator(groupingSeparator);
//            setDecimalSeparator(decimalSeparator);
//            }
//        };
//        this.decimalFormat =  new DecimalFormat(pattern,decimalFormatSymbols){
//            {
//            setRoundingMode(roundingMode);
//            }
//        };
//    }
//
//    /*!******************************************************************************************************************
//    *                                                                                                        Methods
//    ********************************************************************************************************************/
//
//    /**
//     * Parse to Double.
//     * <br>
//     * Input String (doubleString) format use pattern (default - "#.00")
//     * <br>
//     *
//     * @param doubleString
//     * @return float
//     */
//    public  Double stringToDouble(Object doubleString){
//
//        try {
//            return decimalFormat.parse(doubleString.toString()).doubleValue();
//        } catch (ParseException ex) {
//            Logger.getLogger(DecimalFormatter_test.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return new Double(0);
//    }
//
//    /**
//     * Parse Decimal to String.
//     * <br>
//     * Output format format use pattern (default - "#.00")
//     * <br>
//     * @param obj
//     * @return String
//     */
//    public  String toString(Object obj){
//        return decimalFormat.format(obj);
//    }
//
//}
