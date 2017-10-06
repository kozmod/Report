package report.layoutControllers.planning;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.plan.ItemPlanDAO;
import report.entities.items.plan.TableItemPlan;
import report.models_view.data_utils.decimalFormatters.DoubleDFormatter;
import report.models_view.data_utils.decimalFormatters.IntegerDFormatter;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.TableCellFactory;
import report.models_view.nodes_factories.TableFactory;

import java.util.stream.Stream;

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

    public static TableWrapper decorPlan(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO(new ItemPlanDAO());

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


//        TableFactory.setTextFieldCell_IntegerStringConverter(typeIdColumn,quantityColumn);
        TableFactory.setCellFactoryAll(
                new IntegerDFormatter(),
                typeIdColumn,
                quantityColumn);
//        TableFactory.setTextFieldCell_NumberStringConverter(smetColumn,smetColumn );
        TableFactory.setCellFactoryAll(
                new DoubleDFormatter(),
                smetColumn,
                smetColumn
        );
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

        smetSumColumn.setCellFactory(param -> TableCellFactory.getDecimalCell());
        saleSumColumn.setCellFactory(param -> TableCellFactory.getDecimalCell());
        profitColumn.setCellFactory(param -> TableCellFactory.getDecimalCell());


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
        TableWrapper tableWrapper = new TableWrapper(table);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn typeIdColumn   = tableWrapper.addColumn("ID",             "typeID");
        TableColumn typeColumn     = tableWrapper.addColumn("Тип",            "type");
        TableColumn quantityColumn = tableWrapper.addColumn("Кол-во",         "quantity");
        TableColumn smetColumn     = tableWrapper.addColumn("Стоимоть",       "SmetCost");
        TableColumn smetSumColumn  = tableWrapper.addColumn("Себестоимость",  "SmetCostSum");
        TableColumn saleColumn     = tableWrapper.addColumn("Цена",           "SaleCost");
        TableColumn saleSumColumn  = tableWrapper.addColumn("Выручка",        "SaleCostSum");
        TableColumn profitColumn   = tableWrapper.addColumn("Прибыль",        "profit");

        typeIdColumn    .setMaxWidth(50);
        typeIdColumn    .setMinWidth(35);
        typeColumn      .setMaxWidth(80);
        typeColumn      .setMinWidth(50);
        quantityColumn  .setMaxWidth(70);
        quantityColumn  .setMinWidth(50);

        Stream.of(
                smetColumn,
                smetSumColumn,
                saleColumn,
                saleSumColumn,
                profitColumn)
                .forEach(a -> a.setCellFactory(param -> TableCellFactory.getDecimalCell()));


        return tableWrapper;
    }
}
