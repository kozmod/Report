package report.models.services;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.io.File;
import java.util.List;

public class TemplateFactory {

    public static void writeDocxTemplate(File outputFile, List<ObjectPSI> psiList) {
        TemplateDocx docx = new TemplateDocx("D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон.docx");
        docx.process(new ChangeTemplateTask(psiList));
        docx.save(outputFile);
    }

    public static void writeDocTemplate(File outputFile, List<ObjectPSI> psiList) {
        TemplateDoc doc = new TemplateDoc("D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон-2.doc");
        doc.process(new ChangeTemplateTask(psiList));
        doc.save(outputFile);
    }
}
