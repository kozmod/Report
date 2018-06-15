package report.spring.utils;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class FxTableUtils {

    public static <E,K> TableColumn<E, K> addColumn(TableView<E> table, String columnName, Callback<TableColumn.CellDataFeatures<E, K>, ObservableValue<K>> callback) {
        TableColumn<E, K> column = new TableColumn<>(columnName);
        column.setCellValueFactory(callback);
        table.getColumns().add(column);
        return column;
    }

    public static <E,K>  TableColumn<E, K> addColumn(TableView<E> table,String columnName, String fieldName) {
        TableColumn<E, K> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        table.getColumns().add(column);
        return column;
    }

    public static <T> void setTextFieldTableCell(StringConverter<T> converter, TableColumn<?,T> column) {
            column.setCellFactory(
                    TextFieldTableCell.forTableColumn(converter)
            );
    }
}
