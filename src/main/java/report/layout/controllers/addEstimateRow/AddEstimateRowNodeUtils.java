package report.layout.controllers.addEstimateRow;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import report.entities.abstraction.dao.CommonNamedDao;
import report.entities.items.cb.AddEstTIV;
import report.entities.items.estimate.EstimateDao;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.models.view.nodesFactories.TableCellFactory;
import report.usage_strings.SQL;

abstract class AddEstimateRowNodeUtils {
    /**
     * Create TableWrapper to AddEstimateRowController.Contain columns and their options.
     *
     * @return TableWrapper(child of TableView)
     */
    @SuppressWarnings("unchecked")
    public static PriceSumTableWrapper<AddEstTIV> decorEst_add(TableView table) {
        CommonNamedDao dao = new EstimateDao();
        PriceSumTableWrapper<AddEstTIV> tableWrapper = new PriceSumTableWrapper<>(table, dao);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn checkBoxColumn = tableWrapper.addColumn(" * ", "check");
        TableColumn<AddEstTIV, String> JM_nameColumn = tableWrapper.addColumn("Наименование работ/затрат", cellData -> cellData.getValue().JM_nameProperty());
        TableColumn<AddEstTIV, String> BJobColumn = tableWrapper.addColumn("Связанная работа", cellData -> cellData.getValue().bindJobProperty());
        TableColumn<AddEstTIV, Double> valueColumn = tableWrapper.addColumn("Кол-во", "quantity");
        TableColumn<AddEstTIV, String> unitColumn = tableWrapper.addColumn("Eд. изм.", "unit");
        TableColumn<AddEstTIV, Double> priseOneColumn = tableWrapper.addColumn("Стоимость (за единицу)", "priceOne");
        TableColumn<AddEstTIV, Double> priseSumColumn = tableWrapper.addColumn("Стоимость (общая)", "priceSum");

        checkBoxColumn.setMaxWidth(60);
        checkBoxColumn.setMinWidth(30);

        checkBoxColumn.setCellFactory(param -> TableCellFactory.getCheckValueCell());
        JM_nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        BJobColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
//                dao.getDistinct(SQL.Estimate.BINDED_JOB, "-")//todo
        ));
        //TODO: !!!! Check this new issue to add items in table. !!!
        table.setRowFactory(new Callback<TableView<AddEstTIV>, TableRow<AddEstTIV>>() {
            @Override
            public TableRow<AddEstTIV> call(TableView<AddEstTIV> tableView) {
                final TableRow<AddEstTIV> row = new TableRow<AddEstTIV>() {
                    @Override
                    protected void updateItem(AddEstTIV newRow, boolean empty) {
                        super.updateItem(newRow, empty);
                        if (super.getTableView().isEditable()) {
                            super.setDisable(true);
                            if (newRow != null)
                                if (newRow.getJM_name().equals("-")
                                        || newRow.getUnit().equals("-")
                                        || newRow.getPriceSum().equals(0)) {
                                    super.setDisable(false);
                                }
                        } else if (!super.getTableView().isEditable()) {
                            super.setDisable(false);
                        }
                    }
                };
                return row;

            }
        });
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        valueColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, Double> t) -> {
            AddEstTIV element = t.getRowValue();
            double newValue = t.getNewValue();
            element.setQuantity(newValue);
            double priceOne = element.getPriceOne();
            element.setPriceSum(newValue * priceOne);

        });
        unitColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
//                dao.getDistinct(SQL.Estimate.UNIT)
        ));
        priseOneColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priseOneColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, Double> t) -> {
            AddEstTIV element = t.getRowValue();
            double newValue = t.getNewValue();
            element.setPriceOne(newValue);
            double quantity = element.getQuantity();
            element.setPriceSum(newValue * quantity);
        });
        return tableWrapper;
    }

}
