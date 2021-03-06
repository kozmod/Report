
package report.models.view.nodesHelpers;

import java.util.regex.Pattern;


public class InputValidator {

    public static boolean isNumericValid(String inputString) {
        return checkValidation(inputString, "^\\d+\\.?[0-9]+$");

    }

    public static boolean isStringValid(String inputString) {
        return checkValidation(inputString, "^[AbstractJavaFxApplication-zA-Zа-яА-Я0-9\\-]+$");

    }

    public static boolean isPercentValid(String inputString) {
        return checkValidation(inputString, "^[AbstractJavaFxApplication-zA-Zа-яА-Я0-9\\-]+$");

    }

    public static boolean isSiteNumberValid(String inputString) {
        return checkValidation(inputString, "^\\d+[AbstractJavaFxApplication-zA-Zа-яА-Я0-9\\-]?|(\\d+\\.*\\d+)$");

    }

    //input String validator
    private static boolean checkValidation(String inputString, String regEx) {
        return Pattern
                .compile(regEx)
                .matcher(inputString)
                .matches();
    }


}
