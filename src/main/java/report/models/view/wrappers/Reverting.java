package report.models.view.wrappers;

public interface Reverting extends BindBase {
    /**
     * Save Items to MEMENTO.
     */
    void saveMemento();

    /**
     * UNDO changes from MEMENTO.
     */
    void undoChangeItems();
}
