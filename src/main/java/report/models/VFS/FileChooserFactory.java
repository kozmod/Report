package report.models.VFS;

import javafx.stage.FileChooser;

import report.controllers.showEstLayoutController.Est;
import report.usege_strings.PathStrings;
import report.usege_strings.SQL;

import java.io.File;

public class FileChooserFactory {
    private FileChooserFactory() { }

    private  static FileChooser newFCh(String title, String initDirectory){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(initDirectory));
        fileChooser.setTitle(title);
        return fileChooser;
    }



    public static class Open  {

        public static  File getExcel(){
            return  newFCh("Open Resource File",PathStrings.Files.EXCEL)
                    .showOpenDialog(null);
        }

        public static File getSqlBackUp(){
            return  newFCh("Open \".Bak\" File",PathStrings.Files.BACK_UP_SQL)
                    .showOpenDialog(null);
        }
    }


    public  static class Save{

        private Save(){}

//        public static File saveEst(Est enumEst){
//            String title = "SAVE";
//            Path openDir = Paths.get("\\");
//            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
//
//            FileChooser fileChooser = new FileChooser();
//
//            fileChooser.setInitialFileName(enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER)
//                    +"_"
//                    + enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));
//            fileChooser.getExtensionFilters().add(extFilter);
//            fileChooser.setInitialDirectory(openDir.toFile());
//            fileChooser.setTitle(title);
//
//            return  fileChooser.showSaveDialog(null);
//        }

        public static File saveEst(Est enumEst){
            FileChooser fileChooser               = newFCh("Save Estimation","\\");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER)
                    +"_"
                    + enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));

            return  fileChooser.showSaveDialog(null);
        }

        public static File saveKS(String ksNumber){
            FileChooser fileChooser               = newFCh("Save KS","\\");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName("КС-2_№_" + ksNumber
                    + "("
                    + Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER)
                    + ","
                    + Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR)
                    +")"
            );

            return  fileChooser.showSaveDialog(null);
        }


    }

}
