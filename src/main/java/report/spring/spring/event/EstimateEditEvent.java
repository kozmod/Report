package report.spring.spring.event;


public class EstimateEditEvent {
    private Object source;
    private boolean isEditing;

    public EstimateEditEvent(Object source, boolean isEditing) {
        this.source = source;
        this.isEditing = isEditing;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public Object getSource() {
        return source;
    }
}
