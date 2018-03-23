package report.models.services;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.io.File;
import java.util.List;

public class TemplateFactory {

    public static void writeTemplate(File outputFile, List<ObjectPSI> psiList){
        TemplateDocx templateDocx = new TemplateDocx("D:\\IdeaProjects\\Report\\lib\\docs_templates\\шаблон.docx");
        templateDocx.process(new ChangetemplateTask(psiList));
        templateDocx.save(outputFile);
    }

}
