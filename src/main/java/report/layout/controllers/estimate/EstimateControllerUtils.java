package report.layout.controllers.estimate;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KS_DAO;
import report.entities.items.KS.KS_TIV;
import report.entities.items.estimate.EstimateDAO;
import report.entities.items.estimate.EstimateTVI;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.wrappers.table.TableWrapperEST;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;

import java.util.List;

public class EstimateControllerUtils implements TableFactory {

    private EstimateControllerUtils() {

    }


    /**
     * Create TableWrapper(TableWrapper EST).Contain columns and their options.
     *
     * @param enumEst - enumeration. Contain: data of Estimate Tables
     * @return TableWrapper(child of TableView)
     * @see EstimateController.Est - enumeration of "Estimate Tables"
     */
    public static TableWrapperEST getEst(EstimateController.Est enumEst, String title) {
        TableWrapperEST table = new TableWrapperEST(title, new TableView(), new EstimateDAO(enumEst));


        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = table.addColumn("Связанная работа", "bindJob");
        TableColumn valueColumn = table.addColumn("Кол-во", "quantity");
        TableColumn unitColumn = table.addColumn("Eд. изм.", "unit");
        TableColumn Price_oneColumn = table.addColumn("Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = table.addColumn("Стоимость (общая)", "priceSum");
        TableColumn isInKSColumn = table.addColumn("КС", "inKS");


        JM_nameColumn.setMinWidth(300);
        BJobColumnn.setMinWidth(160);

        BJobColumnn.setCellFactory(param -> TableCellFactory.getOnDoubleMouseClickMoveToTextCell());


        valueColumn.setEditable(true);
        Price_oneColumn.setEditable(true);
//
        isInKSColumn.setCellFactory(param -> TableCellFactory.getInKsColoredCell());
        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(enumEst));

        switch (enumEst) {
            case Base:
//                TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);
                valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double> t) {

                        AbstractEstimateTVI editingAbstractEstimateTVI = (AbstractEstimateTVI) t.getTableView().getItems().get(t.getTablePosition().getRow());

                        Double price_one = editingAbstractEstimateTVI.getPriceOne();
                        Double value = t.getNewValue();

                        editingAbstractEstimateTVI.setQuantity(value);
                        editingAbstractEstimateTVI.setPriceSum(value * price_one);

                        table.computeSum();
                        t.getTableView().refresh();
                        //Diseble Save & Cancel Context menu AbstractEstimateTVI
//                    ((ContextMenuModelEst)table.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);

                    }
                });
                break;
            case Changed:
                table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null && ((EstimateTVI) newSelection).getInKS()) {
                        table.setEditable(false);
                        Price_oneColumn.setEditable(false);
//                    System.out.println(((EstimateTVI)newSelection).getInKS());
                    } else if (newSelection != null && !((EstimateTVI) newSelection).getInKS()) {
                        table.setEditable(true);
                        Price_oneColumn.setEditable(true);
//                    System.out.println(((EstimateTVI)newSelection).getInKS());
                    }

                });
                break;
        }
//        TableFactory.setTextFieldCell_NumberStringConverter(Price_oneColumn);
        Price_oneColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AbstractEstimateTVI, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AbstractEstimateTVI, Double> t) {
                AbstractEstimateTVI editingAbstractEstimateTVI = (AbstractEstimateTVI) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Double price_one = t.getNewValue();
                Double value = editingAbstractEstimateTVI.getQuantity();

                editingAbstractEstimateTVI.setPriceOne(price_one);
                editingAbstractEstimateTVI.setPriceSum(value * price_one);

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

        switch (enumEst) {
            case Base:
                table.setContextMenu(ContextMenuFactory.getEst(table));
                break;
            case Changed:
                table.setContextMenu(ContextMenuFactory.getEstPrint(enumEst));
                break;
        }

        return table;
    }


    /**
     * Create TableWrapper(TableWrapper Additional).Contain columns and their options.
     *
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapper decorAdditional(TableView table) {
        TableWrapper tableWrapper = new TableWrapper(table, null);

        tableWrapper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = tableWrapper.addColumn("Связанная работа", "bindJob");
        TableColumn valueColumn = tableWrapper.addColumn("Кол-во", "quantity");
        TableColumn unitColumn = tableWrapper.addColumn("Eд. изм.", "unit");
        TableColumn Price_oneColumn = tableWrapper.addColumn("Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = tableWrapper.addColumn("Стоимость (общая)", "priceSum");
        TableColumn isInKSColumn = tableWrapper.addColumn("КС", "inKS");

        JM_nameColumn.setMinWidth(300);
        BJobColumnn.setMinWidth(160);

        return tableWrapper;
    }

    /**
     * Create TableWrapper(TableWrapper KS).Contain columns and their options.
     *
     * @return TableWrapper(child of TableView)
     */
    public static TableWrapperEST<KS_TIV> decorKS(TableView tableView) {
        TableWrapperEST table = new TableWrapperEST(tableView,
                new KS_DAO(EstimateController.Est.KS));


        table.setEditable(true);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = table.addColumn("Связанная работа", "bindJob");
        TableColumn BPartColumns = table.addColumn("Часть", "buildingPart");
        TableColumn valueColumn = table.addColumn("Кол-во", "quantity");
        TableColumn unitColumn = table.addColumn("Eд. изм.", "unit");
        TableColumn Price_oneColumn = table.addColumn("Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = table.addColumn("Стоимость (общая)", "priceSum");
        TableColumn restPriceSumColumn = table.addColumn("Остаток (общий)", "restOfValue");

        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(EstimateController.Est.KS));
        valueColumn.setEditable(true);

//        TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);

        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueColumn
        );

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double> t) {

                KS_TIV editingItem = (KS_TIV) t.getRowValue();

                Double price_one = editingItem.getPriceOne();

                //Value of editing item in Chaged Tables
                Double valueInChanged = EstimateController.Est.Changed.findEqualsElement(editingItem).getQuantity();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (EstimateController.Est.KS.getTabMap().values()
                                .stream()
                                .flatMap(mapItem -> ((List) mapItem).stream())
                                .filter(editingItem::equalsSuperClass)
                                .mapToDouble(filtered -> ((AbstractEstimateTVI) filtered).getQuantity())
                                .sum()
                                - t.getOldValue()
                                + t.getNewValue());

                //set Value
                editingItem.setQuantity(t.getNewValue());

                //count new Price Sum
                editingItem.setPriceSum(t.getNewValue() * price_one);

//                Set new Rest of Value
                editingItem.setRestOfValue(restOfValue);

                table.computeSum();
                t.getTableView().refresh();

            }
        });

        ContextMenu contextMenuKS = ContextMenuFactory.getCommonSU(table);
        table.setContextMenu(contextMenuKS);
//
//        Est.KS.getTabMap().values().forEach(new Consumer<ObservableList<AbstractEstimateTVI>>() {
//            @Override
//            public void accept(ObservableList<AbstractEstimateTVI> coll) {
//                coll.addListener((ListChangeListener.Change<? extends AbstractEstimateTVI> c) -> {
//                    System.out.println("Changed on " + c);
//                    if(c.next() &&
//                            (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
//                        ((ContextMenuOptional) contexMenuKS).setDisable_SaveUndoPrint_groupe(false);
////                               contexMenuKS.setDisable_SaveUndoPrint_groupe(false);
//                    }
//                });
//            }
//        });
        return table;
    }

}
