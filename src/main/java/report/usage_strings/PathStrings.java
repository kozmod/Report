
package report.usage_strings;


import java.nio.file.Path;
import java.nio.file.Paths;

public  interface PathStrings {

        String USER_DIRRECTORY = System.getProperty("user.dir");

        interface Layout{
                String ACC             = "/view/CorAccountLayout.fxml";
                String ADD_KS          = "/view/KSAddLayout.fxml";
                String ADD_SITE_ROW    = "/view/AddSiteRowLayout.fxml";
                String ALL_PROPERTIES  = "/view/AllPropertiesLayout.fxml";
                String CONT_ADD        = "/view/AddContractorLayout.fxml";
                String COR_FILTER      = "/view/CorFilterLayout.fxml";
                String DEL_SITE        = "/view/DeleteLayout.fxml";
                String EXPENSES        = "/view/ExpensesLayout.fxml";
                String FIN_RES         = "/view/FinResLayout.fxml";
                String INTRO           = "/view/IntroLayout.fxml";
                String LOG             = "/view/LogLayout.fxml";
                String ROOT            = "/view/RootLayout.fxml";
                String SITE_ADD        = "/view/AddSiteLayout.fxml";
                String SITE_EST        = "/view/EstimateLayout.fxml";
                String SQL_CONNECT     = "/view/SQLConnectLayout.fxml";
        }

        interface Files{
                String EXCEL               =  "lib\\excel_files";
                String BACK_UP_SQL         =  "lib\\BackUp_SQL";
                String VARIABLE_PROPERTIES =  "lib\\MyProperties\\formula.properties";
        }
        interface FilesPaths{
                Path EXCEL               = Paths.get("lib\\excel_files");
                Path BACK_UP_SQL         = Paths.get("lib\\BackUp_SQL");
                Path VARIABLE_PROPERTIES = Paths.get( "lib\\MyProperties\\formula.properties");
        }

}
