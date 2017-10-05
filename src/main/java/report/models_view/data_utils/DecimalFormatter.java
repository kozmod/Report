//
//package report.models_view.data_utils;
//
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.text.ParseException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//public class DecimalFormatter {
//
//
//    private static  DecimalFormatSymbols dfc = new DecimalFormatSymbols(){{
//        setGroupingSeparator(' ');
//        setDecimalSeparator('.');
//    }};
//
//    private static DecimalFormat df = new DecimalFormat("###,##0.00",dfc){{
//        setRoundingMode(RoundingMode.DOWN);
//    }};
//
///*!******************************************************************************************************************
//*                                                                                                        Constructor
//********************************************************************************************************************/
//    private DecimalFormatter() {   }
//
///*!******************************************************************************************************************
//*                                                                                                        Methods
//********************************************************************************************************************/
//
//    /**
//     * Parse Decimal to String.
//     * <br>
//     * Output format is "#.00"
//     * <br>
//     * @return String
//     */
//    public static DecimalFormat format(){
//        return df;
//    }
//
//
//    /**
//     * Parse Decimal to String.
//     * <br>
//     * Output format is "###,##0.00"
//     * <br>
//     * @param obj
//     * @return String
//     */
//    public static String formatNumber(Object obj){
//        return df.format(obj);
//    }
//
//    public static String toString_threeZeroes(Object obj){
//        DecimalFormatSymbols dfc = new DecimalFormatSymbols(){{
//            setGroupingSeparator(' ');
//            setDecimalSeparator('.');
//        }};
//        DecimalFormat dff = new DecimalFormat("###,##0.000",dfc){
//            {
//                setRoundingMode(RoundingMode.DOWN);
//            }};
//        return dff.format(obj);
//    }
//
//    /**
//     * Parse to Double.
//     * <br>
//     * Input String (doubleString) format is "#.00"
//     * <br>
//     *
//     * @param doubleString
//     * @return float
//     */
//    public static Double formatString(Object doubleString){
//
//        try {
//            return df.parse(doubleString.toString()).doubleValue();
//        } catch (ParseException ex) {
//            Logger.getLogger(DecimalFormatter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return new Double(0);
//    }
//
//    public static Double stringToDouble_threeZeroes(Object doubleString){
//
//        DecimalFormatSymbols dfc = new DecimalFormatSymbols(){{
//            setGroupingSeparator(' ');
//            setDecimalSeparator('.');
//        }};
//        DecimalFormat dff = new DecimalFormat("###,##0.000",dfc){
//            {
//                setRoundingMode(RoundingMode.DOWN);
//            }};
//        try {
//            return dff.parse(doubleString.toString()).doubleValue();
//        } catch (ParseException ex) {
//            Logger.getLogger(DecimalFormatter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return new Double(0);
//    }
//
//
//
//}
