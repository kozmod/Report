
package report.models_view.nodes_factories;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import report.layoutControllers.EstimateController.Est;
import report.entities.items.account.TableItemAcc;
import report.models.coefficient.Quantity;
import report.entities.items.TableItem;
import report.entities.items.estimate.TableItemEst;
import report.entities.items.expenses.TableItemExpenses;
import report.entities.items.KS.TableItemKS;
import report.entities.items.osr.TableItemOSR;
import report.entities.items.period.TableItemPeriod;
import report.entities.items.plan.TableItemPlan;
import report.entities.items.site.TableItemPreview;
import report.entities.items.variable.TableItemVariable;
import report.entities.items.contractor.ItemContractorDAO;
import report.entities.items.estimate.ItemEstDAO;
import report.entities.items.expenses.ItemExpensesDAO;
import report.entities.items.KS.ItemKSDAO;
import report.entities.items.osr.ItemOSRDAO;
import report.entities.items.period.ItemPeriodDAO;
import report.entities.items.plan.ItemPlanDAO;
import report.entities.items.variable.ItemPropertiesFAO;

import report.models_view.data_utils.decimalFormatters.DFormatter;
import report.models_view.data_utils.decimalFormatters.DoubleDFormatter;
import report.models_view.data_utils.decimalFormatters.stringConvertersFX.StringNumberConverter;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes.TableWrapperEST;

@SuppressWarnings("unchecked")
public class TableFactory {

    private TableFactory() {}


    /**
     * Create TableWrapper(Preview TableWrapper) with 2 columns /Object/.
     *
     * @return TableWrapper(Preview TableWrapper)
     */
    public static TableView getSite(){
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        TableColumn titleCol = new TableColumn("Параметр");
        titleCol.setCellValueFactory(new PropertyValueFactory("firstValue"));

        TableColumn valueCol = new TableColumn("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));
        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());


        table.getColumns().addAll(titleCol, valueCol );

        return table;
    }

