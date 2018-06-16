package report.models.counterpaties;

public enum DocumentType {
    BASE(1),
    CHANGED(2);

    DocumentType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    private int type;

}
