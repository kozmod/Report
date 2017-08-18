
package report.view_models.nodes_factories;

import java.util.List;
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
import report.controllers.showEstLayoutController.Est;
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

import report.view_models.data_models.DecimalFormatter;
import report.view_models.nodes.Table;
import report.view_models.nodes.TableEST;

@SuppressWarnings("unchecked")
public class TableFactory {
    
    private TableFactory() {}

    
    /**
     * Create Table(Preview Table) with 2 columns /Object/.
     * 
     * 
     * @return Table(Preview Table)
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
     * Create Table(Table EST).Contain columns and their options.
     * @see Est - enumeration of "Estimate Tables"
     * @param enumEst - enumeration. Contain: data of Estimate Tables
     * @return Table(child of TableView)
     */
    public static TableEST getEst(Est enumEst){
        TableEST table = new TableEST();
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
        
        valueColumn.setEditable(true);
        Price_oneColumn.setEditable(true);
//        
        isInKSColumn.setCellFactory(param ->  TableCellFactory.getInKsColoredCell());
        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(enumEst));

        switch(enumEst){
            case Base :
            TableFactory.setTextFieldCell_NumbertringConverter(valueColumn);
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
        TableFactory.setTextFieldCell_NumbertringConverter(Price_oneColumn);
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

        switch(enumEst){
            case Base    : table.setContextMenu(ContextMenuFactory.getEst(table));         break;
            case Changed : table.setContextMenu(ContextMenuFactory.getEstPrint(enumEst));   break;
        }
        
      return table;
    }
    
     public static Table getAdditional(){
        Table table =  new Table();
        
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
        
        return table;
     }
    
