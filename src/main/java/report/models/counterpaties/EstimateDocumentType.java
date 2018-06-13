package report.models.counterpaties;

import report.entities.items.KS.KS_TIV;
import report.entities.items.estimate.EstimateTVI;

public enum EstimateDocumentType {
    BASE(EstimateTVI.class),
    BASE_KS(KS_TIV.class),
    CHANGED(EstimateTVI.class),
    CHANGED_KS(KS_TIV.class),
    ADDITIONAL(EstimateTVI.class);

    EstimateDocumentType(Class clazz){
        this.clazz = clazz;

    }

    public Class getClazz() {
        return clazz;
    }

    private Class clazz;
}
