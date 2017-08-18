package report.models.VFS;

import javafx.stage.FileChooser;

import report.controllers.showEstLayoutController.Est;
import report.usege_strings.PathStrings;
import report.usege_strings.SQL;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChooserFactory {
    private FileChooserFactory() { }

    public static class Open{

        private Open(){}

        public static  File getExcel(){
            String title = "Open Resource File";

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(PathStrings.Files.EXCEL));
            fileChooser.setTitle(title);

            return fileChooser.showOpenDialog(null);
        }

        public  File getSqlBackUp(){
            String title = "Open \".Bak\" File";

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File( PathStrings.Files.BACK_UP_SQL));
            fileChooser.setTitle(title);

            return fileChooser.showOpenDialog(null);
        }
    }


    public  static class Save{
//ddddd
//ssssss
        private Save(){}

        public static File get(Est enumEst){
            String title = "SAVE";
            Path openDir = Paths.get("\\");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialFileName(enumEst.getSecondValue(SQL.Common.SITE_NUMBER)
                    +"_"
                    + enumEst.getSecondValue(SQL.Common.CONTRACTOR));
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialDirectory(openDir.toFile());
            fileChooser.setTitle(title);

            return  fileChooser.showSaveDialog(null);
        }
    }

}
