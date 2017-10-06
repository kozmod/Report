package report.models_view.nodes_factories;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import report.models_view.data_utils.decimalFormatters.DFormatter;
import report.models_view.data_utils.decimalFormatters.stringConvertersFX.StringNumberConverter;

public interface TableFactory {
    /**
     *Apply StringConverter to all input cells.
     *@param formatter DFormatter(DecimalFormatSymbols & DecimalFormat)
     *@param columns TableColumn[]
     */
     static void setCellFactoryAll(DFormatter formatter, TableColumn... columns){
        for(TableColumn column  : columns)
            column.setCellFactory(
                    TextFieldTableCell.forTableColumn(
                            new StringNumberConverter(formatter)
                    )
            );
    }

}
