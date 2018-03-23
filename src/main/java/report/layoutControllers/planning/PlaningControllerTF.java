package report.layoutControllers.planning;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import report.entities.items.DItem;
import report.entities.items.discount_coef.DiscountQuery;
import report.entities.items.osr.OSR_TIV;
import report.entities.items.osr.OSR_DAO;
import report.entities.items.plan.PlanTIV;
import report.entities.items.plan.PlanDAO;
import report.layoutControllers.planning.temp.*;
import report.models.coefficient.Quantity;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.converters.numberStringConverters.IntegerStringConverter;
import report.models.view.wrappers.tableWrappers.DiscountTreeTableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;

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

    public static TableWrapper<PlanTIV> decorPlan(TableView<PlanTIV> table){
        TableWrapper<PlanTIV> tableWrapper = new TableWrapper(table,new PlanDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn<PlanTIV,Integer> typeIdColumn
                = tableWrapper.addColumn("ID","typeID");
        TableColumn typeColumn     = tableWrapper.addColumn("Тип","type");
        TableColumn<PlanTIV,Integer> quantityColumn
                = tableWrapper.addColumn("Кол-во","quantity");
        TableColumn<PlanTIV,Integer>  restColumn    = tableWrapper.addColumn("Остаток","rest");
        TableColumn<PlanTIV,Double> smetColumn
                = tableWrapper.addColumn("Стоимоть","SmetCost");
        TableColumn smetSumColumn  = tableWrapper.addColumn("Себестоимость", "SmetCostSum");
        TableColumn<PlanTIV,Double> saleColumn
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

        typeIdColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PlanTIV, Integer> t) -> t.getRowValue().setTypeID(t.getNewValue())
        );

        quantityColumn.setOnEditCommit((TableColumn.CellEditEvent<PlanTIV, Integer> t) -> {
            PlanTIV editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setQuantity(t.getNewValue());
                editingItem.setSaleCostSum((double) (t.getNewValue() * editingItem.getQuantity()));
                editingItem.setSmetCostSum((double) (t.getNewValue() * editingItem.getQuantity()));
            }

        });
        smetColumn.setOnEditCommit((TableColumn.CellEditEvent<PlanTIV, Double> t) -> {
            PlanTIV editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setSmetCost(t.getNewValue());
                editingItem.setSmetCostSum(t.getNewValue() * editingItem.getQuantity());
                editingItem.setProfit(editingItem.getSaleCost() - editingItem.getSmetCost());
            }
        });
        saleColumn.setOnEditCommit((TableColumn.CellEditEvent<PlanTIV, Double> t) -> {
            PlanTIV editingItem =  t.getRowValue();

            if(t.getNewValue() != null){
                editingItem.setSaleCost(t.getNewValue());
                editingItem.setSaleCostSum(t.getNewValue() * editingItem.getQuantity());
                editingItem.setProfit(editingItem.getSaleCost() - editingItem.getSmetCost());
            }
        });

        //TODO: add "ROW_Adding" mechanism )))
//        quantityColumn.setCellFactory(cell -> new AddTextFieldTableCell(new IntegerStringConverter(),tableWrapper.getSetAddingCells()));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
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
        TableWrapper tableWrapper = new TableWrapper(table,new OSR_DAO());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<OSR_TIV,Double> valueAllColumn
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

        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<OSR_TIV, Double> t) -> {
            OSR_TIV editingItem = (OSR_TIV) t.getTableView().getItems().get(t.getTablePosition().getRow());
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
    public static DiscountTreeTableWrapper decorKD(TreeTableView<DItem> treeTable){
        DiscountTreeTableWrapper treeTableWrapper = new DiscountTreeTableWrapper("Коэффициент Дисконтирования",treeTable, new DiscountQuery());

        TreeTableColumn<DItem, String> nameColumn =  new TreeTableColumn<>("Наименование");
        nameColumn.setSortable(false);
        nameColumn.setCellValueFactory((param) ->
                param.getValue().getValue().firstValueProperty()
        );

        TreeTableColumn<DItem, Double> valueColumn = new TreeTableColumn<>("Значение");
        valueColumn.setSortable(false);
        valueColumn.setCellValueFactory((param) ->
                param.getValue().getValue().secondValueProperty().asObject()
        );
        valueColumn.setCellFactory(p -> new EditingTreeTableCell(new DoubleStringConverter()));
        valueColumn.setOnEditCommit(event ->{
            final TreeItem<DItem> current =  event.getTreeTablePosition().getTreeItem();
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
//      public static TreeTableView decorKD(TreeTableView<DItem> treeTable){
////        DiscountCoef dc = new DiscountCoef(1,9.2,
////                new SpecificRisk(1,1d,1d,1d,1d,1d),
////                new MarketRisk(1,1d,1d,1d,1d,1d,1d,1d,1d)
////        );
//        TreeTableColumn<DItem, String> nameColumn =  new TreeTableColumn<>("Наименование");
//        nameColumn.setSortable(false);
//        nameColumn.setCellValueFactory((param) ->
//                param.getQuantity().getQuantity().firstValueProperty()
//        );
//
//        TreeTableColumn<DItem, Double> valueColumn = new TreeTableColumn<>("Значение");
//        valueColumn.setSortable(false);
//        valueColumn.setCellValueFactory((param) ->
//                param.getQuantity().getQuantity().secondValueProperty().asObject()
//        );
////        Callback<TreeTableColumn<DItem,Double>, TreeTableCell<DItem,Double>> defaultCellFactory2
////                =  ComboBoxTreeTableCell.forTreeTableColumn(2d,3d,4d);
//
////        Callback<TreeTableColumn<DItem,Double>, TreeTableCell<DItem,Double>> defaultCellFactory = TextFieldTreeTableCell.forTreeTableColumn(new DoubleStringConverter());
//        valueColumn.setCellFactory(p -> new EditingTreeTableCell(new DoubleStringConverter()));
//
//        valueColumn.setOnEditCommit(event ->{
//            final TreeItem<DItem> parent =  event.getTreeTablePosition().getTreeItem().getParent();
//            final TreeItem<DItem> current =  event.getTreeTablePosition().getTreeItem();
//            current.getQuantity().setSecondValue(event.getNewValue());
////           final double parentNewValue = parent.getChildren().stream().mapToDouble(i -> i.getQuantity().getSecondValue()).sum();
////           parent.getQuantity().setSecondValue(parentNewValue);
//            event.getTreeTableView().getRoot().getQuantity().setSecondValue(Math.random());
//            event.getTreeTableView().refresh();
//        });
//
//
////        Callback<TreeTableColumn<DItem,Double>, TreeTableCell<DItem,Double>> defaultCellFactory = TextFieldTreeTableCell.forTreeTableColumn(new DoubleStringConverter());
////        valueColumn.setCellFactory((TreeTableColumn<DItem,Double> tv) -> {
////            TreeTableCell<DItem,Double> cell = defaultCellFactory.call(tv);
////            cell.itemProperty().addListener((obs, oldTreeItem, newTreeItem) -> {
////                TreeItem<DItem> item = cell.getTreeTableView().getTreeItem(cell.getIndex());
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
