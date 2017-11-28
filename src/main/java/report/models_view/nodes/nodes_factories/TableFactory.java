package report.models_view.nodes.nodes_factories;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;


public interface TableFactory {
    /**
     *Set Text Field cell with string converter to column array.
     *@param converter StringConverter(DecimalFormatSymbols & DecimalFormat)
     *@param columns TableColumn[]
     */
    static void setTextFieldTableCell(StringConverter converter, TableColumn ... columns){
        for(TableColumn column  : columns)
            column.setCellFactory(
                    TextFieldTableCell.forTableColumn(
                            converter
                    )
            );
    }

    /**
     *Set Cell with string formatter to column array.
     *@param converter StringConverter(DecimalFormatSymbols & DecimalFormat)
     *@param columns TableColumn[]
     */
    static <T> void setCellFactory(StringConverter<T> converter, TableColumn... columns) {
        for (TableColumn column : columns) {
            column.setCellFactory(cell ->
                    new TableCell<TableView, T>() {
                        @Override
                        protected void updateItem(T item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(converter.toString(item));
                            }
                        }
                    }
            );
        }
    }

}