    /**
     * Create Table to addSiteRowLayoutController.Contain columns and their options.
     * @return Table(child of TableView)
     */
    public static TableEST getEst_add(){
        TableEST table = new TableEST();
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        TableColumn CheckBoxColumn = table.addColumn(" * ",                       "check");
        TableColumn JM_nameColumn  = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn    = table.addColumn("Связанная работа",          "BindedJob");
        TableColumn valueColumn    = table.addColumn("Кол-во",                    "value");
        TableColumn unitColumn     = table.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_one      = table.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sum      = table.addColumn("Стоимость (общая)",         "price_sum");
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
      
      return  table;  
    }
    
    
    /**
     * Create Table(Table KS).Contain columns and their options.
     * @return Table(child of TableView)
     */
    public static TableEST<TableItemKS> getKS(){
        TableEST table = new TableEST();
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
        
        TableFactory.setTextFieldCell_NumbertringConverter(valueColumn);
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
                
                ((TableEST)t.getTableView()).computeSum();
                t.getTableView().refresh();
                
            }
        });
        
        ContextMenu contexMenuKS = ContextMenuFactory.getCommonSU(table);
        table.setContextMenu(contexMenuKS);
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
     * Create Table(ksAddLayoutController).
     * <br>
     * @return Table(child of TableView)
     */
    public static TableEST getKS_add(){
        TableEST table = new TableEST();
        
//        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BPartColumn   = table.addColumn("Часть",                     "bildingPart");
        TableColumn BJobColumnn   = table.addColumn("Связанная работа",          "BindedJob");
        
        return  table;
    }
    
    /**
     * Create Table(ExpensesAddLayoutController). 
     * <br>
     * @return Table(child of TableView)
     */
    public  static Table<TableItemPreview> getProperty_Site(){
        Table table = new Table();
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        table.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                // Get the table header
                Pane header = (Pane)table.lookup("TableHeaderRow");
                if(header!=null && header.isVisible()) {
                  header.setMaxHeight(0);
                  header.setMinHeight(0);
                  header.setPrefHeight(0);
                  header.setVisible(false);
                  header.setManaged(false);
                }
            }
        });
        
        TableColumn titleColumn  = table.addColumn("Параметр", "firstValue");
        TableColumn valueColumn  = table.addColumn("Значение", "secondValue");


        valueColumn.setCellFactory(param ->  TableCellFactory.getEditSiteCell());
     
        
       return  table; 
    }
    
    /**
     * Create Table "expenses"(expensesesLayoutController).
     * <br>
     * @return Table(child of TableView)
     */
       
    public static Table<TableItemExpenses> getProperty_Expenses(){
        Table table = new Table();
        table.setDAO( new ItemExpensesDAO());
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        

        TableColumn textColumn   = table.addColumn("Наименование", "text");
        TableColumn typeColumn   = table.addColumn("Тип",          "type");
        TableColumn valueColumn  = table.addColumn("Значение",     "value");

        typeColumn.setCellFactory(param ->  TableCellFactory.getExpenseesCell());
        
       return  table; 
    }
    
    /**
     * Create Table "Job & Period"(expensesesLayoutController). 
     * <br>
     * @return Table(child of TableView)
     */
    public static Table<TableItemPeriod>  getProperty_JobPeriod(){
        Table table = new Table();
        table.setDAO( new ItemPeriodDAO());
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn dateFromColumn = table.addColumn("Датаначала",     "dateFrom");
        TableColumn dateToColumn   = table.addColumn("Дата Окончания", "dateTo");
        TableColumn textColumns    = table.addColumn("Коментарии",     "text");

        dateFromColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());
        dateToColumn.setCellFactory(param -> TableCellFactory.getEpochDateCell());
       
        
       return  table; 
    }
    
    /**
     * Create Table "ChangeView"(expensesesLayoutController). 
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
        
        columnV .setCellFactory(param ->  TableCellFactory.getEquialToAboveCell());
        columnPO.setCellFactory(param ->  TableCellFactory.getEquialToAboveCell());
        columnPS.setCellFactory(param ->  TableCellFactory.getEquialToAboveCell());
        
        table.getColumns().addAll(columnV, columnPO, columnPS, columnDate );
       
        return table;
       
    }
    
    /**
     * Create Table "OSR"(allPropertiesLayoutController). 
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     * 
     * @return Table
     */
    public static Table getOSR(){
        Table table = new Table();
        table.setDAO(new ItemOSRDAO());
        
//        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn textColumn     = table.addColumn("Наименование","text");
        TableColumn<TableItemOSR,Double> valueAllColumn
                                   = table.addColumn("Значение (общее)","expenses");
        TableColumn valueColumn    = table.addColumn("Значение (за дом)","expensesPerHouse");
        
        valueAllColumn.setEditable(true);
        valueColumn.setCellFactory(p -> TableCellFactory.getDecimalCell());
        TableFactory.setTextFieldCell_NumbertringConverter(valueAllColumn);
        
        textColumn.setCellFactory(param -> TableCellFactory.getTestIdOSR());

//        ContextMenu cm = ContextMenuFactory.getOSR(table);
//        table.setContextMenu(cm);
        
        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemOSR, Double> t) -> {
            TableItemOSR editingItem = (TableItemOSR) t.getTableView().getItems().get(t.getTablePosition().getRow());
            
            editingItem.setExpenses(t.getNewValue());
            editingItem.setExpensesPerHouse(t.getNewValue() / Quantity.getQuantityValue());
            
            t.getTableView().refresh();
            //Diseble Save & Cancel Context menu Item
        });
        
        return table;
    }
    
    /**
     * Create Table "Variable"(allPropertiesLayoutController). 
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     * 
     * @return Table
     */
    public static Table getVariable(){
        Table table = new Table();
        table.setDAO(new ItemPropertiesFAO());
        
//        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        
        TableColumn textColumn     = table.addColumn("Наименование","text");
        TableColumn<TableItemVariable,Double> valueAllColumn
                                    = table.addColumn("Значение",   "value");
//        
        valueAllColumn.setEditable(true);
        TableFactory.setTextFieldCell_NumbertringConverter(valueAllColumn);
        valueAllColumn.setOnEditCommit((TableColumn.CellEditEvent<TableItemVariable, Double> t) -> {
            t.getRowValue().setValue(t.getNewValue());
        });

        
        return table;
    }
    
    /**
     * Create Table "Contractor"(allPropertiesLayoutController). 
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     * 
     * @return Table
     */
    public static Table getContractor(){
        Table table = new Table();
        table.setDAO(new ItemContractorDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        
        TableColumn idColumn     = table.addColumn("ID", "id");
        TableColumn contractorColumn = table.addColumn("Подрядчик","contractor");
        
   
        idColumn.setMaxWidth(50);
        idColumn.setMinWidth(35);
 
        return table;
    }
    
    /**
     * Create Table "Plan"(allPropertiesLayoutController). 
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     * 
     * @return Table
     */

    public static Table getPlan(){
        Table table = new Table();
        table.setDAO(new ItemPlanDAO());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
        TableColumn<TableItemPlan,Integer> typeIdColumn
                                   = table.addColumn("ID",             "typeID");
        TableColumn typeColumn     = table.addColumn("Тип",            "type");
        TableColumn<TableItemPlan,Integer> quantityColumn 
                                   = table.addColumn("Кол-во",         "quantity");
        TableColumn restColumn     = table.addColumn("Остаток",        "rest");
        TableColumn<TableItemPlan,Double> smetColumn
                                   = table.addColumn("Стоимоть",       "SmetCost");
        TableColumn smetSumColumn  = table.addColumn("Себестоимость",  "SmetCostSum");
        TableColumn<TableItemPlan,Double> saleColumn
                                   = table.addColumn("Цена",           "SaleCost");
        TableColumn saleSumColumn  = table.addColumn("Выручка",        "SaleCostSum");
        TableColumn profitColumn   = table.addColumn("Прибыль",        "profit");
        
        typeIdColumn    .setMaxWidth(50);
        typeIdColumn    .setMinWidth(35);
        typeColumn      .setMaxWidth(80);
        typeColumn      .setMinWidth(50);
        quantityColumn  .setMaxWidth(70);
        quantityColumn  .setMinWidth(50);
        restColumn      .setMaxWidth(80);
        restColumn      .setMinWidth(50);
        
     
        TableFactory.setTextFieldCell_IntegerStringConverter(typeIdColumn,quantityColumn);
        TableFactory.setTextFieldCell_NumbertringConverter(smetColumn,saleColumn );
        
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

        
        return table;
    }
    /**
     * Create Table "Plan"(allPropertiesLayoutController). 
     * <br>
     * Show SQL table Items where dell = 0
     * <br>
     * 
     * @return Table
     */
    public static Table getFact(){
        Table table = new Table();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        
        TableColumn typeIdColumn   = table.addColumn("ID",             "typeID");
        TableColumn typeColumn     = table.addColumn("Тип",            "type");
        TableColumn quantityColumn = table.addColumn("Кол-во",         "quantity");
        TableColumn smetColumn     = table.addColumn("Стоимоть",       "SmetCost");
        TableColumn smetSumColumn  = table.addColumn("Себестоимость",  "SmetCostSum");
        TableColumn saleColumn     = table.addColumn("Цена",           "SaleCost");
        TableColumn saleSumColumn  = table.addColumn("Выручка",        "SaleCostSum");
        TableColumn profitColumn   = table.addColumn("Прибыль",        "profit");
        
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
        
        
        return table;
    }
    /**
     * Create Table "FinRes"(finResLayoutController).
     * <br>
     * <br>
     *
     * @return Table
     */
    public static TableView getFinRes(){
        TableView table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn siteNumberColumn = new TableColumn("№ учстка");
        siteNumberColumn.setCellValueFactory(new PropertyValueFactory("siteNumber"));

        TableColumn contractorColumn = new TableColumn("Субподрядчик");
        contractorColumn.setCellValueFactory(new PropertyValueFactory("contractor"));

        TableColumn NContractColumn  = new TableColumn("№ договора");
        NContractColumn.setCellValueFactory(new PropertyValueFactory("NContract"));

        TableColumn dateContColumn   = new TableColumn("Дата договора");
        dateContColumn.setCellValueFactory(new PropertyValueFactory("dateContract"));
        dateContColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());

        TableColumn finishBuildColumn= new TableColumn("Окончание строительства");
        finishBuildColumn.setCellValueFactory(new PropertyValueFactory("finishBuilding"));

        TableColumn smetCostColumn   = new TableColumn("Сметная стоимость");
        smetCostColumn.setCellValueFactory(new PropertyValueFactory("smetCost"));

        TableColumn costHouseColumn  = new TableColumn("Стоимость дома");
        costHouseColumn.setCellValueFactory(new PropertyValueFactory("costHouse"));

        TableColumn saleHouseColumn  = new TableColumn("Цена продажи");
        saleHouseColumn.setCellValueFactory(new PropertyValueFactory("SaleHouse"));

        TableColumn trueCostColumn   = new TableColumn("TRUECOST");
        trueCostColumn.setCellValueFactory(new PropertyValueFactory("trueCost"));

        TableColumn profitColumn     = new TableColumn("Прибыль");
        profitColumn.setCellValueFactory(new PropertyValueFactory("profit"));


        finishBuildColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
        trueCostColumn.setCellFactory(cell -> TableCellFactory.getDecimalCell());

        table.getColumns().setAll(
                siteNumberColumn,
                contractorColumn,
                NContractColumn,
                dateContColumn ,
                finishBuildColumn,
                saleHouseColumn,
                smetCostColumn,
                costHouseColumn,
                trueCostColumn,
                profitColumn
        );

        return table;
    }
    
    

    
    
//Methods ==============================================================================================
//    private static void  setTextFieldCell_FloatStringConverter(TableColumn ... columns){
//        for(TableColumn column  : columns)
//            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Float>(){
//
//                // chage Float to string in Value Column
//                @Override
//                public String toString(Float object) {
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
//                public String toString(Double object) {
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
    private static  void  setTextFieldCell_NumbertringConverter(TableColumn ... columns){
        for(TableColumn column  : columns)
            column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>(){

                // chage Float to string in Value Column
                @Override
                public String toString(Number object) {
                    return DecimalFormatter.toString(object);
//                    return new DecimalFormat("0.00").format(object);
                }

                @Override
                public Number fromString(String string) {
//                    Number num = new Double(0);
//                    try {
//                      num =  new DecimalFormat("0.00").parse(string).doubleValue();
//                    } catch (ParseException ex) {
//                        Logger.getLogger(TableFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    return DecimalFormatter.stringToDouble(string);
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
