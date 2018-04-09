package report.models.view.nodesFactories;

import javafx.stage.FileChooser;

import report.layout.controllers.estimate.EstimateController.Est;
import report.usage_strings.PathStrings;
import report.usage_strings.SQL;

import java.io.File;

public class FileChooserFactory {
    private FileChooserFactory() {
    }

    private static FileChooser newFCh(String title, String initDirectory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(initDirectory));
        fileChooser.setTitle(title);
        return fileChooser;
    }

    public static File openExcelFolder() {
        return newFCh("Open Resource File", PathStrings.Files.EXCEL)
                .showOpenDialog(null);
    }

    public static File openSqlBackUpFolder() {
        return newFCh("Open \".Bak\" File", PathStrings.Files.BACK_UP_SQL)
                .showOpenDialog(null);
    }
//        public static List<File> openDesktop() {
//            FileChooser fileChooser = newFCh("Save Templates", PathStrings.FilesPaths.DESCTOP.toString());
//            FileChooser.ExtensionFilter docFilter = new FileChooser.ExtensionFilter("DOC (*.doc)", "*.doc");
//            FileChooser.ExtensionFilter docxFilter = new FileChooser.ExtensionFilter("DOCX (*.docx)", "*.docx");
//            FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("ALL",  "*.doc","*.docx");
//            fileChooser.getExtensionFilters().addAll(allFilter,docFilter,docxFilter);
//
//            List<File> files = fileChooser.showOpenMultipleDialog(null);
//            new BinaryStreamQuery().insert(files);
//
//            return files;
//        }


    public static File saveEst(Est enumEst) {
        FileChooser fileChooser = newFCh("Save Estimation", "\\");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(enumEst.getSiteSecondValue(SQL.Common.SITE_NUMBER)
                + "_"
                + enumEst.getSiteSecondValue(SQL.Common.CONTRACTOR));

        return fileChooser.showSaveDialog(null);
    }

    public static File saveKS(String ksNumber) {
        FileChooser fileChooser = newFCh("Save KS", "\\");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("КС-2_№_" + ksNumber
                + "("
                + Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER)
                + ","
                + Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR)
                + ")"
        );

        return fileChooser.showSaveDialog(null);
    }


}
