package report.models.view.wrappers.tableWrappers;

public interface BindBase {
    /**
     * Send data to SQL.
     */
    void toBase();
    /**
     * Set TableData from BASE (use table SQL-name) and SAVE one to MEMENTO.
     */
    void setFromBase();
}
