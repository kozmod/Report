package report.layoutControllers.addEstimateRow;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.TableClone;
import report.models_view.nodes.TableWrapperEST;
import report.models_view.nodes_factories.TableCellFactory;

public class AddEstimateRowTF {

    private AddEstimateRowTF(){}

    /**
     * Create TableWrapper to AddEstimateRowController.Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST decorEst_add(TableView table){
        TableWrapperEST tableWrapper = new TableWrapperEST(table);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn CheckBoxColumn = tableWrapper.addColumn(" * ",                       "check");
        TableColumn JM_nameColumn  = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn    = tableWrapper.addColumn("Связанная работа",          "BindedJob");
        TableColumn valueColumn    = tableWrapper.addColumn("Кол-во",                    "value");
        TableColumn unitColumn     = tableWrapper.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_one      = tableWrapper.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sum      = tableWrapper.addColumn("Стоимость (общая)",         "price_sum");
//
        CheckBoxColumn.setMaxWidth(60);
        CheckBoxColumn.setMinWidth(30);

        CheckBoxColumn.setCellFactory(param -> TableCellFactory.getCheckValueCell());
//        CheckBoxColumn.setCellFactory(new Callback<TableColumn<TableItemCB, Boolean>, TableCell<TableItemCB, Boolean>>() {
//                @Override
//                public TableCell<TableItemCB, Boolean> call(TableColumn<TableItemCB, Boolean> param) {
//                  return TableCellFactory.getCheckValueCell();
//                }
//        });

        return  tableWrapper;
    }

}
