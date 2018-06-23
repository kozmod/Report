package report.models.counterpaties;

public enum BuildingPart {
    FUNDAMENT("ФУНДАМЕНТ"),
    STENI("СТЕНЫ, ПЕРЕКРЫТИЯ"),
    KROWLIA("КРОВЛЯ"),
    PROEMI("ПРОЕМЫ"),
    OTDELKA("ОТДЕЛОЧНЫЕ РАБОТЫ");

    BuildingPart(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private String value;
}