package report.layoutControllers.planning;

import javafx.scene.control.*;
import report.entities.items.discount_coef.DiscountQuery;
import report.entities.items.discount_coef.TableDItem;
import report.entities.items.osr.TableViewItemOSRDAO;
import report.entities.items.osr.TableItemOSR;
import report.entities.items.plan.TableViewItemPlanDAO;
import report.entities.items.plan.TableItemPlan;
import report.layoutControllers.planning.temp.*;
import report.models.coefficient.Quantity;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models.numberStringConverters.numberStringConverters.IntegerStringConverter;
import report.models_view.nodes.cells.AddTextFieldTableCell;
import report.models_view.nodes.node_wrappers.DiscountTreeTableWrapper;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.TableCellFactory;
import report.models_view.nodes.nodes_factories.TableFactory;

public class PlaningControllerTF implements TableFactory {


    private PlaningControllerTF() { }

    /**
     * Create TableWrapper "Plan"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */

    public static TableWrapper<TableItemPlan> decorPlan(TableView<TableItemPlan> table){
        TableWrapper<TableItemPlan> tableWrapper = new TableWrapper(table,new TableViewItemPlanDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn<TableItemPlan,Integer> typeIdColumn
                = tableWrapper.addColumn("ID","typeID");
        TableColumn typeColumn     = tableWrapper.addColumn("Тип","type");
        TableColumn<TableItemPlan,Integer> quantityColumn
                = tableWrapper.addColumn("Кол-во","quantity");
        TableColumn<TableItemPlan,Integer>  restColumn    = tableWrapper.addColumn("Остаток","rest");
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

        quantityColumn.setCellFactory(cell -> new AddTextFieldTableCell(new IntegerStringConverter(),tableWrapper.getSetAddingCells()));
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

//        TableFactory.setTextFieldTableCell(
//                new IntegerStringConverter(),
//                quantityColumn
//        );
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
        TableWrapper tableWrapper = new TableWrapper(table,new TableViewItemOSRDAO());

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


    /**
     * TreeTable
     * @param treeTable
     * @return
     */
    public static DiscountTreeTableWrapper decorKD(TreeTableView<TableDItem> treeTable){
        DiscountTreeTableWrapper treeTableWrapper = new DiscountTreeTableWrapper("Коэффициент Дисконтирования",treeTable, new DiscountQuery());

        TreeTableColumn<TableDItem, String> nameColumn =  new TreeTableColumn<>("Наименование");
        nameColumn.setSortable(false);
        nameColumn.setCellValueFactory((param) ->
                param.getValue().getValue().firstValueProperty()
        );

        TreeTableColumn<TableDItem, Double> valueColumn = new TreeTableColumn<>("Значение");
        valueColumn.setSortable(false);
        valueColumn.setCellValueFactory((param) ->
                param.getValue().getValue().secondValueProperty().asObject()
        );
        valueColumn.setCellFactory(p -> new EditingTreeTableCell(new DoubleStringConverter()));

        valueColumn.setOnEditCommit(event ->{
            final TreeItem<TableDItem> parent =  event.getTreeTablePosition().getTreeItem().getParent();
            final TreeItem<TableDItem> current =  event.getTreeTablePosition().getTreeItem();
            current.getValue().setSecondValue(event.getNewValue());
            event.getTreeTableView().getRoot().getValue().setSecondValue(Math.random());
            event.getTreeTableView().refresh();
        });



        valueColumn.setMaxWidth(120);
        valueColumn.setMinWidth(100);
        treeTable.getColumns().setAll(nameColumn, valueColumn);
//        treeTable.setRoot(dc.tree());
        treeTable.setEditable(true);
        treeTable.setShowRoot(false);

        return treeTableWrapper;

    }
//      public static TreeTableView decorKD(TreeTableView<TableDItem> treeTable){
////        DiscountCoef dc = new DiscountCoef(1,9.2,
////                new SpecificRisk(1,1d,1d,1d,1d,1d),
////                new MarketRisk(1,1d,1d,1d,1d,1d,1d,1d,1d)
////        );
//        TreeTableColumn<TableDItem, String> nameColumn =  new TreeTableColumn<>("Наименование");
//        nameColumn.setSortable(false);
//        nameColumn.setCellValueFactory((param) ->
//                param.getValue().getValue().firstValueProperty()
//        );
//
//        TreeTableColumn<TableDItem, Double> valueColumn = new TreeTableColumn<>("Значение");
//        valueColumn.setSortable(false);
//        valueColumn.setCellValueFactory((param) ->
//                param.getValue().getValue().secondValueProperty().asObject()
//        );
////        Callback<TreeTableColumn<TableDItem,Double>, TreeTableCell<TableDItem,Double>> defaultCellFactory2
////                =  ComboBoxTreeTableCell.forTreeTableColumn(2d,3d,4d);
//
////        Callback<TreeTableColumn<TableDItem,Double>, TreeTableCell<TableDItem,Double>> defaultCellFactory = TextFieldTreeTableCell.forTreeTableColumn(new DoubleStringConverter());
//        valueColumn.setCellFactory(p -> new EditingTreeTableCell(new DoubleStringConverter()));
//
//        valueColumn.setOnEditCommit(event ->{
//            final TreeItem<TableDItem> parent =  event.getTreeTablePosition().getTreeItem().getParent();
//            final TreeItem<TableDItem> current =  event.getTreeTablePosition().getTreeItem();
//            current.getValue().setSecondValue(event.getNewValue());
////           final double parentNewValue = parent.getChildren().stream().mapToDouble(i -> i.getValue().getSecondValue()).sum();
////           parent.getValue().setSecondValue(parentNewValue);
//            event.getTreeTableView().getRoot().getValue().setSecondValue(Math.random());
//            event.getTreeTableView().refresh();
//        });
//
//
////        Callback<TreeTableColumn<TableDItem,Double>, TreeTableCell<TableDItem,Double>> defaultCellFactory = TextFieldTreeTableCell.forTreeTableColumn(new DoubleStringConverter());
////        valueColumn.setCellFactory((TreeTableColumn<TableDItem,Double> tv) -> {
////            TreeTableCell<TableDItem,Double> cell = defaultCellFactory.call(tv);
////            cell.itemProperty().addListener((obs, oldTreeItem, newTreeItem) -> {
////                TreeItem<TableDItem> item = cell.getTreeTableView().getTreeItem(cell.getIndex());
////                if (newTreeItem == null) {
////                    cell.setEditable(false);
////                } else if ( item !=null && item.isLeaf()  ) {
////                    cell.setEditable(true);
////                } else {
////                    cell.setEditable(false);
////                }
////            });
////            return cell ;
////        });
//        valueColumn.setMaxWidth(120);
//        valueColumn.setMinWidth(100);
//        treeTable.getColumns().setAll(nameColumn, valueColumn);
////        treeTable.setRoot(dc.tree());
//        treeTable.setEditable(true);
//        treeTable.setShowRoot(false);
//
//        return treeTable;
//
//    }
//

}
