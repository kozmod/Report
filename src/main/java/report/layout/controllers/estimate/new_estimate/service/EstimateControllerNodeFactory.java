package report.layout.controllers.estimate.new_estimate.service;


import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.entities.items.KS.KsTIV;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.layout.controllers.estimate.EstimateController_old;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.models.view.customNodes.newNode.SumColumnTableTitledStackModel;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.models.view.wrappers.table.TableWrapper;

import java.util.List;
import java.util.Objects;

import static report.spring.utils.FxTableUtils.addColumn;

@SuppressWarnings("Duplicates")
public class EstimateControllerNodeFactory {


    @Autowired
    private EstimateDao estimateDao;


    //todo: припелить
    public TableWrapper tuneAdditionalTable(TableView table) {
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

    //todo: припелить
    public PriceSumTableWrapper<KsTIV> thuneKs(TableView<KsTIV> tableView) {
        PriceSumTableWrapper<KsTIV> table = new PriceSumTableWrapper(tableView, new KsDao(EstimateController_old.Est.KS));

        table.setEditable(true);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<KsTIV, String> JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = table.addColumn("Связанная работа", "bindJob");
        TableColumn BPartColumns = table.addColumn("Часть", "buildingPart");
        TableColumn<KsTIV, Double> valueColumn = table.addColumn("Кол-во", "quantity");
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

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<KsTIV, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<KsTIV, Double> t) {

                KsTIV editingItem = t.getRowValue();

                Double price_one = editingItem.getPriceOne();

                //Value of editing item in Changed Tables
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
