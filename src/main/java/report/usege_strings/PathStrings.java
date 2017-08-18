
package report.usege_strings;


import java.nio.file.Path;
import java.nio.file.Paths;

public  interface PathStrings {

        String USER_DIRRECTORY = System.getProperty("user.dir");

        interface Layout{
                String ACC             = "/view/AccLayout.fxml";
                String ADD_KS          = "/view/addKSLayout.fxml";
                String ADD_SITE_ROW    = "/view/addSiteRowLayout.fxml";
                String ALL_PROPERTIES  = "/view/allPropertiesLayout.fxml";
                String CONT_ADD        = "/view/CorFilterLayout.fxml";
                String COR_FILTER      = "/view/CorFilterLayout.fxml";
                String DEL_SITE        = "/view/delLayout.fxml";
                String EXPENSES        = "/view/ExpensesLayout.fxml";
                String FIN_RES         = "/view/FinResLayout.fxml";
                String LOG             = "/view/LogLayout.fxml";
                String ROOT            = "/view/rootLayout.fxml";
                String SITE_ADD        = "/view/siteAddLayout.fxml";
                String SITE_EST        = "/view/SiteEstLayout.fxml";
                String SQL_CONNECT     = "/view/sqlConnectLayout.fxml";
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
