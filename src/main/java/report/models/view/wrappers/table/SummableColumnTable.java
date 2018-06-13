package report.models.view.wrappers.table;

import javafx.scene.control.TableView;

public interface SummableColumnTable<T> {
    T getProperty();

    T computeProperty();

    TableView tableView();
}
