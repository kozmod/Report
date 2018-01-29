package report.models_view.nodes.table_wrappers;

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
