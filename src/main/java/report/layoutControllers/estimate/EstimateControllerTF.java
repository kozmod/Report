package report.layoutControllers.estimate;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.KS.ItemKSDAO;
import report.entities.items.KS.TableItemKS;
import report.entities.items.TableItem;
import report.entities.items.estimate.ItemEstDAO;
import report.entities.items.estimate.TableItemEst;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes.TableWrapperEST;
import report.models_view.nodes_factories.ContextMenuFactory;
import report.models_view.nodes_factories.TableCellFactory;
import report.models_view.nodes_factories.TableFactory;

import java.util.List;

public class EstimateControllerTF implements TableFactory {

   private EstimateControllerTF(){

   }


    /**
     * Create TableWrapper(TableWrapper EST).Contain columns and their options.
     * @see EstimateController.Est - enumeration of "Estimate Tables"
     * @param enumEst - enumeration. Contain: data of Estimate Tables
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST getEst(EstimateController.Est enumEst,String title){
        TableWrapperEST table = new TableWrapperEST(title,new TableView(),new ItemEstDAO(enumEst));


        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
//                TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);
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
//        TableFactory.setTextFieldCell_NumberStringConverter(Price_oneColumn);
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

//        TableFactory.setTextFieldCell_NumberStringConverter(Price_sumColumn);

        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueColumn,
                Price_oneColumn
        );
        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                Price_sumColumn
        );

        switch(enumEst){
            case Base    : table.setContextMenu(ContextMenuFactory.getEst(table));         break;
            case Changed : table.setContextMenu(ContextMenuFactory.getEstPrint(enumEst));   break;
        }

        return table;
    }


    /**
     * Create TableWrapper(TableWrapper Additional).Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapper decorAdditional(TableView table){
        TableWrapper tableWrapper =  new TableWrapper(table, null);

        tableWrapper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
     * Create TableWrapper(TableWrapper KS).Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST<TableItemKS> decorKS(TableView tableView){
        TableWrapperEST table = new TableWrapperEST(tableView,
                new ItemKSDAO(EstimateController.Est.KS));


        table.setEditable(true);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn       = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn         = table.addColumn("Связанная работа",          "BindedJob");
        TableColumn BPartColumns        = table.addColumn("Часть",                     "bildingPart");
        TableColumn valueColumn         = table.addColumn("Кол-во",                    "value");
        TableColumn unitColumn          = table.addColumn("Eд. изм.",                  "unit");
        TableColumn Price_oneColumn     = table.addColumn("Стоимость (за единицу)",    "price_one");
        TableColumn Price_sumColumn     = table.addColumn("Стоимость (общая)",         "price_sum");
        TableColumn restPriceSumColumn  = table.addColumn("Остаток (общий)",           "restOfValue");

        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(EstimateController.Est.KS));
        valueColumn.setEditable(true);

//        TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);

        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueColumn
        );

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends TableItem, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<? extends TableItem, Double> t) {

                TableItemKS editingItem = (TableItemKS) t.getRowValue();

                Double price_one = editingItem.getPrice_one();

                //Value of editing item in Chaged Tables
                Double valueInChanged = EstimateController.Est.Changed.findEqualsElevent(editingItem).getValue();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (EstimateController.Est.KS.getTabMap().values()
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

}
