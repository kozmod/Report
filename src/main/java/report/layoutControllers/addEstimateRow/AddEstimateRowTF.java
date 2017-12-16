package report.layoutControllers.addEstimateRow;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.cb.TableItemCB;
import report.entities.items.estimate.TableViewItemEstDAO;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.cells.AddComboBoxTableCell;
import report.models_view.nodes.cells.AddTextFieldTableCell;
import report.models_view.nodes.node_wrappers.TableWrapperEST;
import report.models_view.nodes.nodes_factories.TableCellFactory;
import report.usage_strings.SQL;

public class AddEstimateRowTF {

    private AddEstimateRowTF(){}

    /**
     * Create TableWrapper to AddEstimateRowController.Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    @SuppressWarnings("unchecked")
    public static TableWrapperEST decorEst_add(TableView table){
        TableWrapperEST<TableItemCB> tableWrapper = new TableWrapperEST(table, new TableViewItemEstDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn CheckBoxColumn = tableWrapper.addColumn(" * ","check");
        TableColumn<TableItemCB, String>  JM_nameColumn  = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn<TableItemCB, String>   BJobColumn    = tableWrapper.addColumn("Связанная работа",cellData -> cellData.getValue().bindJobProperty());
        TableColumn valueColumn    = tableWrapper.addColumn("Кол-во",                    "value");
        TableColumn unitColumn     = tableWrapper.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_one      = tableWrapper.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sum      = tableWrapper.addColumn("Стоимость (общая)",         "price_sum");
//

        CheckBoxColumn.setMaxWidth(60);
        CheckBoxColumn.setMinWidth(30);

        CheckBoxColumn.setCellFactory(param -> TableCellFactory.getCheckValueCell());
        JM_nameColumn.setCellFactory(param -> new AddTextFieldTableCell(tableWrapper.getSetAddingCells()));
        BJobColumn.setCellFactory(param -> new AddComboBoxTableCell(
                        tableWrapper.getSetAddingCells()
                        ,tableWrapper.getDAO().getDistinctOfColumn(SQL.Estimate.BINDED_JOB,"-").toArray()
                )
        );
        valueColumn.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
        unitColumn.setCellFactory(param -> new AddComboBoxTableCell(
                        tableWrapper.getSetAddingCells()
                        ,tableWrapper.getDAO().getDistinctOfColumn(SQL.Estimate.UNIT).toArray()
                )
        );
        Price_one.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
        Price_sum.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
        BJobColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemCB, String> t) ->{
            TableItemCB element = t.getRowValue();
            String newValue = t.getNewValue();
            element.setBindJob(newValue);
            switch (newValue){
                case "-": element.setJobOrMat("Работа");
                    break;
                default:
                    element.setJobOrMat("Материал");
            }
        });
        return  tableWrapper;
    }

}
