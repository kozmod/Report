package report.layout.controllers.estimate;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.entities.items.KS.KsTIV;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;

import java.util.List;

public abstract class EstimateControllerNodeUtils implements TableFactory {

    /**
     * Create TableWrapper(TableWrapper EST).Contain columns and their options.
     *
     * @param enumEst - enumeration. Contain: data of Estimate Tables
     * @return TableWrapper(child of TableView)
     * @see EstimateController_old.Est - enumeration of "Estimate Tables"
     */
    public static PriceSumTableWrapper getEst(EstimateController_old.Est enumEst, String title) {
        PriceSumTableWrapper table = new PriceSumTableWrapper(title, new TableView(), new EstimateDao(enumEst));


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

                        table.computeProperty();
                        t.getTableView().refresh();
                    }
                });
                break;
            case Changed:
                table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null && ((EstimateTVI) newSelection).isInKS()) {
                        table.setEditable(false);
                        Price_oneColumn.setEditable(false);
//                    System.out.println(((EstimateTVI)newSelection).getInKS());
                    } else if (newSelection != null && !((EstimateTVI) newSelection).isInKS()) {
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

                table.computeProperty();
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
    static TableWrapper decorAdditional(TableView table) {
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
    static PriceSumTableWrapper<KsTIV> decorKS(TableView<KsTIV> tableView) {
        PriceSumTableWrapper table = new PriceSumTableWrapper(tableView, new KsDao(EstimateController_old.Est.KS));

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

        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(EstimateController_old.Est.KS));
        valueColumn.setEditable(true);

//        TableFactory.setTextFieldCell_NumberStringConverter(valueColumn);

        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                valueColumn
        );

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<? extends AbstractEstimateTVI, Double> t) {

                KsTIV editingItem = (KsTIV) t.getRowValue();

                Double price_one = editingItem.getPriceOne();

                //Value of editing item in Chaged Tables
                Double valueInChanged = EstimateController_old.Est.Changed.findEqualsElement(editingItem).getQuantity();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (EstimateController_old.Est.KS.getTabMap().values()
                                .stream()
                                .flatMap(mapItem -> ((List) mapItem).stream())
                                .filter(editingItem::businessKeyEquals)
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

                table.computeProperty();
                t.getTableView().refresh();

            }
        });

        ContextMenu contextMenuKS = ContextMenuFactory.getCommonSU(table);
        table.setContextMenu(contextMenuKS);
        return table;
    }


}
