package report.models.services;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.util.List;
import java.util.function.UnaryOperator;

public class ChangeTemplateTask implements UnaryOperator<String>{

    private final List<ObjectPSI> psiList;

    public ChangeTemplateTask(List<ObjectPSI> psiList) {
        this.psiList = psiList;
    }

    public String apply(String string) {
        for (ObjectPSI psi: psiList){
            String template = "%"+psi.getSqlName()+"%";
            string = string.replaceAll(template, psi.getValue().toString());
        }
        return string;
    }
}
