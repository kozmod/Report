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

    public final static String FUNDAMENT = "ФУНДАМЕНТ";
    private final static String STENI = "СТЕНЫ, ПЕРЕКРЫТИЯ";
    private final static String KROWLIA = "КРОВЛЯ";
    private final static String PROEMI = "ПРОЕМЫ";
    private final static String OTDELKA = "ОТДЕЛОЧНЫЕ РАБОТЫ";

    @Autowired
    private EstimateDao estimateDao;

    private SumColumnTableTitledStackModel newPriceSumTitledPane(String title, DocumentType docType) {
        final PriceSumTableWrapper<EstimateTVI> priceSumTableWrapper = newPriseSumTable(docType);
        priceSumTableWrapper.tableView().setId(title);

        if (Objects.equals(docType, DocumentType.BASE)) {
            priceSumTableWrapper.getTableColumnByName("Кол-во")
                    .ifPresent(quantityColumn ->
                            quantityColumn.setOnEditCommit(tableColumn -> {

                                AbstractEstimateTVI editingAbstractEstimateTVI = tableColumn.getTableView()
                                        .getItems()
                                        .get(tableColumn.getTablePosition().getRow());

                                Double price_one = editingAbstractEstimateTVI.getPriceOne();
                                Double value = (Double) tableColumn.getNewValue();

                                editingAbstractEstimateTVI.setQuantity(value);
                                editingAbstractEstimateTVI.setPriceSum(value * price_one);

                                priceSumTableWrapper.computeProperty();
                                tableColumn.getTableView().refresh();
                            })
                    );
        } else if (Objects.equals(docType, DocumentType.CHANGED)) {
            priceSumTableWrapper.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldSelection, newSelection) -> priceSumTableWrapper.getTableColumnByName("Стоимость (за единицу)")
                                    .ifPresent(periceOneColumn ->
                                                    periceOneColumn.setOnEditCommit(tableColumn -> {

                                                        if (Objects.nonNull(newSelection) && newSelection.isInKS()) {
                                                            priceSumTableWrapper.setEditable(false);
                                                            periceOneColumn.setEditable(false);
                                                        } else if (Objects.nonNull(newSelection) && !newSelection.isInKS()) {
                                                            priceSumTableWrapper.setEditable(true);
                                                            periceOneColumn.setEditable(true);
//
                                                        }
                                                    })
                                    )
                    );
        }
        return new SumColumnTableTitledStackModel(title, priceSumTableWrapper);
    }

