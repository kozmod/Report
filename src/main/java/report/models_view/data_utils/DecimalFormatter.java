
package report.models_view.data_utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DecimalFormatter {

    private static  DecimalFormatSymbols dfc = new DecimalFormatSymbols(){{
        setGroupingSeparator(' ');
        setDecimalSeparator('.'); 
    }};
    
    private static DecimalFormat df = new DecimalFormat("###,##0.00",dfc){{
        setRoundingMode(RoundingMode.DOWN);
    }};

/*!******************************************************************************************************************
*                                                                                                        Constructor
********************************************************************************************************************/     
    private DecimalFormatter() {   }

/*!******************************************************************************************************************
*                                                                                                        Methods
********************************************************************************************************************/      

    /**
     * Parse Decimal to String. 
     * <br>
     * Output format is "#.00"
     * <br> 
     * @return String
     */
    public static DecimalFormat getDecimalFormat(){
        return df; 
    }
    

    /**
     * Parse Decimal to String. 
     * <br>
     * Output format is "###,##0.00"
     * <br>
     * @param obj 
     * @return String
     */
    public static String toString(Object obj){
        return df.format(obj); 
    }
    
    /**
     * Parse to Double.
     * <br>
     * Input String (doubleString) format is "#.00"
     * <br>
     * 
     * @param doubleString
     * @return float
     */
    public static Double stringToDouble(Object doubleString){

        try {
            return df.parse(doubleString.toString()).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(DecimalFormatter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Double(0);
    }

    
}