    /**
     * Create TableWrapper(TableWrapper EST).Contain columns and their options.
     * @see Est - enumeration of "Estimate Tables"
     * @param enumEst - enumeration. Contain: data of Estimate Tables
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST getEst(Est enumEst){
        TableWrapperEST table = new TableWrapperEST();
        table.setDAO(new ItemEstDAO(enumEst));

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn   = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn     = table.addColumn("Связанная работа",          "BindedJob");
        TableColumn valueColumn     = table.addColumn("Кол-во",                    "value");
        TableColumn unitColumn      = table.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_oneColumn = table.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sumColumn = table.addColumn("Стоимость (общая)",         "price_sum");
        TableColumn isInKSColumn    = table.addColumn("КС",                        "inKS");



        JM_nameColumn.setMinWidth(300);
        BJobColumnn.setMinWidth(160);

        BJobColumnn.setCellFactory(param ->   TableCellFactory.getOnDoubleMouseClickMoveToTextCell());



        valueColumn.setEditable(true);
        Price_oneColumn.setEditable(true);
//
        isInKSColumn.setCellFactory(param ->  TableCellFactory.getInKsColoredCell());
        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(enumEst));

        switch(enumEst){
            case Base :
                TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);

//
                valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends TableItem, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<? extends TableItem, Double> t) {

                        TableItem editingItem = (TableItem) t.getTableView().getItems().get(t.getTablePosition().getRow());

                        Double price_one = editingItem.getPrice_one();
                        Double value = t.getNewValue();

                        editingItem.setValue(value);
                        editingItem.setPrice_sum(value*price_one);

                        table.computeSum();
                        t.getTableView().refresh();
                        //Diseble Save & Cancel Context menu Item
//                    ((ContextMenuModelEst)table.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);

                    }
                });
                break;
            case Changed :
                table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null && ((TableItemEst)newSelection).getInKS()) {
                        table.setEditable(false);
                        Price_oneColumn.setEditable(false);
//                    System.out.println(((TableItemEst)newSelection).getInKS());
                    }else if (newSelection != null && !((TableItemEst)newSelection).getInKS()){
                        table.setEditable(true);
                        Price_oneColumn.setEditable(true);
//                    System.out.println(((TableItemEst)newSelection).getInKS());
                    }

                });
                break;
        }
        TableFactory.setTextFieldCell_NumberStringConverter(Price_oneColumn);
        Price_oneColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableItem, Double> t) {
                TableItem editingItem = (TableItem) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Double price_one = t.getNewValue();
                Double value     = editingItem.getValue();

                editingItem.setPrice_one(price_one);
                editingItem.setPrice_sum(value*price_one);

                table.computeSum();
                t.getTableView().refresh();

//                ((ContextMenuModelEst)table.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
            }
        });

        TableFactory.setTextFieldCell_NumberStringConverter(Price_sumColumn);


        switch(enumEst){
            case Base    : table.setContextMenu(ContextMenuFactory.getEst(table));         break;
            case Changed : table.setContextMenu(ContextMenuFactory.getEstPrint(enumEst));   break;
        }

        return table;
    }

    public static TableWrapper getAdditional(){
        TableWrapper tableWrapper =  new TableWrapper();

        tableWrapper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn   = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn     = tableWrapper.addColumn("Связанная работа",          "BindedJob");
        TableColumn valueColumn     = tableWrapper.addColumn("Кол-во",                    "value");
        TableColumn unitColumn      = tableWrapper.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_oneColumn = tableWrapper.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sumColumn = tableWrapper.addColumn("Стоимость (общая)",         "price_sum");
        TableColumn isInKSColumn    = tableWrapper.addColumn("КС",                        "inKS");

        JM_nameColumn.setMinWidth(300);
        BJobColumnn.setMinWidth(160);

        return tableWrapper;
    }

    /**
     * Create TableWrapper to AddSiteRowController.Contain columns and their options.
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


    /**
     * Create TableWrapper(TableWrapper KS).Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST<TableItemKS> getKS(){
        TableWrapperEST table = new TableWrapperEST();
        table.setDAO(new ItemKSDAO(Est.KS));

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn       = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn         = table.addColumn("Связанная работа",          "BindedJob");
        TableColumn BPartColumns        = table.addColumn("Часть",                     "bildingPart");
        TableColumn valueColumn         = table.addColumn("Кол-во",                    "value");
        TableColumn unitColumn          = table.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_oneColumn     = table.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sumColumn     = table.addColumn("Стоимость (общая)",         "price_sum");
        TableColumn restPriceSumColumn  = table.addColumn("Остаток (общий)",           "restOfValue");

        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(Est.KS));
        valueColumn.setEditable(true);

        TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);
        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends TableItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<? extends TableItem, Double> t) {

                TableItemKS editingItem = (TableItemKS) t.getRowValue();

                Double price_one = editingItem.getPrice_one();

                //Value of editing item in Chaged Tables
                Double valueInChanged = Est.Changed.findEqualsElevent(editingItem).getValue();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (Est.KS.getTabMap().values()
                                .stream()
                                .flatMap(mapItem ->((List)mapItem).stream())
                                .filter(editingItem::equalsSuperCalss)
                                .mapToDouble(filtered -> ((TableItem)filtered).getValue())
                                .sum()
                                - t.getOldValue()
                                + t.getNewValue());

                //set Value
                editingItem.setValue(t.getNewValue());

                //count new Price Sum
                editingItem.setPrice_sum(t.getNewValue()*price_one);

//                Set new Rest of Value
                editingItem.setRestOfValue(restOfValue);

                table.computeSum();
                t.getTableView().refresh();

            }
        });

        ContextMenu contextMenuKS = ContextMenuFactory.getCommonSU(table);
        table.setContextMenu(contextMenuKS);
//
//        Est.KS.getTabMap().values().forEach(new Consumer<ObservableList<TableItem>>() {
//            @Override
//            public void accept(ObservableList<TableItem> coll) {
//                coll.addListener((ListChangeListener.Change<? extends TableItem> c) -> {
//                    System.out.println("Changed on " + c);
//                    if(c.next() &&
//                            (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
//                        ((ContextMenuOptional) contexMenuKS).setDisable_SaveUndoPrint_groupe(false);
////                               contexMenuKS.setDisable_SaveUndoPrint_groupe(false);
//                    }
//                });
//            }
//        });
        return  table;
    }

    /**
     * Create TableWrapper(AddKSController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST getKS_add(){
        TableWrapperEST table = new TableWrapperEST();

//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BPartColumn   = table.addColumn("Часть",                     "bildingPart");
        TableColumn BJobColumnn   = table.addColumn("Связанная работа",          "BindedJob");

        return  table;
    }

    /**
     * Create TableWrapper(ExpensesAddLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public  static TableWrapper<TableItemPreview> getProperty_Site(){
        TableWrapper tableWrapper = new TableWrapper();

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableWrapper.getTableView().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                // Get the tableWrapper header
                Pane header = (Pane) tableWrapper.getTableView().lookup("TableHeaderRow");
                if(header!=null && header.isVisible()) {
                    header.setMaxHeight(0);
                    header.setMinHeight(0);
                    header.setPrefHeight(0);
                    header.setVisible(false);
                    header.setManaged(false);
                }
            }
        });

        TableColumn titleColumn  = tableWrapper.addColumn("Параметр", "firstValue");
        TableColumn valueColumn  = tableWrapper.addColumn("Значение", "secondValue");


        valueColumn.setCellFactory(param ->  TableCellFactory.getEditSiteCell());


        return tableWrapper;
    }

    /**
     * Create TableWrapper "expenses"(expensesesLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */

