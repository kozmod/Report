package report.services;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TemplateFactory {

    public static void writeDocxTemplate(File outputFile, Map<String,String> replacementMap) {
        TemplateDocx docx = TemplateDocx.newBuilder()
                .setTemplateFile("D:\\IdeaProjects\\Report\\lib\\docs_templates\\test.docx")
                .setOutputFile(outputFile.toPath())
                .build();
        docx.process(new ReplaceFieldTask(replacementMap));
        docx.save();
    }

//    public static void writeDocTemplate(File outputFile, List<ObjectPSI> psiList) {
//        TemplateDoc doc = new TemplateDoc("D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон-2.doc");
//        doc.process(new ChangeTemplateTask(psiList));
//        doc.save(outputFile);
//    }


}