//    public Map<String,TableColumn<EstimateTVI,?>> tuneEstimateTable(TableView<EstimateTVI> table){
//        final Map<String,TableColumn<EstimateTVI,?>>  columnMap = new LinkedHashMap<>();
//
//        TableColumn<EstimateTVI, String> jobMaterialNameColumn = addColumn(table, "Наименование работ/затрат", column -> column.getValue().JM_nameProperty());
//        TableColumn<EstimateTVI, String> bindJobColumn = addColumn(table,"Связанная работа", column -> column.getValue().bindJobProperty());
//        TableColumn<EstimateTVI, Double> quantityColumn = addColumn(table,"Кол-во", column -> column.getValue().quantityProperty().asObject());
//        TableColumn<EstimateTVI, String> unitColumn = addColumn(table,"Eд. изм.", column -> column.getValue().unitProperty());
//        TableColumn<EstimateTVI, Double> periceOneColumn = addColumn(table,"Стоимость (за единицу)", column -> column.getValue().priceOneProperty().asObject());
//        TableColumn<EstimateTVI, Double> priceOneSumColumn = addColumn(table,"Стоимость (общая)", column -> column.getValue().priceSumProperty().asObject());
//        TableColumn<EstimateTVI, Boolean> isInKSColumn = addColumn(table,"КС", "inKS");
//
//        jobMaterialNameColumn.setMinWidth(300);
//        bindJobColumn.setMinWidth(160);
//
//        quantityColumn.setEditable(true);
//        periceOneColumn.setEditable(true);
//
//        isInKSColumn.setCellFactory(param -> TableCellFactory.getInKsColoredCell());
//
//
//        return columnMap;
//    }


    private PriceSumTableWrapper<EstimateTVI> newPriseSumTable(DocumentType docType) {
        final PriceSumTableWrapper<EstimateTVI> tableWrapper = new PriceSumTableWrapper<>(new TableView<>(), estimateDao);

        tableWrapper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<EstimateTVI, String> jobMaterialNameColumn = tableWrapper.addColumn("Наименование работ/затрат", column -> column.getValue().JM_nameProperty());
        TableColumn<EstimateTVI, String> bindJobColumn = tableWrapper.addColumn("Связанная работа", column -> column.getValue().bindJobProperty());
        TableColumn<EstimateTVI, Double> quantityColumn = tableWrapper.addColumn("Кол-во", column -> column.getValue().quantityProperty().asObject());
        TableColumn<EstimateTVI, String> unitColumn = tableWrapper.addColumn("Eд. изм.", column -> column.getValue().unitProperty());
        TableColumn<EstimateTVI, Double> periceOneColumn = tableWrapper.addColumn("Стоимость (за единицу)", column -> column.getValue().priceOneProperty().asObject());
        TableColumn<EstimateTVI, Double> priceOneSumColumn = tableWrapper.addColumn("Стоимость (общая)", column -> column.getValue().priceSumProperty().asObject());
        TableColumn<EstimateTVI, Boolean> isInKSColumn = tableWrapper.addColumn("КС", "inKS");

        jobMaterialNameColumn.setMinWidth(300);
        bindJobColumn.setMinWidth(160);

        quantityColumn.setEditable(true);
        periceOneColumn.setEditable(true);

        isInKSColumn.setCellFactory(param -> TableCellFactory.getInKsColoredCell());
//        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(enumEst));//todo

        if (docType.equals(DocumentType.BASE)) {
            quantityColumn.setOnEditCommit(tableColumn -> {

                AbstractEstimateTVI editingAbstractEstimateTVI = tableColumn.getTableView()
                        .getItems()
                        .get(tableColumn.getTablePosition().getRow());

                Double price_one = editingAbstractEstimateTVI.getPriceOne();
                Double value = tableColumn.getNewValue();

                editingAbstractEstimateTVI.setQuantity(value);
                editingAbstractEstimateTVI.setPriceSum(value * price_one);

                tableWrapper.computeProperty();
                tableColumn.getTableView().refresh();
            });
        } else if (docType.equals(DocumentType.CHANGED)) {
            tableWrapper.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null && newSelection.isInKS()) {
                    tableWrapper.setEditable(false);
                    periceOneColumn.setEditable(false);
                } else if (newSelection != null && !((EstimateTVI) newSelection).isInKS()) {
                    tableWrapper.setEditable(true);
                    periceOneColumn.setEditable(true);
//                    System.out.println(((EstimateTVI)newSelection).getInKS());
                }

            });
        }
        periceOneColumn.setOnEditCommit(tableColumn -> {
            AbstractEstimateTVI editingAbstractEstimateTVI = tableColumn.getTableView()
                    .getItems()
                    .get(tableColumn.getTablePosition().getRow());
            Double price_one = tableColumn.getNewValue();
            Double value = editingAbstractEstimateTVI.getQuantity();

            editingAbstractEstimateTVI.setPriceOne(price_one);
            editingAbstractEstimateTVI.setPriceSum(value * price_one);

            tableWrapper.computeProperty();
            tableColumn.getTableView().refresh();
        });


        TableFactory.setTextFieldTableCell(
                new DoubleStringConverter(),
                quantityColumn,
                periceOneColumn,
                priceOneSumColumn
        );


//        switch (enumEst) {
//            case Base:
//                table.setContextMenu(ContextMenuFactory.getEst(table));
//                break;
//            case Changed:
//                table.setContextMenu(ContextMenuFactory.getEstPrint(enumEst));
//                break;
//        }
        return tableWrapper;
    }


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
