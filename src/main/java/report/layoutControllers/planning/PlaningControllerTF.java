package report.layoutControllers.planning;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.osr.ItemOSRDAO;
import report.entities.items.osr.TableItemOSR;
import report.entities.items.plan.ItemPlanDAO;
import report.entities.items.plan.TableItemPlan;
import report.models.coefficient.Quantity;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models.numberStringConverters.numberStringConverters.IntegerStringConverter;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.TableCellFactory;
import report.models_view.nodes_factories.TableFactory;

public class PlaningControllerTF implements TableFactory {


    private PlaningControllerTF() {
    }

    /**
     * Create TableWrapper "Plan"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */

    public static TableWrapper<TableItemPlan> decorPlan(TableView<TableItemPlan> table){
        TableWrapper<TableItemPlan> tableWrapper = new TableWrapper<>(table,new ItemPlanDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn<TableItemPlan,Integer> typeIdColumn
                = tableWrapper.addColumn("ID","typeID");
        TableColumn typeColumn     = tableWrapper.addColumn("Тип","type");
        TableColumn<TableItemPlan,Integer> quantityColumn
                = tableWrapper.addColumn("Кол-во","quantity");
        TableColumn restColumn     = tableWrapper.addColumn("Остаток","rest");
        TableColumn<TableItemPlan,Double> smetColumn
                = tableWrapper.addColumn("Стоимоть","SmetCost");
        TableColumn smetSumColumn  = tableWrapper.addColumn("Себестоимость", "SmetCostSum");
        TableColumn<TableItemPlan,Double> saleColumn
                = tableWrapper.addColumn("Цена","SaleCost");
        TableColumn saleSumColumn  = tableWrapper.addColumn("Выручка","SaleCostSum");
        TableColumn profitColumn   = tableWrapper.addColumn("Прибыль","profit");


        typeIdColumn    .setMaxWidth(50);
        typeIdColumn    .setMinWidth(35);
        typeColumn      .setMaxWidth(80);
        typeColumn      .setMinWidth(50);
        quantityColumn  .setMaxWidth(70);
        quantityColumn  .setMinWidth(50);
        restColumn      .setMaxWidth(80);
        restColumn      .setMinWidth(50);

        typeIdColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemPlan, Integer> t) -> {
            t.getRowValue().setTypeID(t.getNewValue());
        });

        quantityColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemPlan, Integer> t) -> {
            TableItemPlan editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setQuantity(t.getNewValue());
                editingItem.setSaleCostSum((double) (t.getNewValue() * editingItem.getQuantity()));
                editingItem.setSmetCostSum((double) (t.getNewValue() * editingItem.getQuantity()));
            }

        });
        smetColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemPlan, Double> t) -> {
            TableItemPlan editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setSmetCost(t.getNewValue());
                editingItem.setSmetCostSum(t.getNewValue() * editingItem.getQuantity());
                editingItem.setProfit(editingItem.getSaleCost() - editingItem.getSmetCost());
            }
        });
        saleColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemPlan, Double> t) -> {
            TableItemPlan editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setSaleCost(t.getNewValue());
                editingItem.setSaleCostSum(t.getNewValue() * editingItem.getQuantity());
                editingItem.setProfit(editingItem.getSaleCost() - editingItem.getSmetCost());
            }
        });

        TableFactory.setCellFactory(
                new DoubleStringConverter(),
                smetColumn,
                smetSumColumn,
                saleColumn,
                saleSumColumn,
                profitColumn
        );
        TableFactory.setCellFactory(
                new IntegerStringConverter(),
                typeIdColumn
        );

        TableFactory.setTextFieldTableCell(
                new IntegerStringConverter(),
                quantityColumn
        );
        return tableWrapper;
    }


    /**
     * Create TableWrapper "Plan"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    public static TableWrapper decorFact(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table,null);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn typeIdColumn   = tableWrapper.addColumn("ID",             "typeID");
        TableColumn typeColumn     = tableWrapper.addColumn("Тип",            "type");
        TableColumn quantityColumn = tableWrapper.addColumn("Кол-во",         "quantity");
        TableColumn smetColumn     = tableWrapper.addColumn("Стоимоть",       "SmetCost");
        TableColumn smetSumColumn  = tableWrapper.addColumn("Себестоимость",  "SmetCostSum");
        TableColumn costHouseSumColumn  = tableWrapper.addColumn("Стоимость дома",  "costHouseSum");
        TableColumn saleColumn     = tableWrapper.addColumn("Цена",           "SaleCost");
        TableColumn saleSumColumn  = tableWrapper.addColumn("Выручка",        "SaleCostSum");
        TableColumn profitColumn   = tableWrapper.addColumn("Прибыль",        "profit");

        typeIdColumn    .setMaxWidth(50);
        typeIdColumn    .setMinWidth(35);
        typeColumn      .setMaxWidth(80);
        typeColumn      .setMinWidth(50);
        quantityColumn  .setMaxWidth(70);
        quantityColumn  .setMinWidth(50);

        TableFactory.setCellFactory(
                new DoubleStringConverter(),
                smetColumn,
                smetSumColumn,
                costHouseSumColumn,
                saleColumn,
                saleSumColumn,
                profitColumn
        );

        return tableWrapper;
    }

    /**
     * Create TableWrapper "OSR"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    @SuppressWarnings("Duplicates")
    static TableWrapper decorOSR(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table,new ItemOSRDAO());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<TableItemOSR,Double> valueAllColumn
                = tableWrapper.addColumn("Значение (общее)","expenses");
        TableColumn valueColumn    = tableWrapper.addColumn("Значение (за дом)","expensesPerHouse");

        valueAllColumn.setEditable(true);
//        valueColumn.setCellFactory(p -> TableCellFactory.getDecimalCell());
//        TableFactory.setTextFieldCell_NumberStringConverter(valueAllColumn);
        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueAllColumn,
                valueColumn
        );

        textColumn.setCellFactory(param -> TableCellFactory.getTestIdOSR());

        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemOSR, Double> t) -> {
            TableItemOSR editingItem = (TableItemOSR) t.getTableView().getItems().get(t.getTablePosition().getRow());
            editingItem.setExpenses(t.getNewValue());
            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.value());

            t.getTableView().refresh();
        });

        return tableWrapper;
    }

}
