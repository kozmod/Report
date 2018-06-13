package report.layout.controllers.estimate.new_estimate.service;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsDao;
import report.entities.items.KS.KS_TIV;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.layout.controllers.estimate.EstimateController_old;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.EstimateDocumentType;
import report.models.view.customNodes.newNode.SumColumnTableTitledStackModel;
import report.models.view.customNodes.newNode.SumVboxModel;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.models.view.wrappers.table.TableWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("Duplicates")
public class EstimateControllerNodeFactory {

    public final static String FUNDAMENT = "ФУНДАМЕНТ";
    private final static String STENI = "СТЕНЫ, ПЕРЕКРЫТИЯ";
    private final static String KROWLIA = "КРОВЛЯ";
    private final static String PROEMI = "ПРОЕМЫ";
    private final static String OTDELKA = "ОТДЕЛОЧНЫЕ РАБОТЫ";

    @Autowired
    private EstimateDao estimateDao;

    public Tab newTab(String title, Node content) {
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(content);
        return tab;
    }


    @SuppressWarnings("unchecked")
    public SumVboxModel newEstimateVboxModel(EstimateDocumentType docType,EstimateData estimateData) {
        SumColumnTableTitledStackModel fundament = newPriceSumTitledPane(FUNDAMENT, docType);
        fundament.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,FUNDAMENT));

        SumColumnTableTitledStackModel steni = newPriceSumTitledPane(STENI, docType);
        steni.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,STENI));

        SumColumnTableTitledStackModel krowlia = newPriceSumTitledPane(KROWLIA, docType);
        krowlia.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,KROWLIA));

        SumColumnTableTitledStackModel proemi = newPriceSumTitledPane(PROEMI, docType);
        proemi.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,PROEMI));

        SumColumnTableTitledStackModel otdelka = newPriceSumTitledPane(OTDELKA, docType);
        otdelka.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,OTDELKA));

        List<SumColumnTableTitledStackModel> titledStackModelList = Arrays.asList(fundament,steni,krowlia,proemi,otdelka);
        titledStackModelList.forEach(SumColumnTableTitledStackModel::computeSum);

        return new SumVboxModel(titledStackModelList);
    }


    private SumColumnTableTitledStackModel newPriceSumTitledPane(String title, EstimateDocumentType docType) {
        final PriceSumTableWrapper<EstimateTVI> priceSumTableWrapper = newPriseSumTable(docType);
        priceSumTableWrapper.tableView().setId(title);

        if (Objects.equals(docType, EstimateDocumentType.BASE)) {
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
        } else if (Objects.equals(docType, EstimateDocumentType.CHANGED)) {
            priceSumTableWrapper.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldSelection, newSelection) -> priceSumTableWrapper.getTableColumnByName("Стоимость (за единицу)")
                                    .ifPresent(periceOneColumn ->
                                                    periceOneColumn.setOnEditCommit(tableColumn -> {

                                                        if (Objects.nonNull(newSelection) && newSelection.getInKS()) {
                                                            priceSumTableWrapper.setEditable(false);
                                                            periceOneColumn.setEditable(false);
                                                        } else if (Objects.nonNull(newSelection) && !newSelection.getInKS()) {
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

    private PriceSumTableWrapper<EstimateTVI> newPriseSumTable(EstimateDocumentType docType) {
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

        if (docType.equals(EstimateDocumentType.BASE)) {
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
        } else if (docType.equals(EstimateDocumentType.CHANGED)) {
            tableWrapper.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null && newSelection.getInKS()) {
                    tableWrapper.setEditable(false);
                    periceOneColumn.setEditable(false);
                } else if (newSelection != null && !((EstimateTVI) newSelection).getInKS()) {
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
    public PriceSumTableWrapper<KS_TIV> thuneKs(TableView<KS_TIV> tableView) {
        PriceSumTableWrapper<KS_TIV> table = new PriceSumTableWrapper(tableView, new KsDao(EstimateController_old.Est.KS));

        table.setEditable(true);
        table.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<KS_TIV, String> JM_nameColumn = table.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = table.addColumn("Связанная работа", "bindJob");
        TableColumn BPartColumns = table.addColumn("Часть", "buildingPart");
        TableColumn<KS_TIV, Double> valueColumn = table.addColumn("Кол-во", "quantity");
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

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<KS_TIV, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<KS_TIV, Double> t) {

                KS_TIV editingItem = t.getRowValue();

                Double price_one = editingItem.getPriceOne();

                //Value of editing item in Changed Tables
                Double valueInChanged = EstimateController_old.Est.Changed.findEqualsElement(editingItem).getQuantity();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (EstimateController_old.Est.KS.getTabMap().values()
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

                table.computeProperty();
                t.getTableView().refresh();

            }
        });

        ContextMenu contextMenuKS = ContextMenuFactory.getCommonSU(table);
        table.setContextMenu(contextMenuKS);
        return table;
    }

}
