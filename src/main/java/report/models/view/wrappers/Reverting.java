package report.models.view.wrappers;

public interface Reverting extends BindBase {
    /**
     * Save Items to MEMENTO.
     */
    void saveTableItems() ;
    /**
     * UNDO changes from MEMENTO.
     */
    void undoChangeItems();
}