    public static TableWrapper<TableItemExpenses> getProperty_Expenses(){
        TableWrapper tableWrapper = new TableWrapper();
        tableWrapper.setDAO( new ItemExpensesDAO());

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn textColumn   = tableWrapper.addColumn("Наименование", "text");
        TableColumn typeColumn   = tableWrapper.addColumn("Тип",          "type");
        TableColumn valueColumn  = tableWrapper.addColumn("Значение",     "value");

        typeColumn.setCellFactory(param ->  TableCellFactory.getExpenseesCell());

        return tableWrapper;
    }

    /**
     * Create TableWrapper "Job & Period"(expensesesLayoutController).
     * <br>
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapper<TableItemPeriod> getProperty_JobPeriod(){
        TableWrapper tableWrapper = new TableWrapper();
        tableWrapper.setDAO( new ItemPeriodDAO());

        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn dateFromColumn = tableWrapper.addColumn("Датаначала",     "dateFrom");
        TableColumn dateToColumn   = tableWrapper.addColumn("Дата Окончания", "dateTo");
        TableColumn textColumns    = tableWrapper.addColumn("Коментарии",     "text");

        dateFromColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());
        dateToColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());


        return tableWrapper;
    }

    /**
     * Create TableWrapper "ChangeView"(expensesesLayoutController).
     * <br>
     * Show SQL table Items where dell = 1
     * <br>
     *
     * @return TableView
     */
    public static TableView getChangeView(){
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn columnV = new TableColumn("K");
        columnV.setCellValueFactory(new PropertyValueFactory("value"));


        TableColumn columnPO = new TableColumn("Onew");
        columnPO.setCellValueFactory(new PropertyValueFactory("price_one"));

        TableColumn columnPS = new TableColumn("Sum");
        columnPS.setCellValueFactory(new PropertyValueFactory("price_sum"));

        TableColumn columnDate = new TableColumn("Date");
        columnDate.setCellValueFactory(new PropertyValueFactory("dateCreate"));
        columnDate.setMinWidth(80);

        columnV .setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());
        columnPO.setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());
        columnPS.setCellFactory(param ->  TableCellFactory.getEqualsToAboveCell());

        table.getColumns().addAll(columnV, columnPO, columnPS, columnDate );

        return table;

    }

    /**
     * Create TableWrapper "OSR"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    public static TableWrapper decorOSR(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO(new ItemOSRDAO());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<TableItemOSR,Double> valueAllColumn
                = tableWrapper.addColumn("Значение (общее)","expenses");
        TableColumn valueColumn    = tableWrapper.addColumn("Значение (за дом)","expensesPerHouse");

        valueAllColumn.setEditable(true);
        valueColumn.setCellFactory(p -> TableCellFactory.getDecimalCell());
        TableFactory.setTextFieldCell_NumberStringConverter(valueAllColumn);

        textColumn.setCellFactory(param -> TableCellFactory.getTestIdOSR());

//        ContextMenu cm = ContextMenuFactory.getOSR(tableWrapper);
//        tableWrapper.setContextMenu(cm);

        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemOSR, Double> t) -> {
            TableItemOSR editingItem = (TableItemOSR) t.getTableView().getItems().get(t.getTablePosition().getRow());

            editingItem.setExpenses(t.getNewValue());
            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.getQuantityValue());

            t.getTableView().refresh();
            //Diseble Save & Cancel Context menu Item
        });

        return tableWrapper;
    }

    /**
     * Create TableWrapper "Variable"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    public static TableWrapper decorVariable(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO(new ItemPropertiesFAO());

//        tableWrapper.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn textColumn     = tableWrapper.addColumn("Наименование","text");
        TableColumn<TableItemVariable,Double> valueAllColumn
                = tableWrapper.addColumn("Значение",   "value");
//
        valueAllColumn.setEditable(true);
        TableFactory.setTextFieldCell_NumbertringConverter_threeZeroes(valueAllColumn);
        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemVariable, Double> t) -> {
            t.getRowValue().setValue(t.getNewValue());
        });


        return tableWrapper;
    }

    /**
     * Create TableWrapper "Contractor"(AllPropertiesController).
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     *
     * @return TableWrapper
     */
    public static TableWrapper decorContractor(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setDAO(new ItemContractorDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn idColumn     = tableWrapper.addColumn("ID", "id");
        TableColumn contractorColumn = tableWrapper.addColumn("Подрядчик","contractor");


        idColumn.setMaxWidth(50);
        idColumn.setMinWidth(35);

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


        TableFactory.setTextFieldCell_IntegerStringConverter(typeIdColumn,quantityColumn);
        TableFactory.setTextFieldCell_NumberStringConverter(smetColumn,saleColumn );

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
    /**
     * Create TableWrapper "FinRes"(FinResController).
     * <br>
     * <br>
     *
     * @return TableWrapper
     */
//    public static TableView getFinRes(){
//        TableView table = new TableView();
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        TableColumn siteNumberColumn = new TableColumn("№ учстка");
//        siteNumberColumn.setCellValueFactory(new PropertyValueFactory("siteNumber"));
//
//        TableColumn contractorColumn = new TableColumn("Субподрядчик");
//        contractorColumn.setCellValueFactory(new PropertyValueFactory("contractor"));
//
//        TableColumn NContractColumn  = new TableColumn("№ договора");
//        NContractColumn.setCellValueFactory(new PropertyValueFactory("NContract"));
//
//        TableColumn dateContColumn   = new TableColumn("Дата договора");
//        dateContColumn.setCellValueFactory(new PropertyValueFactory("dateContract"));
//        dateContColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
//
//        TableColumn finishBuildColumn= new TableColumn("Окончание строительства");
//        finishBuildColumn.setCellValueFactory(new PropertyValueFactory("finishBuilding"));
//
//        TableColumn smetCostColumn   = new TableColumn("Сметная стоимость");
//        smetCostColumn.setCellValueFactory(new PropertyValueFactory("smetCost"));
//
//        TableColumn costHouseColumn  = new TableColumn("Стоимость дома");
//        costHouseColumn.setCellValueFactory(new PropertyValueFactory("costHouse"));
//
//        TableColumn saleHouseColumn  = new TableColumn("Цена продажи");
//        saleHouseColumn.setCellValueFactory(new PropertyValueFactory("SaleHouse"));
//
////        TableColumn trueCostColumn   = new TableColumn("TRUECOST");
////        trueCostColumn.setCellValueFactory(new PropertyValueFactory("trueCost"));
//
//        TableColumn profitColumn     = new TableColumn("Прибыль");
//        profitColumn.setCellValueFactory(new PropertyValueFactory("profit"));
//
//
//        finishBuildColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
////        trueCostColumn.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//
//        table.getColumns().setAll(
//                siteNumberColumn,
//                contractorColumn,
//                NContractColumn,
//                dateContColumn ,
//                finishBuildColumn,
//                saleHouseColumn,
//                smetCostColumn,
//                costHouseColumn,
////                trueCostColumn,
//                profitColumn
//        );
//
//        return table;
//    }

    /**
     * Decorate PreviewT TableView (RootLayoutController)
     * @param table
     */
    public static void decorPreview(TableView table){

        TableWrapper tableWrapper = new TableWrapper(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPrefHeight(450);

        TableColumn titleCol = tableWrapper.addColumn ("Параметр", "firstValue");
        TableColumn valueCol = tableWrapper.addColumn ("Значение", "secondValue");

        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());

    }

    /**
     * Decorate Intro TableView (IntroLayoutController)
     * @param table
     */
    public static void decorIntroFinishedSite(TableView table){

        TableWrapper tableWrapper = new TableWrapper(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));



        TableColumn siteNumberCol = tableWrapper.addColumn ("Участок", "siteNumber");
        TableColumn typeHomeCol   = tableWrapper.addColumn ("ТипДома", "typeHome");
        TableColumn smetCostCol   = tableWrapper.addColumn ("Стоимоть", "smetCost");
        TableColumn saleCostCol   = tableWrapper.addColumn ("Цена",     "saleCost");

        smetCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());
        saleCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());


    }

    /**
     * Decorate FinRes TableView (FinResController)
     * @param table
     */
    @SuppressWarnings("unchecked")
    public static void decorFinRes(TableView table){

        TableWrapper tableWrapper = new TableWrapper(table);
        tableWrapper.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn siteNumberColumn  = tableWrapper.addColumn("№ учстка", "siteNumber");
        TableColumn contractorColumn  = tableWrapper.addColumn("Субподрядчик","contractor");
        TableColumn NContractColumn   = tableWrapper.addColumn("№ договора", "NContract");
        TableColumn dateContColumn    = tableWrapper.addColumn("Дата договора", "dateContract");
        TableColumn finishBuildColumn = tableWrapper.addColumn("Окончание строительства", "finishBuilding");
        TableColumn smetCostColumn    = tableWrapper.addColumn("Сметная стоимость", "smetCost");
        TableColumn costHouseColumn   = tableWrapper.addColumn("Стоимость дома", "costHouse");
        TableColumn saleHouseColumn   = tableWrapper.addColumn("Цена продажи","SaleHouse");
//        TableColumn trueCostColumn    = tableWrapper.addColumn("TRUECOST", "trueCost");
        TableColumn profitColumn      = tableWrapper.addColumn("Прибыль", "profit");


        dateContColumn.setCellFactory   (cell -> TableCellFactory.getEpochDateCell());
        finishBuildColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
        smetCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
        costHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
        saleHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
//        trueCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
        profitColumn.setCellFactory     (cell -> TableCellFactory.getDecimalCell());
    }


    /**
     * Decorate KS_add TableView (AddKSController)
     */
    public static void decorAddKS(TableView table){
        TableWrapper tableWrapper = new TableWrapper(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn JM_nameColumn = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BPartColumn   = tableWrapper.addColumn("Часть",                     "bildingPart");
        TableColumn BJobColumnn   = tableWrapper.addColumn("Связанная работа",          "BindedJob");


    }



    /**
     * Decorate KS_add TableView (AddKSController)
     */
    public static void decorAcc(TableView<TableItemAcc> table){

        TableWrapper tableWrapper = new TableWrapper(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn dateCol = tableWrapper.addColumn("Дата","date");
        TableColumn numCol  = tableWrapper.addColumn(" № ","num");

        //***************************  Client Column  ********************************
        TableColumn<TableItemAcc,Objects> ClientCol = new TableColumn<>("Клиент");
        TableColumn ITNCol        = tableWrapper.addColumn(ClientCol, "ИНН",         "ITN_Client");
        TableColumn nameClientCol = tableWrapper.addColumn(ClientCol, "Наименование","name_Client");
        TableColumn accClientCol  = tableWrapper.addColumn(ClientCol, "Счет",        "accNum_Client");
        table.getColumns().add(ClientCol);

        //***************************  Correspondent Column  ***************************
        TableColumn<TableItemAcc,Objects> CorCol = new TableColumn<>("Корреспондент");
        TableColumn BICCOrCol   = tableWrapper.addColumn(CorCol,"BIC",         "BIC_Cor");
        TableColumn accCorCol   = tableWrapper.addColumn(CorCol,"Счет",        "accNum_Cor");
        TableColumn nameCorCol  = tableWrapper.addColumn(CorCol,"Наименование","name_Cor");
        table.getColumns().add(CorCol);

        TableColumn VOCol   = tableWrapper.addColumn("ВО",        "VO");
        TableColumn dascCol = tableWrapper.addColumn("Содержание","description");

        //***************************  Turnover Column  ********************************
        TableColumn<TableItemAcc,Objects> TurnoverCol = new TableColumn<>("Обороты");
        TableColumn  debCol         = tableWrapper.addColumn(TurnoverCol,"Дебет",  "deb");
        TableColumn credCol         = tableWrapper.addColumn(TurnoverCol,"Кредит", "cred");
        TableColumn outgoingRestCol = tableWrapper.addColumn(TurnoverCol,"Остаток","outgoingRest");
        table.getColumns().add(TurnoverCol);


        dateCol.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
        debCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
        credCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
        outgoingRestCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());


    }

    //Methods ==============================================================================================
//    private static void  setTextFieldCell_FloatStringConverter(TableColumn ... columns){
//        for(TableColumn column  : columns)
//            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Float>(){
//
//                // chage Float to string in Value Column
//                @Override
//                public String formatNumber(Float object) {
//                    return new DecimalFormat("0.00").format(object);
//                }
//
//                @Override
//                public Float fromString(String string) {
//                    Float num = new Float(0);
//                    try {
//                      num =  new DecimalFormat("0.00").parse(string).floatValue();
//                    } catch (ParseException ex) {
//                        Logger.getLogger(TableFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    return num;
//                }
//
//            }));
//    }
//    
//    private static void  setTextFieldCell_DoubleStringConverter(TableColumn ... columns){
//        for(TableColumn column  : columns)
//            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>(){
//
//                // chage Float to string in Value Column
//                @Override
//                public String formatNumber(Double object) {
//                    return new DecimalFormat("0.00").format(object);
//                }
//
//                @Override
//                public Double fromString(String string) {
//                    Double num = new Double(0);
//                    try {
//                      num =  new DecimalFormat("0.00").parse(string).doubleValue();
//                    } catch (ParseException ex) {
//                        Logger.getLogger(TableFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    return num;
//                }
//
//            }));
//    }


    private static  void setTextFieldCell_NumberStringConverter(TableColumn ... columns){


        DoubleDFormatter dFormatter = new DoubleDFormatter();
            for(TableColumn column  : columns)
                column.setCellFactory(
                        TextFieldTableCell.forTableColumn(
                                new StringConverter<Double>(){
                // change Float to string in Value Column
                @Override
                public String toString(Double object) {
                    return dFormatter.toString(object);
//                    return new DecimalFormat("0.00").format(object);
                }

                @Override
                public Double fromString(String string) {
//                    Number num = new Double(0);
//                    try {
//                      num =  new DecimalFormat("0.00").parse(string).doubleValue();
//                    } catch (ParseException ex) {
//                        Logger.getLogger(TableFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                        return dFormatter.fromString(string);

                }

            }));
    }

    /**
     *Apply StringConverter to all input cells.
     *@param formatter DFormatter(DecimalFormatSymbols & DecimalFormat)
     *@param columns TableColumn[]
     */
    private static  void setCellFactoryAll(DFormatter formatter, TableColumn ... columns){
            for(TableColumn column  : columns)
                column.setCellFactory(
                        TextFieldTableCell.forTableColumn(
                                new StringNumberConverter(formatter)
                        )
                );
    }



    private static  void  setTextFieldCell_NumbertringConverter_threeZeroes(TableColumn ... columns){
        DoubleDFormatter dFormatter = new DoubleDFormatter("###,##0.000");
        for(TableColumn column  : columns)
            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>(){
                @Override
                public String toString(Number object) {
//                    return DecimalFormatter.toString_threeZeroes(object);
                    return dFormatter.toString(object);

                    }

                @Override
                public Number fromString(String string) {
                    return dFormatter.fromString(string);
                }

            }));
    }

    private static void  setTextFieldCell_IntegerStringConverter(TableColumn ... columns){
        for(TableColumn column  : columns)
            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){

                // chage Float to string in Value Column
                @Override
                public String toString(Integer object) {
                    return Integer.toString(object);
                }

                @Override
                public Integer fromString(String string) {
                    return new Integer(string);
                }

            }));
    }
//=======================================================================================================        


}
