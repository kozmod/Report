
package report.view_models;

import java.util.regex.Pattern;


public class InputValidator {
    
//    public static boolean isStringValid(String  inputString){
//
//        Pattern p   = null;
//        Matcher m   = null;
//        boolean res = false;
//
//        char ch =inputString.charAt(0);
//            p = Pattern.compile("^[a-zA-Zа-яА-Я0-9\\-]+$");
////            p = Pattern.compile("^[а-яА-Яa-zA-Z0-9\\-]");
//            m = p.matcher(Character.toString(ch));
//            res = m.matches();
//
////                if(res == true){
////                    p = Pattern.compile("[а-яA-Za-zА-Я0-9\\s\\-_]{1,20}");
////                    m = p.matcher(inputString);
////                    //System.err.println(m.matches());
////                    res = m.matches();
////                }
//
//        return res;
//    }
//
//      public static boolean isNumericValid(String  inputString){
//        Pattern p   = null;
//        Matcher m   = null;
//        boolean res = false;
//
//        char ch =inputString.charAt(0);
//            p = Pattern.compile("^\\d+\\.?[0-9]+$");
//            m = p.matcher(Character.toString(ch));
//            res = m.matches();
//
////                if(res == true){
//////                    p = Pattern.compile("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$"); //"([0-9]*[.])?[0-9]{1,20}" //[0-9]+(\.[0-9][0-9]?)? //^(?:[1-9]\\d*|0)?(?:\\.\d+)?$
////                    m = p.matcher(inputString);
////                    //System.err.println(m.matches());
////                    res = m.matches();
////                }
//
//        return res;
//      }


    public static boolean  isNumericValid(String  inputString){
          return Pattern
                  .compile("^\\d+\\.?[0-9]+$")
                  .matcher(inputString)
                  .matches();

    }
    public static boolean  isStringValid(String  inputString){
          return Pattern
                  .compile("^[a-zA-Zа-яА-Я0-9\\-]+$")
                  .matcher(inputString)
                  .matches();

    }

    public static boolean  isPercentValid(String  inputString){
          return Pattern
                  .compile("^[a-zA-Zа-яА-Я0-9\\-]+$")
                  .matcher(inputString)
                  .matches();

    }



//      }    public static boolean isPercentValid(String  inputString){
//        Pattern p   = null;
//        Matcher m   = null;
//        boolean res = false;
//
//        char ch =inputString.charAt(0);
//            p = Pattern.compile("^[0-9]");
//            m = p.matcher(Character.toString(ch));
//            res = m.matches();
//
//                if(res == true){
//                    p = Pattern.compile("^[1-9][0-9]?$|^100$");
//                    m = p.matcher(inputString);
//                    //System.err.println(m.matches());
//                    res = m.matches();
//                }
//
//        return res;
//      }



          
}
